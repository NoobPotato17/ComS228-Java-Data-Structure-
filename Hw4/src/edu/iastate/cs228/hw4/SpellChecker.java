package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Spell Checker is basically implements Dictionary class
 * @author Ram Luitel
 *
 */
public class SpellChecker {

	/**
	 * Displays usage information.
	 *
	 * There's no reason that you should need to modify this.
	 */
	private static void doUsage() {
		System.out.println("Usage: SpellChecker [-i] <dictionary> <document>\n"
				+ "                    -d <dictionary>\n" + "                    -h");
	}

	/**
	 * Displays detailed usage information and exits.
	 *
	 * There's no reason that you should need to modify this.
	 */
	private static void doHelp() {
		doUsage();
		System.out.println("\n" + "When passed a dictionary and a document, spell check the document.  Optionally,\n"
				+ "the switch -n toggles non-interactive mode; by default, the tool operates in\n"
				+ "interactive mode.  Interactive mode will write the corrected document to disk,\n"
				+ "backing up the uncorrected document by concatenating a tilde onto its name.\n\n"
				+ "The optional -d switch with a dictionary parameter enters dictionary edit mode.\n"
				+ "Dictionary edit mode allows the user to query and update a dictionary.  Upon\n"
				+ "completion, the updated dictionary is written to disk, while the original is\n"
				+ "backed up by concatenating a tilde onto its name.\n\n"
				+ "The switch -h displays this help and exits.");
		System.exit(0);
	}

	/**
	 * Runs the three modes of the SpellChecker based on the input arguments. DO
	 * NOT change this method in any way other than to set the name and sect
	 * variables.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			doUsage();
			System.exit(-1);
		}

		/*
		 * In order to be considered for the competition, set these variables.
		 */
		String name = "Ram Lutiel"; // First and Last
		String sect = "A"; // "A" or "B"

		Timer timer = new Timer();

		timer.start();

		if (args[0].equals("-h"))
			doHelp();
		else if (args[0].equals("-n"))
			doNonInteractiveMode(args);
		else if (args[0].equals("-d"))
			doDictionaryEditMode(args);
		else
			doInteractiveMode(args);

		timer.stop();

