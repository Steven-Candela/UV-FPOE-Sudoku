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
 * Controlador para la vista de reglas del juego
 *
 * Esta clase se encarga de manejar los eventos de la pantalla
 * donde se explican las reglas del Sudoku
 */
public class ReglasController {

    /**
     * Este método se ejecuta cuando el jugador hace clic en el botón de "Volver al menú"
     *
     * Usa el objeto "ActionEvent" para obtener la ventana actual y reemplazar la escena
     * con la del menú principal
     *
     * @param event el evento que ocurre al presionar el botón
     * @throws IOException si hay un error al cargar el archivo FXML del menú
     */
    @FXML
    public void volverAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sudoku/vista/menu-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
    }
}
