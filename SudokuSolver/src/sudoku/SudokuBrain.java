package sudoku;

public class SudokuBrain {
	private int[][] sudokuValues;

	public SudokuBrain() {
		sudokuValues = new int[9][9];
	}

	public void add(int row, int col, int newValue) {
		sudokuValues[row][col] = newValue;
	}

	public void remove(int row, int col) {
		sudokuValues[row][col] = 0;
	}

	public int[][] test() {
		return this.sudokuValues;

	}

	public boolean solve() {
		return solve(0, 0);

	}

	// FUNKAR INTE
	private boolean solve(int row, int col) {

		if (sudokuValues[row][col] == 0) {
			for (int i = 1; i <= 9; i++) {
				if (checkAll(row, col)) {
					sudokuValues[row][col] = i;
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
				} else {
					sudokuValues[row][col] = 0;
					return false;
				}
			}

		}

		else if (sudokuValues[row][col] != 0) {
			if (checkAll(row, col)) {
				if (col == 8 && row == 8) {
					return true;
				} else if (col == 8) {
					return solve(row + 1, 0);
				} else {
					return solve(row, col + 1);
				}
			}
		}
		return false;

	}

	// Check if value already exists in the row. Return true if value doesn't exist
	// in the row
	private boolean checkRow(int row, int value) {
		/*
		 * int i = 0; while (i < 9 && value != sudokuValues[row][i]) { i++; } return i
		 * == 9;
		 */

		for (int i = 0; i < 9; i++) {
			if (sudokuValues[row][i] == value) {
				return false;
			}
		}
		return true;

	}

	// Check if value already exists in the col. Return true if value doesn't exist
	// in the col
	private boolean checkCol(int col, int value) {
		/*
		 * int i = 0; while (i < 9 && value != sudokuValues[i][col]) { i++; } return i
		 * == 9;
		 */

		for (int i = 0; i < 9; i++) {
			if (sudokuValues[i][col] == value) {
				return false;
			}
		}
		return true;

	}

	// Check if value already exists in the 3x3 box, Return true if value doesn't
	// exist in the box
	private boolean check3x3(int row, int col, int value) { // row,col,value

		for (int i = (row / 3) * 3; i < (row / 3 + 1) * 3; i++) {
			for (int y = (col / 3) * 3; y < (col / 3 + 1) * 3; y++) {
				if (sudokuValues[i][y] == value) {
					return false;
				}
			}
		}
		return true;

	}

	private boolean checkAll(int row, int col) {
		/*
		 * int nr = sudokuValues[y][x]; return (checkRow(row, nr) && checkCol(col, nr)
		 * && check3x3(row, col, nr);
		 */

		int nr = sudokuValues[row][col];
		sudokuValues[row][col] = 0;
		if (checkRow(row, nr) && checkCol(col, nr) && check3x3(row, col, nr)) {
			sudokuValues[row][col] = nr;
			return true;
		} else {
			sudokuValues[row][col] = nr;
			return false;
		}

	}

	public void printAll() {
		for (int i = 0; i < 9; i++) {
			for (int y = 0; y < 9; y++) {
				System.out.println("Row: " + (i + 1) + " col: " + (y + 1) + " has the value: " + sudokuValues[i][y]);
			}
		}
	}
}
