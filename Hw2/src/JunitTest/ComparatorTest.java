package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.iastate.cs228.hw2.CustomComparator;

public class ComparatorTest {

	
	private CustomComparator testComparator;
	
	@Before
	public void before() {
		char[] charArray = {'a','d','b', '-'};
		testComparator = new CustomComparator(charArray);
	}
	
	@Test
	public void testCustomComparator() {
		assertEquals(0, testComparator.getCharacterOrdering('a'));
	}
	
	@Test
	public void testCustomComparator1() {
		assertEquals(1, testComparator.getCharacterOrdering('d'));
	}
	
	@Test
	public void testCustomComparator2() {
		assertEquals(2, testComparator.getCharacterOrdering('b'));
	}

	@Test
	public void testCustomComparator3() {
		assertEquals(3, testComparator.getCharacterOrdering('-'));
	}
	
	@Test
	public void testCustomComparator4() {
		assertEquals(-1, testComparator.getCharacterOrdering(' '));
	}
	
	@Test
	public void testCustomComparator5() {
		assertEquals(-1, testComparator.getCharacterOrdering('f'));
	}
	
	@Test
	public void testCompare() {
		String a = "abd";
		String b = "a-b";
		assertEquals(-1, testComparator.compare(a, b));
	}
	
	@Test
	public void testCompare1() {
		String a = "a-b";
		String b = "abd";
		assertEquals(1, testComparator.compare(a, b));
	}
	
	@Test
	public void testCompare2() {
		String a = "afg";
		String b = "abd";
		assertEquals(-3, testComparator.compare(a, b));
	}
	
	@Test
	public void testCompare3() {
		String a = "abd";
		String b = "abd";
		assertEquals(0, testComparator.compare(a,b));
	}
	
	@Test
	public void testCompare4() {
		String a = "abda";
		String b = "abd";
		assertEquals(1, testComparator.compare(a, b));
	}
	
	@Test
	public void testCompare5() {
		String a = "abda";
		String b = "b";
		assertEquals(-2, testComparator.compare(a, b));
	}
}
