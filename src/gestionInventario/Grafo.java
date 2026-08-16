package gestionInventario;

import java.util.*;

public class Grafo {

    // Atributos
    private final Map<String, List<Arista>> listaAdyacencia;

    // Métodos
    // Constructor
    public Grafo() {
        listaAdyacencia = new HashMap<>();
    }

    public void agregarVertice(String nuevaVertice) {
        listaAdyacencia.putIfAbsent(nuevaVertice, new ArrayList<>());
    }

    public boolean existeVertice(String vertice) {
        return listaAdyacencia.containsKey(vertice);
    }

    public void agregarArista(String origen, String nuevoDestino, int pesoArista) {
        agregarVertice(origen); // El algoritmo se asegura de agregar los vértices si son nuevos
        agregarVertice(nuevoDestino);
        // El algoritmo agrega la arista a ambos vértices involucrados (no dirigido)
        listaAdyacencia.get(origen).add(new Arista(nuevoDestino, pesoArista));
        listaAdyacencia.get(nuevoDestino).add(new Arista(origen, pesoArista));
    }

    public void mostrarGrafo() {
        System.out.println(obtenerRepresentacion());
    }

    public String obtenerRepresentacion() {
        StringBuilder texto = new StringBuilder();
        texto.append("MAPA DE UBICACIONES\n");

        if (listaAdyacencia.isEmpty()) {
            texto.append("El mapa no tiene ubicaciones registradas.");
            return texto.toString();
        }

        for (Map.Entry<String, List<Arista>> entry : listaAdyacencia.entrySet()) {
            texto.append(entry.getKey()).append(" -> ");
            if (entry.getValue().isEmpty()) {
                texto.append("(sin conexiones)");
            } else {
                for (Arista arista : entry.getValue()) {
                    texto.append("[").append(arista.getDestino()).append(", ").append(arista.getPeso()).append("] ");
                }
            }
            texto.append("\n");
        }

        return texto.toString();
    }

    public void algoritmoDijkstra(String inicio,
                                  Map<String, Integer> distancias,
                                  Map<String, String> predecesores) {
        PriorityQueue<Vertice> colaVertices = new PriorityQueue<>(Comparator.comparingInt(Vertice::getDistancia));

        // Inicialización
        for (String vertice : listaAdyacencia.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
            predecesores.put(vertice, null);
        }

        if (!distancias.containsKey(inicio)) {
            // El vértice de inicio no existe en el grafo; no hay nada que explorar
            return;
        }

        distancias.put(inicio, 0);  // La distancia al vértice de inicio es 0 por defecto
        colaVertices.add(new Vertice(inicio, 0));

        while (!colaVertices.isEmpty()) {
            Vertice v = colaVertices.poll();
            String verticeActual = v.getNombre();

            // Explorar todos los vecinos
            for (Arista arista : listaAdyacencia.get(verticeActual)) {
                String vecino = arista.getDestino();
                int nuevaDistancia = distancias.get(verticeActual) + arista.getPeso();

                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    predecesores.put(vecino, verticeActual);
                    colaVertices.add(new Vertice(vecino, nuevaDistancia));
                }
            }
        }
    }

    public List<String> reconstruirCamino(String inicio, String destino, Map<String, String> predecesores) {
        List<String> camino = new ArrayList<>();

        for (String verticeActual = destino; verticeActual != null; verticeActual = predecesores.get(verticeActual)) {
            camino.add(verticeActual);
        }

        Collections.reverse(camino);
        if (!camino.isEmpty() && camino.get(0).equals(inicio)) {
            return camino;
        }

        return new ArrayList<>();
    }
}
