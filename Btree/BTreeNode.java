package Btree;

public class BTreeNode<T> {
    protected int n;
    protected Object[] keys;
    protected BTreeNode<T>[] children;
    public boolean leaf;

    public BTreeNode(int t) {
        n = 0;
        keys = new Object[2 * t - 1];
        children = new BTreeNode[2 * t];
        leaf = true;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Keys: ");
        for (int i = 0; i < n; i++) {
            sb.append(keys[i]).append(" ");
        }
        sb.append("\n");

        if (!leaf) {
            sb.append("Children: ");
            for (int i = 0; i < n + 1; i++) {
                sb.append(children[i]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    
   


    
    
        
    }

    // Implementa los métodos y operaciones necesarios para trabajar con los nodos del árbol
