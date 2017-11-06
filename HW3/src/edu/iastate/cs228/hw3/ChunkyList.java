package edu.iastate.cs228.hw3;


/*
 * @author Ram	Luitel 
 */
import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 *
 * A link to the JavaDoc documentation for the interface AbstractSequentialList
 * <E> is provided next to the pdf spec on Blackboard.
 *
 * You should carefully study the complete methods given below to learn how to
 * go about implementing other methods.
 *
 * You are encouraged to introduce private methods that can be used to simplify
 * the implementation of public methods.
 */
public class ChunkyList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public ChunkyList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize
	 *            number of elements that may be stored in each node, must be an
	 *            even number
	 */
	public ChunkyList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public ChunkyList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	/**
	 * Return the size of elements in this chunkyList
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Add item at the end of the list
	 * 
	 * @param item
	 *            to be added
	 */
	@Override
	public boolean add(E item) {
		add(size, item);
		return true;
	}

	/**
	 * Add item at given logical index
	 * 
	 * @param pos
	 *            the logical index of element
	 * @param item
	 *            to be added
	 */
	@Override
	public void add(int pos, E item) {
		if (pos < 0 || pos > size)
			throw new IndexOutOfBoundsException();
		Node node = find(pos).node;
		int offset = find(pos).offset;
		addHelper(item, node, offset);
	}

	/**
	 * Adds item at the position corresponding to the given node and offset
	 * according to the add rules.
	 * 
	 * @param item
	 *            to be added
	 * @param currentNode
	 *            the current node
	 * @param offset
	 *            the offset with in the node, where item will be added
	 * @return NodeInfo object; containing the information about node and offset
	 *         where item need to be added.
	 */
	private NodeInfo addHelper(E item, Node currentNode, int offset) {
		if (size == 0 || (currentNode == tail && currentNode.previous.count == nodeSize)) {
			Node newNode = new Node();
			link(tail.previous, newNode);
			newNode.addItem(item);
			size++;
			return new NodeInfo(newNode, 0);
		}
		if (offset == 0 && currentNode.previous != head && currentNode.previous.count < nodeSize) {
			currentNode = currentNode.previous;
			offset = currentNode.count;
		}
		if (currentNode.count < nodeSize) {
			currentNode.addItem(offset, item);
			size++;
			return new NodeInfo(currentNode, offset);
		} else {

			Node newNode1 = new Node();
			int start = nodeSize / 2;
			for (int i = start; i < nodeSize; ++i) {
				newNode1.addItem(currentNode.data[i]);
				currentNode.data[i] = null;
			}
			currentNode.count = start;
			if (offset <= nodeSize / 2) {
				currentNode.addItem(offset, item);
				link(currentNode, newNode1);
				size++;
				return new NodeInfo(currentNode, offset);
			} else {
				offset = offset - nodeSize / 2;
				newNode1.addItem(offset, item);
				link(currentNode, newNode1);
				size++;

				return new NodeInfo(newNode1, offset);
			}
		}
	}

	/**
	 * This method help to link the node that need to be added
	 * 
	 * @param current
	 *            the current node
	 * @param newNode
	 *            the new node being added
	 */
	private void link(Node current, Node newNode) {
		newNode.next = current.next;
		newNode.next.previous = newNode;
		newNode.previous = current;
		current.next = newNode;
	}

	/**
	 * This method help to unlink the node that need to be deleted
	 * 
	 * @param needToRemove
	 *            the node that need to be delete
	 */
	private void unlink(Node needToRemove) {
		needToRemove.previous.next = needToRemove.next;
		needToRemove.next.previous = needToRemove.previous;
	}

	/**
	 * Remove element from given index.
	 * 
	 * @param pos
	 *            the position of element that need to be removed.
	 * @return element at index pos.
	 */
	@Override
	public E remove(int pos) {
		if (pos < 0 || pos > size)
			throw new IndexOutOfBoundsException(pos + " is out of bound");

		Node removeNode = find(pos).node;
		int offset = find(pos).offset;
		E removedItem = removeNode.data[offset];
		removeNode.removeItem(offset);
		size--;

		if (removeNode.count < nodeSize / 2) {
			if (removeNode.next != tail) {
				moveEments(removeNode, removeNode.next);
			} else if (removeNode.count == 0) {
				unlink(removeNode);
			}
		}
		return removedItem;
	}

	/**
	 * This method will merge the the element if necessary to do so.
	 * 
	 * @param node
	 *            the node where elements are merging.
	 * @param nextNode
	 *            the node from where the elements are merging from.
	 */
	private void moveEments(Node node, Node nextNode) {
		int element = nextNode.count;
		if (element > nodeSize / 2) {
			node.addItem(nextNode.data[0]);
			nextNode.removeItem(0);
		} else {
			for (int i = 0; i < nextNode.count; i++) {
				node.addItem(nextNode.data[i]);
			}
			unlink(nextNode);
		}
	}

	/**
	 * Removes each element (from the list) that is equal to an element in an
	 * array arr[]. You should use an efficient algorithm to implement this
	 * method. One efficient implementation is given as follows. Sort the array
	 * arr[] with Arrays.sort(). Use a ListIterator object to access each
	 * element in this list. If the element is found in the array arr[] with
	 * Arrays.binarySearch(), then remove the element from the list.
	 * 
	 * @param arr
	 *            array of (unsorted) elements
	 */
	public void removeAll(E[] arr) {

		if (arr == null || arr.length == 0)
			return;
		Arrays.sort(arr);
		ChunkyListIterator list = new ChunkyListIterator(0);
		while (list.hasNext()) {
			E item = list.next();
			if (Arrays.binarySearch(arr, item) >= 0)
				list.remove();
		}
	}

	/**
	 * Find the node and the offset within that node for an element.
	 * 
	 * @param pos
	 *            where items need to be added or remove
	 * @return NodeInfo object; containing the information about node and offset
	 *         where item need to be added or remove.
	 */
	private NodeInfo find(int pos) {
		if (pos == size)
			return new NodeInfo(tail, 0);

		if (pos < 0 || pos > size)
			throw new IndexOutOfBoundsException("Index " + pos + " out of bound");
		Node currentNode = head.next;
		int numOfelements = 0;
		int tempSize = numOfelements + currentNode.count - 1;

		while (pos > tempSize) {
			numOfelements += currentNode.count;
			currentNode = currentNode.next;
			tempSize = numOfelements + currentNode.count - 1;
		}
		return new NodeInfo(currentNode, pos - numOfelements);
	}

	/**
	 * Helper class to keep track of node and offset
	 * 
	 * @author Ram Luitel
	 *
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * Returns an iterator over the elements in this list
	 * 
	 * @return Iterator for ChunkyList
	 */
	@Override
	public Iterator<E> iterator() {

		return new ChunkyListIterator();
	}

	/**
	 * Returns an iterator over the elements in this list
	 * 
	 * @return Iterator for ChunkyList
	 */
	@Override
	public ListIterator<E> listIterator() {

		return new ChunkyListIterator();
	}

	/**
	 * Returns an iterator over the elements in this list
	 * 
	 * @param index,
	 *            the starting index to iterate
	 * @return Iterator for ChunkyList
	 */
	@Override
	public ListIterator<E> listIterator(int index) {

		return new ChunkyListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal
	 * structure of the nodes and the position of the iterator.
	 *
	 * This complete example illustrates how a method in this data structure is
	 * implemented. You should study this code carefully and use this method to
	 * show the contents of the list every time you implement and use a new
	 * method.
	 *
	 * @param iter
	 *            an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			for (int i = 0; i < nodeSize; ++i) {
				if (i > 0)
					sb.append(", ");
				E data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements
	 * in an array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		// Unchecked warning unavoidable.
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the
		 * number of elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset.
		 * Precondition: count < nodeSize
		 * 
		 * @param item
		 *            element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " +
			// count + " to node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements
		 * to the right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset
		 *            array index at which to put the new element
		 * @param item
		 *            element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			++count;
			data[offset] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " +
			// offset + " to node: " + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting
		 * elements left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	/**
	 * Iterator class
	 * 
	 * @author Ram Luitel
	 *
	 */
	private class ChunkyListIterator implements ListIterator<E> {

		/**
		 * logical indication of no direction of cursor
		 */
		private static final int NONE = 0;
		/**
		 * logical indication of cursor direction as frontwards
		 */
		private static final int FRONT = 1;
		/**
		 * logical indication of cursor direction as backwards
		 */
		private static final int BEHIND = -1;

		/**
		 * Actual index of element
		 */
		private int logicalIndex;
		/**
		 * directio to determine which way to iterate
		 */
		private int direction;
		/**
		 * The current node
		 */
		private Node currentNode;
		/**
		 * The offset with in the current node
		 */
		private int currentOffset;
		/**
		 * Node needs to be removed
		 */
		private Node removeNode;
		/**
		 * The offset of removed element
		 */
		private int offsetOfRemoveNode;

		/**
		 * Default constructor
		 */
		public ChunkyListIterator() {
			direction = NONE;
			logicalIndex = 0;
			currentNode = head.next;
			currentOffset = 0;
			removeNode = null;
			offsetOfRemoveNode = -1;
		}

		/**
		 * Constructor finds node at a given position.
		 * 
		 * @param pos
		 */
		public ChunkyListIterator(int pos) {
			this();
			if (pos < 0 || pos > size)
				throw new IndexOutOfBoundsException("Index out of bound");
			NodeInfo nodInfo = find(pos);
			currentNode = nodInfo.node;
			currentOffset = nodInfo.offset;
			logicalIndex = pos;
		}

		/**
		 * Determine whether there is next elemtn or not in this list
		 * 
		 * @return true if this list iterator has more elements when traversing
		 *         the list
		 */
		@Override
		public boolean hasNext() {

			return logicalIndex < size;
		}

		/**
		 * Returns the next element in the list and advances the cursor
		 * position.
		 * 
		 * @return next elemnet if there is any
		 */
		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException("No such element found");

			E nextItem = currentNode.data[currentOffset];
			direction = BEHIND;
			removeNode = currentNode;
			offsetOfRemoveNode = currentOffset;
			currentOffset++;
			if (currentOffset >= currentNode.count) {
				currentNode = currentNode.next;
				currentOffset = 0;
			}
			logicalIndex++;
			return nextItem;
		}

		/**
		 * Removes from the list the last element that was returned by next() or
		 * previous()
		 */
		@Override
		public void remove() {
			if (direction == NONE) {
				throw new IllegalStateException();
			}
			removeNode.removeItem(offsetOfRemoveNode);
			boolean isMerge = (removeNode.count < nodeSize / 2 && removeNode.next != tail);
			if (direction == FRONT && (currentOffset == currentNode.count && !isMerge)) {
				currentNode = currentNode.next;
				currentOffset = 0;
			} else {

				if (offsetOfRemoveNode < removeNode.count || isMerge) {
					currentNode = removeNode;
					currentOffset = offsetOfRemoveNode;
				}
				logicalIndex--;
			}
			if (removeNode.count < nodeSize / 2) {
				if (removeNode.next != tail) {
					moveEments(removeNode, removeNode.next);
				} else if (removeNode.count == 0) {
					unlink(removeNode);
				}
			}
			size--;
			direction = NONE;
			removeNode = null;
			offsetOfRemoveNode = -1;
		}

		/**
		 * Inserts the specified element into the list
		 * 
		 * @param item
		 *            to be inserted
		 */
		@Override
		public void add(E item) {
			NodeInfo infoOfAddNode = addHelper(item, currentNode, currentOffset);
			Node nodeTobeAdded = infoOfAddNode.node;
			int offset = infoOfAddNode.offset;

			if (offset < nodeTobeAdded.count - 1) {
				currentNode = nodeTobeAdded;
				currentOffset = offset + 1;
			} else {
				currentNode = nodeTobeAdded.next;
				currentOffset = 0;
			}
			direction = NONE;
			removeNode = null;
			offsetOfRemoveNode = -1;
			logicalIndex++;
		}

		/**
		 * Returns true if this list iterator has more elements when traversing
		 * the list in the reverse direction
		 * 
		 * @return true if there is previous element fasle otehrwise
		 */
		@Override
		public boolean hasPrevious() {

			return logicalIndex > 0;
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to next()
		 * 
		 * @return logoicalIndex the index of the next element
		 */
		@Override
		public int nextIndex() {

			return logicalIndex;
		}

		/**
		 * Returns the previous element in the list and moves the cursor
		 * position backwards.
		 * 
		 * @return previous element
		 */
		@Override
		public E previous() {
			if (!hasPrevious())
				throw new NoSuchElementException("No such element present");
			direction = FRONT;
			currentOffset--;
			if (currentOffset < 0) {
				currentNode = currentNode.previous;
				currentOffset = currentNode.count - 1;
			}
			removeNode = currentNode;
			offsetOfRemoveNode = currentOffset;
			logicalIndex--;
			return currentNode.data[currentOffset];
		}

		/**
		 * Returns the index of the element that would be returned by a
		 * subsequent call to previous().
		 * 
		 * @return index of the element that would be returned by a subsequent
		 *         call to previous().
		 */
		@Override
		public int previousIndex() {

			return logicalIndex - 1;
		}

		/**
		 * Replaces the last element returned by next() or previous() with the
		 * specified element
		 * 
		 * @param item
		 *            to be set
		 */
		@Override
		public void set(E item) {
			if (direction == NONE)
				throw new IllegalStateException();

			removeNode.data[offsetOfRemoveNode] = item;
		}
	}
}
