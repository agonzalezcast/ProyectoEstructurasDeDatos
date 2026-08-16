package gestionInventario;

import javax.swing.*;
import java.util.List;

public class Cliente {
    private String nombre;
    private int prioridad;
    private String ubicacion;
    private ListaProductos carrito;

    private List<String> caminoEntrega;
    private int distanciaEntrega;

    public Cliente(String nombre, int prioridad, String ubicacion){
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.ubicacion = ubicacion;
        this.carrito = new ListaProductos();
        this.caminoEntrega = null;
        this.distanciaEntrega = -1;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    public List<String> getCaminoEntrega() {
        return caminoEntrega;
    }

    public int getDistanciaEntrega() {
        return distanciaEntrega;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setCarrito(ListaProductos carrito) {
        this.carrito = carrito;
    }

    public void setCaminoEntrega(List<String> caminoEntrega) {
        this.caminoEntrega = caminoEntrega;
    }

    public void setDistanciaEntrega(int distanciaEntrega) {
        this.distanciaEntrega = distanciaEntrega;
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

    public void mostrarRutaEntrega() {
        if (caminoEntrega == null || caminoEntrega.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay información de ruta de entrega disponible para " + nombre + ".");
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append("RUTA DE ENTREGA ÓPTIMA\n");
        texto.append("Cliente: ").append(nombre).append("\n");
        texto.append("Ubicación de origen: ").append(ubicacion).append("\n\n");
        texto.append("Camino más corto hacia la Tienda:\n");
        texto.append(String.join(" -> ", caminoEntrega));
        texto.append("\nDistancia total del recorrido: ").append(distanciaEntrega);

        JOptionPane.showMessageDialog(null, texto.toString());
    }
}
