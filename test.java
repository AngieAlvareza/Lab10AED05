package BTree;

public class test {
	public static void main (String[] args) {
		try {
		ArbolB tree1 = new ArbolB(3);
		tree1.insertar(1);
		tree1.insertar(2);
		tree1.insertar(3);
		tree1.insertar(4);
		tree1.insertar(5);
		tree1.insertar(6);
		tree1.showBTree();
		//tree1.max();
		}catch(Exception exc) {
			System.out.println(exc);
		}
	}
}
