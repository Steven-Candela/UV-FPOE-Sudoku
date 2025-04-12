package com.sudoku.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Controlador para la vista de Créditos del juego Sudoku
 *
 * Esta clase maneja los eventos que ocurren en la pantalla de créditos,
 * como volver al menú principal
 */
public class CreditosController {

    /**
     * Este método se ejecuta cuando el usuario hace clic en el botón para volver al menú
     *
     * Carga el archivo FXML del menú principal y cambia la escena actual por la del menú
     *
     * @param event El evento que se genera al hacer clic en el botón
     * @throws IOException Si ocurre un error al cargar el archivo FXML del menú
     */
    @FXML
    public void volverAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sudoku/vista/menu-view.fxml"));
        Parent root = loader.load();

        // Obtiene la ventana actual usando el botón que generó el evento
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
    }
}
