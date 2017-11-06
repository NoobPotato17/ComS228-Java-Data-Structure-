package edu.iastate.cs228.hw1;

/**
 * This is supper class of all other class in the package. The class has a field
 * named seqarr, which is a character array with protected access
 * 
 * @author Ram Luitel
 */
public class Sequence {
	/* Character array that represent the sequence of character */
	protected char[] seqarr;

	/**
	 * Constructor
	 * 
	 * @param sarr
	 *            the given character array of sequence
	 */
	public Sequence(char[] sarr) {
		seqarr = new char[sarr.length];
		for (int i = 0; i < sarr.length; ++i) {
			if (!this.isValidLetter(sarr[i]))
				throw new IllegalArgumentException("Invalid sequence letter for class " + this.getClass().getName());
			else
				seqarr[i] = sarr[i];
		}
	}

	/**
	 * This method return the length of seqarr array
	 * 
	 * @return length of seqarr array
	 */
	public int seqLength() {
		return seqarr.length;
	}

	/**
	 * This method make a copy of seqarr array
	 * 
	 * @return copy. The copy of seqarr array
	 */
	public char[] getSeq() {
		char copy[] = new char[seqLength()];
		for (int i = 0; i < seqLength(); i++) {
			copy[i] = seqarr[i];
		}
		return copy;
	}

	/**
	 * The method returns the string representation of the char array seqarr.
	 * 
	 * @return result the string represent of seqarr
	 */
	public String toString() {
		String result = new String();
		for (int i = 0; i < seqLength(); i++) {
			result += seqarr[i];
		}
		return result;
	}

	/**
	 * Compare this object with other object
	 * 
	 * @param obj
	 *            the object to be compared
	 * @return true if the argument obj is not null and is of the same type as
	 *         this object such that both objects represent the identical
	 *         sequence of characters in a case insensitive mode otherwise
	 *         return false
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		Sequence test = (Sequence) obj;
		return (test.toString().equalsIgnoreCase(this.toString()));
	}

	/**
	 * The method returns true if the character let is an uppercase or
	 * lowercase. Otherwise, it returns false.
	 * 
	 * @param let
	 *            the character
	 * @return true if character is either upper case or lower case character,
	 *         otherwise return false;
	 */
	public boolean isValidLetter(char let) {
		if (Character.isUpperCase(let) || Character.isLowerCase(let))
			return true;
		else
			return false;
	}

}
