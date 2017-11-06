package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for GenomicDNASequence class
 * 
 * @author Ram Luitel
 */
public class GenomicDNASequenceTest {

	/*
	 * object of sequence class
	 */
	private Sequence seq;
	/**
	 * object of Object class
	 */
	private Object obj;
	/*
	 * Character array that is going to pass in DNASequence class
	 */
	private char[] sequence = { 'c', 't', 'T', 'c', 'c', 'A','T','G','c','c','g','c','c' };
	GenomicDNASequence seq1;

	/**
	 * THis is set up method that is going to use the object and variable in
	 * rest of the methods
	 */
	@Before
	public void setup() {
		String str = "CTTCCATGCCGCC";
		seq = new GenomicDNASequence(sequence);
		seq1 = new GenomicDNASequence(str.toCharArray());
		obj = (GenomicDNASequence) seq1;
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
		boolean result = seq.isValidLetter('B');
		assertEquals(msg, false, result);
	}

	/**
	 * This method check the getSeq() method
	 */
	@Test
	public void getSeqTest() {
		char[] copy = seq.getSeq();
		assertTrue(Arrays.equals(sequence, copy));

	}

	/**
	 * This method test equals() method
	 */
	@Test
	public void equalsTest() {
		assertEquals(obj, seq);
	}

	/**
	 * This method test extractExons method
	 */
	@Test
	public void extractExonsTest() {
		int exonPosition[] = { 2, 5, 7,9 };
		String stingResult = "TCCGC";
		char resultSequence[] = seq1.extractExons(exonPosition);
		assertTrue(Arrays.equals(stingResult.toCharArray(), resultSequence));

	}

}
