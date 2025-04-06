package com.sudoku.controlador;

import com.sudoku.modelo.Sudoku;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.*;

public class ManejadorPista {
    private Sudoku sudoku;
    private TextField[][] celdas;
    private Set<String> celdasConPista;
    private int pistasUsadas = 0;
    private static final int MAX_PISTAS = 3;
    private Label labelPista;


    /**
     * Constructor de la clase ManejadorPista
     * Se encarga de inicializar las pistas del juego, incluyendo
     * la referencia al tablero, las celdas y el label que muestra
     * el número de pistas restantes
     *
     * @param sudoku El objeto Sudoku que contiene la lógica del juego
     * @param celdas La matriz de TextField que representa las celdas del tablero
     * @param labelPista La etiqueta donde se mostrará la cantidad de pistas restantes
     */
    public ManejadorPista(Sudoku sudoku, TextField[][] celdas, Label labelPista) {
        this.sudoku = sudoku;
        this.celdas = celdas;
        this.celdasConPista = new HashSet<>();
        this.labelPista = labelPista;
        actualizarLabel();
    }

    /**
     * Da una pista al jugador colocando un número correcto en una celda vacía
     *
     * - Verifica que no se haya excedido el número máximo de pistas permitidas
     * - Busca una celda vacía que aún no haya recibido una pista
     * - Selecciona una celda aleatoriamente y obtiene su valor correcto
     * - Rellena la celda con el número correcto, la hace no editable
     *   y actualiza la cantidad de pistas usadas
     *
     * Si ya se alcanzó el límite de pistas, muestra una alerta
     */
    public void darPista() {
        if (pistasUsadas >= MAX_PISTAS) {
            mostrarAlerta();
            return;
        }

        List<int[]> celdasDisponibles = new ArrayList<>();
        int[][] tablero = sudoku.getTablero();

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                String coordenada = fila + "," + columna;
                if (tablero[fila][columna] == 0 && !celdasConPista.contains(coordenada)) {
                    celdasDisponibles.add(new int[]{fila, columna});
                }
            }
        }

        Random random = new Random();
        int[] seleccionada = celdasDisponibles.get(random.nextInt(celdasDisponibles.size()));
        int fila = seleccionada[0];
        int columna = seleccionada[1];

        int solucion = encontrarSolucion(fila, columna);
        if (solucion != -1) {
            celdas[fila][columna].setText(String.valueOf(solucion));
            celdas[fila][columna].setEditable(false);
            celdasConPista.add(fila + "," + columna);

            pistasUsadas++;
            actualizarLabel();
        }
    }

    /**
     * Intenta encontrar un número válido del 1 al 6 que se pueda colocar en la celda especificada,
     * cumpliendo con las reglas del Sudoku
     *
     * @param fila    la fila de la celda a evaluar.
     * @param columna la columna de la celda a evaluar.
     * @return un número válido entre 1 y 6 que se puede colocar en esa posición,
     *         o 0 si no hay ningún número válido disponible.
     */
    private int encontrarSolucion(int fila, int columna) {
        for (int num = 1; num <= 6; num++) {
            if (sudoku.validarFila(fila, num) && sudoku.validarColumna(columna, num) && sudoku.validarSubregion(fila, columna, num)) {
                return num;
            }
        }
        return 0;
    }

    /**
     * Muestra una alerta al jugador indicando que
     * ha alcanzado el límite de pistas disponibles
     *
     * La alerta tiene un título y un mensaje
     * Se detiene la ejecución hasta que el jugador cierre la alerta
     */
    private void mostrarAlerta() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Límite de Pistas");
        alerta.setHeaderText(null);
        alerta.setContentText("Ya has usado todas tus pistas.");
        alerta.showAndWait();
    }

    private void actualizarLabel() {
        labelPista.setText("Pistas: " + pistasUsadas + "/3");
    }
}
