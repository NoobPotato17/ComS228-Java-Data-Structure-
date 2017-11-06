package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * The MergeSort class extends SortWithStatistics and use the merge sort
 * algorithm to sort the world list.
 * @author Ram Luitel
 *
 */
public class MergeSort extends SorterWithStatistics {
	/**
	 * Sorts the array words.
	 * @param words input array to be sorted.
	 * @param comp Comparator used to sort the input array.
	 */
	@Override
	public void sortHelper(String[] words, Comparator<String> comp){

		if (words.length <= 1) {
			return;
		}
		String first[] = new String[words.length / 2];
		String second[] = new String[words.length - first.length];
		for (int i = 0; i < first.length; i++) {
			first[i] = words[i];
		}
		for (int j = 0; j < second.length; j++) {
			second[j] = words[first.length + j];
		}
		sortHelper(first, comp);
		sortHelper(second, comp);
		merge(words, first, second, comp);
	}

	/**
	 * This is a helper method for merge sort which use merge sort algorithm 
	 * @param words input array to be sorted.
	 * @param first the first half of words
	 * @param second the second half of words
	 * @param comp Comparator used to sort the input array.
	 */
	private void merge(String[] words, String[] first, String[] second, Comparator<String> comp) {
		int iFirst = 0;
		int iSecond = 0;
		int j = 0;
		while (iFirst < first.length && iSecond < second.length) {
			if (comp.compare(first[iFirst], second[iSecond]) < 0)
			{
				words[j] = first[iFirst];
				iFirst++;
			} else {
				words[j] = second[iSecond];
				iSecond++;
			}
			j++;
		}
		// Copy any remaining entries of the first half
		while (iFirst < first.length) {
			words[j] = first[iFirst];
			iFirst++;
			j++;
		}
		// Copy any remaining entries of the second half
		while (iSecond < second.length) {
			words[j] = second[iSecond];
			iSecond++;
			j++;
		}
	}
}
