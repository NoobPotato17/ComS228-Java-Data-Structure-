package edu.iastate.cs228.hw4;

/**
 * Node class which is used by the BinarySearchTree class to store data.
 * Normally this class would be a private class inside the BST class, it is
 * public for testing purposes. Because of this, most methods and constructors
 * have been implemented. You may add new protected methods, but DO NOT modify
 * already implemented or change their behavior or you risk losing points in
 * tests.
 * 
 * @author Ram Luitel
 */
public class Node<T> {

    /**
     * Instance variables of the Nodes which are connected to this Node and the
     * data it contains.
     */
    private Node<T> left, right, parent;
    private T data;

    /**
     * Creates a new Node that is disconnected from all others which stores the
     * given data.
     * 
     * @param d
     */
    public Node(T d) {
        this(null, null, null, d);
    }

    /**
     * Creates a new Node that is connected to all the given Nodes and stores
     * the given data.
     * 
     * @param d
     */
    public Node(Node<T> left, Node<T> right, Node<T> parent, T d) {
        this.data = d;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public Node<T> getLeft() {
        return this.left;
    }

    public void setLeft(Node<T> newLeft) {
        this.left = newLeft;
    }

    public Node<T> getRight() {
        return this.right;
    }

    public void setRight(Node<T> newRight) {
        this.right = newRight;
    }

    public Node<T> getParent() {
        return this.parent;
    }

    public void setParent(Node<T> newParent) {
        this.parent = newParent;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T newData) {
        this.data = newData;
    }

    /**
     * Returns the next Node in an inorder traversal of the BST which contains
     * this Node.
     * 
     * @return the next Node in the traversal.
     */
    public Node<T> getSuccessor() {
    	
        Node<T> successor = this;
        if (successor.right != null) {
        	successor = successor.right;
			while (successor.left != null) {
				successor = successor.left;
			}
			return successor;
		}
		Node<T> pSuccessor = successor.parent;
		Node<T> child = successor;
		while (pSuccessor != null && pSuccessor.right == child) {
			child = pSuccessor;
			pSuccessor = pSuccessor.parent;
		}
		return pSuccessor; 
    }
    
    /**
     * Returns the previous Node in an inorder traversal of the BST which
     * contains this Node.
     * 
     * @return the previous Node in the traversal.
     */
    public Node<T> getPredecessor() {
        
    	Node<T> predecessor = this;
        if (predecessor.left != null) {
        	predecessor = predecessor.left;
			while (predecessor.left != null) {
				predecessor = predecessor.left;
			}
			return predecessor;
		}
		Node<T> pPredecessor = predecessor.parent;
		Node<T> child = predecessor;
		while (pPredecessor != null && pPredecessor.left == child) {
			child = pPredecessor;
			pPredecessor = pPredecessor.parent;
		}
		return pPredecessor; 
    }
}
