package edu.iastate.cs228.hw4;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Extension of the AbstractCollection class based on a Binary Search Tree.
 * Efficiencies may vary by implementation, but all methods should have at least
 * the worst case runtimes of a standard Tree.
 * 
 * @author Ram Luitel
 */
public class BinarySearchTree<E extends Comparable<? super E>> extends AbstractCollection<E> {

	/**
	 * Member variables to support the tree: - A Node referencing the root of
	 * the tree - An int specifying the element count
	 */
	private Node<E> root;
	private int size;

	/**
	 * Constructs an empty BinarySearchTree
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * Constructs a new BinarySearchTree whose root is exactly the given Node.
	 * (For testing purposes, set the root to the given Node, do not clone it)
	 * 
	 * @param root
	 *            - The root of the new tree
	 * @param size
	 *            - The number of elements already contained in the new tree
	 */
	public BinarySearchTree(Node<E> root, int size) {
		if (root == null || size == 0)
			throw new NullPointerException("No element ");
		this.root = root;
		this.size = size;
	}

	/**
	 * Adds the given item to the tree if it is not already there.
	 * 
	 * @return false if item already exists in the tree and true otherwise.
	 * @param item
	 *            - Item to be added to the tree
	 * @throws IllegalArgumentException
	 *             - If item is null
	 */
	@Override
	public boolean add(E item) throws IllegalArgumentException {

		if ( item == null ) throw new IllegalArgumentException();
		if(contains(item))
			return false;
		addHelper(item);
		size++;
		return true;
	}

	/**
	 * This is a add  helper method
	 * @param item 
	 * 			Item to be added to the tree
	 */
	private void addHelper(E item) {

		Node<E> nodeToAdd = new Node<E>(item);
		if (root == null)
			root = nodeToAdd;
		else {
			Node<E> child = root;
			Node<E> parent;
			while (true) {
				parent = child;
				if (item.compareTo(child.getData()) < 0) {
					child = child.getLeft();
					if (child == null) {
						parent.setLeft(nodeToAdd);
						nodeToAdd.setParent(parent);
						return;
					}
				} else {
					child = child.getRight();
					if (child == null) {
						parent.setRight(nodeToAdd);
						nodeToAdd.setParent(parent);
						return;
					}
				}

			}
		}

	}

	/**
	 * Removes the given item from the tree if it is there. Because the item is
	 * an Object it will need to be cast to an E type. To verify that this is a
	 * safe cast, compare its class to the class of the root Node's data.
	 * 
	 * @return false if the list is empty or item does not exist in the tree,
	 *         true otherwise
	 * @param item
	 *            - The item to be removed from the treef
	 */
	
	@Override
	public boolean remove(Object item) {

		if (item == null || size == 0)
			return false;
		
		if (root.getData().getClass() != item.getClass())
			return false;
		 E temp  = (E) item;
		Node<E> toDel = findNode(temp);
		if (toDel == null)
			return false;
		delete(toDel);
		size--;
		return true;
	}

	/**
	 * Find the node containing given data
	 * @param item
	 *         item in the node
	 * @return
	 * 			node of conatining item
	 */
	private Node<E> findNode(E item) {
		Node<E> curpos = root;
		while (curpos != null) {
			int cv = item.compareTo(curpos.getData());
			if (cv == 0)
				return curpos;
			if (cv > 0)
				curpos = curpos.getRight();
			else
				curpos = curpos.getLeft();
		}
		return null;
	}

	/**
	 *  This is remove helper method
	 * @param aNode
	 */
	private void delete(Node<E> aNode) {
		if (aNode == null)
			throw new NullPointerException("Null pointer");
		Node<E> toDel = aNode;
		if (toDel.getLeft() != null && toDel.getRight() != null) {
			Node<E> sNode =toDel.getSuccessor();
			toDel.setData(sNode.getData());
			toDel = sNode;
		}
		 if (toDel.getLeft() != null)
			linkChildToParent(toDel, toDel.getLeft());
		else 
			linkChildToParent(toDel, toDel.getRight());
	}
	/**
	 * Remove toDel by connecting its only child to its parent.
	 * 
	 * @param toDel
	 * 			node to be deleated
	 * @param child
	 * 			node to be connected 
	 */
	private void linkChildToParent(Node<E> toDel, Node<E> child) {
		if (toDel == root) {
			root = child;
			if (child != null)
				child.setParent(null);
		} 
		else {
			if (toDel.getParent().getLeft() != null && 
					toDel.getParent().getLeft().getData().equals( toDel.getData())){
				toDel.getParent().setLeft(child);
			}
			
			//(toDel.parent.left == toDel)
			else
				toDel.getParent().setRight(child);
			if (child != null)
				child.setParent(toDel.getParent());
		}
	}

