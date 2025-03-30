package com.sudoku.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameController {
    @FXML
    private GridPane gridPrincipal;

    @FXML
    private TextField[][] celdas = new TextField[6][6];

    @FXML
    public void initialize() {
        cargarCeldas();
    }

    private void cargarCeldas() {
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                String celdaId = "celda" + fila + columna;
                TextField celda = (TextField) gridPrincipal.lookup("#" + celdaId);
                if (celda != null) {
                    celdas[fila][columna] = celda;
                    configurarEventos(celda);
                }
            }
        }
    }

    private void configurarEventos(TextField celda) {
        celda.setOnKeyReleased(e -> {
            String texto = celda.getText();
            if (!texto.matches("[1-6]")) {
                celda.setText("");
            }
        });
    }
}