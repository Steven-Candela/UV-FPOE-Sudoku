package com.sudoku.modelo;

import java.util.*;

/**
 * Clase que representa el modelo de un Sudoku 6x6
 * Implementa la interfaz ValidacionSudoku para validar filas, columnas y subregiones
 */
public class Sudoku implements ValidacionSudoku {
    private final int[][] tablero;
    private static final int SIZE = 6;
    private static final Random RANDOM = new Random();
    public int cuatrosRestantes;

    public int getCuatrosRestantes(){
        return cuatrosRestantes;
    }

    public void setCuatrosRestantes(int cuatrosRestantes) {
        this.cuatrosRestantes = cuatrosRestantes;
    }

    /**
     * Constructor que inicializa el tablero de Sudoku y lo genera
     */
    public Sudoku() {
        tablero = new int[SIZE][SIZE];
        generarTablero();
    }

    /**
     * Intenta llenar el tablero de Sudoku recursivamente
     * Se asegura de que los números sean válidos en cada celda
     *
     * @param fila La fila actual que se está llenando
     * @param columna La columna actual que se está llenando
     * @return true si se ha completado el tablero, false si no es posible completar
     */
    private boolean llenarSudoku(int fila, int columna) {
        if (fila == SIZE) return true; // Si hemos llegado al final del tablero, se ha llenado correctamente

        // Determina la siguiente fila y columna a llenar
        int siguienteFila = (columna == SIZE - 1) ? fila + 1 : fila;
        int siguienteColumna = (columna == SIZE - 1) ? 0 : columna + 1;

        // Lista de números del 1 al 6 (para el tablero 6x6)
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6);
        Collections.shuffle(numeros); // Mezcla la lista para probar los números aleatoriamente

