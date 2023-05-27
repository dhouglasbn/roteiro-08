
package adt.bst;

import adt.bst.BSTNode.Builder;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return recursiveHeight(this.root);
	}

	@Override
	public BSTNode<T> search(T element) {
		return this.recursiveSearch(this.root, element);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void insert(T element) {
		BSTNode<T> newNode;
		if (!this.isEmpty()) {
			newNode = (BSTNode<T>) new BSTNode.Builder<T>()
								.data(element)
								.left(null)
								.right(null)
								.parent(null)
								.build();
			this.root = newNode;
		} else {
			this.recursiveInsert(this.root, element);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		BSTNode<T> result = null;
		if (!this.isEmpty()) result = recursiveMaximum(this.root);
		return result;
	}


	@Override
	public BSTNode<T> minimum() {
		BSTNode<T> result = null;
		if (!this.isEmpty()) result = recursiveMinimum(this.root);
		return result;
	}


	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> node = this.search(element);
		if (node != null) {
			if (node.getRight() != null) {
				result = this.recursiveMinimum((BSTNode<T>) node.getRight());
			} else {
				BSTNode<T> aux = (BSTNode<T>) node.getParent();
				
				while (aux != null && aux.getData().compareTo(node.getData()) == -1) {
					aux = (BSTNode<T>) aux.getParent();
				}
				
				result = aux;
			}
		}
		return result;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> node = this.search(element);
		if (node != null) {
			if (node.getLeft() != null) {
				result = this.recursiveMaximum((BSTNode<T>) node.getLeft());
			} else {
				BSTNode<T> aux = (BSTNode<T>) node.getParent();
				
				while (aux != null && aux.getData().compareTo(node.getData()) == 1) {
					aux = (BSTNode<T>) aux.getParent();
				}
				
				result = aux;
			}
		}
		return result;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> toRemove = this.search(element);
		
		if (toRemove == this.root) {
			this.root = null;
		}
	}

	@Override
	public T[] preOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

	private int recursiveHeight(BSTNode<T> node) {
		int result = -1;
		
		if (node != null) {
			result = 1 + Math.max(
					recursiveHeight((BSTNode<T>) node.getLeft()),
					recursiveHeight((BSTNode<T>) node.getRight())
					);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private void recursiveInsert(BSTNode<T> node, T element) {
		if (node.getData().compareTo(element) == -1) {
			if (node.getLeft() == null) {
				BSTNode<T> newNode = (BSTNode<T>) new BSTNode.Builder<T>()
						.data(element)
						.left(null)
						.right(null)
						.parent(node)
						.build();
				node.setLeft(newNode);
			} else {
				recursiveInsert((BSTNode<T>) node.getLeft(), element);
			}
		} else {
            if (node.getRight() == null) { 
				BSTNode<T> newNode = (BSTNode<T>) new BSTNode.Builder<T>()
						.data(element)
						.left(null)
						.right(null)
						.parent(node)
						.build();
                node.setRight(newNode);
            } else {
            	recursiveInsert((BSTNode<T>) node.getRight(), element);
            }
        }
	}
	
	private BSTNode<T> recursiveSearch(BSTNode<T> node, T element) {
		BSTNode<T> result = null;
		if (node != null) {
			if(element.compareTo(node.getData()) == 0) result = node;
			else if (element.compareTo(node.getData()) == -1)  {
				result = this.recursiveSearch((BSTNode<T>) node.getLeft(), element);
			} else result = this.recursiveSearch((BSTNode<T>) node.getRight(), element);
		}
		return result;
	}
	
	private BSTNode<T> recursiveMinimum(BSTNode<T> node) {
		BSTNode<T> result = node;
		if (node.getLeft() != null) result = this.recursiveMinimum((BSTNode<T>) node.getLeft());
		return result;
	}
	
	private BSTNode<T> recursiveMaximum(BSTNode<T> node) {
		BSTNode<T> result = node;
		if (node.getRight() != null) result = this.recursiveMaximum((BSTNode<T>) node.getRight());
		return result;
	}
}
