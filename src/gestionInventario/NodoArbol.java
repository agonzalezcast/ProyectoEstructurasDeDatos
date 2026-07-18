package gestionInventario;

public class NodoArbol {

    private NodoProducto producto;
    private NodoArbol izq;
    private NodoArbol der;

    public NodoArbol (NodoProducto producto){
        this.producto = producto;
        izq = der = null;
    }

    public NodoProducto getProducto() {
        return producto;
    }

    public NodoArbol getIzq() {
        return izq;
    }

    public NodoArbol getDer() {
        return der;
    }

    public void setProducto(NodoProducto producto) {
        this.producto = producto;
    }

    public void setIzq(NodoArbol izq) {
        this.izq = izq;
    }

    public void setDer(NodoArbol der) {
        this.der = der;
    }
}
