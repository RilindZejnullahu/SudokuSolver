package sudoku;

public class SudokuBrain {
	int[][] sudokuValues = new int[9][9];

	public void add(int row, int col, int newValue) {
		sudokuValues[row][col] = newValue;
	}
	public void remove(int row, int col) {
		sudokuValues[row][col] = 0;
	}

	public boolean solve() {
		return true;

	}

	private boolean solve(int row, int col) {
		return true;

	}

	// Check if value already exists in the row
	private boolean checkRow(int row, int value) {
		for (int i = 0; i < sudokuValues.length; i++) {
			if (sudokuValues[row][i] == value) {
				return true;
			}
		}
		return false;

	}

	// Check if value already exists in the col
	private boolean checkCol(int col, int value) {
		for (int i = 0; i < sudokuValues.length; i++) {
			if (sudokuValues[i][col] == value) {
				return true;
			}
		}
		return false;
	}

	// Check if value already exists in the 3x3 box
	private boolean check3x3(int row, int col, int value) {
		int rowAreaStart = (row / 3) + 3;
		int colAreaStart = (col / 3) + 3;
		
		for (int i = rowAreaStart; i < rowAreaStart + 3; i++) {
			for (int y = colAreaStart; i < colAreaStart + 3; y++) {
				if(sudokuValues[i][y] == value) {
					return true;
				}
			}
		}
		return false;
	}

	public void printAll() {
		for (int i = 0; i < 9; i++) {
			for (int y = 0; y < 9; y++) {
				System.out.println("Row: " + (i + 1) + " col: " + (y + 1) + " has the value: " + sudokuValues[i][y]);
			}
		}
	}
}
