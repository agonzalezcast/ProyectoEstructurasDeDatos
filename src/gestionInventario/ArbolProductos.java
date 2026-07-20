package gestionInventario;

public class ArbolProductos {
    //Atributos
    private NodoArbol raiz;

    //Constructor
    public ArbolProductos(){
        raiz = null;
    }

    //Getter
    public NodoArbol getRaiz() {
        return raiz;
    }

    //Setter
    public void setRaiz(NodoArbol raiz) {
        this.raiz = raiz;
    }

    //Operaciones
    private boolean estaVacio(){return raiz == null;}


    public void insertar(NodoProducto producto){
        NodoArbol nodo = new NodoArbol(producto);

        if(estaVacio()){
            setRaiz(nodo);
            return;
        }

        NodoArbol temp = raiz;
        NodoArbol padreTemp= temp;

        while (temp!=null){
            padreTemp = temp;
            if(temp.getProducto().getNombre().compareToIgnoreCase(producto.getNombre()) < 0) temp = temp.getDer();
            else if(temp.getProducto().getNombre().compareToIgnoreCase(producto.getNombre()) > 0) temp = temp.getIzq();
            else{
                System.out.println("\nEl producto ya se encuentra en el inventario.\n");
                return;
            }
        }

        if(padreTemp.getProducto().getNombre().compareToIgnoreCase(producto.getNombre()) < 0) padreTemp.setDer(nodo);
        else padreTemp.setIzq(nodo);
    }

    public NodoArbol buscar(String nombreProducto) {
        if (estaVacio()) {
            System.out.println("El inventario está vacío.\n");
            return null;
        }
        NodoArbol temp = raiz;
        while (temp != null) {
            int comparacion = temp.getProducto().getNombre().compareToIgnoreCase(nombreProducto);
            if (comparacion == 0) {
                return temp;
            }
            if (comparacion < 0) {
                temp = temp.getDer();
            } else {
                temp = temp.getIzq();
            }
        }
        System.out.println("El producto buscado no está en el inventario.\n");
        return null;
    }

    public NodoArbol buscarPadre(String nombreProducto){
        if(estaVacio()){
            System.out.println("El inventario está vacío.\n");
            return null;
        }

        NodoArbol temp = raiz;
        NodoArbol padreTemp= temp;

        while (temp!=null && temp.getProducto().getNombre().compareToIgnoreCase(nombreProducto) != 0){
            padreTemp = temp;
            if(temp.getProducto().getNombre().compareToIgnoreCase(nombreProducto) < 0) temp = temp.getDer();
            else temp = temp.getIzq();
        }
        if(temp.getProducto().getNombre().compareToIgnoreCase(nombreProducto) == 0){
            return padreTemp;
        }else{
            System.out.println("El producto buscado no está en el inventario.\n");
            return null;
        }
    }

    private void enOrdenRec(NodoArbol raiz, StringBuilder texto) {
        if (raiz != null) {
            enOrdenRec(raiz.getIzq(), texto);
            NodoProducto producto = raiz.getProducto();

            texto.append("Nombre: ").append(producto.getNombre()).append("\n");
            texto.append("Precio: ").append(producto.getPrecio()).append("\n");
            texto.append("Categoría: ").append(producto.getCategoria()).append("\n");
            texto.append("Fecha de vencimiento: ").append(producto.getFechaVencimiento()).append("\n");
            texto.append("Cantidad: ").append(producto.getCantidad()).append("\n");
            texto.append("-----------------------------\n");

            enOrdenRec(raiz.getDer(), texto);
        }
    }

    public String obtenerInventario() {
        if (estaVacio()) {
            return "El inventario está vacío.";
        }

        StringBuilder texto = new StringBuilder();
        texto.append("===== INVENTARIO =====\n\n");
        enOrdenRec(raiz, texto);
        return texto.toString();

    }

