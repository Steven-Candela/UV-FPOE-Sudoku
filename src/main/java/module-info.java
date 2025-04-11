module com.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sudoku.controlador to javafx.fxml;

    exports com.sudoku;
}
