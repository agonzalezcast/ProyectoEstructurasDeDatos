import gestionInventario.Cliente;
import gestionInventario.NodoProducto;
import gestionInventario.Tienda;

import javax.swing.*;

public class Main {

    public static Tienda tienda = new Tienda();

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "SISTEMA DE GESTIÓN DE INVENTARIOS\n\n" +
                            "1. Agregar producto al inventario\n" +
                            "2. Mostrar inventario\n" +
                            "3. Registrar cliente y llenar carrito\n" +
                            "4. Atender siguiente cliente\n" +
                            "0. Salir\n\n" +
                            "Seleccione una opción:"
            ));
            switch (opcion) {
                case 1:
                    agregarProductoInventario();
                    break;

                case 2:
                    mostrarInventario();
                    break;

                case 3:
                    registrarCliente();
                    break;

                case 4:
                    atenderCliente();
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void agregarProductoInventario() {

        String nombre = JOptionPane.showInputDialog("Nombre del producto:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio unitario del producto:"));
        String categoria = JOptionPane.showInputDialog("Categoría del producto:");
        String fechaVencimiento = JOptionPane.showInputDialog("Fecha de vencimiento:");
        int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad disponible en inventario:"));

        NodoProducto producto = new NodoProducto(nombre, precio, categoria, fechaVencimiento, cantidad);
        tienda.agregarProductoInventario(producto);
        JOptionPane.showMessageDialog(null, "Producto agregado al inventario correctamente.");
    }

    public static void mostrarInventario() {
        tienda.mostrarInventario();
    }

    public static void registrarCliente() {
        String nombreCliente = JOptionPane.showInputDialog("Nombre del cliente:");

        int prioridad = Integer.parseInt(JOptionPane.showInputDialog(
                "Prioridad del cliente:\n\n" +
                        "1. Básico\n" +
                        "2. Afiliado\n" +
                        "3. Premium"
        ));

        if (prioridad < 1 || prioridad > 3) {
            JOptionPane.showMessageDialog(null, "La prioridad debe estar entre 1 y 3.");
            return;
        }

        Cliente cliente = new Cliente(nombreCliente, prioridad);

        int agregarMas;

        do {
            String nombreProducto = JOptionPane.showInputDialog("Nombre del producto que desea agregar al carrito:");
            NodoProducto productoInventario = tienda.buscarProductoInventario(nombreProducto);
            if (productoInventario == null) {
                JOptionPane.showMessageDialog(null, "El producto no existe en el inventario.");
            } else {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad que desea agregar al carrito:"));
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0.");
                } else {
                    cliente.agregarProducto(productoInventario, cantidad);
                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito.");
                }
            }
            agregarMas = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea agregar otro producto al carrito?",
                    "Carrito",
                    JOptionPane.YES_NO_OPTION
            );
        } while (agregarMas == JOptionPane.YES_OPTION);
        tienda.registrarCliente(cliente);
        JOptionPane.showMessageDialog(null, "Cliente registrado en la cola correctamente.");
    }

    public static void atenderCliente() {
        Cliente cliente = tienda.atenderSiguienteCliente();
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "No hay clientes en la cola.");
            return;
        }
        cliente.mostrarFactura();
    }
}