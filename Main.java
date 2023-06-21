package BTree;

import Exception.ExceptionIsEmpty;

public class Main {
    public static void main(String[] args) throws ExceptionIsEmpty {
        int t = 2; // Grado máximo del árbol B
        ArbolB arbol = new ArbolB(t);

        try {
            arbol.insertar(10);
            arbol.insertar(20);
            arbol.insertar(30);
            arbol.insertar(40);
            arbol.insertar(50);

            System.out.println("Árbol B:");
            arbol.showBTree();

            NodoArbolB nodoMayor = arbol.obtenerNodoMayor();
            if (nodoMayor != null) {
            	System.out.println();
            	System.out.println("\n");
                System.out.println("El nodo con el valor máximo es:");
                nodoMayor.imprimir();
            } else {
                System.out.println("El árbol está vacío.");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar en el árbol: " + e.getMessage());
        }
        
        int valorMaximo = arbol.obtenerValorMaximo();
        System.out.println("\n\n");
        //System.out.println();
        //System.out.println();
        System.out.println("El valor máximo del árbol es : " + valorMaximo);
        ////////
        //MINIMOOOO
        NodoArbolB nodoMinimo = arbol.obtenerNodoMinimo();
        System.out.println("El nodo minimo es : "+nodoMinimo.key[0]);
        
        
        //CMAINO CRECORRIDO 
        String caminoRecorrido = arbol.getCaminorecorrido(30);
        System.out.println("Camino recorrido para el valor 30: " + caminoRecorrido);
//        try {
//            ArbolB arbo2 = new ArbolB(3); // Crea un árbol B con grado 3
//
//            // Inserta valores en el árbol
//            arbol.insertar(50);
//            arbol.insertar(30);
//            arbol.insertar(70);
//            arbol.insertar(10);
//            arbol.insertar(40);
//            arbol.insertar(60);
//            arbol.insertar(80);
//
//            // Obtén el nodo mínimo de la raíz del árbol
//            NodoArbolB nodoMinimo = arbo2.obtenerNodoMinimo();
//
//            // Imprime el nodo mínimo
//            if (nodoMinimo != null) {
//                System.out.println("El nodo mínimo de la raíz del árbol es: " + nodoMinimo);
//            } else {
//                System.out.println("El árbol está vacío.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        
    }
    
    
    
}
