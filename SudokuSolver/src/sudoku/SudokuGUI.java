package sudoku;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuGUI extends Application {
	private SudokuBrain sudoku;
	private OneLetterTextField[][] matrixTextField;

	@Override
	public void start(Stage stage) throws Exception {
		matrixTextField = new OneLetterTextField[9][9];
		sudoku = new SudokuBrain();
		loadUI(stage);

	}

	// building the UI
	private void loadUI(Stage stage) {
		// Components we need for the UI
		BorderPane root = new BorderPane();
		TilePane matrix = new TilePane();
		HBox bottomBox = new HBox();

		Button solveButton = new Button("Solve");
		Button clearButton = new Button("Clear");

		bottomBox.setPadding(new Insets(5, 5, 10, 5));
		bottomBox.setSpacing(10); //Distance between buttons
		bottomBox.getChildren().addAll(solveButton, clearButton);

		BorderPane.setMargin(matrix, new Insets(4));
		root.setPrefSize(458, 500);
		root.setCenter(matrix);
		root.setBottom(bottomBox);

		matrix.setPrefColumns(9);
		matrix.setPrefRows(9);

		// Building the sudoku 9x9 matrix
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				OneLetterTextField newCell = new OneLetterTextField();

				newCell.setFont(Font.font("Verdana", FontWeight.NORMAL, 26));

				// Set color of the textfield square to orange if it's in the correct position
				if ((row < 3 && col < 3) || (row > 5 && col < 3) || (row > 5 && col > 5) || (row < 3 && col > 5)
						|| (row > 2 && row < 6 && col > 2 && col < 6)) {
					newCell.setStyle("-fx-control-inner-background: #EC7425;");
				}
				matrixTextField[row][col] = newCell;

				newCell.setPrefHeight(50);
				newCell.setPrefWidth(50);
				newCell.setAlignment(Pos.CENTER);

				matrix.getChildren().add(newCell);
			}

		}
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setTitle("Sudoku Solver");
		stage.setScene(scene);
		stage.show();

		// When you press the solve button
		solveButton.setOnAction(event -> {
			solve();
		});

		// When you press the clear button
		clearButton.setOnAction(event -> {
			clear(true);
		});

	}

	private void solve() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				// Check if there's a number in the square.
				if (matrixTextField[row][col].getLength() == 1) {
					// add all the values to the matrix that we will use in the SudokuBrain class
					sudoku.add(row, col, Integer.valueOf(matrixTextField[row][col].getText()));
				}
			}
		}
		// Check before trying to solve. Return false if colliding numbers.
		// Solve the sudoku. Return true if solvable.

		if (sudoku.checkBeforeSolve() && sudoku.solve()) {
			int[][] newSudokuNumbers = sudoku.getSudokuMatrix();
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					matrixTextField[row][col].setText(String.valueOf(newSudokuNumbers[row][col]));
				}
			}
		}
		// Impossible to solve the sudoku.
		else {
			// Clear all values, so you can change values if wrong without pressing Clear
			unsolvableMessageAndClear();
		}

	}

	private void unsolvableMessageAndClear() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Impossible to solve!");
		alert.setContentText("Make sure your numbers are correct");
		alert.showAndWait();
		clear(false);
	}

	private void clear(boolean removeText) {
		for (int i = 0; i < 9; i++) {
			for (int y = 0; y < 9; y++) {
				if (matrixTextField[i][y].getLength() == 1) {
					if (removeText) {
						matrixTextField[i][y].setText("");
					}
					sudoku.remove(i, y);
				}

			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}