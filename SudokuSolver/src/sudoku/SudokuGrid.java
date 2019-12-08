package sudoku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuGrid extends Application {
	SudokuBrain sudoku;
	OneLetterTextField oneIntegerOnly;
	OneLetterTextField[][] matrixTextField;

	@Override
	public void start(Stage stage) throws Exception {
		sudoku = new SudokuBrain();
		oneIntegerOnly = new OneLetterTextField();
		matrixTextField = new OneLetterTextField[9][9];
		loadUI(stage);
		
	}
	
	//building the UI
	private void loadUI(Stage stage) {
		BorderPane root = new BorderPane();
		TilePane root2 = new TilePane();
		HBox bottomBox = new HBox();

		bottomBox.setPadding(new Insets(5, 5, 10, 5));
		bottomBox.setSpacing(10);
		Button solveButton = new Button("Solve");
		Button clearButton = new Button("Clear");
		bottomBox.getChildren().addAll(solveButton, clearButton);

		root.setPrefSize(458, 500);
		root.setMargin(root2, new Insets(4));
		root.setCenter(root2);
		root.setBottom(bottomBox);

		root2.setPrefColumns(9);
		root2.setPrefRows(9);


		// Building the sudoku 9x9 matrix
		for (int i = 0; i < 9; i++) {
			for (int y = 0; y < 9; y++) {
				OneLetterTextField nyRuta = new OneLetterTextField();
				nyRuta.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));

				//Set color of the textfield square to orange if it's in the correct position 
				if ((i < 3 && y < 3) || (i > 5 && y < 3) || (i > 5 && y > 5) || (i < 3 && y > 5)
						|| (i > 2 && i < 6 && y > 2 && y < 6)) {
					nyRuta.setStyle("-fx-control-inner-background: #EC7425");
				}
				matrixTextField[i][y] = nyRuta;
				
				nyRuta.setPrefHeight(50);
				nyRuta.setPrefWidth(50);
				nyRuta.setAlignment(Pos.CENTER);

				root2.getChildren().add(nyRuta);
			}

		}
		
		//When you press the solve button
		solveButton.setOnAction(event -> {
			solve();
		});
		
		//When you press the clear button
		clearButton.setOnAction(event -> {
			clear();
		});

		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setTitle("Sudoku Solver");
		stage.setScene(scene);
		stage.show();
	}
	
	private void solve() {
		for (int i = 0; i < 9; i++) {
			for (int y = 0; y < 9; y++) {
				if(matrixTextField[i][y].getLength() == 1) { //Check if there's a number in the square.
					//add all the values to the matrix that we will use in the SudokuBrain class
					sudoku.add(i, y, Integer.valueOf(matrixTextField[i][y].getText())); 
				}
				
			}
		}
	}
	private void clear() {
		sudoku.printAll(); //Just testing right now.
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