        // Intenta colocar un número en la celda actual
        for (int num : numeros) {
            if (validarMovimiento(fila, columna, num)) { // Si el número es válido
                tablero[fila][columna] = num; // Coloca el número
                if (llenarSudoku(siguienteFila, siguienteColumna)) return true; // Llama recursivamente para llenar el siguiente
                tablero[fila][columna] = 0; // Si no funciona, resetea la celda
            }
        }
        return false; // Si no se pudo llenar, retorna false
    }

    /**
     * Genera un tablero de Sudoku completo y lo prepara para ser jugado
     * Llama a este método para generar un tablero nuevo
     */
    private void generarTablero() {
        // Llena el tablero con ceros y luego intenta llenarlo correctamente
        do {
            Arrays.stream(tablero).forEach(row -> Arrays.fill(row, 0));
        } while (!llenarSudoku(0, 0)); // Llama a llenarSudoku para generar un tablero
        eliminarNumeros(); // Elimina algunos números para crear el Sudoku jugable
    }

    /**
     * Elimina números del tablero para crear un Sudoku con casillas vacías
     * Garantiza que el Sudoku tenga una única solución
     */
    private void eliminarNumeros() {
        int celdasAEliminar = 12; // Número de celdas a eliminar
        while (celdasAEliminar > 0) {
            int fila = RANDOM.nextInt(SIZE); // Escoge una fila aleatoria
            int columna = RANDOM.nextInt(SIZE); // Escoge una columna aleatoria

            if (tablero[fila][columna] != 0) { // Si la celda no está vacía
                int respaldo = tablero[fila][columna];// Guarda el valor
                tablero[fila][columna] = 0; // Elimina el número

                // Verifica si la solución sigue siendo única
                if (!esSolucionUnica()) {
                    tablero[fila][columna] = respaldo; // Restaura el número si no es único
                } else {
                    if (respaldo == 4) {
                        cuatrosRestantes++;
                        System.out.println(cuatrosRestantes);
                    }
                    celdasAEliminar--; // Reduce el contador de celdas a eliminar
                }
            }
        }
    }

    /**
     * Verifica si el tablero tiene una única solución
     * Esto es importante para asegurarse de que el Sudoku tenga una única forma de resolverse
     *
     * @return true si la solución es única, false si hay múltiples soluciones
     */
    private boolean esSolucionUnica() {
        int[][] copia = Arrays.stream(tablero).map(int[]::clone).toArray(int[][]::new);
        return contarSoluciones(copia, 0, 0) == 1;
    }

    /**
     * Cuenta el número de soluciones posibles para un tablero dado
     * Se usa una técnica de búsqueda recursiva para encontrar todas las soluciones
     *
     * @param copia El tablero a resolver
     * @param fila La fila actual en el tablero
     * @param columna La columna actual en el tablero
     * @return El número de soluciones encontradas
     */
    private int contarSoluciones(int[][] copia, int fila, int columna) {
        if (fila == SIZE) return 1; // Si hemos llegado al final del tablero, hemos encontrado una solución

        int siguienteFila = (columna == SIZE - 1) ? fila + 1 : fila;
        int siguienteColumna = (columna == SIZE - 1) ? 0 : columna + 1;

        if (copia[fila][columna] != 0) return contarSoluciones(copia, siguienteFila, siguienteColumna); // Si la celda ya está llena, pasa a la siguiente

        int soluciones = 0;
        for (int num = 1; num <= SIZE; num++) { // Intenta colocar un número en la celda
            if (validarMovimiento(fila, columna, num)) { // Si el número es válido
                copia[fila][columna] = num; // Coloca el número en la celda
                soluciones += contarSoluciones(copia, siguienteFila, siguienteColumna); // Llama recursivamente para la siguiente celda
                if (soluciones > 1) return soluciones; // Si encontramos más de una solución, retorna inmediatamente
                copia[fila][columna] = 0; // Resetea la celda si no funciona
            }
        }
        return soluciones; // Retorna el número total de soluciones encontradas
    }

    /**
     * Válida que no haya repetido numeros en la fila del tablero
     *
     * @param fila La fila que se está verificando
     * @param valor El valor que se quiere colocar
     * @return true si el valor no está repetido en la fila, false si ya existe
     */
    @Override
    public boolean validarFila(int fila, int valor) {
        return Arrays.stream(tablero[fila]).noneMatch(num -> num == valor); // Verifica que no haya duplicados en la fila
    }

    /**
     * Válida que no haya repetido numeros en la columna del tablero
     *
     * @param columna La columna que se está verificando
     * @param valor El valor que se quiere colocar
     * @return true si el valor no está repetido en la columna, false si ya existe
     */
    @Override
    public boolean validarColumna(int columna, int valor) {
        return Arrays.stream(tablero).noneMatch(row -> row[columna] == valor); // Verifica que no haya duplicados en la columna
    }

    /**
     * Válida que no haya repetido números en la subregión 3x2 en la que se encuentra la celda
     *
     * @param fila La fila de la celda
     * @param columna La columna de la celda
     * @param valor El valor que se quiere colocar
     * @return true si el valor no está repetido en la subregión, false si ya existe
     */
    @Override
    public boolean validarSubregion(int fila, int columna, int valor) {
        int subgridRow = (fila / 3) * 3; // Fila inicial de la subregión
        int subgridCol = (columna / 2) * 2; // Columna inicial de la subregión
        for (int i = 0; i < 3; i++) { // Itera sobre las filas de la subregión
            for (int j = 0; j < 2; j++) { // Itera sobre las columnas de la subregión
                if (tablero[subgridRow + i][subgridCol + j] == valor) return false;// Si el valor ya está en la subregión, retorna false
            }
        }
        return true; // Si no se encuentra el valor, retorna true
    }

    /**
     * Válida que un movimiento sea válido en la fila, columna y subregión
     *
     * @param fila La fila de la celda
     * @param columna La columna de la celda
     * @param valor El valor que se quiere colocar
     * @return true si el movimiento es válido, false si no lo es
     */
    @Override
    public boolean validarMovimiento(int fila, int columna, int valor) {
        return validarFila(fila, valor) && validarColumna(columna, valor) && validarSubregion(fila, columna, valor);
    }

    /**
     * Establece un valor en una celda del tablero si el movimiento es válido
     *
     * @param fila La fila de la celda
     * @param columna La columna de la celda
     * @param valor El valor a colocar en la celda
     */
    public void setValor(int fila, int columna, int valor) {
        if (validarMovimiento(fila, columna, valor)) tablero[fila][columna] = valor;
        if (valor == 4 ) {
            cuatrosRestantes--;
        }
    }

    /**
     * Obtiene el tablero de Sudoku actual
     *
     * @return El tablero de Sudoku como una matriz de enteros
     */
    public int[][] getTablero() {
        return tablero;
    }
}

