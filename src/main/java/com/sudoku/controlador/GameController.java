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

    /**
     * Método que se ejecuta automáticamente al inicializar la vista del juego.
     *
     * - Crea una nueva instancia del juego
     * - Carga los TextFields del tablero en una matriz
     * - Llena el tablero con valores aleatoriamente
     * - Inicializa el manejador de pistas
     * - Configura el botón de pista para que dé una pista al hacer clic
     */
    @FXML
    public void initialize() {
        sudoku = new Sudoku();
        cargarCeldas();
        llenarTablero();
        manejadorPista = new ManejadorPista(sudoku, celdas, labelPista);

        btnPista.setOnAction(e -> manejadorPista.darPista());
    }

    /**
     * Asocia cada textField del tablero con matriz TextField[][],
     * usando su ID en formato "celdaXY", donde X es la fila y Y la columna
     * Configura los eventos de teclado para cada celda.
     */
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

    /**
     * Llena el tablero del Sudoku con los valores que iniciara el juego
     *
     * Este método recorre la matriz del tablero obtenida desde el modelo `sudoku`
     * y asigna los valores entre 1 y 6 las celdas del tablero
     *
     *  se marcan como no editables para evitar que
     * el usuario las modifique.
     */
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

    /**
     * Configura los eventos del teclado para una celda específica del tablero
     * Este metodo asigna un evento que se ejecuta cuando el jugador ingresa un valor en una celda, este
     * verifica si el valor que se ingresó es un número entre 1 y 6 y válida si el movimiento es válido
     *
     * @param celda    El TextField correspondiente a una celda del tablero.
     * @param fila     La fila de la celda dentro del tablero.
     * @param columna  La columna de la celda dentro del tablero.
    */
    private void configurarEventos(TextField celda, int fila, int columna) {
        celda.setOnKeyReleased(e -> {
            String texto = celda.getText().trim();
            if (texto.matches("[1-6]")) {
                int valor = Integer.parseInt(texto);
                if (sudoku.validarMovimiento(fila, columna, valor)) {
                    sudoku.setValor(fila, columna, valor);
                }
            } else {
                celda.clear();
            }
        });
    }
}