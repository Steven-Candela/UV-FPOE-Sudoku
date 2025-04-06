module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sudoku to javafx.fxml;
    exports com.sudoku;

    exports com.sudoku.controlador;
    opens com.sudoku.controlador to javafx.fxml;
}