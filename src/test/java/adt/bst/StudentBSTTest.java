package adt.bst;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import adt.bst.BSTImpl;
import adt.bst.extended.FloorCeilBSTImpl;
import adt.bt.BTNode;

public class StudentBSTTest {

	private BSTImpl<Integer> tree;
	private BTNode<Integer> NIL = new BTNode<Integer>();
	private SimpleBSTManipulationImpl<Integer> simpleBSTManipulationImpl;
	private FloorCeilBSTImpl floorCeilBSTImpl;

	private void fillTree() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		for (int i : array) {
			tree.insert(i);
		}
	}

	@Before
	public void setUp() {
		this.tree = new BSTImpl<Integer>();
		this.simpleBSTManipulationImpl = new SimpleBSTManipulationImpl<Integer>();
		this.floorCeilBSTImpl = new FloorCeilBSTImpl();
	}

	@Test
	public void testInit() {
		assertTrue(tree.isEmpty());
		assertEquals(0, tree.size());
		assertEquals(-1, tree.height());

		assertEquals(NIL, tree.getRoot());

		assertArrayEquals(new Integer[] {}, tree.order());
		assertArrayEquals(new Integer[] {}, tree.preOrder());
		assertArrayEquals(new Integer[] {}, tree.postOrder());

		assertEquals(NIL, tree.search(12));
		assertEquals(NIL, tree.search(-23));
		assertEquals(NIL, tree.search(0));

		assertEquals(null, tree.minimum());
		assertEquals(null, tree.maximum());

		assertEquals(null, tree.sucessor(12));
		assertEquals(null, tree.sucessor(-23));
		assertEquals(null, tree.sucessor(0));

		assertEquals(null, tree.predecessor(12));
		assertEquals(null, tree.predecessor(-23));
		assertEquals(null, tree.predecessor(0));
	}

	@Test
	public void testMinMax() {
		tree.insert(6);
		assertEquals(new Integer(6), tree.minimum().getData());
		assertEquals(new Integer(6), tree.maximum().getData());

		tree.insert(23);
		assertEquals(new Integer(6), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(-34);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(5);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());

		tree.insert(9);
		assertEquals(new Integer(-34), tree.minimum().getData());
		assertEquals(new Integer(23), tree.maximum().getData());
	}

	@Test
	public void testSucessorPredecessor() {

		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		assertEquals(null, tree.predecessor(-40));
		assertEquals(null, tree.sucessor(232).getData());

		assertEquals(new Integer(76), tree.predecessor(232).getData());
		assertEquals(new Integer(-34), tree.sucessor(-40).getData());

		assertEquals(new Integer(-40), tree.predecessor(-34).getData());
		assertEquals(new Integer(0), tree.sucessor(-34).getData());

		assertEquals(new Integer(-34), tree.predecessor(0).getData());
		assertEquals(new Integer(2), tree.sucessor(0).getData());

		assertEquals(new Integer(0), tree.predecessor(2).getData());
		assertEquals(new Integer(5), tree.sucessor(2).getData());
	}

	@Test
	public void testSize() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		int size = 12;
		assertEquals(size, tree.size());

		while (!tree.isEmpty()) {
			tree.remove(tree.getRoot().getData());
			assertEquals(--size, tree.size());
		}
	}
	
	@Test
	public void testPreOrder() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] order = { 6, -34, -40, 5, 2, 0, 23, 9, 12, 76, 67, 232 };
		assertArrayEquals(order, tree.preOrder());
	}
	
	@Test
	public void testOrder() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] order = { -40, -34, 0, 2, 5, 6, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());
	}
	
	@Test
	public void testPostOrder() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] order = { -40, 0, 2, 5, -34, 12, 9, 67, 232, 76, 23, 6 };
		assertArrayEquals(order, tree.postOrder());
	}
	
	@Test
	public void testHeight() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] preOrder = new Integer[] { 6, -34, -40, 5, 2, 0, 23, 9, 12,
				76, 67, 232 };
		assertArrayEquals(preOrder, tree.preOrder());
		assertEquals(4, tree.height());

		tree.remove(0);
		assertEquals(3, tree.height());

		tree.remove(2);
		assertEquals(3, tree.height());
	}

	@Test
	public void testRemove() {
		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		Integer[] order = { -40, -34, 0, 2, 5, 6, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());

		tree.remove(6);
		order = new Integer[] { -40, -34, 0, 2, 5, 9, 12, 23, 67, 76, 232 };
		assertEquals(new Integer(9), tree.getRoot().getData());
		assertArrayEquals(order, tree.order());

		tree.remove(9);
		order = new Integer[] { -40, -34, 0, 2, 5, 12, 23, 67, 76, 232 };
		assertEquals(new Integer(12), tree.getRoot().getData());
		assertArrayEquals(order, tree.order());

		assertEquals(NIL, tree.search(6));
		assertEquals(NIL, tree.search(9));

	}
	
	@Test
	public void testRemoveLeaf() {
		fillTree();
		
		Integer[] order = { -40, -34, 0, 2, 5, 6, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		
		tree.remove(67);
		order = new Integer[] { -40, -34, 0, 2, 5, 6, 9, 12, 23, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		tree.remove(0);
		order = new Integer[] { -40, -34, 2, 5, 6, 9, 12, 23, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		tree.remove(-40);
		order = new Integer[] { -34, 2, 5, 6, 9, 12, 23, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		assertEquals(NIL, tree.search(67));
		assertEquals(NIL, tree.search(0));
		assertEquals(NIL, tree.search(-40));
	}
	
	@Test
	public void testRemoveWithOnlyOneChild() {
		fillTree();
		
		Integer[] order = { -40, -34, 0, 2, 5, 6, 9, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		
		tree.remove(9);
		order = new Integer[] { -40, -34, 0, 2, 5, 6, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		tree.remove(2);
		order = new Integer[] { -40, -34, 0, 5, 6, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		tree.remove(5);
		order = new Integer[] { -40, -34, 0, 6, 12, 23, 67, 76, 232 };
		assertArrayEquals(order, tree.order());
		
		assertEquals(NIL, tree.search(9));
		assertEquals(NIL, tree.search(2));
		assertEquals(NIL, tree.search(5));
	}

	@Test
	public void testSearch() {

		fillTree(); // -40 -34 0 2 5 6 9 12 23 67 76 232

		assertEquals(new Integer(-40), tree.search(-40).getData());
		assertEquals(new Integer(-34), tree.search(-34).getData());
		assertEquals(NIL, tree.search(2534));
	}
	
	@Test
	public void testEquals() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		fillTree();
		
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		for (int i : array) {
			tree2.insert(i);
		}
		
		assertTrue(this.simpleBSTManipulationImpl.equals(tree, tree2));
	}
	
	@Test
	public void testEqualsEmptyBst() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		
		assertTrue(this.simpleBSTManipulationImpl.equals(tree, tree2));
	}
	
	@Test
	public void testEqualsOneElementBst() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();

		tree.insert(5);
		tree2.insert(5);
		
		assertTrue(this.simpleBSTManipulationImpl.equals(tree, tree2));
	}
	
	@Test
	public void testIsDifferentOneElementBst() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();

		tree.insert(6);
		tree2.insert(5);
		
		assertFalse(this.simpleBSTManipulationImpl.equals(tree, tree2));
	}
	
	@Test
	public void testIsDifferent() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		fillTree();
		
		Integer[] array = { 6, 26, -32, 4, 8, 1, -3, 80, 15, 70, 250, -50 };
		for (int i : array) {
			tree2.insert(i);
		}
		
		assertFalse(this.simpleBSTManipulationImpl.equals(tree, tree2));
	}
	
	@Test
	public void testIsSimilar() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		fillTree();
		
		Integer[] array = { 6, 26, -32, 4, 8, 1, -3, 80, 15, 70, 250, -50 };
		for (int i : array) {
			tree2.insert(i);
		}
		
		assertTrue(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
	}
	
	@Test
	public void testIsSimilarEmptyBsts() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		assertTrue(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
	}
	
	@Test
	public void testIsSimilarOneElementBsts() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		tree.insert(5);
		tree2.insert(6);
		
		assertTrue(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
	}
	
	@Test
	public void testIsNotSimilar() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		fillTree();
		
		Integer[] array = { 6, -32, 4, 8, 1, -3, 80, 15, 70, 250, -50 };
		for (int i : array) {
			tree2.insert(i);
		}
		
		assertFalse(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
	}
	
	@Test
	public void testIsNotSimilarOneChildDifference() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		tree.insert(5);
		tree2.insert(10);
		tree2.insert(15);
		
		assertFalse(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
		
		tree2.remove(15);
		tree2.insert(2);
		assertFalse(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
	}
	
	@Test
	public void testIsNotSimilarTwoChildsDifference() {
		BSTImpl<Integer> tree2 = new BSTImpl<Integer>();
		tree.insert(5);
		tree2.insert(10);
		tree2.insert(15);
		tree2.insert(2);
		
		assertFalse(this.simpleBSTManipulationImpl.isSimilar(tree, tree2));
	}
	
	@Test
	public void test1StatisticOrder() {
		fillTree();
		
		assertEquals(new Integer(-40), this.simpleBSTManipulationImpl.orderStatistic(tree, 1));
	}
	
	@Test
	public void test12StatisticOrder() {
		fillTree();
		
		assertEquals(new Integer(232), this.simpleBSTManipulationImpl.orderStatistic(tree, 12));
	}
	
	@Test
	public void test6StatisticOrder() {
		fillTree();
		
		assertEquals(new Integer(6), this.simpleBSTManipulationImpl.orderStatistic(tree, 6));
	}
	
	@Test
	public void testPreviousInexistentStatisticOrder() {
		fillTree();
		
		assertNull(this.simpleBSTManipulationImpl.orderStatistic(tree, -1));
	}
	
	@Test
	public void testNextInexistentStatisticOrder() {
		fillTree();
		
		assertNull(this.simpleBSTManipulationImpl.orderStatistic(tree, 13));
	}
	
	@Test
	public void testEmptyBstStatisticOrder() {
		assertNull(this.simpleBSTManipulationImpl.orderStatistic(tree, 1));
	}
	
	@Test
	public void testFloor1() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(0), this.floorCeilBSTImpl.floor(array, 1));
	}
	
	@Test
	public void testFloor260() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(232), this.floorCeilBSTImpl.floor(array, 260));
	}
	
	@Test
	public void testFloor12() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(12), this.floorCeilBSTImpl.floor(array, 12.4));
	}
	
	@Test
	public void testFloor67() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(67), this.floorCeilBSTImpl.floor(array, 67));
	}
	
	@Test
	public void testInexistentFloor() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertNull(this.floorCeilBSTImpl.floor(array, -41));
	}
	
	@Test
	public void testEmptyListFloor() {
		Integer[] array = new Integer[0];
		
		assertNull(this.floorCeilBSTImpl.floor(array, 0));
		assertNull(this.floorCeilBSTImpl.floor(array, 1));
		assertNull(this.floorCeilBSTImpl.floor(array, 5));
		assertNull(this.floorCeilBSTImpl.floor(array, -1));
		assertNull(this.floorCeilBSTImpl.floor(array, -5));
	}
	
	@Test
	public void testOneElementListFloor() {
		Integer[] array = new Integer[] {2};
		
		assertNull(this.floorCeilBSTImpl.floor(array, 1));
		assertNull(this.floorCeilBSTImpl.floor(array, -1));
		assertEquals(new Integer(2), this.floorCeilBSTImpl.floor(array, 3));
		assertEquals(new Integer(2), this.floorCeilBSTImpl.floor(array, 5));
		assertEquals(new Integer(2), this.floorCeilBSTImpl.floor(array, 10));
	}
	
	@Test
	public void testCeil1() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(2), this.floorCeilBSTImpl.ceil(array, 1));
	}
	
	@Test
	public void testCeilMinus50() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(-40), this.floorCeilBSTImpl.ceil(array, -50));
	}
	
	@Test
	public void testCeil12() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(23), this.floorCeilBSTImpl.ceil(array, 12.4));
	}
	
	@Test
	public void testCeil67() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertEquals(new Integer(67), this.floorCeilBSTImpl.ceil(array, 67));
	}
	
	@Test
	public void testInexistentCeil() {
		Integer[] array = { 6, 23, -34, 5, 9, 2, 0, 76, 12, 67, 232, -40 };
		
		assertNull(this.floorCeilBSTImpl.ceil(array, 233));
	}
	
	@Test
	public void testEmptyListCeil() {
		Integer[] array = new Integer[0];
		
		assertNull(this.floorCeilBSTImpl.ceil(array, 0));
		assertNull(this.floorCeilBSTImpl.ceil(array, 1));
		assertNull(this.floorCeilBSTImpl.ceil(array, 5));
		assertNull(this.floorCeilBSTImpl.ceil(array, -1));
		assertNull(this.floorCeilBSTImpl.ceil(array, -5));
	}
	
	@Test
	public void testOneElementListCeil() {
		Integer[] array = new Integer[] {2};
		
		assertNull(this.floorCeilBSTImpl.ceil(array, 3));
		assertNull(this.floorCeilBSTImpl.ceil(array, 5));
		assertEquals(new Integer(2), this.floorCeilBSTImpl.ceil(array, 1));
		assertEquals(new Integer(2), this.floorCeilBSTImpl.ceil(array, -1));
		assertEquals(new Integer(2), this.floorCeilBSTImpl.ceil(array, -10));
	}
}