		System.out.println("Student name:   " + name);
		System.out.println("Student sect:   " + sect);
		System.out.println("Execution time: " + timer.runtime() + " ms");
	}

	/**
	 * Carries out the Interactive mode of the Spell Checker.
	 * 
	 * @param args
	 *            the arguments given to the main. The correct number of
	 *            arguments may or may not be contained in it. Call doUsage()
	 *            and exit if the parameter count is incorrect.
	 */
	public static void doInteractiveMode(String[] args) {
		if (args.length < 3) {
			doUsage();
			return;
		}
		Scanner fromKeyboard = null;
		Scanner fromFile = null;
		Dictionary dictionary = null;
		File file = null;
		File file2 = null;
		String[] arrayOfWords;
		Map<Integer, String> hashMap = new HashMap<Integer, String>();
		ArrayList<Integer> lines = new ArrayList<Integer>();
		try {
			fromKeyboard = new Scanner(System.in);
			dictionary = new Dictionary(args[1]);
			file = new File(args[2]);
			file2 = new File(args[2] + "~");
			fromFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (InputMismatchException e) {
			System.out.println("Input does not match");
		}
		int index = 0;
		while (fromFile.hasNextLine()) {
			int lineStart = index;
			ArrayList<Integer> problemWords = new ArrayList<Integer>();
			String line = fromFile.nextLine();
			System.out.println(line);
			arrayOfWords = line.split(" ");
			String linePointingError = new String();
			for (int i = 0; i < arrayOfWords.length; i++) {
				String temp = null;
				String word = arrayOfWords[i];
				if (word.length() > 1) {
					int start = 0;
					int end = word.length() - 1;
					while ((end > 0) && (word.charAt(end) == '.' || word.charAt(end) == ',' || word.charAt(end) == ':'
							|| word.charAt(end) == '?' || word.charAt(end) == ';' || word.charAt(end) == '-')) {
						end--;
					}
					while ((start < word.length() - 1) && (word.charAt(start) == '.' || word.charAt(start) == ','
							|| word.charAt(start) == ':' || word.charAt(start) == '?' || word.charAt(start) == ';'
							|| word.charAt(start) == '-')) {
						start++;
					}
					if (start > word.length() - 1)
						start--;

					if ((end > start) && (start < word.length()) && (end > 0) && (start != end)) {
						end++;
						temp = word.substring(start, end);
					}
				} else {
					temp = word;
				}
				if (dictionary.hasWord(temp)) {
					for (int j = 0; j <= word.length(); j++)
						linePointingError += " ";

					hashMap.put(index, word);
					index++;
				} else {
					linePointingError += "^";
					for (int k = 0; k < word.length(); k++)
						linePointingError += " ";
					problemWords.add(index);
					hashMap.put(index, word);
					index++;
				}
			}
			System.out.println(linePointingError);
			lines.add(index);
			int lineEnd = index;

			for (int z = 0; z < problemWords.size(); z++) {
				System.out.print(hashMap.get(problemWords.get(z)) + ": " + "[r]eplace/[a]ccept?");
				String response = fromKeyboard.next();
				System.out.println();
				if (response.equals("r")) {
					System.out.print("Replacement text: ");
					String correctedWord = fromKeyboard.next();
					while (!isValid(correctedWord)) {
						System.out.println("Invalid word: Enter again please");
						correctedWord = fromKeyboard.next();
					}
					hashMap.replace(problemWords.get(z), correctedWord);
				} else {
					dictionary.addEntry(hashMap.get(problemWords.get(z)));
				}
			}
			for (int y = lineStart; y < lineEnd; y++) {
				if (y == lineEnd - 1) {
					System.out.println(hashMap.get(y));

				} else
					System.out.print(hashMap.get(y) + " ");
			}
			writeToFile(file2, lines, hashMap);
		}

	}

	/**
	 * Copy the content of original file file and 
	 * @param file
	 * @param file2
	 * @param hashMap
	 * @param lines
	 */
	private static void writeToFile(File file, ArrayList<Integer> ListOflines, Map<Integer, String> hashMap) {
		PrintWriter outPut = null;
		try {
			outPut = new PrintWriter(file);
		} catch (Exception e) {
			System.out.println("File not found");
		}
		for (int i = 0; i < ListOflines.size(); i++) {
			int previous = 0;
			if (i > 0)
				previous = ListOflines.get(i);
			for (int j = previous; j < ListOflines.get(i); j++) {
				if (j == ListOflines.get(i) - 1)
					outPut.write(hashMap.get(j) + "\n");
				else
					outPut.write(hashMap.get(j) + " ");
			}
		}
	}

	/**
	 * Carries out the Non-Interactive mode of the Spell Checker.
	 * 
	 * @param args
	 *            the arguments given to the main. The correct number of
	 *            arguments may or may not be contained in it. Call doUsage()
	 *            and exit if the parameter count is incorrect.
	 */
	public static void doNonInteractiveMode(String[] args) {
		if (args.length < 3) {
			doUsage();
			return;
		}
		Dictionary dictionary = null;
		Scanner fromFile = null;
		Scanner fromLine;
		try {
			dictionary = new Dictionary(args[1]);
			fromFile = new Scanner(new File(args[2]));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		while (fromFile.hasNextLine()) {
			String line = fromFile.nextLine();
			line.trim();
			System.out.println(line);
			fromLine = new Scanner(line);
			String linePointingError = new String();
			while (fromLine.hasNext()) {
				String word = fromLine.next();
				String temp = word.replace("[^a-ZA-z'-]", "");
				if (word.length() > 1) {
					int start = 0;
					int end = word.length() - 1;
					while ((end > 0) && (word.charAt(end) == '.' || word.charAt(end) == ',' || word.charAt(end) == ':'
							|| word.charAt(end) == '?' || word.charAt(end) == '-' || word.charAt(end) == ';')) {
						end--;
					}
					while ((start < word.length() - 1) && (word.charAt(start) == '?' || word.charAt(start) == ','
							|| word.charAt(start) == ':' || word.charAt(start) == '.' || word.charAt(start) == '-'
							|| word.charAt(start) == ';')) {
						start++;
					}
					if (start > word.length() - 1)
						start--;

					if ((end > start) && (start < word.length()) && (end > 0) && (start != end)) {
						end++;
						temp = word.substring(start, end);
					}
				} else 
					temp = word;
				if (dictionary.hasWord(temp.toLowerCase())) {
					for (int i = 0; i <= word.length(); i++)
						linePointingError += " ";
				} else {
					linePointingError += "^";
					for (int i = 0; i < word.length(); i++)
						linePointingError += " ";
				}
			}
			System.out.println(linePointingError);
		}
		fromFile.close();

	}

	/**
	 * Carries out the Dictionary Edit mode of the Spell Checker.
	 * 
	 * @param args
	 *            the arguments given to the main. The correct number of
	 *            arguments may or may not be contained in it. Call doUsage()
	 *            and exit if the parameter count is incorrect.
	 */
	public static void doDictionaryEditMode(String[] args) {
		if (args.length < 2) {
			doUsage();
			return;
		}
		Dictionary dictionary = null;
		Scanner fromKeyboard = null;
		Scanner scanOperation = null;
		try {
			dictionary = new Dictionary(args[1]);
			fromKeyboard = new Scanner(System.in);
			scanOperation = new Scanner(System.in);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		System.out.println("Editing " + args[1]);
		String word = "";
		String operation = "n";
		while (!word.equals("!quit")) {
			System.out.print("Word:");
			word = fromKeyboard.next();
			System.out.println();
			if (dictionary.hasWord(word)) {
				System.out.println("'" + word + "'" + " was found");
				System.out.print("[r]emove/[g]et definition/[c]hange " + "definition/do [n]othing: ");
				operation = fromKeyboard.next();
				System.out.println();
				if (operation.equals("r")) {
					dictionary.removeEntry(word);
				} else if (operation.equals("g")) {
					if (dictionary.getDefinitionOf(word) != null)
						System.out.println(dictionary.getDefinitionOf(word));
					else
						System.out.println("<undefine>");
				} else if (operation.equals("c")) {

					System.out.print("New Definition:");
					String newDef = scanOperation.nextLine();
					System.out.println();
					dictionary.updateEntry(word, newDef);
				} else
					continue;
			} else if (word.equals("!quit")) {
				break;
			} else if (!isValid(word)) {
				System.out.println("'" + word + "'" + " is invalid. Please enter a valid word.");
			} else if (!dictionary.hasWord(word)) {
				System.out.println("'" + word + "'" + " not found");
				System.out.print("[a]dd/add with [d]efinition/do [n]othing: ");
				operation = scanOperation.next();
				System.out.println();
				if (operation.equals("a")) {
					dictionary.addEntry(word);
				} else if (operation.equals("d")) {
					scanOperation = new Scanner(System.in);
					System.out.print("Definition:");
					String newDef = scanOperation.nextLine();
					System.out.println();
					dictionary.addEntry(word, newDef);
				}
			}

		}
		File orig = new File(args[1]);
		String str = args[1] + "~";
		orig.renameTo(new File(str));
		try {
			dictionary.printToFile(args[1]);
			orig = new File(args[1]);
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}

	}

	/**
	 * This method will check either the word is valid or not
	 * 
	 * @param word
	 *            word need to check validity
	 * @return true if word is valid false otherwise
	 */
	private static boolean isValid(String word) {
		if (word == null)
			throw new NullPointerException();
		for (int i = 0; i < word.length(); i++) {
			if (!(Character.isAlphabetic(word.charAt(i)) || (word.charAt(i) == '-') || (word.charAt(i) == ',')
					|| (word.charAt(i) == '\'')))
				return false;
		}
		return true;
	}

	/**
	 * Timer class used for this project's competition. DO NOT modify this class
	 * in any way or you will be ineligible for Eternal Glory.
	 */
	private static class Timer {
		private long startTime;
		private long endTime;

		public void start() {
			startTime = System.nanoTime();
		}

		public void stop() {
			endTime = System.nanoTime();
		}

		public long runtime() {
			return endTime - startTime;
		}
	}
}
