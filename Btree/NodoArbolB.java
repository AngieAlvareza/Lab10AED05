package Btree;

public class NodoArbolB<T extends Comparable<T>> {

    int n; // número de claves almacenadas en el nodo
    boolean leaf; // Si el nodo es hoja (nodo hoja=true; nodo interno=false)
    T[] key; // almacena las claves en el nodo
    NodoArbolB<T>[] child; // arreglo con referencias a los hijos

    // Constructores
    public NodoArbolB(int t) {
        n = 0;
        leaf = true;
        key = (T[]) new Comparable[((2 * t) - 1)];
        child = new NodoArbolB[(2 * t)];
    }

    public void imprimir() {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
                System.out.print(key[i] + " | ");
            } else {
                System.out.print(key[i]);
            }
        }
        System.out.print("]");
    }

    public int find(T k) {
        for (int i = 0; i < n; i++) {
            if (key[i].equals(k)) {
                return i;
            }
        }
        return -1;
    }
}
