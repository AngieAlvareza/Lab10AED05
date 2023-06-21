package BTree;
//import Acti1.BSTree.Node;
import Exception.*;

public class ArbolB {
    NodoArbolB root;
    int t;

    //Constructor
    public ArbolB(int t) {
        this.t = t;
        root = new NodoArbolB(t);
    }

    
    //Busca el valor ingresado y muestra el contenido del nodo que contiene el valor
    public void buscarNodoPorClave(int num) {
        NodoArbolB temp = search(root, num);

        if (temp == null) {
            System.out.println("No se ha encontrado un nodo con el valor ingresado");
        } else {
            print(temp);
        }
    }

    //Search
    private NodoArbolB search(NodoArbolB actual, int key) {
        int i = 0;//se empieza a buscar siempre en la primera posicion

        //Incrementa el indice mientras el valor de la clave del nodo sea menor
        while (i < actual.n && key > actual.key[i]) {
            i++;
        }

        //Si la clave es igual, entonces retornamos el nodo
        if (i < actual.n && key == actual.key[i]) {
            return actual;
        }

        //Si llegamos hasta aqui, entonces hay que buscar los hijos
        //Se revisa primero si tiene hijos
        if (actual.leaf) {
            return null;
        } else {
            //Si tiene hijos, hace una llamada recursiva
            return search(actual.child[i], key);
        }
    }

    public void insertar(int key) throws Exception {
        NodoArbolB r = root;

        //Si el nodo esta lleno lo debe separar antes de insertar
        if (r.n == ((2 * t) - 1)) {
            NodoArbolB s = new NodoArbolB(t);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            split(s, 0, r);
            nonFullInsert(s, key);
        } else {
            nonFullInsert(r, key);
        }
    }

    // Caso cuando la raiz se divide
    // x =          | | | | | |
    //             /
    //      |10|20|30|40|50|
    // i = 0
    // y = |10|20|30|40|50|
    private void split(NodoArbolB x, int i, NodoArbolB y) {
        //Nodo temporal que sera el hijo i + 1 de x
        NodoArbolB z = new NodoArbolB(t);
        z.leaf = y.leaf;
        z.n = (t - 1);

        //Copia las ultimas (t - 1) claves del nodo y al inicio del nodo z      // z = |40|50| | | |
        for (int j = 0; j < (t - 1); j++) {
            z.key[j] = y.key[(j + t)];
        }

        //Si no es hoja hay que reasignar los nodos hijos
        if (!y.leaf) {
            for (int k = 0; k < t; k++) {
                z.child[k] = y.child[(k + t)];
            }
        }

        //nuevo tamanio de y                                                    // x =            | | | | | |
        y.n = (t - 1);                                                          //               /   \
                                                                                //  |10|20| | | |
        //Mueve los hijos de x para darle espacio a z
        for (int j = x.n; j > i; j--) {
            x.child[(j + 1)] = x.child[j];
        }
        //Reasigna el hijo (i+1) de x                                           // x =            | | | | | |
        x.child[(i + 1)] = z;                                                   //               /   \
                                                                                //  |10|20| | | |     |40|50| | | |
        //Mueve las claves de x
        for (int j = x.n; j > i; j--) {
            x.key[(j + 1)] = x.key[j];
        }

        //Agrega la clave situada en la mediana                                 // x =            |30| | | | |
        x.key[i] = y.key[(t - 1)];                                              //               /    \
        x.n++;                                                                  //  |10|20| | | |      |40|50| | | |
    }
    private void nonFullInsert(NodoArbolB x, int key) {
        //Si es una hoja
        if (x.leaf) {
            int i = x.n; //cantidad de valores del nodo
            //busca la posicion i donde asignar el valor
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i] = x.key[i - 1];//Desplaza los valores mayores a key
                i--;
            }

