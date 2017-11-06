package edu.iastate.cs228.hw2;

import java.util.Comparator;
/**
 *  This class will keep track of the word sorted, the total words sorted, time to sort the list
 *  and total time take sort one millions word list.
 * @author Ram Luitel
 *
 */
public abstract class SorterWithStatistics implements Sorter {
	/*
	 * Object of Stopwatch class
	 */
	private Stopwatch timer;
	/*
	 * The number of word sort at each call
	 */
	private int wordsSorted;
	/*
	 * The total word sorted
	 */
	private int totalWordsSorted;
	/*
	 * Keep track of time to sort the word list
	 */
	private long timeToSort;
	/*
	 * Keep track of total time to sort one millions words.
	 */
	private long totalTime;
	/*
	 * This is basically flag to keep track either the wordList is sorted or not
	 */
	boolean sorted;
	
    /***
     * Constructor, all the variables initialized in here
     */
	public SorterWithStatistics(){
		
		  timer = new Stopwatch();
		  totalWordsSorted = 0;
          totalTime = 0;
          timeToSort = 0;
          wordsSorted = 0;
          sorted = false;
	}

    /***
     * Public interface to sortHelper that keeps track of performance
     * statistics, including counting words sorted and timing sort
     * instances.
	 * @param words input array to be sorted.
	 * @param comp Comparator used to sort the input array.
     */
	public void sort(String[] words, Comparator<String> comp){
		timer.start();
		sortHelper( words,comp);
		timer.stop();
		sorted = true;
		timeToSort = timer.getElapsedTime();
		totalTime+= timeToSort;
		wordsSorted = words.length;
		totalWordsSorted += wordsSorted;
	}
	
	/**
	 * Sorts the array words.
	 * @param words input array to be sorted.
	 * @param comp Comparator used to sort the input array.
	 */
	protected abstract void sortHelper(String[] words, Comparator<String> comp);
		
	/**
	 * Returns number of words sorted in last sort.  Throws IllegalStateException if nothing has been sorted.
	 * @return number of words sorted in last sort.
	 */
	public int getWordsSorted(){
		
		if(!sorted)
      	  throw new IllegalStateException("Nothing has been sorted");
          return wordsSorted;
	}
	
	/**
	 * Returns time the last sort took.  Throws IllegalStateException if nothing has been sorted.
	 * @return time last sort took.
	 */
	public long getTimeToSortWords(){
          if(!sorted)
        	  throw new IllegalStateException("Nothing has been sorted");
          return timeToSort;
	}
	
	/**
	 * Returns total words sorted by this instance.
	 * @return total number of words sorted.
	 */
	public int getTotalWordsSorted() {
          return totalWordsSorted;
	}
	
	/**
	 * Returns the total amount of time spent sorting by this instance.
	 * @return total time spent sorting.
	 */
	public long getTotalTimeToSortWords() {
		return totalTime;
	}
}
