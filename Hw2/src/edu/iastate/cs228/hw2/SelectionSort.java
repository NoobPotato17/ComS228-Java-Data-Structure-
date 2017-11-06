package edu.iastate.cs228.hw2;

import java.util.Comparator;
/**
 * The Selection class extends SortWithStatistics and use the selection sort
 * algorithm to sort the world list.
 * @author Ram Luitel
 *
 */
public class SelectionSort extends SorterWithStatistics {
	/**
	 * Sorts the array words.
	 * @param words input array to be sorted.
	 * @param comp Comparator used to sort the input array.
	 */
	@Override
	public void sortHelper(String[] words, Comparator<String> comp) {
		      
		if(words == null || words.length <= 0)
			throw new IllegalArgumentException("Null pointer or zero");
		
		for(int i = 0; i < words.length-1; i++)
		{
			int ismall = i;
			for(int j = i+1; j < words.length; j++)
			{
				if(comp.compare(words[j], words[ismall]) < 0)
					ismall = j;
			}
			if( i != ismall)
			{
				String temp = words[i];
				words[i] = words[ismall];
				words[ismall] = temp;
			}
		}
	}
}
