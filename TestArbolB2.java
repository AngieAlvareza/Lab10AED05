package BTree;
public class TestArbolB2 {
    public static void main(String[] args) throws Exception {
        int t = 3;
        ArbolB arbolB = new ArbolB(t);
        
        // Insertar valores en el árbol B
        int[] valores = {20, 10, 50, 30, 40, 60, 80, 70, 90};
        System.out.println("-- INSERTANDO VALORES AL ÁRBOL B --");
        for (int i = 0; i < valores.length; i++) {
            System.out.println("Insertando valor: " + valores[i]);
            arbolB.insertar(valores[i]);
        }
        
        // Mostrar estado del árbol B
        System.out.println("\n-- ESTADO DEL ÁRBOL B --");
        arbolB.showBTree();
        System.out.println("");
        
        // Eliminar un valor del árbol B
        int valorEliminar = 30;
        System.out.println("Eliminando valor: " + valorEliminar);
        arbolB.eliminar(valorEliminar);
        
        // Mostrar estado del árbol B después de la eliminación
        System.out.println("\n-- ESTADO DEL ÁRBOL B DESPUÉS DE LA ELIMINACIÓN --");
        arbolB.showBTree();
        System.out.println("");
    }
    
}
