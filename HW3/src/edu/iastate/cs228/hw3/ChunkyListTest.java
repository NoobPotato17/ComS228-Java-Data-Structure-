package edu.iastate.cs228.hw3;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * 
 * @author Caleb Brose
 *
 */
public class ChunkyListTest
{
	ChunkyList<String> cl;

	@Before
	public void setup()
	{
		cl = new ChunkyList<String>(4);
	}

	/**
	 * Creates the base example given in the spec
	 */
	private void createGiven()
	{
		cl.add("A");
		cl.add("B");
		cl.add("X");
		cl.add("X");
		cl.add("C");
		cl.add("D");
		cl.add("E");
		cl.remove(2);
		cl.remove(2);
		// System.out.println(cl.toStringInternal());
	}

	/**
	 * Adds a single element
	 */
	@Test
	public void singleAdd()
	{
		cl.add("A");
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals("[(A, -, -, -)]"));
	}

	/**
	 * Adds three elements, all should remain in the same node
	 */
	@Test
	public void multiAdd()
	{
		cl.add("A");
		cl.add("B");
		cl.add("C");
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals("[(A, B, C, -)]"));
	}

	/**
	 * Adds six elements, creating a second node in the process
	 */
	@Test
	public void splitAdd()
	{
		cl.add("A");
		cl.add("B");
		cl.add("C");
		cl.add("D");
		cl.add("E");
		cl.add("F");
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals("[(A, B, C, D), (E, F, -, -)]"));
	}

	/**
	 * All add examples in the spec
	 */
	@Test
	public void insertAdd()
	{
		createGiven();
		cl.add("V");
		cl.add("W");
		cl.add(2, "X");
		cl.add(2, "Y");
		cl.add(2, "Z");
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals(
				"[(A, B, Z, -), (Y, X, -, -), (C, D, E, V), (W, -, -, -)]"));
	}

	/**
	 * Removes a single element, no shifting should be needed
	 */
	@Test
	public void removeSimple()
	{
		insertAdd();
		cl.remove(7);
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals(
				"[(A, B, Z, -), (Y, X, -, -), (C, D, V, -), (W, -, -, -)]"));
	}
	
	@Test
	public void removeSimpleEND()
	{
		insertAdd();
		cl.remove(9);
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals(
				"[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]"));
	}
	

	/**
	 * Removes a single element, should delete a node in the process
	 */
	@Test
	public void removeNode()
	{
		insertAdd();
		cl.remove(9);
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals(
				"[(A, B, Z, -), (Y, X, -, -), (C, D, E, V)]"));
	}

	/**
	 * Removes a single element, forces a mini-merge
	 */
	@Test
	public void removeMiniMerge()
	{
		insertAdd();
		cl.remove(4);
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals(
				"[(A, B, Z, -), (Y, C, -, -), (D, E, V, -), (W, -, -, -)]"));
	}

	/**
	 * Removes two elements, forces a full merge
	 */
	@Test
	public void removeFullMerge()
	{
		insertAdd();
		cl.remove(1);
		cl.remove(1);
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals(
				"[(A, Y, X, -), (C, D, E, V), (W, -, -, -)]"));
	}

	/**
	 * Tests all remove examples given in spec
	 */
	@Test
	public void deleteRemoveFull()
	{
		insertAdd();
		cl.remove(9);
		cl.remove(3);
		cl.remove(3);
		cl.remove(5);
		cl.remove(3);
		// System.out.println(cl.toStringInternal());
		assertTrue(cl.toStringInternal().equals("[(A, B, Z, -), (D, V, -, -)]"));
	}
}
