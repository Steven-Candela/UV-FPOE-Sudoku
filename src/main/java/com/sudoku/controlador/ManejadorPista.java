package com.sudoku.controlador;

import com.sudoku.modelo.Sudoku;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.*;

public class ManejadorPista {
    private Sudoku sudoku;
    private TextField[][] celdas;
    private Set<String> celdasConPista; // Guarda las coordenadas de las pistas dadas
    private int pistasUsadas = 0; // Contador de pistas usadas
    private static final int MAX_PISTAS = 3; // Límite de pistas
    private Label labelPista; // Etiqueta para mostrar las pistas usadas

    public ManejadorPista(Sudoku sudoku, TextField[][] celdas, Label labelPista) {
        this.sudoku = sudoku;
        this.celdas = celdas;
        this.celdasConPista = new HashSet<>();
        this.labelPista = labelPista;
        actualizarLabel(); // Mostrar el contador al inicio
    }

    public void darPista() {
        if (pistasUsadas >= MAX_PISTAS) {
            mostrarAlerta();
            return;
        }

        List<int[]> celdasDisponibles = new ArrayList<>();
        int[][] tablero = sudoku.getTablero();

        // Buscar todas las celdas vacías que no han sido usadas para pista
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                String coordenada = fila + "," + columna;
                if (tablero[fila][columna] == 0 && !celdasConPista.contains(coordenada)) {
                    celdasDisponibles.add(new int[]{fila, columna});
                }
            }
        }

        if (celdasDisponibles.isEmpty()) {
            System.out.println("No hay más pistas disponibles.");
            return;
        }

        // Seleccionar una celda aleatoria de las disponibles
        Random random = new Random();
        int[] seleccionada = celdasDisponibles.get(random.nextInt(celdasDisponibles.size()));
        int fila = seleccionada[0];
        int columna = seleccionada[1];

        int solucion = encontrarSolucion(fila, columna);
        if (solucion != -1) {
            celdas[fila][columna].setText(String.valueOf(solucion));
            celdas[fila][columna].setEditable(false);
            celdasConPista.add(fila + "," + columna);

            pistasUsadas++; // Aumentar el contador
            actualizarLabel(); // Actualizar la etiqueta
            System.out.println("Pista agregada en coordenada: (" + fila + ", " + columna + ") con el número: " + solucion);
        } else {
            System.out.println("No se encontró una solución válida para la celda seleccionada.");
        }
    }

    private int encontrarSolucion(int fila, int columna) {
        for (int num = 1; num <= 6; num++) {
            if (sudoku.validarFila(fila, num) && sudoku.validarColumna(columna, num) && sudoku.validarSubregion(fila, columna, num)) {
                return num; // Devuelve el número válido encontrado
            }
        }
        return -1; // No se encontró solución válida
    }

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
