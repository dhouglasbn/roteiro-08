
package adt.bst;

@SuppressWarnings("unchecked")
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
		int result = -1;
		if (!this.isEmpty()) result = recursiveHeight(this.root);
		return result;
	}

	@Override
	public BSTNode<T> search(T element) {
		return this.recursiveSearch(this.root, element);
	}


	@Override
	public void insert(T element) {
		BSTNode<T> newNode;
		if (this.isEmpty()) {
			newNode = (BSTNode<T>) new BSTNode.Builder<T>()
								.data(element)
								.left(new BSTNode<T>())
								.right(new BSTNode<T>())
								.parent(new BSTNode<T>())
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
		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				result = this.recursiveMinimum((BSTNode<T>) node.getRight());
			} else {
				BSTNode<T> aux = (BSTNode<T>) node.getParent();
				
				while (!aux.isEmpty() && aux.getData().compareTo(node.getData()) == -1) {
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
		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				result = this.recursiveMaximum((BSTNode<T>) node.getLeft());
			} else {
				BSTNode<T> aux = (BSTNode<T>) node.getParent();
				
				while (!aux.isEmpty() && aux.getData().compareTo(node.getData()) == 1) {
					aux = (BSTNode<T>) aux.getParent();
				}
				
				if (!aux.isEmpty()) result = aux;
			}
		}
		return result;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> toRemove = this.search(element);
		this.remove(toRemove);
	}

	private void remove(BSTNode<T> toRemove) {
		BSTNode<T> nil = new BSTNode<T>();
		if (!toRemove.isEmpty()) {
			if (toRemove.isLeaf()) { // nó folha
				if (toRemove == this.root) {
					this.root = nil;
				} else {
					if (toRemove.getData().compareTo(toRemove.getParent().getData()) == -1) {
						toRemove.getParent().setLeft(nil);
					} else {
						toRemove.getParent().setRight(nil);
					}
				}
				
			} else if (!toRemove.getLeft().isEmpty() && toRemove.getRight().isEmpty()) { // nó com filho à esquerda
				if (toRemove == this.root) {
					this.root = (BSTNode<T>) toRemove.getLeft();
					this.root.setParent(nil);
				} else {
					toRemove.getLeft().setParent(toRemove.getParent());
					if (toRemove.getData().compareTo(toRemove.getParent().getData()) == -1) {
						toRemove.getParent().setLeft(toRemove.getLeft());
					} else {
						toRemove.getParent().setRight(toRemove.getLeft());
					}
				}
			} else if (!toRemove.getRight().isEmpty() && toRemove.getLeft().isEmpty()) { // nó com filho à direita
				if (toRemove == this.root) {
					this.root = (BSTNode<T>) toRemove.getRight();
					this.root.setParent(nil);
				} else {
					toRemove.getRight().setParent(toRemove.getParent());
					if (toRemove.getData().compareTo(toRemove.getParent().getData()) == -1) {
						toRemove.getParent().setLeft(toRemove.getRight());
					} else {
						toRemove.getParent().setRight(toRemove.getRight());
					}
				}
			} else { // nó com dois filhos
				BSTNode<T> sucessor = this.sucessor(toRemove.getData());
				toRemove.setData(sucessor.getData());
				this.remove(sucessor);
			}
		}
	}

	@Override
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		this.recursivePreOrder(this.root, array, 0);
		return array;
	}

	@Override
	public T[] order() {
		T[] array = (T[]) new Comparable[this.size()];
		this.recursiveOrder(this.root, array, 0);
		return array;
	}

	@Override
	public T[] postOrder() {
		T[] array = (T[]) new Comparable[this.size()];
		this.recursivePostOrder(this.root, array, 0);
		return array;
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
		if (!node.isEmpty()) {
			result = 1 + Math.max(
					recursiveHeight((BSTNode<T>) node.getLeft()),
					recursiveHeight((BSTNode<T>) node.getRight())
					);
		}
		return result;
	}

	private void recursiveInsert(BSTNode<T> node, T element) {
		if (node.getData().compareTo(element) == 1) {
			if (node.getLeft().isEmpty()) {
				BSTNode<T> newNode = (BSTNode<T>) new BSTNode.Builder<T>()
						.data(element)
						.left(new BSTNode<T>())
						.right(new BSTNode<T>())
						.parent(node)
						.build();
				node.setLeft(newNode);
			} else {
				recursiveInsert((BSTNode<T>) node.getLeft(), element);
			}
		} else {// 6 23 -34 5
            if (node.getRight().isEmpty()) { 
				BSTNode<T> newNode = (BSTNode<T>) new BSTNode.Builder<T>()
						.data(element)
						.left(new BSTNode<T>())
						.right(new BSTNode<T>())
						.parent(node)
						.build();
                node.setRight(newNode);
            } else {
            	recursiveInsert((BSTNode<T>) node.getRight(), element);
            }
        }
	}
	
	private BSTNode<T> recursiveSearch(BSTNode<T> node, T element) {
		BSTNode<T> result = new BSTNode<T>();
		if (!node.isEmpty() && element != null) {
			if(element.compareTo(node.getData()) == 0) result = node;
			else if (element.compareTo(node.getData()) == -1)  {
				result = this.recursiveSearch((BSTNode<T>) node.getLeft(), element);
			} else result = this.recursiveSearch((BSTNode<T>) node.getRight(), element);
		}
		return result;
	}
	
	private BSTNode<T> recursiveMinimum(BSTNode<T> node) {
		BSTNode<T> result = null;
		if (!node.isEmpty()) {
			result = node;
			if (!node.getLeft().isEmpty()) result = this.recursiveMinimum((BSTNode<T>) node.getLeft());
		}
		return result;
	}
	
	private BSTNode<T> recursiveMaximum(BSTNode<T> node) {
		BSTNode<T> result = null;
		if (!node.isEmpty()) {
			result = node;
			if (!node.getRight().isEmpty()) result = this.recursiveMaximum((BSTNode<T>) node.getRight());
		}
		return result;
	}
	
	private void recursivePreOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			array[index] = node.getData();
			if (!node.getLeft().isEmpty()) {
				recursivePreOrder((BSTNode<T>) node.getLeft(), array, index + 1);
			}
			if (!node.getRight().isEmpty()) {
				recursivePreOrder(
						(BSTNode<T>) node.getRight(),
						array,
						this.size((BSTNode<T>) node.getLeft()) + index + 1
						);
			}
		}
	}
	
	
	private void recursiveOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				recursiveOrder((BSTNode<T>) node.getLeft(), array, index);
			}
			index = this.size((BSTNode<T>) node.getLeft()) + index;
			array[index] = node.getData();
			if (!node.getRight().isEmpty()) {
				recursiveOrder(
						(BSTNode<T>) node.getRight(),
						array,
						index + 1
						);
			}
		}
	}
	
	
	private void recursivePostOrder(BSTNode<T> node, T[] array, int index) {
		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				recursivePostOrder((BSTNode<T>) node.getLeft(), array, index);
			}
			index = this.size((BSTNode<T>) node.getLeft()) + index;
			if (!node.getRight().isEmpty()) {
				recursivePostOrder(
						(BSTNode<T>) node.getRight(),
						array,
						index
						);
			}
			index = this.size((BSTNode<T>) node.getLeft()) + this.size((BSTNode<T>) node.getRight()) + index;
			array[index] = node.getData();
		}
	}
}
