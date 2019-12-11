package sudoku;

import javafx.scene.control.TextField;

public class OneLetterTextField extends TextField {
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (matches(text)) {
			super.clear();
			super.replaceText(0, 0, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (matches(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean matches(String text) {
		return text.isEmpty() ||  text.matches("[1-9]");
	}

}
