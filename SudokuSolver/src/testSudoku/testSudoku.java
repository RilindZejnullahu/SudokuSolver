package testSudoku;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sudoku.SudokuBrain;


public class testSudoku {
	SudokuBrain sudoku;
	
	@Before
	public void setUp() throws Exception {
		sudoku = new SudokuBrain();
	}

	@After
	public void tearDown() throws Exception {
		sudoku = null;
	}

	@Test
	public void testEmpty() {
		//Possible to solve
		assertTrue(sudoku.solve());
		//Check that values are correct
		assertEquals("row 1, col1, should contain the number 1", 1, sudoku.getNumber(0, 0));
		assertEquals("row 1, col9, should contain the number 9", 9, sudoku.getNumber(0, 8));
		assertEquals("row 9, col1, should contain the number 9", 9, sudoku.getNumber(8, 0));
		assertEquals("row 9, col9, should contain the number 2", 2, sudoku.getNumber(8, 8));
		assertEquals("row 5, col5, should contain the number 9", 9, sudoku.getNumber(4, 4));
	}
	@Test
	public void testSolvable() {
		//Figure 1
		sudoku.add(0, 2, 8);
		sudoku.add(0, 5, 9);
		sudoku.add(0, 7, 6);
		sudoku.add(0, 8, 2);
		sudoku.add(1, 8, 5);
		sudoku.add(2, 0, 1);
		sudoku.add(2, 2, 2);
		sudoku.add(2, 3, 5);
		sudoku.add(3, 3, 2);
		sudoku.add(3, 4, 1);
		sudoku.add(3, 7, 9);
		sudoku.add(4, 1, 5);
		sudoku.add(4, 6, 6);
		sudoku.add(5, 0, 6);
		sudoku.add(5, 7, 2);
		sudoku.add(5, 8, 8);
		sudoku.add(6, 0, 4);
		sudoku.add(6, 1, 1);
		sudoku.add(6, 3, 6);
		sudoku.add(6, 5, 8);
		sudoku.add(7, 0, 8);
		sudoku.add(7, 1, 6);
		sudoku.add(7, 4, 3);
		sudoku.add(7, 6, 1);
		sudoku.add(8, 6, 4);
		
		//Possible to solve
		assertTrue(sudoku.solve());
		//Check that values are correct
		assertEquals("row 1, col1, should contain the number 5", 5, sudoku.getNumber(0, 0));
		assertEquals("row 5, col5, should contain the number 8", 8, sudoku.getNumber(4, 4));
		assertEquals("row 9, col1, should contain the number 9", 9, sudoku.getNumber(8, 0));
		assertEquals("row 9, col9, should contain the number 6", 6, sudoku.getNumber(8, 8));

	}
	@Test
	public void testUnsolvable() {
		//Test duplicate numbers in same row
		sudoku.add(0, 0, 2);
		sudoku.add(0, 8, 2);
		assertFalse(sudoku.solve());
		sudoku.remove(0, 0);
		sudoku.remove(0, 8);
		
		//Test duplicate numbers in same col
		sudoku.add(0, 8, 2);
		sudoku.add(8, 8, 2);
		assertFalse(sudoku.solve());
		sudoku.remove(0, 8);
		sudoku.remove(8, 8);
		
		//Test duplicate numbers in same 3x3 region
		sudoku.add(0, 0, 2);
		sudoku.add(2, 2, 2);
		assertFalse(sudoku.solve());
		sudoku.remove(0, 0);
		sudoku.remove(2, 2);
		
	}
	
}
