package gestionInventario;
import javax.swing.*;
import java.util.ArrayList;

public class ListaProductos {

    private NodoProducto primero;

    public ListaProductos() {
        primero = null;
    }

    private boolean estaVacia(){
        return primero == null;
    }

    public void insertarInicio(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad) {

        NodoProducto producto = new NodoProducto(nombre, precio, categoria, fechaVencimiento, cantidad);

        producto.setSiguiente(primero);

        primero = producto;

        JOptionPane.showMessageDialog(null,"El producto se ha agregado al inicio.");
    }

    public void insertarFinal(String nombre, double precio, String categoria, String fechaVencimiento, int cantidad) {

        NodoProducto producto = new NodoProducto(nombre, precio, categoria, fechaVencimiento, cantidad);

        if (primero == null) {

            primero = producto;

        } else {

            NodoProducto temp = primero;

            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }

            temp.setSiguiente(producto);
        }

        JOptionPane.showMessageDialog(null,"El producto se agrego al final.");
    }

    public void mostrarProductos() {

        if (estaVacia()) {
            JOptionPane.showMessageDialog(null,"No hay productos registrados.");
            return;
        }

        NodoProducto temp = primero;

        while (temp != null) {
            if (temp.getListaImagenes().isEmpty()){
                JOptionPane.showMessageDialog(null,"\nNombre: " + temp.getNombre() + "\nPrecio: " + temp.getPrecio() +
                        "\nCategoria: " + temp.getCategoria()+ "\nFecha vencimiento: " + temp.getFechaVencimiento() +
                        "\nCantidad: " + temp.getCantidad() + "\nNo hay imagenes adjuntas del producto.");
            }else {
                JOptionPane.showMessageDialog(null, "\nNombre: " + temp.getNombre() + "\nPrecio: " + temp.getPrecio() +
                        "\nCategoria: " + temp.getCategoria() + "\nFecha vencimiento: " + temp.getFechaVencimiento() +
                        "\nCantidad: " + temp.getCantidad() + "\nImagenes: " + temp.getListaImagenes());
            }

            temp = temp.getSiguiente();
        }
    }

    public NodoProducto buscarProducto(String nombre) {

        if (estaVacia()) {
            JOptionPane.showMessageDialog(null,"No hay productos registrados.");
            return null;
        }

        NodoProducto temp = primero;

        while (temp != null) {

            if (temp.getNombre().equalsIgnoreCase(nombre)) {
                return temp;
            }
            temp = temp.getSiguiente();

        }
        JOptionPane.showMessageDialog(null, "\nNo hay productos con el nombre: " + nombre);
        return null;

    }


    public void modificarProducto(String nombreActual) {

        NodoProducto producto = buscarProducto(nombreActual);

        if (producto == null) {
            return;

        }
        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre del producto: ");
        double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio unitario del producto: "));
        String nuevaCategoria = JOptionPane.showInputDialog("Nueva categoría del producto: ");
        String nuevaFechaVencimiento = JOptionPane.showInputDialog("Nueva fecha de vencimiento del producto: ");
        int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Nuevo número de unidades que se van a almacenar: "));

        producto.setNombre(nuevoNombre);
        producto.setPrecio(nuevoPrecio);
        producto.setCategoria(nuevaCategoria);
        producto.setFechaVencimiento(nuevaFechaVencimiento);
        producto.setCantidad(nuevaCantidad);

        JOptionPane.showMessageDialog(null, "El producto " + nuevoNombre + " has sido actualizado correctamente.");

    }


    public NodoProducto eliminarProducto(String nombre) {

        if (estaVacia()) {

            JOptionPane.showMessageDialog(null,"No hay productos registrados.");
            return null;

        }

        if (primero.getNombre().equalsIgnoreCase(nombre)) {
            NodoProducto aux = primero;
            primero = primero.getSiguiente();
            return aux;

        }

        NodoProducto anterior = primero;
        NodoProducto actual = primero.getSiguiente();

        while (actual != null) {

            if (actual.getNombre().equalsIgnoreCase(nombre)) {

                anterior.setSiguiente(actual.getSiguiente());
                return actual;

            }

            anterior = actual;
            actual = actual.getSiguiente();

        }

        return null;

    }


    public void agregarImagenAProducto(String nombre, String rutaImagen) {

        NodoProducto producto = buscarProducto(nombre);

        if (producto == null) {
            return;

        }

        producto.agregarImagen(rutaImagen);

        JOptionPane.showMessageDialog(null,"La imagen se ha adjuntado correctamente");

    }


    public void mostrarReporteCostos() {

        if (primero == null) {

            JOptionPane.showMessageDialog(null,"No hay productos registrados.");
            return;

        }

        NodoProducto temp = primero;
        double totalAcumulado = 0;

        while (temp != null) {

            double totalProducto = temp.getPrecio() * temp.getCantidad();

            totalAcumulado += totalProducto;

            JOptionPane.showMessageDialog(null,"===== REPORTE DE COSTOS =====\n\nProducto: " + temp.getNombre() + "\nPrecio unitario: " + temp.getPrecio() +
                    "\nCantidad: " + temp.getCantidad() + "\nTotal del producto: " + totalProducto + "\n-----------------------------");

            temp = temp.getSiguiente();

        }

        JOptionPane.showMessageDialog(null,"Costo total acumulado: " + totalAcumulado);

    }
}
