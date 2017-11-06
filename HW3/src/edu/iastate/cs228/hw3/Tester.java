package edu.iastate.cs228.hw3;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
ChunkyList<Character>  list = new ChunkyList<Character>(4);
		
		list.add('A');
		list.add('B');
		list.add('C');
		list.add('E');
		list.add(3,'D');
		list.add('V');
		list.add('W');
		list.add(2, 'X');
		list.add(2, 'Y');
		list.add(2, 'Z');
		
		Character [] arr = {'A','B','C','W','V','D','X','Z'};
		list.removeAll(arr);
		//list.remove(arr[1);
		//list.remove(9);
		
		
		
		System.out.println(list.size());
		System.out.println(list.toStringInternal());

	}

}
