package gestionInventario;

import javax.swing.*;

public class Cliente {
    private String nombre;
    private int prioridad;
    private ListaProductos carrito;

    public Cliente(String nombre, int prioridad){
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.carrito = new ListaProductos();
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setCarrito(ListaProductos carrito) {
        this.carrito = carrito;
    }

    public void agregarProducto(NodoProducto producto, int cantidad){
        if (producto == null){
            JOptionPane.showMessageDialog(null, "El producto no existe en el inventario.");
            return;
        }

        carrito.insertarFinal(producto.getNombre(), producto.getPrecio(), producto.getCategoria(),
                producto.getFechaVencimiento(), cantidad);
    }

    public void mostrarFactura(){
        JOptionPane.showMessageDialog(null, "Factura de " + nombre + ":");
        carrito.mostrarReporteCostos();
    }
}