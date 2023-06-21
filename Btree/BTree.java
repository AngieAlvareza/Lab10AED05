package Btree;

import java.util.Comparator;

import Exception.*;

public class BTree<T extends Comparable<T>> {
	
    protected BTreeNode<T> root;
    private int t;
    private Comparator<T> comparator;

    public BTree(int t, Comparator<T> comparator) {
        this.t = t;
        this.comparator = comparator;
        root = new BTreeNode<>(t);
    }

    public void searchNodeByKey(T key) {
        BTreeNode<T> temp = search(root, key);

        if (temp == null) {
            System.out.println("No node found with the given key");
        } else {
            print(temp);
        }
    }

    private void print(BTreeNode<T> temp) {
    	System.out.println(temp.toString());
    }

	private BTreeNode<T> search(BTreeNode<T> current, T key) {
        int i = 0;

        while (i < current.n && comparator.compare(key, (T) current.keys[i]) > 0) {
            i++;
        }

        if (i < current.n && comparator.compare(key, (T) current.keys[i]) == 0) {
            return current;
        }

        if (current.leaf) {
            return null;
        } else {
            return search(current.children[i], key);
        }
    }

    public void insert(T key) throws Exception {
        BTreeNode<T> r = root;

        if (r.n == (2 * t - 1)) {
            BTreeNode<T> s = new BTreeNode<>(t);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.children[0] = r;
            split(s, 0, r);
            nonFullInsert(s, key);
        } else {
            nonFullInsert(r, key);
        }
    }

    private void split(BTreeNode<T> x, int i, BTreeNode<T> y) {
        BTreeNode<T> z = new BTreeNode<>(t);
        z.leaf = y.leaf;
        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }

        if (!y.leaf) {
            for (int k = 0; k < t; k++) {
                z.children[k] = y.children[k + t];
            }
        }

        for (int j = x.n; j > i; j--) {
            x.children[j + 1] = x.children[j];
        }

        x.children[i + 1] = z;

        for (int j = x.n - 1; j >= i; j--) {
            x.keys[j + 1] = x.keys[j];
        }

        x.keys[i] = y.keys[t - 1];
        x.n++;
    }

    private void nonFullInsert(BTreeNode<T> x, T key) {
        int i = x.n;

        if (x.leaf) {
            while (i >= 1 && comparator.compare(key, (T) x.keys[i - 1]) < 0) {
                x.keys[i] = x.keys[i - 1];
                i--;
            }

            x.keys[i] = key;
            x.n++;
        } else {
            while (i > 0 && comparator.compare(key, (T) x.keys[i - 1]) < 0) {
            	 i--;
            }

            if (x.children[i].n == (2 * t - 1)) {
                split(x, i, x.children[i]);

                if (comparator.compare(key, (T) x.keys[i]) > 0) {
                    i++;
                }
            }
           
            nonFullInsert(x.children[i], key);
        }
    }

    public void delete(T key) {
    	if (root.n == 0) {
            System.out.println("The tree is empty. Deletion is not possible.");
            return;
        }

        delete(root, key);

        if (root.n == 0 && !root.leaf) {
            root = root.children[0];
        }
    }
    private void delete(BTreeNode<T> node, T key) {
        int i = 0;

        while (i < node.n && comparator.compare(key, (T) node.keys[i]) > 0) {
            i++;
        }

        if (i < node.n && comparator.compare(key, (T) node.keys[i]) == 0) {
            if (node.leaf) {
                removeFromLeaf(node, i);
            } else {
                removeFromNonLeaf(node, i);
            }
        } else {
            if (node.leaf) {
                System.out.println("The key " + key + " does not exist in the tree.");
                return;
            }

            boolean flag = (i == node.n);

            if (node.children[i].n < t) {
                fill(node, i);
            }

            if (flag && i > node.n) {
                delete(node.children[i - 1], key);
            } else {
                delete(node.children[i], key);
            }
        }
    }
    private void removeFromLeaf(BTreeNode<T> node, int index) {
        for (int i = index + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }

        node.n--;
    }
    private void removeFromNonLeaf(BTreeNode<T> node, int index) {
        T key = (T) node.keys[index];
        BTreeNode<T> predecessor = node.children[index];
        BTreeNode<T> successor = node.children[index + 1];

        if (predecessor.n >= t) {
            T predecessorKey = getPredecessor(predecessor);
            node.keys[index] = predecessorKey;
            delete(predecessor, predecessorKey);
        } else if (successor.n >= t) {
            T successorKey = getSuccessor(successor);
            node.keys[index] = successorKey;
            delete(successor, successorKey);
        } else {
            merge(node, index, predecessor, successor);
            delete(predecessor, key);
        }
    }
    private T getPredecessor(BTreeNode<T> node) {
        while (!node.leaf) {
            node = node.children[node.n];
        }

        return (T) node.keys[node.n - 1];
    }
    private T getSuccessor(BTreeNode<T> node) {
        while (!node.leaf) {
            node = node.children[0];
        }

        return (T) node.keys[0];
    }
    
    private void fill(BTreeNode<T> node, int index) {
        if (index != 0 && node.children[index - 1].n >= t) {
            borrowFromPrevious(node, index);
        } else if (index != node.n && node.children[index + 1].n >= t) {
            borrowFromNext(node, index);
        } else {
            if (index != node.n) {
                merge(node, index, node.children[index], node.children[index + 1]);
            } else {
                merge(node, index - 1, node.children[index - 1], node);
            }
        }
    }

    private void borrowFromPrevious(BTreeNode<T> node, int index) {
        BTreeNode<T> child = node.children[index];
        BTreeNode<T> sibling = node.children[index - 1];

        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }

        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }
        child.keys[0] = node.keys[index - 1];

        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        node.keys[index - 1] = sibling.keys[sibling.n - 1];

        child.n++;
        sibling.n--;
    }
    
    private void borrowFromNext(BTreeNode<T> node, int index) {
        BTreeNode<T> child = node.children[index];
        BTreeNode<T> sibling = node.children[index + 1];

        child.keys[child.n] = node.keys[index];

        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        node.keys[index] = sibling.keys[0];

        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n++;
        sibling.n--;
    }
    private void merge(BTreeNode<T> node, int index, BTreeNode<T> leftChild, BTreeNode<T> rightChild) {
        leftChild.keys[leftChild.n] = node.keys[index];

        for (int i = 0; i < rightChild.n; i++) {
            leftChild.keys[leftChild.n + 1 + i] = rightChild.keys[i];
        }

        if (!leftChild.leaf) {
            for (int i = 0; i <= rightChild.n; i++) {
                leftChild.children[leftChild.n + 1 + i] = rightChild.children[i];
            }
        }

        for (int i = index + 1; i < node.n; i++) {
            node.keys[i - 1] = node.keys[i];
        }

        for (int i = index + 2; i <= node.n; i++) {
            node.children[i - 1] = node.children[i];
        }

        leftChild.n += rightChild.n + 1;
        node.n--;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(root, sb);
        return sb.toString();
    }

    private void toString(BTreeNode<T> node, StringBuilder sb) {
        sb.append(node.toString());

        if (!node.leaf) {
            for (int i = 0; i <= node.n; i++) {
                toString(node.children[i], sb);
            }
        }
    }
}

    



    //private void delete

    
    