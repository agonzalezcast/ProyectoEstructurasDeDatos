package gestionInventario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tienda {

    private ArbolProductos inventario;
    private ColaClientes colaClientes;
    private String ubicacion;
    private Grafo grafoUbicaciones;

    public Tienda() {
        inventario = new ArbolProductos();
        colaClientes = new ColaClientes(20);
        ubicacion = "Tienda";
        grafoUbicaciones = new Grafo();
        inicializarMapaBasico();
    }

    private void inicializarMapaBasico() {
        grafoUbicaciones.agregarVertice(ubicacion);
        grafoUbicaciones.agregarVertice("Alajuela");
        grafoUbicaciones.agregarVertice("Guanacaste");
        grafoUbicaciones.agregarVertice("Cartago");
        grafoUbicaciones.agregarVertice("Heredia");
        grafoUbicaciones.agregarVertice("San José");

        grafoUbicaciones.agregarArista(ubicacion, "San José", 3);
        grafoUbicaciones.agregarArista("San José", "Alajuela", 5);
        grafoUbicaciones.agregarArista("San José", "Guanacaste", 6);
        grafoUbicaciones.agregarArista("San José", "Cartago", 2);
        grafoUbicaciones.agregarArista("Alajuela", "Cartago", 4);
        grafoUbicaciones.agregarArista("Guanacaste", "Heredia", 7);
        grafoUbicaciones.agregarArista(ubicacion, "Heredia", 10);
    }

    public String getUbicacion() {
        return ubicacion;
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

    public String mostrarInventario() {
        return inventario.obtenerInventario();
    }

    public void registrarCliente(Cliente cliente) {
        grafoUbicaciones.agregarVertice(cliente.getUbicacion());
        colaClientes.insertar(cliente);
    }

    public boolean hayClientesEnCola() {
        return !colaClientes.estaVacia();
    }

    // Gestión del Grafo desde el menú
    public void agregarVerticeGrafo(String nuevaUbicacion) {
        grafoUbicaciones.agregarVertice(nuevaUbicacion);
    }

    public void agregarAristaGrafo(String origen, String destino, int peso) {
        grafoUbicaciones.agregarArista(origen, destino, peso);
    }

    public boolean existeUbicacion(String ubicacion) {
        return grafoUbicaciones.existeVertice(ubicacion);
    }

    public String mostrarMapaUbicaciones() {
        return grafoUbicaciones.obtenerRepresentacion();
    }

    public Cliente atenderSiguienteCliente() {
        Cliente frente = colaClientes.verFrente();
        if (frente == null) {
            return null; // Cola vacía
        }

        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        grafoUbicaciones.algoritmoDijkstra(frente.getUbicacion(), distancias, predecesores);

        Integer distanciaHaciaTienda = distancias.get(ubicacion);

        if (distanciaHaciaTienda == null || distanciaHaciaTienda == Integer.MAX_VALUE) {
            return null;
        }

        Cliente cliente = colaClientes.eliminar();
        List<String> camino = grafoUbicaciones.reconstruirCamino(cliente.getUbicacion(), ubicacion, predecesores);
        cliente.setCaminoEntrega(camino);
        cliente.setDistanciaEntrega(distanciaHaciaTienda);
        return cliente;
    }
}
