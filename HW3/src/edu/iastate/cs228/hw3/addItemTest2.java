package edu.iastate.cs228.hw3;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

public class addItemTest2 {

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
	public void initialTest() {
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, B, C, D), (E, F, -, -)]");
		test = test && (6==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest() {
		list.add(6, "G");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, B, C, D), (E, F, G, -)]");
		test = test && (7==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest2() {
		list.add(1,"1");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, 1, B, -), (C, D, -, -), (E, F, -, -)]");
		test = test && (7==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest3() {
		list.add(2, "2");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, B, 2, -), (C, D, -, -), (E, F, -, -)]");
		test = test && (7==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest4() {
		list.add(3, "3");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, B, -, -), (C, 3, D, -), (E, F, -, -)]");
		test = test && (7==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest6() {
		list.add(4, "4");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, B, C, D), (4, E, F, -)]");
		test = test && (7==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest7() {
		list.add(3, "3");
		list.add(2, "2");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(A, B, 2, -), (C, 3, D, -), (E, F, -, -)]");
		test = test && (8==list.size());
		assertEquals(true, test);
	}
	
	@Test
	public void addPosTest8() {
		list.add(0,"0");
		//uncomment if you want to see what your list looks like
		//System.out.println(list.toStringInternal());
		boolean test = list.toStringInternal().equals("[(0, A, B, -), (C, D, -, -), (E, F, -, -)]");
		test = test && (7==list.size());
		assertEquals(true, test);
	}
}
