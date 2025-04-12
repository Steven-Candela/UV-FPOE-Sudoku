package com.sudoku.modelo;

/**
 * Interfaz que define los métodos necesarios para validar los movimientos en un Sudoku
 * Esta interfaz permite validar filas, columnas, subregiones y movimientos generales
 * en un tablero de Sudoku
 */
public interface ValidacionSudoku {

    /**
     * Valida si el valor propuesto es válido en la fila especificada
     * @param fila El número de la fila que se quiere validar
     * @param valor El valor que se quiere poner en la fila
     * @return true si el valor es válido para la fila, false si no lo es
     */
    boolean validarFila(int fila, int valor);

    /**
     * Valida si el valor propuesto es válido en la columna especificada
     * @param columna El número de la columna que se quiere validar
     * @param valor El valor que se quiere poner en la columna
     * @return true si el valor es válido para la columna, false si no lo es
     */
    boolean validarColumna(int columna, int valor);

    /**
     * Valida si el valor propuesto es válido en la subregión especificada
     * Las subregiones son áreas dentro del tablero de Sudoku
     * @param fila El número de la fila en la que se encuentra la celda a validar
     * @param columna El número de la columna en la que se encuentra la celda a validar
     * @param valor El valor que se quiere poner en la subregión
     * @return true si el valor es válido para la subregión, false si no lo es
     */
    boolean validarSubregion(int fila, int columna, int valor);

    /**
     * Valida si un movimiento propuesto es válido. Este método debe combinar la validación
     * de fila, columna y subregión
     * @param fila El número de la fila donde se desea hacer el movimiento
     * @param columna El número de la columna donde se desea hacer el movimiento
     * @param valor El valor que se quiere poner en la celda
     * @return true si el movimiento es válido, false si no lo es
     */
    boolean validarMovimiento(int fila, int columna, int valor);
}
