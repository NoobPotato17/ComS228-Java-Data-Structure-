package edu.iastate.cs228.hw3;

import static org.junit.Assert.assertEquals;


import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class ChunkyListIteratorTest {

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
	public void iteratorTest() {
		ListIterator<String> iter = list.listIterator();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(| A, B, C, D), (E, F, -, -)]",list.toStringInternal(iter));
	}

	@Test
	public void iteratorTest2() {
		ListIterator<String> iter = list.listIterator(1);
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, | B, C, D), (E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest3() {
		ListIterator<String> iter = list.listIterator(4);
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (| E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest4() {
		ListIterator<String> iter = list.listIterator(6);
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (E, F |, -, -)]", list.toStringInternal(iter));
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void iteratorTest5() {
		ListIterator<String> iter = list.listIterator(7);
	}
	
	@Test
	public void iteratorTest6() {
		ListIterator<String> iter = list.listIterator();
		//System.out.println(list.toStringInternal(iter));
		String data = iter.next();
		boolean test = data.equals("A");
		test = test & "[(A, | B, C, D), (E, F, -, -)]".equals(list.toStringInternal(iter));
		assertEquals(true, test);
	}
	
	@Test
	public void iteratorTest7() {
		ListIterator<String> iter = list.listIterator(3);
		String data = iter.next();
		boolean test = data.equals("D");
		test = test & "[(A, B, C, D), (| E, F, -, -)]".equals(list.toStringInternal(iter));
		assertEquals(true, test);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void iteratorTest8() {
		ListIterator<String> iter = list.listIterator(6);
		String data = iter.next();
	}
	
	@Test
	public void iteratorTest9() {
		boolean test = list.contains("A");
		assertEquals(true, test);
	}
	
	@Test
	public void iteratorTest10() {
		boolean test = list.contains("G");
		assertEquals(false, test);
	}
	
	@Test
	public void iteratorTest11() {
		ListIterator<String> iter = list.listIterator(3);
		//System.out.println(list.toStringInternal(iter));
		int index = iter.previousIndex();
		assertEquals(2, index);
	}
	
	@Test
	public void iteratorTest12() {
		ListIterator<String> iter = list.listIterator();
		//System.out.println(list.toStringInternal(iter));
		int index = iter.previousIndex();
		assertEquals(-1, index);
	}
	
	@Test
	public void iteratorTest13() {
		list.add(2, "2");
		ListIterator<String> iter = list.listIterator(3);
		//System.out.println(list.toStringInternal(iter));
		int index = iter.previousIndex();
		assertEquals(2, index);
	}
	
	@Test
	public void iteratorTest14() {
		ListIterator<String> iter = list.listIterator(3);
		String data = iter.previous();
		assertEquals("C", data);
	}
	
	@Test
	public void iteratorTest15() {
		list.add(2, "2");
		ListIterator<String> iter = list.listIterator(3);
		//System.out.println(list.toStringInternal(iter));
		String data = iter.previous();
		assertEquals("2", data);
	}
	
	@Test
	public void iteratorTest16() {
		int index = list.indexOf("A");
		assertEquals(0, index);
	}
	
	@Test
	public void iteratorTest17() {
		int index = list.indexOf("E");
		assertEquals(4, index);
	}
	
	@Test
	public void iteratorTest18() {
		list.add(2,"2");
		int index = list.indexOf("C");
		assertEquals(3, index);
	}
	
	@Test
	public void iteratorTest19() {
		ListIterator<String> iter = list.listIterator(2);
		iter.add("2");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, 2, -), (| C, D, -, -), (E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest20() {
		ListIterator<String> iter = list.listIterator(2);
		iter.add("2");
		//System.out.println(list.toStringInternal(iter));
		iter.add("1");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, 2, 1), (| C, D, -, -), (E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest21() {
		ListIterator<String> iter = list.listIterator(4);
		iter.add("4");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (4, | E, F, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest22() {
		ListIterator<String> iter = list.listIterator(5);
		iter.add("5");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (E, 5, | F, -)]", list.toStringInternal(iter));
	}
	
	@Test (expected = IllegalStateException.class)
	public void iteratorTest23() {
		ListIterator<String> iter = list.listIterator(4);
		iter.remove();
	}
	
	@Test (expected = IllegalStateException.class)
	public void iteratorTest24() {
		ListIterator<String> iter = list.listIterator(4);
		iter.next();
		iter.remove();
		iter.remove();
	}
	
	@Test (expected = IllegalStateException.class)
	public void iteratorTest25() {
		ListIterator<String> iter = list.listIterator(4);
		iter.next();
		iter.add("G");
		iter.remove();
	}
	
	@Test
	public void iteratorTest26() {
		ListIterator<String> iter = list.listIterator(4);
		//System.out.println(list.toStringInternal(iter));
		iter.next();
		//System.out.println(list.toStringInternal(iter));
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (| F, -, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest27() {
		ListIterator<String> iter = list.listIterator(4);
		//System.out.println(list.toStringInternal(iter));
		iter.previous();
		//System.out.println(list.toStringInternal(iter));
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, -), (| E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest28() {
		ListIterator<String> iter = list.listIterator(5);
		//System.out.println(list.toStringInternal(iter));
		iter.next();
		//System.out.println(list.toStringInternal(iter));
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (E |, -, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest29() {
		ListIterator<String> iter = list.listIterator(0);
		iter.next();
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(| B, C, D, -), (E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest30() {
		ListIterator<String> iter = list.listIterator(0);
		//System.out.println("\n");
		//System.out.println(list.toStringInternal(iter));
		iter.next();
		//System.out.println(list.toStringInternal(iter));
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		iter.next();
		//System.out.println(list.toStringInternal(iter));
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		iter.next();
		//System.out.println(list.toStringInternal(iter));
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(| D, E, F, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest31() {
		list.add("G");
		ListIterator<String> iter = list.listIterator(0);
		iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(| D, E, -, -), (F, G, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest32() {
		ListIterator<String> iter = list.listIterator(0);
		iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		iter.next();
		iter.next();
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(C, | E, F, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest33() {
		list.add("G");
		ListIterator<String> iter = list.listIterator(0);
		iter.next();
		iter.remove();
		iter.next();
		iter.remove();
		iter.next();
		iter.next();
		iter.remove();
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(C, | E, -, -), (F, G, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest34() {
		ListIterator<String> iter = list.listIterator(4);
		iter.next();
		iter.set("G");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (G, | F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest35() {
		ListIterator<String> iter = list.listIterator(3);
		iter.next();
		iter.set("G");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, G), (| E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest36() {
		ListIterator<String> iter = list.listIterator(4);
		iter.previous();
		//System.out.println(list.toStringInternal(iter));
		iter.set("G");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, | G), (E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest37() {
		ListIterator<String> iter = list.listIterator(3);
		iter.next();
		iter.set("G");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, G), (| E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest38() {
		ListIterator<String> iter = list.listIterator(6);
		System.out.println(list.toStringInternal(iter));
		iter.previous();
		System.out.println(list.toStringInternal(iter));
		iter.set("H");
		System.out.println(list.toStringInternal(iter));
		iter.set("G");
		System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (E, | G, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest39() {
		ListIterator<String> iter = list.listIterator(1);
		//System.out.println(list.toStringInternal(iter));
		iter.previous();
		//System.out.println(list.toStringInternal(iter));
		iter.set("H");
		//System.out.println(list.toStringInternal(iter));
		iter.set("G");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(| G, B, C, D), (E, F, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest40() {
		ListIterator<String> iter = list.listIterator(5);
		//System.out.println(list.toStringInternal(iter));
		System.out.println(iter.next());
		//System.out.println(list.toStringInternal(iter));
		iter.set("G");
		//System.out.println(list.toStringInternal(iter));
		assertEquals("[(A, B, C, D), (E, G |, -, -)]", list.toStringInternal(iter));
	}
	
	@Test
	public void iteratorTest41() {
		//System.out.println(list.toStringInternal(iter));
		String data = list.get(0);
		assertEquals("A", data);
	}
	
	@Test
	public void iteratorTest42() {
		//System.out.println(list.toStringInternal(iter));
		String data = list.get(3);
		assertEquals("D", data);
	}
	
	@Test
	public void iteratorTest43() {
		//System.out.println(list.toStringInternal(iter));
		String data = list.get(4);
		assertEquals("E", data);
	}
	
	@Test
	public void iteratorTest44() {
		//System.out.println(list.toStringInternal(iter));
		String data = list.get(5);
		assertEquals("F", data);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void iteratorTest45() {
		//System.out.println(list.toStringInternal(iter));
		String data = list.get(6);
	}
	
	@Test
	public void iteratorTest46() {
		list.add("G");
		list.add("H");
		list.add(2, "2");
		//System.out.println(list.toStringInternal());
		String data = list.get(2);
		assertEquals("2", data);
	}
	
	@Test
	public void iteratorTest47() {
		list.add("G");
		list.add("H");
		list.add(2, "2");
		//System.out.println(list.toStringInternal());
		String data = list.get(3);
		assertEquals("C", data);
	}
	
	@Test
	public void iteratorTest48() {
		list.add("G");
		list.add("H");
		list.add(2, "2");
		//System.out.println(list.toStringInternal());
		String data = list.get(5);
		assertEquals("E", data);
	}
	
	@Test
	public void iteratorTest49() {
		String arr[] = {"A","B"};
		list.removeAll(arr);
		//System.out.println(list.toStringInternal());
		assertEquals("[(C, D, -, -), (E, F, -, -)]", list.toStringInternal());
	}
	
	@Test
	public void iteratorTest50() {
		list.add("A");
		list.add("B");
		String arr[] = {"A","B"};
		list.removeAll(arr);
		//System.out.println(list.toStringInternal());
		assertEquals("[(C, D, -, -), (E, F, -, -)]", list.toStringInternal());
	}
	
	@Test
	public void iteratorTest51() {
		list.add("G");
		list.add("H");
		list.add(2, "2");
		String arr[] = {"2","H","A"};
		list.removeAll(arr);
		//System.out.println(list.toStringInternal());
		assertEquals("[(B, C, D, -), (E, F, G, -)]", list.toStringInternal());
	}
	
	@Test
	public void iteratorTest52()
	{
		ListIterator<String> iter = list.listIterator(2);
		System.out.println(list.toStringInternal(iter));
		System.out.println(iter.hasNext());
		iter.next();
		System.out.println(list.toStringInternal(iter));
		System.out.println(iter.hasNext());
		iter.next();
		System.out.println(list.toStringInternal(iter));
		System.out.println(iter.hasNext());
		iter.next();
		System.out.println(list.toStringInternal(iter));
		System.out.println(iter.hasNext());
		iter.next();
		System.out.println(list.toStringInternal(iter));
	}
}
