package edu.iastate.cs228.hw1;

/**
 * This class extends the class Sequence. The specific requirement in this class
 * is that isValidLetter(char c) method return false if c is either of of this
 * "BbJjOoUuXxZz" character.
 * 
 * @author Ram Luitel
 */
public class ProteinSequence extends Sequence {
	/**
	 * Constructor
	 * 
	 * @param psarr
	 *            the given character array of protein sequence
	 */
	public ProteinSequence(char[] psarr) {
		super(psarr);
	}

	@Override
	public boolean isValidLetter(char aa) {
		if ((super.isValidLetter(aa)) && !(aa == 'B' || aa == 'b' || aa == 'J' || aa == 'j' || aa == 'O' || aa == 'o'
				|| aa == 'U' || aa == 'u' || aa == 'X' || aa == 'x' || aa == 'Z' || aa == 'z'))
			return true;
		else
			return false;
	}
}
