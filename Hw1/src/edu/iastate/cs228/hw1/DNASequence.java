package edu.iastate.cs228.hw1;

/**
 * This is a subclass of the class Sequence. The only valid character for DNA
 * sequence are "aAcCgGtT". Except this all other character are invalid
 * 
 * @author Ram Luitel
 */
public class DNASequence extends Sequence {
	/**
	 * Constructor.
	 * 
	 * @param dnaarr
	 *            the character array of DNA sequence
	 */
	public DNASequence(char[] dnaarr) {
		super(dnaarr);
	}

	/**
	 * This method check whether that character is valid DNA sequence character
	 * or not
	 * 
	 * @return The method returns true if the character argument is equal to one
	 *         of the eight characters ’a’, ’A’, ’c’, ’C’, ’g’, ’G’, ’t’ and
	 *         ’T’. Otherwise, it returns false.
	 */
	@Override
	public boolean isValidLetter(char let) {
		if (let == 'a' || let == 'A' || let == 'c' || let == 'C' || let == 'g' || let == 'G' || let == 't'
				|| let == 'T')
			return true;
		else
			return false;
	}

}
