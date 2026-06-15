import gestionInventario.ListaProductos;
import gestionInventario.NodoProducto;

import javax.swing.*;
import java.io.IOException;

public class Main {

    public static ListaProductos aplicacion = new ListaProductos();

    public static void main(String[] args) throws IOException {
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "Sistema de Gestiones " + "\n" + "1. Cliente\n" + "2. Administrador\n" + "0. Salir"));

            switch (opcion) {
                case 1:
                    JOptionPane.showMessageDialog(null,"Esta sección está en desarrollo. Volviendo al menú principal.");
                    break;
                case 2:
                    menuAdministrador();
                    break;
            }

        } while (opcion != 0);
    }

    public static void menuCliente() {
        //En desarrollo
    }

    public static void menuAdministrador() {
        int opcionAdmin;
        do {
            opcionAdmin = Integer.parseInt(JOptionPane.showInputDialog(
                    "MENÚ ADMINISTRADOR\n\n" + "1. Agregar producto al inicio de la lista\n" + "2. Agregar producto al final de la lista\n" +
                            "3. Mostrar todos los productos\n" + "4. Buscar producto\n" +
                            "5. Modificar producto\n" + "6. Agregar imagen a un producto\n" +
                            "7. Eliminar producto\n" + "8. Reporte de costos totales\n" + "0. Volver al menú principal\n"));

            if (opcionAdmin == 1) {
                String nombre = JOptionPane.showInputDialog("Nombre del producto: ");
                double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio unitario del producto: "));
                String categoria = JOptionPane.showInputDialog("Categoría del producto: ");
                String fechaVencimiento = JOptionPane.showInputDialog("Fecha de vencimiento del producto: ");
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Número de unidades que se van a almacenar: "));
                aplicacion.insertarInicio(nombre, precio, categoria, fechaVencimiento, cantidad);
            }
            if (opcionAdmin == 2) {
                String nombre = JOptionPane.showInputDialog("Nombre del producto: ");
                double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio unitario del producto: "));
                String categoria = JOptionPane.showInputDialog("Categoría del producto: ");
                String fechaVencimiento = JOptionPane.showInputDialog("Fecha de vencimiento del producto: ");
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Número de unidades que se van a almacenar: "));
                aplicacion.insertarFinal(nombre, precio, categoria, fechaVencimiento, cantidad);
            }
            if (opcionAdmin == 3) {
                aplicacion.mostrarProductos();
            }
            if (opcionAdmin == 4) {
                String nombre = JOptionPane.showInputDialog("Nombre del producto que desea buscar: ");
                NodoProducto temp = aplicacion.buscarProducto(nombre);
                if (temp != null){
                    if (temp.getListaImagenes().isEmpty()){
                        JOptionPane.showMessageDialog(null,"\nNombre: " + temp.getNombre() + "\nPrecio: " + temp.getPrecio() +
                                "\nCategoria: " + temp.getCategoria()+ "\nFecha vencimiento: " + temp.getFechaVencimiento() +
                                "\nCantidad: " + temp.getCantidad() + "\nNo hay imagenes adjuntas del producto.");
                    }else {
                        JOptionPane.showMessageDialog(null, "\nNombre: " + temp.getNombre() + "\nPrecio: " + temp.getPrecio() +
                                "\nCategoria: " + temp.getCategoria() + "\nFecha vencimiento: " + temp.getFechaVencimiento() +
                                "\nCantidad: " + temp.getCantidad() + "\nImagenes: " + temp.getListaImagenes());
                    }
                }
            }
            if (opcionAdmin == 5) {
                String nombreViejo = JOptionPane.showInputDialog("Nombre del producto que desea modificar: ");

                aplicacion.modificarProducto(nombreViejo);
            }
            if (opcionAdmin == 6) {
                String nombre = JOptionPane.showInputDialog("Nombre del producto al que desea adjuntar una imagen: ");
                String ruta = JOptionPane.showInputDialog("Ruta donde se almacena la imagen: ");
                aplicacion.agregarImagenAProducto(nombre, ruta);
            }
            if (opcionAdmin == 7) {
                String nombre = JOptionPane.showInputDialog("Nombre del producto que desea eliminar: ");
                aplicacion.eliminarProducto(nombre);
            }
            if (opcionAdmin == 8) {
                aplicacion.mostrarReporteCostos();
            }
        }while (opcionAdmin != 0);
    }
}

