package gestionInventario;

public class Tienda {

    private ArbolProductos inventario;
    private ColaClientes colaClientes;

    public Tienda() {
        inventario = new ArbolProductos();
        colaClientes = new ColaClientes(20);
    }

    public void agregarProductoInventario(NodoProducto producto) {
        inventario.insertar(producto);
    }

    public NodoProducto buscarProductoInventario(String nombre) {
        NodoArbol nodo = inventario.buscar(nombre);
        if (nodo == null) {
            return null;
        }
        return nodo.getProducto();
    }

    public void mostrarInventario() {
        inventario.enOrden();
    }

    public void registrarCliente(Cliente cliente) {
        colaClientes.insertar(cliente);
    }

    public Cliente atenderSiguienteCliente() {
        return colaClientes.eliminar();
    }
}