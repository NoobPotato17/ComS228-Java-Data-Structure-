package edu.iastate.cs228.hw2;

import java.util.Comparator;

/**
 * The QuickSort class extends SortWithStatistics and use the quick sort
 * algorithm to sort the world list.
 * 
 * @author Ram Luitel
 *
 */
public class QuickSort extends SorterWithStatistics {
	/**
	 * Sorts the array words.
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param comp
	 *            Comparator used to sort the input array.
	 */
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {

		if (words == null || words.length == 0)
			throw new RuntimeException("Null pointer or zero size");
		quickSortRec(words, 0, words.length - 1, comp);
	}

	/**
	 * This is a helper method for quick sort which use quick sort algorithm
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param first
	 *            the first index of word list
	 * @param last
	 *            the last index of word list
	 * @param comp
	 *            Comparator used to sort the input array.
	 */
	private void quickSortRec(String[] words, int first, int last, Comparator<String> comp) {
		if (first >= last)
			return;
		int mid = partition(words, first, last, comp); // performs a partition
		quickSortRec(words, first, mid, comp); // recursive calls
		quickSortRec(words, mid + 1, last, comp);
	}

	/**
	 * This is a helper method for quicksortRec(). This basically help to
	 * partition the word list.
	 * 
	 * @param words
	 *            input array to be sorted.
	 * @param first
	 *            the first index of word list
	 * @param last
	 *            the last index of word list
	 * @param comp
	 *            Comparator used to sort the input array.
	 * @return right the partition index of word list
	 */
	private int partition(String[] words, int first, int last, Comparator<String> comp) {
		String pivot = words[first];
		int left = first;
		int right = last;
		while (true) {
			while (comp.compare(words[left], pivot) < 0)
				left++;
			while (comp.compare(words[right], pivot) > 0)
				right--;
			if (left < right) {
				String temp = words[left];
				words[left++] = words[right];
				words[right--] = temp;
			} else
				break;
		}
		return right;
	}
}
