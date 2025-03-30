package com.sudoku.controlador;

import javafx.fxml.FXML;
import com.sudoku.modelo.Sudoku;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    private GridPane gridPrincipal;

    @FXML
    private Button btnPista;

    @FXML
    private Label labelPista;

    private ManejadorPista manejadorPista;

    private final TextField[][] celdas = new TextField[6][6];
    private Sudoku sudoku;

    @FXML
    public void initialize() {
        sudoku = new Sudoku();
        cargarCeldas();
        llenarTablero();
        manejadorPista = new ManejadorPista(sudoku, celdas, labelPista);

        btnPista.setOnAction(e -> manejadorPista.darPista());
    }

    private void cargarCeldas() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                TextField celda = (TextField) gridPrincipal.lookup("#celda" + fila + columna);
                if (celda != null) {
                    celdas[fila][columna] = celda;
                    configurarEventos(celda, fila, columna);
                }
            }
        }
    }

    private void llenarTablero() {
        int[][] tablero = sudoku.getTablero();
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                int valor = tablero[fila][columna];
                if (valor != 0) {
                    TextField celda = celdas[fila][columna];
                    celda.setText(String.valueOf(valor));
                    celda.setEditable(false);
                }
            }
        }
    }

    private void configurarEventos(TextField celda, int fila, int columna) {
        celda.setOnKeyReleased(e -> {
            String texto = celda.getText().trim();
            if (texto.matches("[1-6]")) {
                int valor = Integer.parseInt(texto);
                if (sudoku.validarMovimiento(fila, columna, valor)) {
                    sudoku.setValor(fila, columna, valor);
                } else {
                    celda.clear();
                }
            } else {
                celda.clear();
            }
        });
    }
}