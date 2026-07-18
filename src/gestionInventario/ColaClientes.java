package gestionInventario;

public class ColaClientes {

    private Cliente[] elementos;
    private int cantidad;

    public ColaClientes(int longitud){
        elementos = new Cliente[longitud];
        cantidad = 0;
    }

    private boolean estaVacia() {
        return cantidad == 0;
    }

    private boolean estaLlena(){
        return cantidad == elementos.length;
    }

    public void insertar(Cliente cliente){
        if (estaLlena()){
            System.out.println("La cola esta llena.");
            return;
        }

        int posicion = cantidad;

        while (posicion > 0 && elementos[posicion - 1].getPrioridad() < cliente.getPrioridad()){
            elementos[posicion] = elementos[posicion - 1];
            posicion--;
        }

        elementos[posicion] = cliente;
        cantidad++;
    }

    public Cliente eliminar(){
        if (estaVacia()){
            System.out.println("La cola de clientes esta vacía.");
            return null;
        }

        Cliente temp = elementos[0];

        for (int i = 0; i < cantidad - 1; i++){
            elementos[i] = elementos[i + 1];
        }
        elementos[cantidad - 1] = null;
        cantidad--;

        return temp;
    }

    public Cliente verFrente(){
        if (estaVacia()){
            System.out.println("La cola de clientes esta vacía.");
            return null;
        }
        return elementos[0];
    }

}
