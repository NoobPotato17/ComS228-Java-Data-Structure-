package edu.iastate.cs228.hw1;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for Sequence class
 * 
 * @author Ram Luitel
 */
public class SequenceTest {

	/*
	 * object of sequence class
	 */
	private Sequence seq;
	/**
	 * object of Object class
	 */
	private Object obj;
	/*
	 * Character array that is going to pass in Sequence class
	 */
	private char[] sequence = { 'a', 'b', 'c', 'd' };

	/**
	 * THis is set up method that is going to use the object and variable in
	 * rest of the methods
	 */
	@Before
	public void setup() {
		String str = "abcd";
		seq = new Sequence(sequence);
		Sequence seq1 = new Sequence(str.toCharArray());
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
		assertTrue(Arrays.equals(sequence, copy));
	}

	/**
	 * This method test equals() method. This method also test the toString
	 * method as toString method is being called in equals method so basically
	 * this test toString method as well
	 */
	@Test
	public void equalsTest() {
		assertEquals(obj, seq);
	}
}
