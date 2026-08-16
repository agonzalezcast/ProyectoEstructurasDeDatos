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

        int opcion = -1;

        do {

            String entrada = JOptionPane.showInputDialog(
                    "SISTEMA DE GESTIÓN DE INVENTARIOS\n\n" +
                            "1. Agregar producto al inventario\n" +
                            "2. Mostrar inventario\n" +
                            "3. Registrar cliente y llenar carrito\n" +
                            "4. Atender siguiente cliente\n" +
                            "5. Agregar ubicación al mapa (vértice)\n" +
                            "6. Agregar conexión entre ubicaciones (arista)\n" +
                            "7. Mostrar mapa de ubicaciones\n" +
                            "0. Salir\n" +
                            "Seleccione una opción:"
            );

            if (entrada == null) {
                opcion = 0;
                JOptionPane.showMessageDialog(null, "Saliendo del sistema.");
                break;
            }

            if (entrada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una opción.");
                continue;
            }

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
                continue;
            }

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
                case 5:
                    agregarUbicacion();
                    break;
                case 6:
                    agregarConexion();
                    break;
                case 7:
                    mostrarMapaUbicaciones();
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
        JOptionPane.showMessageDialog(null, tienda.mostrarInventario());
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

        String ubicacionCliente = JOptionPane.showInputDialog(
                "Ubicación del cliente (mapa actual):\n" + tienda.mostrarMapaUbicaciones() +
                        "\nIngrese el nombre de la ubicación del cliente:\n" +
                        "(si es una ubicación nueva, se agregará automáticamente al mapa, " +
                        "pero deberá conectarla con una arista antes de poder atenderlo)"
        );

        if (ubicacionCliente == null || ubicacionCliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una ubicación válida para el cliente.");
            return;
        }

        Cliente cliente = new Cliente(nombreCliente, prioridad, ubicacionCliente.trim());

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

                } else if (cantidad > productoInventario.getCantidad()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "No hay suficientes unidades disponibles.\n" +
                                    "Cantidad disponible: " + productoInventario.getCantidad()
                    );

                } else {

                    cliente.agregarProducto(productoInventario, cantidad);

                    productoInventario.setCantidad(
                            productoInventario.getCantidad() - cantidad
                    );

                    JOptionPane.showMessageDialog(
                            null,
                            "Producto agregado al carrito.\n" +
                                    "Unidades restantes: " + productoInventario.getCantidad()
                    );

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

        if (!tienda.hayClientesEnCola()) {
            JOptionPane.showMessageDialog(null, "No hay clientes en la cola.");
            return;
        }

        Cliente cliente = tienda.atenderSiguienteCliente();

        if (cliente == null) {
            JOptionPane.showMessageDialog(null,
                    "No se puede atender al siguiente cliente: su ubicación está desconectada " +
                            "del resto del mapa.\n\nAgregue una conexión (arista) que la vincule con el " +
                            "resto de las ubicaciones y vuelva a intentarlo.");
            return;
        }

        cliente.mostrarFactura();
        cliente.mostrarRutaEntrega();
    }

    public static void agregarUbicacion() {
        String nuevaUbicacion = JOptionPane.showInputDialog("Nombre de la nueva ubicación:");

        if (nuevaUbicacion == null || nuevaUbicacion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de ubicación válido.");
            return;
        }

        tienda.agregarVerticeGrafo(nuevaUbicacion.trim());
        JOptionPane.showMessageDialog(null, "Ubicación agregada al mapa correctamente.");
    }

    public static void agregarConexion() {
        JOptionPane.showMessageDialog(null, "Mapa actual de ubicaciones:\n" + tienda.mostrarMapaUbicaciones());

        String origen = JOptionPane.showInputDialog("Ubicación de origen:");
        if (origen == null || origen.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una ubicación de origen válida.");
            return;
        }

        String destino = JOptionPane.showInputDialog("Ubicación de destino:");
        if (destino == null || destino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una ubicación de destino válida.");
            return;
        }

        int peso;
        try {
            peso = Integer.parseInt(JOptionPane.showInputDialog("Distancia (peso) entre ambas ubicaciones:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un número válido para la distancia.");
            return;
        }

        if (peso <= 0) {
            JOptionPane.showMessageDialog(null, "La distancia debe ser mayor a 0.");
            return;
        }

        tienda.agregarAristaGrafo(origen.trim(), destino.trim(), peso);
        JOptionPane.showMessageDialog(null, "Conexión agregada correctamente entre " + origen + " y " + destino + ".");
    }

    public static void mostrarMapaUbicaciones() {
        JOptionPane.showMessageDialog(null, tienda.mostrarMapaUbicaciones());
    }
}
