package edu.iastate.cs228.hw2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This is actual class the is use to compare the object of CharacterValues.
 * This class implement Comparator
 * 
 * @author Ram Luitel
 *
 */
public class CustomComparator implements Comparator<String> {
	/***
	 * Lookup table mapping characters in lexicographical order to to their
	 * input order.
	 */
	private CharacterValue[] characterOrdering;

	/***
	 * Creates an array of CharacterValue from characterOrdering. Sorts it using
	 * Arrays.sort().
	 * 
	 * @param characterOrdering
	 *            character order from configuration file
	 */
	public CustomComparator(char[] characterOrdering) {

		if (characterOrdering.length <= 0)
			throw new IllegalArgumentException("Null reference");
		this.characterOrdering = new CharacterValue[characterOrdering.length];
		for (int i = 0; i < characterOrdering.length; i++) {
			this.characterOrdering[i] = new CharacterValue(i, characterOrdering[i]);
		}

		Comparator<CharacterValue> comparator = new Comparator<CharacterValue>() {

			@Override
			public int compare(CharacterValue value1, CharacterValue value2) {

				return value1.character - value2.character;
			}
		};
		Arrays.sort(this.characterOrdering, comparator);
	}

	/***
	 * Compares two words based on the configuration
	 * 
	 * @param a
	 *            first word
	 * @param b
	 *            second word
	 * @return negative if a<b, 0 if equal, postive if a>b
	 */
	@Override
	public int compare(String a, String b) {

		int i = 0;
		while (i < a.length() && i < b.length()) {
			if (a.charAt(i) == b.charAt(i))
				i++;
			else
				break;
		}
		if (i < a.length() && i < b.length())
			return getCharacterOrdering(a.charAt(i)) - getCharacterOrdering(b.charAt(i));
		if (i == a.length() && i == b.length())
			return 0;
		if (a.length() < b.length())
			return -1;
		return 1;
	}

	/**
	 * Uses binary search to find the order of key.
	 * 
	 * @param key
	 * @return ordering value for key or -1 if key is an invalid character.
	 */
	public int getCharacterOrdering(char key) {

		return binarySearch(characterOrdering, new CharacterValue(0, key));
	}

	/**
	 * Searches characterOrdering for key via binary search
	 * 
	 * @param characterOrdering
	 *            the specified sort order
	 * @param key
	 *            the search term
	 * @return ordering value for key or -1 if key is an invalid character.
	 */
	private int binarySearch(CharacterValue[] characterOrdering, CharacterValue key) {

		if (characterOrdering == null || characterOrdering.length == 0)
			return -1;
		return binarySearchRec(characterOrdering, key, 0, characterOrdering.length - 1);
	}

	/**
	 * This is private helper method for binary search method.
	 * 
	 * @param characterOrdering.
	 *            The array of CharacterValue
	 * @param key
	 *            the search term
	 * @param start
	 *            starting index
	 * @param end
	 *            end index
	 * @return ordering value for key or -1 if key is an invalid character.
	 */
	private int binarySearchRec(CharacterValue[] characterOrdering, CharacterValue key, int start, int end) {
		if (start > end)
			return -1;
		int middle = (start + end) / 2;
		if (characterOrdering[middle].character == key.character)
			return characterOrdering[middle].value;

		else if (characterOrdering[middle].character > key.character) {
			end = middle - 1;
			return binarySearchRec(characterOrdering, key, start, end);
		} else {
			start = middle + 1;
			return binarySearchRec(characterOrdering, key, start, end);
		}
	}

	/**
	 * This is given class
	 */
	private static class CharacterValue {
		public int value;
		public char character;

		public CharacterValue(int value, char character) {
			this.value = value;
			this.character = character;
		}

		/**
		 * @param o
		 *            object of Object class
		 * @return true if o is equals to this CharacterValue false otherwise
		 */
		public boolean equals(Object o) {
			if (o == null || o.getClass() != this.getClass()) {
				return false;
			}
			CharacterValue other = (CharacterValue) o;
			return value == other.value && character == other.character;
		}
	}
}
