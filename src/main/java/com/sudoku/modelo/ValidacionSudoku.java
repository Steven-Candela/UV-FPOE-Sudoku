package com.sudoku.modelo;

public interface ValidacionSudoku {
    boolean validarFila(int fila, int valor);
    boolean validarColumna(int columna, int valor);
    boolean validarSubregion(int fila, int columna, int valor);
    boolean validarMovimiento(int fila, int columna, int valor);
}
