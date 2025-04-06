package com.sudoku.modelo;

import java.util.*;

public class Sudoku implements ValidacionSudoku {
    private final int[][] tablero;
    private static final int SIZE = 6;
    private static final Random RANDOM = new Random();

    public Sudoku() {
        tablero = new int[SIZE][SIZE];
        generarTablero();
    }

    private boolean llenarSudoku(int fila, int columna) {
        if (fila == SIZE) return true; // Tablero completo

        int siguienteFila = (columna == SIZE - 1) ? fila + 1 : fila;
        int siguienteColumna = (columna == SIZE - 1) ? 0 : columna + 1;

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collections.shuffle(numeros);

        for (int num : numeros) {
            if (validarMovimiento(fila, columna, num)) {
                tablero[fila][columna] = num;
                if (llenarSudoku(siguienteFila, siguienteColumna)) return true;
                tablero[fila][columna] = 0; // Backtracking
            }
        }
        return false;
    }

    private void generarTablero() {
        do {
            Arrays.stream(tablero).forEach(row -> Arrays.fill(row, 0)); // Reiniciar tablero
        } while (!llenarSudoku(0, 0));
        eliminarNumeros();
    }

    private void eliminarNumeros() {
        int celdasAEliminar = 12;
        while (celdasAEliminar > 0) {
            int fila = RANDOM.nextInt(SIZE);
            int columna = RANDOM.nextInt(SIZE);

            if (tablero[fila][columna] != 0) {
                int respaldo = tablero[fila][columna];
                tablero[fila][columna] = 0;

                if (!esSolucionUnica()) {
                    tablero[fila][columna] = respaldo; // Restaurar si pierde unicidad
                } else {
                    celdasAEliminar--;
                }
            }
        }
    }

    private boolean esSolucionUnica() {
        int[][] copia = Arrays.stream(tablero).map(int[]::clone).toArray(int[][]::new);
        return contarSoluciones(copia, 0, 0) == 1;
    }

    private int contarSoluciones(int[][] copia, int fila, int columna) {
        if (fila == SIZE) return 1;

        int siguienteFila = (columna == SIZE - 1) ? fila + 1 : fila;
        int siguienteColumna = (columna == SIZE - 1) ? 0 : columna + 1;

        if (copia[fila][columna] != 0) return contarSoluciones(copia, siguienteFila, siguienteColumna);

        int soluciones = 0;
        for (int num = 1; num <= SIZE; num++) {
            if (validarMovimiento(fila, columna, num)) {
                copia[fila][columna] = num;
                soluciones += contarSoluciones(copia, siguienteFila, siguienteColumna);
                if (soluciones > 1) return soluciones; // Si hay más de una solución, detener
                copia[fila][columna] = 0;
            }
        }
        return soluciones;
    }

    @Override
    public boolean validarFila(int fila, int valor) {
        return Arrays.stream(tablero[fila]).noneMatch(num -> num == valor);
    }

    @Override
    public boolean validarColumna(int columna, int valor) {
        return Arrays.stream(tablero).noneMatch(row -> row[columna] == valor);
    }

    @Override
    public boolean validarSubregion(int fila, int columna, int valor) {
        int subgridRow = (fila / 3) * 3;
        int subgridCol = (columna / 2) * 2;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (tablero[subgridRow + i][subgridCol + j] == valor) return false;
            }
        }
        return true;
    }

    @Override
    public boolean validarMovimiento(int fila, int columna, int valor) {
        return validarFila(fila, valor) && validarColumna(columna, valor) && validarSubregion(fila, columna, valor);
    }

    public boolean esJuegoTerminado() {
        return Arrays.stream(tablero).flatMapToInt(Arrays::stream).noneMatch(num -> num == 0);
    }

    public void setValor(int fila, int columna, int valor) {
        if (validarMovimiento(fila, columna, valor)) tablero[fila][columna] = valor;
    }

    public int getValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    public int[][] getTablero() {
        return tablero;
    }
}
