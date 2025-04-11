package com.sudoku.controlador;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.sudoku.modelo.Sudoku;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class GameController {
    @FXML
    private GridPane gridPrincipal;

    @FXML
    private Button btnPista;

    @FXML
    private Label labelPista;

    @FXML
    private Label labelCronometro;

    @FXML
    private Button btnVolverMenu;

    @FXML
    private Label LabelErrores;

    private int segundos = 0;
    private Timeline cronometro;
    private int errores = 0;

    private ManejadorPista manejadorPista;
    private final TextField[][] celdas = new TextField[6][6];
    private Sudoku sudoku;

    private void iniciarCronometro() {
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundos++;
            int min = segundos / 60;
            int seg = segundos % 60;
            labelCronometro.setText(String.format("%02d:%02d", min, seg));
        }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    @FXML
    private void volverAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sudoku/vista/menu-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
    }

    @FXML
    public void initialize() {
        sudoku = new Sudoku();
        cargarCeldas();
        llenarTablero();
        manejadorPista = new ManejadorPista(sudoku, celdas, labelPista);

        btnPista.setOnAction(e -> manejadorPista.darPista());
        iniciarCronometro();
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
                    celda.setEditable(false);
                    sudoku.setValor(fila, columna, valor);

                    celda.setStyle("-fx-background-color: #9aff84;");
                    PauseTransition pausa = new PauseTransition(Duration.seconds(1));
                    pausa.setOnFinished(event -> celda.setStyle(""));
                    pausa.play();

                    if (tableroCompleto()) {
                        detenerJuego();
                        mostrarAlertaVictoria();
                    }
                } else {
                    celda.setStyle("-fx-background-color: #ff8484;");
                    errores++;
                    LabelErrores.setText("Errores: " + errores + "/3");
                    System.out.println("Error por parte del usuario: " + errores + " errores totales");

                    if (errores >= 3) {
                        detenerJuego();
                        mostrarAlertaDerrota();
                    }
                }
            } else {
                celda.setStyle("");
                celda.clear();
            }
        });
    }

    private boolean tableroCompleto() {
        int[][] tablero = sudoku.getTablero();
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (tablero[fila][columna] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void mostrarAlertaDerrota() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Límite de errores");
        alerta.setHeaderText(null);
        alerta.setContentText("Has cometido muchos errores, has perdido...");
        alerta.showAndWait();
    }

    private void mostrarAlertaVictoria() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¡Victoria!");
        alerta.setHeaderText(null);
        alerta.setContentText("¡Felicidades! Completaste has ganado.");
        alerta.showAndWait();
    }

    private void detenerJuego() {
        if (cronometro != null) {
            cronometro.stop();
        }

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                if (celdas[fila][columna] != null) {
                    celdas[fila][columna].setEditable(false);
                }
            }
        }

        btnPista.setDisable(true);
    }
}
