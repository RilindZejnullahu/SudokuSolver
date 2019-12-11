package sudoku;

import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class SudokuBrain {
	private int[][] sudokuNumbers;

	/**
	 * Constructs an empty sudoku.
	 */
	public SudokuBrain() {
		sudokuNumbers = new int[9][9];

	}

	/**
	 * 
	 * Insert the number in the matrix at the specified position.
	 * 
	 * @param row      the row where you want to insert the number.
	 * @param col      the column where you want to insert the number.
	 * @param newValue the number you want to insert.
	 */
	public void add(int row, int col, int newNumber) {
		sudokuNumbers[row][col] = newNumber;
	}

	/**
	 * 
	 * Retrieve the integer in the specified position from the matrix
	 * 
	 * @param row the row of the number you want to get.
	 * @param col the column of the number you want to get.
	 * @return the number in the specified position from the matrix.
	 */
	public int getNumber(int row, int col) {
		return sudokuNumbers[row][col];
	}

	/**
	 * 
	 * Removes the number in the specified position from the matrix.
	 * 
	 * @param row the row of the number you want to remove.
	 * @param col the column of the number you want to remove.
	 */
	public void remove(int row, int col) {
		sudokuNumbers[row][col] = 0;
	}

	/**
	 * 
	 * Return the matrix that contains all the sudoku numbers.
	 * 
	 * @return matrix with all the sudoku numbers.
	 */
	public int[][] getSudokuMatrix() {
		return this.sudokuNumbers;

	}

	/**
	 * Calls the private solve method that handles the sudoku matrix.
	 * 
	 * @return true if solvable, else false.
	 */
	public boolean solve() {
		return solve(0, 0);

	}
	
	//TAGEN FRÅN GOOGLE
	String f(int[][] b){
		int[] a = Stream.of(b) //we start with a stream of objects Stream<int[]>
                .flatMapToInt(IntStream::of) //we I'll map each int[] to IntStream
                .toArray(); //we're now IntStream, just collect the ints to array.
		  String P="0121213121213121214",                         // Both lines and rows are repeated according to this pattern.
		         R[]={"╔═╤╦╗","║ │║║x","╟─┼╫╢","╠═╪╬╣","╚═╧╩╝"},  // Characters found on each line.
		                                                          //   (note the 'x')
		         r="";                                            // The string under construction
		  for (int X: P.getBytes()) {                             // For each line,
		    for (int x:                                           //  For each character in the pattern,
		         P.replace("1",R[X-=48].length()>5?"151":"111")   //    *but* with a cell width of 3,
		                                                          //    and with an optional character ('x')
		         .getBytes())
		      r+=R[X].charAt(x-48);                               //   append the real mapped character
		    r+="\n";                                              //  then append a new line
		  }
		  for(int i:a)                                            // For each number in the input
		    r = r.replaceFirst("x",i>0?""+i:" ");                 //  replace the first 'x' with that number.
		                                                          //    (or space if zero)
		  return r;                                               // Return the constructed string.
		}
	
	private boolean solve(int row, int col) {
		// If the square is empty
		if (sudokuNumbers[row][col] == 0) {
			for (int i = 1; i <= 9; i++) {
				sudokuNumbers[row][col] = i;
				if(SudokuGrid.print) {
					try {
						Thread.sleep(SudokuGrid.delay);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(f(sudokuNumbers));
				}

				if (checkAll(row, col)) {
					if (col == 8 && row == 8) {
						return true;
					} else if (col == 8) {
						if (solve(row + 1, 0)) {
							return true;
						}

					} else {
						if (solve(row, col + 1)) {
							return true;
						}

					}
				}

			}
			sudokuNumbers[row][col] = 0;
			return false;
		}
		// If the square isn't empty
		else {
			if (checkAll(row, col)) {
				if (col == 8 && row == 8) {
					return true;
				} else if (col == 8) {
					return solve(row + 1, 0);
				} else {
					return solve(row, col + 1);
				}
			}
			return false;
		}
	}

	/**
	 * 
	 * Check if the numbers in the sudoku are colliding before trying to solve.
	 * 
	 * @return true if numbers aren't colliding.
	 */
	public boolean checkBeforeSolve() {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (sudokuNumbers[row][col] > 0 && !checkAll(row, col))
					return false;
			}
		}
		return true;
	}

	/*
	 * Check if value already exists in the row. Return true if value doesn't exist
	 * in the row
	 */
	private boolean checkRow(int row, int number) {
		for (int i = 0; i < 9; i++) {
			if (sudokuNumbers[row][i] == number) {
				return false;
			}
		}
		return true;

	}

	/*
	 * Check if value already exists in the col. Return true if value doesn't exist
	 * in the column
	 */
	private boolean checkCol(int col, int number) {
		for (int i = 0; i < 9; i++) {
			if (sudokuNumbers[i][col] == number) {
				return false;
			}
		}
		return true;

	}

	/*
	 * Check if value already exists in the 3x3 box. Return true if value doesn't
	 * exist in the box
	 */
	private boolean check3x3(int row, int col, int number) {

		for (int i = (row / 3) * 3; i < (row / 3 + 1) * 3; i++) {
			for (int y = (col / 3) * 3; y < (col / 3 + 1) * 3; y++) {
				if (sudokuNumbers[i][y] == number) {
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Check if all the other checks are true (row, col, 3x3) Return true if all the
	 * other checks are true.
	 */
	private boolean checkAll(int row, int col) {
		int saveNumber = sudokuNumbers[row][col];
		sudokuNumbers[row][col] = 0; // 0 to be able to check collisions

		boolean check = checkRow(row, saveNumber) && checkCol(col, saveNumber) && check3x3(row, col, saveNumber);
		sudokuNumbers[row][col] = saveNumber;

		return check;
	}

}
