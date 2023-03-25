import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import javax.swing.*;

public class SudokuController {
    final int SIZE = 9;
    @FXML
    private GridPane grid;
    private TextField[][] board;
    public void initialize() {
        board = new TextField[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = new TextField();
                board[i][j].setPrefSize(30, 25);
                board[i][j].setMaxWidth(30);
                board[i][j].setBackground(null);
                grid.add(board[i][j], j, i);
                int finalI = i;
                int finalJ = j;
                board[i][j].setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if (event.getCode() == KeyCode.ENTER) {
                            String digit = board[finalI][finalJ].getText();
                            validateInput(digit);
                        }
                    }
                });
            }
        }
    }
    private void validateInput(String num) {
        if(!isSingleDigitNumber(num)) {
            board[0][0].setText("");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please use single digit numbers only!");
            alert.showAndWait();
        }
    }

    @FXML
    void clearBtnHandler(ActionEvent event) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j].setText("");
            }
        }
    }
    public static boolean isSingleDigitNumber(String input) {
        if ( input.length() != 1) {
            return input.equals("");
        }
        return input.matches("[0-9]");
    }

    @FXML
    void setBtnHandler(ActionEvent event) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String num = board[i][j].getText();
                validateInput(num);
            }
        }
    }

}
