package com.sudoku.modelo;

/**
 * Interfaz que define las validaciones necesarias para un juego de Sudoku.
 * Se utiliza para verificar si un número puede colocarse en una fila, columna,
 * subregión o posición específica del tablero sin violar las reglas del juego
 */
public interface ValidacionSudoku {

    /**
     * Válida si un valor puede colocarse en una fila específica del tablero.
     *
     * @param fila  La fila donde se desea colocar el valor (0 a 5).
     * @param valor El número que se desea colocar (1 a 6).
     * @return true si el valor no está repetido en la fila, false en caso contrario
     */
    boolean validarFila(int fila, int valor);

    /**
     * Válida si un valor puede colocarse en una columna específica del tablero
     *
     * @param columna La columna donde se desea colocar el valor
     * @param valor   El número que se desea colocar
     * @return true si el valor no está repetido en la columna, false en caso contrario
     */
    boolean validarColumna(int columna, int valor);

    /**
     * Válida si un valor puede colocarse en la subregión correspondiente a una posición
     *
     * @param fila    La fila de la celda
     * @param columna La columna de la celda
     * @param valor   El número que se desea colocar
     * @return true si el valor no está repetido en la subregión, false en caso contrario
     */
    boolean validarSubregion(int fila, int columna, int valor);

    /**
     * Válida si un movimiento es válido teniendo en cuenta la fila, columna y subregión
     *
     * @param fila    La fila de la celda
     * @param columna La columna de la celda
     * @param valor   El número que se desea colocar
     * @return true si el movimiento cumple con todas las reglas del Sudoku, false en caso contrario
     */
    boolean validarMovimiento(int fila, int columna, int valor);
}
