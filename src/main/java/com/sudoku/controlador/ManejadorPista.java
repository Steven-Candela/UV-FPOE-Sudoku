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

    private Runnable verificadorVictoria;

    /**
     * Constructor del ManejadorPista
     *
     * @param sudoku el objeto Sudoku con el tablero y métodos de validación
     * @param celdas la matriz de TextFields que representa la interfaz del tablero
     * @param labelPista el Label donde se muestra la cantidad de pistas usadas
     */
    public ManejadorPista(Sudoku sudoku, TextField[][] celdas, Label labelPista, Runnable verificadorVictoria) {
        this.sudoku = sudoku;
        this.celdas = celdas;
        this.celdasConPista = new HashSet<>();
        this.labelPista = labelPista;
        this.verificadorVictoria = verificadorVictoria;
        actualizarLabel();
    }

    /**
     * Da una pista al jugador, coloca un número correcto en una celda vacía
     * Solo se permite hasta un máximo de 3 pistas
     * Si se supera el límite, se muestra una alerta
     */
    public void darPista() {
        if (pistasUsadas >= MAX_PISTAS) {
            mostrarAlerta();
            return;
        }

        List<int[]> celdasDisponibles = new ArrayList<>();
        int[][] tablero = sudoku.getTablero();

        // Buscar todas las celdas vacías que aún no tienen pista.
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                String coordenada = fila + "," + columna;
                if (tablero[fila][columna] == 0 && !celdasConPista.contains(coordenada)) {
                    celdasDisponibles.add(new int[]{fila, columna});
                }
            }
        }

        // Elegir una celda vacía al azar para dar la pista.
        Random random = new Random();
        int[] seleccionada = celdasDisponibles.get(random.nextInt(celdasDisponibles.size()));
        int fila = seleccionada[0];
        int columna = seleccionada[1];

        // Obtener un número válido para esa celda.
        int solucion = encontrarSolucion(fila, columna);
        if (solucion != -1) {
            celdas[fila][columna].setText(String.valueOf(solucion));
            celdas[fila][columna].setEditable(false);
            celdasConPista.add(fila + "," + columna);

            sudoku.setValor(fila, columna, solucion); // Actualiza el tablero lógico
            celdasConPista.add(fila + "," + columna);

            pistasUsadas++;
            actualizarLabel();

            // Verificar si el tablero ya está completo
            if (verificadorVictoria != null) {
                verificadorVictoria.run();
            }
        }
    }

    /**
     * Encuentra un número válido para colocar en la celda indicada
     *
     * @param fila la fila de la celda
     * @param columna la columna de la celda
     * @return un número válido entre 1 y 6 o 0 si no encuentra ninguno
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
     * Muestra una alerta al usuario indicando que ya se usó todas las pistas disponibles
     */
    private void mostrarAlerta() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Límite de Pistas");
        alerta.setHeaderText(null);
        alerta.setContentText("Ya has usado todas tus pistas.");
        alerta.showAndWait();
    }

    /**
     * Actualiza el texto del Label que muestra cuántas pistas se han usado
     */
    private void actualizarLabel() {
        labelPista.setText("Pistas: " + pistasUsadas + "/3");
    }
}