            x.key[i] = key;//asigna el valor al nodo
            x.n++; //aumenta la cantidad de elementos del nodo
        } else {
            int j = 0;
            //Busca la posicion del hijo
            while (j < x.n && key > x.key[j]) {
                j++;
            }

            //Si el nodo hijo esta lleno lo separa
            if (x.child[j].n == (2 * t - 1)) {
                split(x, j, x.child[j]);

                if (key > x.key[j]) {
                    j++;
                }
            }

            nonFullInsert(x.child[j], key);
        }
    }

    public void showBTree() {
        print(root);
    }
    
    public void eliminar(int key) {
    	eliminar(root,key);
    	
    }
    
    private void eliminar(NodoArbolB nodo, int key) {
    	if(nodo==null) {
    		System.out.println("ERROR. La clave no existe en el arbol" );
    		return;
    	}
    	int indice = nodo.find(key);
    	if (indice !=-1) {
    		eliminarClave(nodo, key, indice);
    		
    	}else {
    		if(nodo.leaf){
    			System.out.println("ERROR la clave no existe ene l arbo");
    			return;	
    		}
    		boolean esUltimoHijo = (indice == nodo.n);
            if (esUltimoHijo) {
                NodoArbolB hijoDerecho = nodo.child[indice];
                if (hijoDerecho.n >= t) {
                    eliminar(nodo.child[indice], key);
                    return;
                }
            }
            
            boolean esPrimerHijo = (indice == 0);
            if (esPrimerHijo) {
                NodoArbolB hijoIzquierdo = nodo.child[0];
                if (hijoIzquierdo.n >= t) {
                    eliminar(nodo.child[0], key);
                    return;
                }
            }
                
                fusionarNodos(nodo, indice);
                eliminar(nodo.child[indice], key);
            }
    }
    	
	private void eliminarClave(NodoArbolB nodo, int key, int indice) {
	    if (nodo.leaf) {
	        for (int i = indice; i < nodo.n - 1; i++) {
	            nodo.key[i] = nodo.key[i + 1];
	        }
	        nodo.n--;
	    } else {
	        NodoArbolB nodoPred = nodo.child[indice];
	        NodoArbolB nodoSucc = nodo.child[indice + 1];
	        
	        if (nodoPred.n >= t) {
	            int predecesor = obtenerPredecesor(nodoPred);
	            nodo.key[indice] = predecesor;
	            eliminar(nodoPred, predecesor);
	        } else if (nodoSucc.n >= t) {
	            int sucesor = obtenerSucesor(nodoSucc);
	            nodo.key[indice] = sucesor;
	            eliminar(nodoSucc, sucesor);
	        } else {
	            fusionarNodos(nodo, indice);
	            eliminar(nodoPred, key);
	        }
	    }
	}
    	    
    	    
	    private int obtenerPredecesor(NodoArbolB nodo) {
	        while (!nodo.leaf) {
	            nodo = nodo.child[nodo.n];
	        }
	        return nodo.key[nodo.n - 1];
	    }

	    private int obtenerSucesor(NodoArbolB nodo) {
	        NodoArbolB actual = nodo;
	        while (!actual.leaf) {
	            actual = actual.child[0];
	        }
	        return actual.key[0];
	    }


	    
	    private void fusionarNodos(NodoArbolB nodo, int indice) {
	        NodoArbolB hijoIzquierdo = nodo.child[indice];
	        NodoArbolB hijoDerecho = nodo.child[indice + 1];
	        
	        hijoIzquierdo.key[t - 1] = nodo.key[indice];
	        
	        for (int i = 0; i < hijoDerecho.n; i++) {
	            hijoIzquierdo.key[i + t] = hijoDerecho.key[i];
	        }
	        
	        if (!hijoIzquierdo.leaf) {
	            for (int i = 0; i <= hijoDerecho.n; i++) {
	                hijoIzquierdo.child[i + t] = hijoDerecho.child[i];
	            }
	        }
	        
	        for (int i = indice + 1; i < nodo.n; i++) {
	            nodo.key[i - 1] = nodo.key[i];
	        }
	        
	        for (int i = indice + 2; i <= nodo.n; i++) {
	            nodo.child[i - 1] = nodo.child[i];
	        }
	        
	        hijoIzquierdo.n += hijoDerecho.n + 1;
	        nodo.n--;
	    }

    	    
    	    


    //Print en preorder
    private void print(NodoArbolB n) {
        n.imprimir();

        //Si no es hoja
        if (!n.leaf) {
            //recorre los nodos para saber si tiene hijos
            for (int j = 0; j <= n.n; j++) {
                if (n.child[j] != null) {
                    System.out.println();
                    print(n.child[j]);
                }
            }
        }
    }
    
//    public int max() {
//    	NodoArbolB aux = this.root;
//    	int n[];
//    	n = new int[1];
//    	max(aux, n);
//    	return n[0];
//    }
//    
//    private int max(NodoArbolB aux, int f[]) {
//    	if (aux.n == 0) {
//    		return 0;
//    	}
//    	if (aux.child[aux.n+1].n != 0) {
//    		f[0] = max(aux.child[aux.n+1], f);
//    	}else {
//    		return aux.key[aux.n];
//    	}
//    	return f[0];
//    }
//    }
    
    public NodoArbolB obtenerNodoMayor() {
        return max(root);
    }

    private NodoArbolB max(NodoArbolB nodo) {
        if (nodo == null) {
            return null;
        }

        // Recorre los hijos de manera recursiva hasta llegar al último nodo
        if (!nodo.leaf) {
            return max(nodo.child[nodo.n]);
        }

        return nodo;
        
    }
    
    /////////////////////////
    public int obtenerValorMaximo() throws ExceptionIsEmpty {
        NodoArbolB nodoMaximo = obtenerNodoMaximo(root);
        if (nodoMaximo != null) {
            int indiceMaximo = nodoMaximo.n - 1;
            return nodoMaximo.key[indiceMaximo];
        } else {
            throw new ExceptionIsEmpty("El árbol está vacío.");
        }
    }

    private NodoArbolB obtenerNodoMaximo(NodoArbolB nodo) {
        if (nodo == null || nodo.n == 0) {
            return null;
        }

        if (nodo.leaf) {
            return nodo;
        } else {
            return obtenerNodoMaximo(nodo.child[nodo.n]);
        }
    }
    
    
    public NodoArbolB obtenerNodoMinimo() {
        if (root == null) {
            return null; // El árbol está vacío, retorna null
        }

        NodoArbolB nodoActual = root;
        while (!nodoActual.leaf) {
            nodoActual = nodoActual.child[0]; // Recorre hacia el hijo más a la izquierda
        }

        return nodoActual;
    }
    
    
    //CAMINOO RECORRIDO
    public String getCaminorecorrido(int key) {
        StringBuilder camino = new StringBuilder();
        getCaminorecorrido(root, key, camino);
        return camino.toString();
    }

    private void getCaminorecorrido(NodoArbolB nodo, int key, StringBuilder camino) {
        int i = 0;
        while (i < nodo.n && key > nodo.key[i]) {
            camino.append(nodo.key[i]).append(" ");
            i++;
        }
        if (i < nodo.n && key == nodo.key[i]) {
            camino.append(nodo.key[i]).append(" (Elemento encontrado)");
            return;
        }

        if (nodo.leaf) {
            camino.append("(Elemento no encontrado)");
            return;
        }

        camino.append(nodo.key[i]).append(" ");
        getCaminorecorrido(nodo.child[i], key, camino);
    }


}





///
    
    

