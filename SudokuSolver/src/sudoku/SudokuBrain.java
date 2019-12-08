package sudoku;

public class SudokuBrain {
	int[][] sudokuValues = new int[9][9];
	

	
	public void add(int row, int col, int newValue) {
		sudokuValues[row][col] = newValue;
	}
	
	public void printAll() {
		for (int i = 0; i < 9; i++) {
			for (int y = 0; y < 9; y++) {
				System.out.println("Row: " + (i+1)  + " col: " + (y+1) + " has the value: " + sudokuValues[i][y]);
			}
		}
	}
}
