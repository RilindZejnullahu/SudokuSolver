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

	public int[][] getSudokuMatrix() {
		return this.sudokuValues;

	}

	public boolean solve() {
		return solve(0, 0);

	}

	/*
	 * SKA SKRIVA PSEUDOKODEN FINARE. 1. Om rutan är tom kontrollera rutan för varje
	 * siffra 1-9 om checkAll är true Om true, return true, gå till solve(nästa
	 * cell) kontrollera för edge fall(sista col, sista rad&col)
	 * 
	 * Om false, sätt rutan till 0, retrun false
	 * 
	 * 2. Om rutan är inte tom kontrollera checkAll Om true, return true, gå till
	 * solve(nästa cell) kontrollera för edge fall(sista col, sista rad&col)
	 * 
	 * Annars false
	 */

	private boolean solve(int row, int col) {
		if (sudokuValues[row][col] == 0) {
			if (col == 8 && row == 8) {
				for (int i = 1; i <= 9; i++) {
					sudokuValues[row][col] = i;
					if (checkAll(row, col)) {
						return true;
					}
				}
				return false;
			} else {

				for (int i = 1; i <= 9; i++) {
					sudokuValues[row][col] = i;
					if (checkAll(row, col)) {
						if (col == 8) {
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
				sudokuValues[row][col] = 0;
				return false;

			}

		} else {
			// kolla för när det finns siffror
			if (col == 8 && row == 8) {
				return checkAll(row, col);
			}
			if (checkAll(row, col)) {
				if (col == 8) {
					return solve(row + 1, 0);
				} else {
					return solve(row, col + 1);
				}
			}
			// OM SUDOKUN ÄR OMÖJLIG KOMMER HIT
			System.out.println("a");
			return false;
		}
	}

	// Check if value already exists in the row. Return true if value doesn't exist
	// in the row
	private boolean checkRow(int row, int value) {
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
		for (int i = 0; i < 9; i++) {
			if (sudokuValues[i][col] == value) {
				return false;
			}
		}
		return true;

	}

	// Check if value already exists in the 3x3 box, Return true if value doesn't
	// exist in the box
	private boolean check3x3(int row, int col, int value) {

		for (int i = (row / 3) * 3; i < (row / 3 + 1) * 3; i++) {
			for (int y = (col / 3) * 3; y < (col / 3 + 1) * 3; y++) {
				if (sudokuValues[i][y] == value) {
					return false;
				}
			}
		}
		return true;
	}

	// Check if all the other checks are true (row, col, 3z3)
	// return true, if all the other checks are true. Else false
	private boolean checkAll(int row, int col) {
		int saveValue = sudokuValues[row][col];
		sudokuValues[row][col] = 0; // 0 to be able to check all alternatives

		boolean check = checkRow(row, saveValue) && checkCol(col, saveValue) && check3x3(row, col, saveValue);
		sudokuValues[row][col] = saveValue;
		
		return check;
	}

}
