import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import java.util.HashSet;
import java.util.Set;

import static com.sun.prism.paint.Color.BLACK;
import static javafx.scene.paint.Color.YELLOW;

/**
 * The SudokuController class handles the logic for the FXML view. It contains methods for initializing the Sudoku board,
 * resetting input if invalid, validating the Sudoku board, and handling button events.
 */
public class SudokuController {
    private static final int BLOCK_SIZE = 3;
    private static final int GRID_SIZE = 9;
    private static final int FONT_SIZE = 20;
    @FXML
    private GridPane grid;
    private TextField[][] sudokuBoard;
    /**
     * Checks if the given input is a single-digit number.
     * @param input the input to check
     * @return true if the input is a single-digit number, false otherwise.
     */
    private static boolean isSingleDigitNumber(String input) {
        if (input.length() != 1) {
            return input.equals("");
        }
        return input.matches("[0-9]");
    }
    /**
     * Initializes the Sudoku board UI.
     */
    public void initialize() {
        sudokuBoard = new TextField[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                sudokuBoard[i][j] = new TextField();
                sudokuBoard[i][j].setPrefSize(40, 40);
                sudokuBoard[i][j].setMaxWidth(40);
                sudokuBoard[i][j].setBackground(null);
                sudokuBoard[i][j].setAlignment(javafx.geometry.Pos.CENTER);
                sudokuBoard[i][j].setStyle("-fx-font-size:" +FONT_SIZE+"px;");
                grid.add(sudokuBoard[i][j], j, i);
                int finalI = i;
                int finalJ = j;
                sudokuBoard[i][j].setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        String digit = sudokuBoard[finalI][finalJ].getText();
                        if (!isSingleDigitNumber(digit) || !isValidSudoku()) {
                            resetInput(finalI, finalJ);
                        }
                    }
                });
            }
        }
    }
    /**
     * Resets the input value of the text field at the given index if the input is invalid.
     * @param i the index of the row
     * @param j the index of the column
     */
    private void resetInput(int i, int j) {
        sudokuBoard[i][j].setText("");
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Input!");
        alert.showAndWait();
    }
    /**
     * Clears the input values from the Sudoku board.
     * @param event the button event
     */

    @FXML
    void clearBtnHandler(ActionEvent event) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                sudokuBoard[i][j].setText("");
            }
        }
    }
    /**
     * Validates the current input values on the Sudoku board.
     * @return true if the Sudoku board is valid, false otherwise
     */
    public boolean isValidSudoku() {
        Set<String> seen = new HashSet<>();
        boolean isCompleted = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String digit = sudokuBoard[i][j].getText();
                if (!digit.equals("")) {
                    if (!seen.add(digit + " in row " + i) ||
                            !seen.add(digit + " in column " + j) ||
                            !seen.add(digit + " in block " + i / BLOCK_SIZE + "-" + j / BLOCK_SIZE)) {
                        return false;
                    }
                } else {
                    isCompleted = false;
                }
            }
        }
            if (isCompleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! You solved the puzzle!");
                alert.showAndWait();
                setBtnHandler(new ActionEvent());
            }
            return true;
        }
    /**
     * Called when the "Set" button is pressed. Disables all valid and invalid input fields.
     * @param event the button event
     */
        @FXML
        void setBtnHandler (ActionEvent event){
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    String digit = sudokuBoard[i][j].getText();
                    if (!isSingleDigitNumber(digit) || !isValidSudoku()) {
                        resetInput(i, j);
                    } else if (!digit.equals("")) {
                        sudokuBoard[i][j].setDisable(true);
                    }
                }
            }
        }

    }
