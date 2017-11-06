package JunitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.iastate.cs228.hw2.CustomComparator;
import edu.iastate.cs228.hw2.QuickSort;

public class QuickSortTest {

	private CustomComparator comp;
	private String[] strArr;
	private String[] sortedArr;
	
	@Before
	public void before() {
		try {
			File f = new File("10.alphabet.txt");
			Scanner sc = new Scanner(f);
			char[] charArr = new char[(int)f.length()];
			int index = 0;
			while(sc.hasNextLine()) {
				charArr[index] = sc.nextLine().charAt(0);
				index++;
			}
			comp = new CustomComparator(charArr);
			sc.close();
			
		}
		catch(FileNotFoundException e) {
			System.out.println("file not found");
		}
		try {
			File f = new File("10.wordlist.txt");
			Scanner sc = new Scanner(f);
			ArrayList<String> strArr = new ArrayList<>();
			while(sc.hasNextLine()) {
				strArr.add(sc.nextLine());
			}
			sc.close();
			this.strArr = strArr.toArray(new String[strArr.size()]);
		}
		catch(FileNotFoundException e) {
			System.out.println("file not found");
		}
		try {
			File f = new File("10.sortedlist.txt");
			Scanner sc = new Scanner(f);
			ArrayList<String> sortedArr = new ArrayList<>();
			while(sc.hasNextLine()) {
				sortedArr.add(sc.nextLine());
			}
			sc.close();
			this.sortedArr = sortedArr.toArray(new String[sortedArr.size()]);
		}
		catch(FileNotFoundException e) {
			System.out.println("File not Found");
		}
		
	}
	
	@Test
	public void testSelectionSort() {
		QuickSort qs = new QuickSort();
		qs.sort(strArr, comp);
		boolean test = true;
		for (int i = 0; i < strArr.length; i++) {
			if (!strArr[i].equals(sortedArr[i])) {
				test = false;
			}
		}
		assertEquals(true, test);
	}

}
