package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Dictionary class
 * @author Ram Luitel
 *
 */
public class Dictionary {
	/**
	 * An instance of a BinarySearchTree which stores this Dictionary's list of
	 * words.
	 */
	private BinarySearchTree<Entry> binaryTree;

	/**
	 * Constructs a new Dictionary which is empty.
	 */
	public Dictionary() {
		binaryTree = new BinarySearchTree<Entry>();
	}

	/**
	 * Constructs a new Dictionary whose word list is exactly (a deep copy of)
	 * the list stored in the given tree. (For testing purposes, you can set
	 * this Dictionary's BST to the given BST, rather clone it, but your final
	 * method must do the deep copy)
	 * 
	 * @param tree
	 *            - The tree of the existing word list
	 */
	public Dictionary(BinarySearchTree<Entry> tree) {

		if (tree == null)
			throw new IllegalArgumentException();
		ArrayList<Entry> listOfTree = tree.getPreorderTraversal();
		for (int i = 0; i < listOfTree.size(); i++)
			binaryTree.add(listOfTree.get(i));
	}

	/**
	 * Constructs a new Dictionary from the file specified by the given file
	 * name. Each line of the file will contain at least one word with an
	 * optional definition. Each line will have no leading or trailing
	 * whitespace. For each line of the file, create a new Entry containing the
	 * word and definition (if given) and add it to the BST.
	 * 
	 * @param filename
	 *            - The file containing the wordlist
	 * @throws FileNotFoundException
	 *             - If the given file does not exist
	 */
	public Dictionary(String filename) throws FileNotFoundException {
		this();
		File file;
		Scanner fromFile = null;
		try {
			file = new File(filename);
			fromFile = new Scanner(file).useDelimiter(":");
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
		String line = null;
		Scanner fromLine;
		Entry entry;
		while (fromFile.hasNextLine()) {
			line = fromFile.nextLine();
			fromLine = new Scanner(line);
			entry = new Entry(fromLine.next());
			if (fromLine.hasNext())
				entry.udateDefination(fromLine.nextLine());
			binaryTree.add(entry);
		}
		fromFile.close();
	}

	/**
	 * Adds a new Entry to the Dictionary for the given word with no definition.
	 * 
	 * @param word
	 *            - The word to add to the Dictionary
	 * @return true only if the Entry was successfully added to the Dictionary,
	 *         false otherwise.
	 */
	public boolean addEntry(String word) {

		return binaryTree.add(new Entry(word));
	}

	/**
	 * Adds a new Entry to the Dictionary for the given word and definition.
	 * 
	 * @param word
	 *            - The word to add to the Dictionary
	 * @param definition
	 *            - The definition of the given word
	 * @return true only if the Entry was successfully added to the Dictionary,
	 *         false otherwise.
	 */
	public boolean addEntry(String word, String definition) {
		return binaryTree.add(new Entry(word, definition)); 
															
	}

	/**
	 * Tests whether or not word exists in this Dictionary.
	 * 
	 * @param word
	 *            - The word to test.
	 * @return true is word exists in this Dictionary, false otherwise.
	 */
	public boolean hasWord(String word) {

		return binaryTree.contains(new Entry(word.toLowerCase()));
	}

	/**
	 * Returns the definition of the given word in the Dictionary, if it is
	 * there.
	 * 
	 * @param word
	 *            - The word to retrieve the definition of.
	 * @return the definition of the word.
	 * @throws IllegalArgumentExeception
	 *             - If word does not exist in the Dictionary.
	 */
	public String getDefinitionOf(String word) throws IllegalArgumentException {
		if (!hasWord(word))
			throw new IllegalArgumentException();

		Entry item = binaryTree.get(new Entry(word));
		return item.defination;

	}

	/**
	 * Removes the given word from the word dictionary if it is there.
	 * 
	 * @param word
	 *            - The word to remove from Dictionary.
	 * @return true only if the word is successfully removed from the
	 *         BinarySearchTree, false otherwise.
	 */
	public boolean removeEntry(String word) {

		return binaryTree.remove(new Entry(word));
	}

	/**
	 * Changes the definition of given word if it is there.
	 * 
	 * @param word
	 *            - The word to change the definition of
	 * @param newDef
	 *            - The new definition of the word
	 * @return true if the definition was successfully updated, false otherwise.
	 */
	public boolean updateEntry(String word, String newDef) {

		if (word == null)
			return false;
		Entry entry = new Entry(word);
		Entry temp = (Entry) binaryTree.get(new Entry(word));
		if (entry.equals(temp))
			temp.udateDefination(newDef);

		return true;
	}

	/**
	 * Outputs this Dictionary to the given file. The file should be formatted
	 * as follows: 1) One word and definition should appear per line separated
	 * by exactly one space. 2) Lines should not have any leading or trailing
	 * whitespace except for a single newline. 3) Each line of the file should
	 * have text. There should be no empty lines. 4) The words should be sorted
	 * alphabetically (i.e. using the BST's inorder traversal)
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public void printToFile(String filename) throws FileNotFoundException {
		FileWriter fw;
		try {
			fw = new FileWriter(filename);
			ArrayList<Entry> entrylist = binaryTree.getInorderTravseral();
			String fileContains = "";
			for (Entry e : entrylist) {
				fileContains = e.words + " " + e.defination + "\n";
				fw.write(fileContains);
			}
			fw.close();
		} catch (IOException e) {

			throw new FileNotFoundException();
		}

	}

	/**
	 * Returns the number of items stored in the Dictionary.
	 */
	public int entryCount() {

		return binaryTree.size();
	}

	/**
	 * Returns a sorted list of Entries (as returned by an inorder traversal of
	 * the BST)
	 * 
	 * @return an ArrayList of sorted Entries
	 */
	public ArrayList<Entry> getSortedEntries() {
		return binaryTree.getInorderTravseral();
	}

	/**
	 * A Key-Value Pair class which represents an entry in a Dictionary.
	 * 
	 * @author
	 */
	public static class Entry implements Comparable<Entry> {

		/**
		 * Instance variables storing the word and definition of this Entry
		 */
		private String words;
		private String defination;

		/**
		 * Constructs a new Entry with the given word with no definition
		 * 
		 * @param w
		 *            - The word to create an entry for.
		 */
		public Entry(String w) {
			words = w;
			defination = null;
		}

		/**
		 * Constructs a new Entry with the given word and definition
		 * 
		 * @param w
		 *            - The word to create an entry for.
		 * @param d
		 *            - The definition of the given word.
		 */
		public Entry(String w, String d) {
			this(w);
			if (d != null)
				defination = d;
		}

		/**
		 * Compares the word contained in this entry to the word in other.
		 * Returns a value < 0 if the word in this Entry is alphabetically
		 * before the other word, = 0 if the words are the same, and > 0
		 * otherwise.
		 */
		@Override
		public int compareTo(Entry other) {

			return this.words.compareTo(other.words);

		}

		/**
		 * Tests for equality of this Entry with the given Object. Two entries
		 * are considered equal if the words they contain are equal regardless
		 * of their definitions.
		 */
		@Override
		public boolean equals(Object o) {
			if (o == null)
				throw new NullPointerException("null pointer");
			if (o.getClass() != this.getClass())
				return false;

			Entry temp = (Entry) o;
			return this.words.equals(temp.words);
		}

		/**
		 * Update the definaiton of word.
		 * @param d
		 * 			The new definaiton that is being updated
		 */
		public void udateDefination(String d) {

			defination = d;

		}
	}
}