    private void preOrdenRec(NodoArbol raiz){
        if (raiz != null){
            System.out.print(raiz.getProducto().getNombre() + " ");
            preOrdenRec(raiz.getIzq());
            preOrdenRec(raiz.getDer());
        }
    }

    public void preOrden(){
        preOrdenRec(raiz);
        System.out.println("\n");
    }

    private void postOrdenRec(NodoArbol raiz){
        if (raiz != null){
            postOrdenRec(raiz.getIzq());
            postOrdenRec(raiz.getDer());
            System.out.print(raiz.getProducto().getNombre() + " ");
        }
    }

    public void postOrden(){
        postOrdenRec(raiz);
        System.out.println("\n");
    }

    private NodoArbol buscarSucesor(NodoArbol nodo){
        NodoArbol temp = nodo.getDer();
        NodoArbol sucesor = nodo;
        NodoArbol padreSucesor = sucesor;

        while(temp != null){
            padreSucesor = sucesor;
            sucesor = temp;
            temp = temp.getIzq();
        }

        if(sucesor != nodo.getDer()){
            padreSucesor.setIzq(sucesor.getDer());
            sucesor.setDer(nodo.getDer());
        }
        return sucesor;
    }

    public NodoArbol eliminar(String nombreProducto){
        //Considerar el caso de que la estructura esté vacía
        if(estaVacio()){
            System.out.println("El inventario está vacío.\n");
            return null;
        }
        //Buscar el nodo dentro de la estructura; si no está, se da un mensje y retorna null
        NodoArbol nodo = buscar(nombreProducto);
        if(nodo == null) return null;
        if (nodo == raiz){//Si el nodo por eliminar es la raiz, se consideran sus hijos:
            if(nodo.getDer() == null && nodo.getIzq() == null) setRaiz(null);//Si no tiene, se pone la raiz en null y se retorna;
            else if(nodo.getDer() == null) setRaiz(nodo.getIzq());//Si solo tiene izq, ese hijo izq se vuelve la nueva raiz
            else if(nodo.getIzq() == null) setRaiz(nodo.getDer());//Si solo tiene der, ese hijo der se vuelve la nueva raiz
            else{//Si tiene los dos, buscamos su sucesor, le ponemos como hijo izq el hijo izq de la raiz
                NodoArbol sucesor = buscarSucesor(nodo);
                sucesor.setIzq(raiz.getIzq());
                setRaiz(sucesor);
            }return nodo;
        }
        //Si es cualquier otro nodo:
        NodoArbol padre = buscarPadre(nombreProducto);// Buscamos su padre y lo almacenamos en una variable
        if(nodo.getDer() == null && nodo.getIzq() == null){//Si no tiene hijos:
            if(nodo == padre.getIzq()) padre.setIzq(null);//Si es un hijo izq, se pone null a su padre por la izq
            else padre.setDer(null);//Si es un hijo der, se pone null a su padre por la der
        }else if(nodo.getDer() == null){//Si solo tiene izq:
            if(nodo == padre.getIzq()) padre.setIzq(nodo.getIzq());//Si es un hijo izq, se pone su hijo como hijo izq a su padre
            else padre.setDer(nodo.getIzq());//Si es un hijo der, se pone su hijo como hijo der a su padre
        }else if(nodo.getIzq() == null){//Si solo tiene der:
            if(nodo == padre.getIzq()) padre.setIzq(nodo.getDer());//Si es un hijo izq, se pone su hijo como hijo izq a su padre
            else padre.setDer(nodo.getDer());//Si es un hijo der, se pone su hijo como hijo der a su padre
        }else{//Si tiene dos hijos:
            NodoArbol sucesor = buscarSucesor(nodo);
            sucesor.setIzq(nodo.getIzq());
            if(nodo == padre.getIzq()) padre.setIzq(sucesor);//Si es un hijo izq, se pone su sucesor como hijo izq a su padre
            else padre.setDer(sucesor);//Si es un hijo der, se pone su sucesor como hijo der a su padre
        }
        return nodo;
    }

}