	/**
	 * Retrieves data of the Node in the tree that contains item. i.e. the data
	 * such that Node.data.equals(item) is true
	 * 
	 * @return null if item does not exist in the tree, otherwise the data
	 *         stored at the Node that meets the condition above.
	 * @param item
	 *            - The item to be retrieved
	 */
	public E get(E item) {
		
		if(item == null) throw new NullPointerException();
		if (contains(item))
			return findNode(item).getData();

		return null;
	}

	/**
	 * Tests whether or not item exists in the tree. i.e. this should only
	 * return true if a Node exists in the tree such that Node.data.equals(item)
	 * is true
	 * 
	 * @return false if item does not exist in the tree, otherwise true
	 * @param item
	 *            - The item check
	 */
	@Override
	public boolean contains(Object item) {
		if (item == null)
			return false;
		//if(item.getClass() != this.getClass())
			//return false;
		E temp = (E) item;
		return findNode(temp) != null;
	}

	/**
	 * Removes all elements from the tree
	 */
	@Override
	public void clear() {
		size = 0;
		root = null;
	}

	/**
	 * Tests whether or not the tree contains any elements.
	 * 
	 * @return false if the tree contains at least one element, true otherwise.
	 */
	@Override
	public boolean isEmpty() {

		return size == 0;
	}

	/**
	 * Retrieves the number of elements in the tree.
	 */
	@Override
	public int size() {

		return size;
	}

	/**
	 * Returns a new BSTIterator instance.
	 */
	@Override
	public Iterator<E> iterator() {
		return new BSTIterator();
	}

	/**
	 * Returns an ArrayList containing all elements in the tree in the order
	 * given by a preorder traversal of the tree.
	 * 
	 * @return an ArrayList of elements from the traversal.
	 */
	public ArrayList<E> getPreorderTraversal() {
		ArrayList<E> list = new ArrayList<E>();
		recPreOrderTraversal(root, list);
		return list;
	}

	/**
	 * 
	 * @param r
	 */
	private void recPreOrderTraversal(Node<E> r, ArrayList<E> list) {
		if (r == null)
			return;
		list.add(r.getData());
		recPreOrderTraversal(r.getLeft(), list);
		recPreOrderTraversal(r.getRight(), list);

	}

	/**
	 * Returns an ArrayList containing all elements in the tree in the order
	 * given by a postorder traversal of the tree.
	 * 
	 * @return an ArrayList of elements from the traversal.
	 */
	public ArrayList<E> getPostOrderTraversal() {
		ArrayList<E> list = new ArrayList<E>();
		recPostOrderTraversal(root, list);
		return list;
	}

	/**
	 * 
	 * @param r
	 * @return
	 */
	private void recPostOrderTraversal(Node<E> r, ArrayList<E> list) {
		if (r == null)
			return;
		recPostOrderTraversal(r.getLeft(), list);
		recPostOrderTraversal(r.getRight(), list);
		list.add(r.getData());
	}

	/**
	 * Returns an ArrayList containing all elements in the tree in the order
	 * given by a inorder traversal of the tree.
	 * 
	 * @return an ArrayList of elements from the traversal.
	 */
	public ArrayList<E> getInorderTravseral() {
		ArrayList<E> list = new ArrayList<E>();
		recInOrderTraversal(root, list);
		return list;
	}

	/**
	 * 
	 * @param r
	 * @return
	 */
	private void recInOrderTraversal(Node<E> r, ArrayList<E> list) {
		if (r == null)
			return;
		recInOrderTraversal(r.getLeft(), list);
		list.add(r.getData());
		recInOrderTraversal(r.getRight(), list);

	}

	/**
	 * Implementation of the Iterator interface which returns elements in the
	 * order of an inorder traversal using Nodes predecessor and successor.
	 * 
	 * @author
	 */
	private class BSTIterator implements Iterator<E> {
		/**
		 * node to be visited next
		 */
		private Node<E> next;
		/**
		 * node last visited by next()
		 */
		private Node<E> last = null;

		/**
		 * default constructor
		 */
		public BSTIterator() {
			next = root;
			if (next != null)
				while (next.getLeft() != null)
					next = next.getLeft();
		}

		/**
		 * Returns true if more elements exist in the inorder traversal, false
		 * otherwise.
		 */
		@Override
		public boolean hasNext() {
			return next != null;
		}

		/**
		 * Returns the next item in the inorder traversal.
		 * 
		 * @return the next item in the traversal.
		 * @throws IllegalStateException
		 *             - if no more elements exist in the traversal.
		 */
		@Override
		public E next() throws IllegalStateException {
			if (!hasNext())
				throw new NoSuchElementException();
			last = next;
			next = next.getSuccessor();
			return (E) last.getData();
		}

		/**
		 * Removes the last item that was returned by calling next().
		 * 
		 * @throws IllegalStateException
		 *             - if next() has not been called yet or remove() is called
		 *             multiple times in a row.
		 */
		@Override
		public void remove() throws IllegalStateException {
			if (last == null)
				throw new IllegalStateException();

			if (last.getLeft() != null && last.getRight() != null)
				next = last;
			delete(last);
			last = null;
		}
	}

	
}
