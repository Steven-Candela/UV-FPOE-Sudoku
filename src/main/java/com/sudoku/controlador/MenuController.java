package com.sudoku.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    /**
     * Método que se ejecuta cuando el jugador hace clic en el botón "Jugar"
     * Cambia la vista actual y carga el tablero del Sudoku
     *
     * @param event El evento generado por hacer clic en el botón
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void irAJugar(ActionEvent event) throws IOException {
        cargarVista("/com/sudoku/vista/game-view.fxml", "Sudoku 6x6", event);
    }

    /**
     * Método que se ejecuta cuando el jugador hace clic en el botón "Reglas"
     * Cambia la vista actual y muestra las reglas del juego
     *
     * @param event El evento generado por hacer clic en el botón
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void verReglas(ActionEvent event) throws IOException {
        cargarVista("/com/sudoku/vista/rules-view.fxml", "¿Cómo jugar?", event);
    }

    /**
     * Método que se ejecuta cuando el jugador hace clic en el botón "Créditos"
     * Cambia la vista actual y muestra los créditos del juego
     *
     * @param event El evento generado por hacer clic en el botón
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    @FXML
    private void verCreditos(ActionEvent event) throws IOException {
        cargarVista("/com/sudoku/vista/credits-view.fxml", "Créditos", event);
    }

    /**
     * Método que se ejecuta cuando el jugador hace clic en el botón "Salir"
     * Cierra completamente la aplicación
     */
    @FXML
    private void salirDelJuego() {
        System.exit(0);
    }

    /**
     * Método privado que permite cambiar la escena actual por otra
     * Se usa para ir a jugar, ver reglas o ver créditos
     *
     * @param rutaFXML La ruta del archivo FXML que se quiere cargar
     * @param titulo   El título que tendrá la ventana
     * @param event    El evento que contiene información sobre el botón presionado
     * @throws IOException Si ocurre un error al cargar el archivo FXML
     */
    private void cargarVista(String rutaFXML, String titulo, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
    }
}
