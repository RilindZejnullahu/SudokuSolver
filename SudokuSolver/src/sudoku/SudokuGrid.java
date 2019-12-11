package sudoku;

import java.io.IOException;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuGrid extends Application {
	private SudokuBrain sudoku;
	public static boolean print;
	public static int delay;
	public static OneLetterTextField[][] matrixTextField;

	@Override
	public void start(Stage stage) throws Exception {
		delay = 0;
		print = false;
		matrixTextField = new OneLetterTextField[9][9];
		sudoku = new SudokuBrain();
		loadUI(stage);

	}

	// building the UI
	private void loadUI(Stage stage) {
		// Components we need for the UI
		BorderPane root = new BorderPane();
		TilePane root2 = new TilePane();
		HBox bottomBox = new HBox();

		Button solveButton = new Button("Solve");
		Button clearButton = new Button("Clear");
		Button loadButton  = new Button("Load");
		CheckBox printCheckBox  = new CheckBox("Print");
		TextField delayText = new TextField();
		delayText.textProperty().addListener((observable, oldValue, newValue) -> {
			delay = Integer.valueOf(newValue);
		});
		delayText.setVisible(false);
		bottomBox.setPadding(new Insets(5, 5, 10, 5));
		bottomBox.setSpacing(10);
		bottomBox.getChildren().addAll(solveButton, clearButton, loadButton, printCheckBox, delayText);

		BorderPane.setMargin(root2, new Insets(4));
		root.setPrefSize(458, 500);
		root.setCenter(root2);
		root.setBottom(bottomBox);

		root2.setPrefColumns(9);
		root2.setPrefRows(9);

		// Building the sudoku 9x9 matrix
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				OneLetterTextField nyRuta = new OneLetterTextField();
				nyRuta.setFont(Font.font("Verdana", FontWeight.NORMAL, 26));

				// Set color of the textfield square to orange if it's in the correct position
				if ((row < 3 && col < 3) || (row > 5 && col < 3) || (row > 5 && col > 5) || (row < 3 && col > 5)
						|| (row > 2 && row < 6 && col > 2 && col < 6)) {
					nyRuta.setStyle("-fx-control-inner-background: #EC7425");
				}
				matrixTextField[row][col] = nyRuta;
				nyRuta.setPrefHeight(50);
				nyRuta.setPrefWidth(50);
				nyRuta.setAlignment(Pos.CENTER);

				root2.getChildren().add(nyRuta);
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
		printCheckBox.setOnAction(event -> {
			delayText.setVisible(!print);
			print = printCheckBox.isSelected();
		});
		

		loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) {
       		 int[][] testMatrix = new int[9][9];
    		 testMatrix[0][0] = 6;
    		 
                BorderPane secondaryLayout = new BorderPane();
                HBox bottomBox = new HBox();
                Button selectButton = new Button("Select");
                bottomBox.getChildren().addAll(selectButton);
                secondaryLayout.setBottom(bottomBox);
                Scene secondScene = new Scene(secondaryLayout, 230, 400);
 
                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Second Stage");
                newWindow.setScene(secondScene);
                
                ObservableList<String> items = FXCollections.observableArrayList("matris1", "matris2");
                ListView<String> listView = new ListView<String>(items);
                

                listView.getSelectionModel().getSelectedItem();
                secondaryLayout.setCenter(listView);
                // Set position of second window, related to primary window.
                newWindow.setX(stage.getX() + 485);
                newWindow.setY(stage.getY() + 100);
 
                newWindow.show();
                
        		selectButton.setOnAction(event2 -> {
        			for (int row = 0; row < 9; row++) {
        				for (int col = 0; col < 9; col++) {
        					if(testMatrix[row][col] > 0 ) {
        						matrixTextField[row][col].setText(String.valueOf(testMatrix[row][col]));
        					}	
        				}
        			}

        		});
                
            }
            
            
            
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