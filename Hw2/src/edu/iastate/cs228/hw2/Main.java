package edu.iastate.cs228.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is main class. This class test all three search class and analyzes the
 * effectiveness of three search algorithm
 * 
 * @author Ram Luitel
 *
 */
public class Main {
	/**
	 * Main method takes two file names as arguments.The first file, the
	 * configuration file, contains a list of characters and second is the
	 * wordlist, is a list of words to be sorted according to the order defined
	 * in the configuration file.
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 * @throws FileConfigurationException
	 */
	public static void main(String args[]) throws FileNotFoundException, FileConfigurationException {

		String order;
		String wordsFile;
		if (args.length < 2) {
			System.out.println("Too less  argumetn");
			return;
		} else {
			order = args[0];
			wordsFile = args[1];
		}
		char[] orderArray = readCharacterOrdering(order);
		CustomComparator comparator = new CustomComparator(orderArray);
		String[] wordList = readWordsFile(wordsFile, comparator);
		SorterWithStatistics sort[] = { new QuickSort(), new MergeSort(), new SelectionSort() };
		String copyWords[] = null;
		String sortMethod = "";

		// each sort method sorts 100000 words
		for (int i = 0; i < sort.length; i++) {
			int counter = 0;
			while ((sort[i]).getTotalWordsSorted() < 100000) {
				copyWords = Arrays.copyOf(wordList, wordList.length);
				sort[i].sort(copyWords, comparator);
				counter++;
			}
			if (i == 0)
				sortMethod = "  Quick";
			if (i == 1)
				sortMethod = "  Merge";
			if (i == 2)
				sortMethod = "Selection";

			System.out.println(sortMethod + "\n*********");
			System.out.println("Length of the word list = " + wordList.length);
			long totalwords = sort[i].getTotalWordsSorted();
			System.out.println("Total number of words sorted = " + totalwords);
			long totalTime = (long) (sort[i].getTimeToSortWords());
			System.out.println("The total time spent sorting = " + totalTime / 1000000.0 + " millisecond.");
			System.out.println("Average time required to sort the word list = " + totalTime / (counter * 1000000.0)
					+ " millisecond");
			System.out.println("Words per second = " + (1000000000 * totalwords) / totalTime + "\n");
		}
	}

	/**
	 * Reads the characters contained in filename (the input alphabet) and
	 * returns them as a character array.
	 * 
	 * @param filename
	 *            the file containing the list of characters
	 * @returns an char array representing the ordering of characters to be used
	 *          or null if there is an exception. FileNotFoundException is
	 *          thrown when filename is does not exist.
	 *          FileConfigurationException is thrown when any line of the input
	 *          file is repeated or when any line contains other than exactly 1
	 *          byte before the terminating newline.
	 */
	public static char[] readCharacterOrdering(String filename)
			throws FileNotFoundException, FileConfigurationException {

		ArrayList<Character> list = new ArrayList<Character>();
		File file = new File(filename);
		Scanner fromFile = new Scanner(file);

		while (fromFile.hasNextLine()) {
			String temp = fromFile.nextLine();
			if (temp.length() > 0) {
				for (int i = 0; i < temp.length(); i++)
					list.add(temp.charAt(i));
			}
		}
		fromFile.close();
		if (list.size() > 0) {
			char[] charArray = new char[list.size()];
			for (int x = 0; x < charArray.length; x++) {
				charArray[x] = list.get(x);
			}
			return charArray;
		}
		return null;
	}

	/**
	 * Reads the words from the file and returns them as a String array.
	 * 
	 * @param filename
	 *            file containing words
	 * @return the words contained in the file or null if there was an
	 *         exception. FileNotFoundException is thrown when filename is does
	 *         not exist. FileConfigurationException is thrown when the file
	 *         contains any characters that didn't first appear in the input
	 *         alphabet.
	 */
	public static String[] readWordsFile(String filename, CustomComparator comp)
			throws FileNotFoundException, FileConfigurationException {

		ArrayList<String> list = new ArrayList<String>();
		File file = new File(filename);
		Scanner fromFile = new Scanner(file);
		int i = 0;
		while (fromFile.hasNextLine()) {
			String word = fromFile.nextLine();
			if (word != null)
				comp = new CustomComparator(word.toCharArray());
			if (isValid(word, comp) && word.length() > 0) {
				list.add(i, word);
				i++;
			}
		}
		fromFile.close();
		String[] stringList = new String[list.size()];
		return list.toArray(stringList);
	}

	/**
	 * Returns whether or not word is valid.
	 * 
	 * @param word
	 *            word to be checked.
	 * @param comparator
	 *            comparator used to check if characters are valid.
	 * @return true if every character in the word is in the input alphabet,
	 *         else false.
	 */
	public static boolean isValid(String word, CustomComparator comparator) {
		for (int i = 0; i < word.length(); i++) {
			if (comparator.getCharacterOrdering(word.charAt(i)) == -1)
				return false;
		}
		return true;
	}
	private static class FileConfigurationException extends Exception {
	}
}
