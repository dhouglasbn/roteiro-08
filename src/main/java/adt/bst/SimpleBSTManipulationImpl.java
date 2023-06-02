package adt.bst;

/**
 * - Esta eh a unica classe que pode ser modificada 
 * @author adalbertocajueiro
 *
 * @param <T>
 */
public class SimpleBSTManipulationImpl<T extends Comparable<T>> implements SimpleBSTManipulation<T> {

	@Override
	public boolean equals(BST<T> tree1, BST<T> tree2) {
		return this.equals(
				(BSTNode<T>) tree1.getRoot(),
				(BSTNode<T>) tree2.getRoot()
		);
	}

	private boolean equals(BSTNode<T> node1, BSTNode<T> node2) {
		boolean result = false;
		if (node1.isEmpty() && node2.isEmpty()) {
			result = true;
		} else if (node1.equals(node2)) {
			result = this.equals(
					(BSTNode<T>) node1.getLeft(),
					(BSTNode<T>) node2.getLeft()
			) && this.equals(
					(BSTNode<T>) node1.getRight(),
					(BSTNode<T>) node2.getRight()
			);
		}
		return result;
	}

	@Override
	public boolean isSimilar(BST<T> tree1, BST<T> tree2) {
		return this.isSimilar(
				(BSTNode<T>) tree1.getRoot(),
				(BSTNode<T>) tree2.getRoot()
		);
	}

	private boolean isSimilar(BSTNode<T> node1, BSTNode<T> node2) {
		boolean result = false;
		if (node1.isEmpty() && node2.isEmpty()) {
			result = true;
		} else if (!node1.isEmpty() && !node2.isEmpty()) {
			result = this.isSimilar(
					(BSTNode<T>) node1.getLeft(),
					(BSTNode<T>) node2.getLeft()
			) && this.isSimilar(
					(BSTNode<T>) node1.getRight(),
					(BSTNode<T>) node2.getRight()
			);
		}
		return result;
	}

	@Override
	public T orderStatistic(BST<T> tree, int k) {
		return this.orderStatistic((BSTNode<T>) tree.getRoot(), new int[] {1}, k);
	}

	private T orderStatistic(BSTNode<T> node, int[] index, int k) {
		T result = null;
		if (!node.isEmpty()) {
			// procura a esquerda antes
			if (!node.getLeft().isEmpty()) {
				result = this.orderStatistic((BSTNode<T>)node.getLeft(), index, k);
			}
			
			// encontrou a posição atribui resultado
			// caso contrário incrementa index
			if (result == null && index[0] == k) {
				result = node.getData();
			} else {
				index[0] = index[0] + 1;
			}
			
			// se não é o nó nem a esquerda procura a direita
			if (result == null && !node.getRight().isEmpty()) {
				result = this.orderStatistic((BSTNode<T>) node.getRight(), index, k);
			}
		}
		return result;
	}
}
