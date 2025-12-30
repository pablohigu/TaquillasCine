package entregaPSP.Cine;

import java.util.ArrayList;
import java.util.List;

public class Cine {
    private int asientosDisponibles;
    private List<Cliente> listaClientes; 
    private boolean abierto;
    private int entradasVendidas;

    public Cine(int numeroAsientos) {
        this.asientosDisponibles = numeroAsientos;
        this.listaClientes = new ArrayList<>(); 
        this.abierto = true;
        this.entradasVendidas = 0;
    }
    public synchronized void entrarCine(Cliente cliente) {
        if (abierto) {
            listaClientes.add(cliente);
            System.out.println("LOG: Cliente " + cliente.getId() + " entra a la lista de espera.");
            notifyAll(); 
        }
    }

    public synchronized void cerrarCine() {
        this.abierto = false;
        notifyAll(); 
        System.out.println("--- CINE CERRADO ---");
    }
    public synchronized Cliente venderEntrada() {
        while (listaClientes.isEmpty() && abierto) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        if (asientosDisponibles <= 0) {
            return null; 
        }

        if (listaClientes.isEmpty()) {
            return null;
        }
        Cliente clienteAtendido = listaClientes.remove(0); 
        
        asientosDisponibles--;
        entradasVendidas++;
        
        return clienteAtendido;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }
    
    public int getGenteQuedaSinEntrada() {
        return listaClientes.size(); 
    }
    
    public boolean hayAsientosLibres() {
        return asientosDisponibles > 0;
    }
}