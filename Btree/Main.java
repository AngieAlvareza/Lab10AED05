package Btree;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        // Crea un comparador para enteros en orden ascendente
    	int t=2;
        Comparator<Integer> comparator = Comparator.naturalOrder();

        // Crea un árbol B con grado 3 y utiliza el comparador de enteros
        BTree<Integer> bTree = new BTree<>(3, comparator);

        try {
            // Insertar claves en el árbol
            bTree.insert(10);
            bTree.insert(20);
            bTree.insert(5);
            bTree.insert(15);
            bTree.insert(30);
//            bTree.insert(1);
//            bTree.insert(12);

            // Imprimir el árbol después de insertar las claves
            System.out.println("Árbol después de insertar las claves:");
            System.out.println(bTree.root.toString());
            //System.out.println(bTree);

            // Buscar una clave en el árbol
            int keyToSearch = 15;
            System.out.println("Buscando la clave " + keyToSearch + ":");
            bTree.searchNodeByKey(keyToSearch);

            // Eliminar una clave del árbol
            int keyToDelete = 20;
            System.out.println("Eliminando la clave " + keyToDelete + ":");
            bTree.delete(keyToDelete);

            // Imprimir el árbol después de eliminar la clave
            System.out.println("Árbol después de eliminar la clave " + keyToDelete + ":");
            System.out.println(bTree);
            
            System.out.println("Quiero encontrar el nodo 25");
            bTree.searchNodeByKey(25);
            
            System.out.println("Quiero encontrar el nodo 20");
            bTree.searchNodeByKey(25);
            
            System.out.println("Añadimos el 32");
            bTree.insert(32);
            System.out.println(bTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
//    }
//        System.out.println(bTree.root.toString());

        //System.out.println(bTree.root.toString);
    }
}