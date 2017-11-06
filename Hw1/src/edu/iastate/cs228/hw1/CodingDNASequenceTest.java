package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for CodingDNASequence class
 * 
 * @author Ram Luitel
 */
public class CodingDNASequenceTest {

	/*
	 * object of CodingDNASequence class
	 */
	private CodingDNASequence seq;
	/**
	 * object of Object class
	 */
	private Object obj;
	/*
	 * Character array that is going to pass
	 */
	private char[] dnaSequence = { 'a', 't', 'g' };

	/**
	 * This is set up method that is going to use the object and variable in
	 * rest of the methods
	 */
	@Before
	public void setup() {
		String str = "ATG";
		seq = new CodingDNASequence(dnaSequence);
		DNASequence seq1 = new CodingDNASequence(str.toCharArray());
		obj = (Sequence) seq1;
	}

	/**
	 * This method check the valid character
	 */
	@Test
	public void isValidLetterTest() {
		String msg = " character a is valid";
		boolean result = seq.isValidLetter('a');
		assertEquals(msg, true, result);
	}

	/**
	 * This method check the invalid valid character
	 */
	@Test
	public void isValidLetterTest1() {
		String msg = " character a is valid";
		boolean result = seq.isValidLetter('$');
		assertEquals(msg, false, result);
	}

	/**
	 * This method check the getSeq() method
	 */
	@Test
	public void getSeqTest() {
		char[] copy = seq.getSeq();
		assertTrue(Arrays.equals(dnaSequence, copy));
	}

	/**
	 * This method test equals() method
	 */
	@Test
	public void equalsTest() {
		assertEquals(obj, seq);
	}

	/**
	 * This method test checkStartCodon methods
	 */
	@Test
	public void checkStartCodonTest() {
		String msg = "Have valid start condo";
		boolean result = seq.checkStartCodon();
		assertEquals(msg, true, result);
	}

	/**
	 * This method test translate method
	 */
	@Test
	public void translateTest() {
		String test1 = "ATGCCTCAATAG";
		//String test2 = "ATGTGAAAAAAAAAAAAAAAA";
		//String test = "ATGCCGCCACCGCTGACGTCGTCTACGAAACTCAG";
		seq = new CodingDNASequence(test1.toCharArray());
		String result = "MPQ";
		//String result2 ="M";
		//assertTrue(Arrays.equals(seq.translate(), result2.toCharArray()));
		assertEquals(seq.translate().length, result.length());
	}
}
