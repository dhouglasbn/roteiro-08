package adt.bst.extended;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Note que esta classe estende sua implementacao de BST (BSTImpl).
 * Dependendo do design que voce use, sua BSTImpl precisa ter apenas funcionando
 * corretamente o metodo insert para que voce consiga testar esta classe.
 */
public class FloorCeilBSTImpl extends BSTImpl<Integer> implements FloorCeilBST {

	@Override
	public Integer floor(Integer[] array, double numero) {
		BSTImpl<Integer> bst = new BSTImpl<Integer>();
		for (Integer number: array) {
			bst.insert(number);
		}
		return this.floor((BSTNode<Integer>)bst.getRoot(), numero);
	}

	private Integer floor(BSTNode<Integer> node, double numero) {
		Integer result = null;
		if (!node.isEmpty()) {
			if (node.getData() == numero) {
				result = node.getData();
			} else if (node.getData() < numero) {
				result = this.floor((BSTNode<Integer>) node.getRight(), numero);
				if (result == null) {
					result = node.getData();
				}
			} else {
				result = this.floor((BSTNode<Integer>) node.getLeft(), numero);
			}
		}
		return result;
	}

	@Override
	public Integer ceil(Integer[] array, double numero) {
		//TODO Implemente seu codigo aqui
		throw new UnsupportedOperationException("Not implemented yet!");
	}

}
