package edu.iastate.cs228.hw3;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

public class removeItemTest {

	private ChunkyList<String> list = new ChunkyList<String>();
	
	@Before
	public void initialize() {
		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.add("F");
	}
	
	@Test
	public void removeTest() {
		String data = list.remove(0);
		boolean test = data.equals("A");

		test = test && "[(B, C, D, -), (E, F, -, -)]".equals(list.toStringInternal());
		assertEquals(true, test);
	}
	
	@Test
	public void removeTest1() {
		String data = list.remove(5);
		boolean test = data.equals("F");
		test = test && "[(A, B, C, D), (E, -, -, -)]".equals(list.toStringInternal());
		assertEquals(true, test);
	}
	
	@Test
	public void removeTest2() {
		String data = list.remove(5);
		boolean test = data.equals("F");
		data = list.remove(4);
		test = test && data.equals("E");
		test = test && "[(A, B, C, D)]".equals(list.toStringInternal());
		assertEquals(true, test);
	}

	@Test
	public void removeTest3() {
		String data = list.remove(0);
		boolean test = data.equals("A");
		System.out.println(list.toStringInternal());
		data = list.remove(0);
		test = test && data.equals("B");
		System.out.println(list.toStringInternal());
		data = list.remove(0);
		test = test && data.equals("C");
		System.out.println(list.toStringInternal());
		test = test && "[(D, E, F, -)]".equals(list.toStringInternal());
		assertEquals(true, test);
	}
	
	@Test
	public void removeTest4() {
		list.add("G");
		String data = list.remove(0);
		boolean test = data.equals("A");
		data = list.remove(0);
		test = test && data.equals("B");
		data = list.remove(0);
		test = test && data.equals("C");
		test = test && "[(D, E, -, -), (F, G, -, -)]".equals(list.toStringInternal());
		assertEquals(true, test);
	}
}
