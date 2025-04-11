package com.sudoku.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnJugar, btnReglas, btnCreditos, btnSalir;

    @FXML
    private void irAJugar() throws IOException {
        cargarVista("/com/sudoku/vista/game-view.fxml", "Sudoku 6x6");
    }

    @FXML
    private void verReglas() throws IOException {
        cargarVista("/com/sudoku/vista/rules-view.fxml", "¿Cómo jugar?");
    }

    @FXML
    private void verCreditos() throws IOException {
        cargarVista("/com/sudoku/vista/credits-view.fxml", "Créditos");
    }

    @FXML
    private void salirDelJuego() {
        System.exit(0);
    }

    private void cargarVista(String rutaFXML, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
        Parent root = loader.load();
        Stage stage = (Stage) btnJugar.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
    }
}
