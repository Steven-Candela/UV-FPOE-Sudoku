package com.sudoku.modelo;

import java.util.*;

public class Sudoku implements ValidacionSudoku {
    private final int[][] tablero;
    private static final int SIZE = 6;
    private static final Random RANDOM = new Random();

    /**
     * Constructor de la clase Sudoku.
     *
     * Inicializa el tablero de juego como una matriz vacía de tamaño 6x6
     * y genera un nuevo tablero válido automáticamente llamando al método generarTablero()
     */
    public Sudoku() {
        tablero = new int[SIZE][SIZE];
        generarTablero();
    }

    /**
     * Intenta llenar el tablero de Sudoku recursivamente usando backtracking
     *
     * El método prueba colocar números aleatorios del 1 al 6 en la posición actual del tablero,
     * validando que cada número cumpla las reglas del Sudoku
     * Si logra completar todo el tablero, devuelve true, si no encuentra solución válida,
     * realiza retroceso (backtracking) y prueba otras combinaciones.
     *
     * @param fila    La fila actual en el tablero.
     * @param columna La columna actual en el tablero.
     * @return true si el tablero se pudo llenar completamente con una solución válida, false en caso contrario.
     */
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
                tablero[fila][columna] = 0;
            }
        }
        return false;
    }

    /**
     * Genera un tablero de Sudoku completamente y lo modifica eliminando ciertos números
     */
    private void generarTablero() {
        do {
            Arrays.stream(tablero).forEach(row -> Arrays.fill(row, 0));
        } while (!llenarSudoku(0, 0));
        eliminarNumeros();
    }

    /**
     * Elimina 12 numeros del tablero Sudoku de forma aleatoria,
     * asegurando que el tablero tenga una solución
     *
     * Este método selecciona posiciones aleatorias en el tablero y
     * elimina el valor que contenga
     */
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

    /**
     * Verifica si el tablero actual tiene una única solución válida.
     *
     * Este método crea una copia del tablero actual para no modificar el original
     * y luego utiliza un algoritmo de backtracking para contar cuántas soluciones válidas existen.
     *
     * @return es true si el tablero tiene exactamente una solución, false en caso contrario.
     */
    private boolean esSolucionUnica() {
        int[][] copia = Arrays.stream(tablero).map(int[]::clone).toArray(int[][]::new);
        return contarSoluciones(copia, 0, 0) == 1;
    }

    /**
     * Cuenta la cantidad de soluciones posibles para un tablero Sudoku dado,
     * comenzando desde una posición específica. Utiliza backtracking para probar
     * combinaciones válidas.
     *
     * Este método se detiene anticipadamente si encuentra más de una solución,
     * lo que permite verificar si el tablero tiene una solución única.
     *
     * @param copia Matriz de enteros que representa una copia del tablero Sudoku actual.
     * @param fila Fila actual desde donde se empieza la verificación.
     * @param columna Columna actual desde donde se empieza la verificación.
     * @return El número de soluciones encontradas (máximo 2; si hay más de una, retorna >1).
     */
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
                if (soluciones > 1) return soluciones;
                copia[fila][columna] = 0;
            }
        }
        return soluciones;
    }

    /**
     * Verifica si un valor específico no está presente en una fila del tablero.
     *
     * @param fila  la fila del tablero que se desea validar (índice de 0 a 5).
     * @param valor el número que se desea comprobar (debe estar entre 1 y 6).
     * @return {@code true} si el valor no está presente en la fila, {@code false} si ya existe.
     */
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

    /**
     * Valida que un valor no esté repetido en una columna del tablero.
     *
     * @param columna la columna a validar (índice de 0 a 5).
     * @param valor el valor que se desea comprobar (de 1 a 6).
     * @return {@code true} si el valor no está presente en la columna; {@code false} si ya existe.
     */
    @Override
    public boolean validarMovimiento(int fila, int columna, int valor) {
        return validarFila(fila, valor) && validarColumna(columna, valor) && validarSubregion(fila, columna, valor);
    }

    /**
     * Verifica si el juego de Sudoku ha sido completado.
     *
     * Recorre todas las celdas del tablero y comprueba que no haya ninguna con el valor 0,
     * lo cual indica que están todas las celdas llenas.
     *
     * @return {@code true} si el tablero está completamente lleno (sin ceros), {@code false} en caso contrario.
     */
    public boolean esJuegoTerminado() {
        return Arrays.stream(tablero).flatMapToInt(Arrays::stream).noneMatch(num -> num == 0);
    }

    /**
     * Establece un valor en la posición indicada del tablero si el movimiento es válido.
     *
     * @param fila   La fila donde se desea colocar el valor (0 a 5).
     * @param columna La columna donde se desea colocar el valor (0 a 5).
     * @param valor   El valor que se desea colocar (1 a 6).
     */
    public void setValor(int fila, int columna, int valor) {
        if (validarMovimiento(fila, columna, valor)) tablero[fila][columna] = valor;
    }

    /**
     * Obtiene el valor almacenado en una celda específica del tablero de Sudoku.
     *
     * @param fila    la fila de la celda (índice de 0 a 5)
     * @param columna la columna de la celda (índice de 0 a 5)
     * @return el valor entero en la celda indicada (0 si está vacía)
     */
    public int getValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    /**
     * Obtiene el tablero actual del Sudoku.
     *
     * @return una matriz de enteros de 6x6 que representa el estado actual del tablero.
     *         Los valores válidos son del 1 al 6, y el valor 0 indica una celda vacía.
     */
    public int[][] getTablero() {
        return tablero;
    }
}
