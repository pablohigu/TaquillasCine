package entregaPSP.Cine;

import java.util.ArrayList;
import java.util.List;

public class Cine {
    private int asientosDisponibles;
    private List<List<Cliente>> sistemaColas; 
    
    private int maxPorCola;
    private int numeroColas;
    
    private boolean abierto;
    private int entradasVendidas;

   
    public Cine(int numeroAsientos, int numeroColas, int maxPorCola) {
        this.asientosDisponibles = numeroAsientos;
        this.numeroColas = numeroColas;
        this.maxPorCola = maxPorCola;
        this.abierto = true;
        this.entradasVendidas = 0;
        this.sistemaColas = new ArrayList<>();
        for (int i = 0; i < numeroColas; i++) {
            sistemaColas.add(new ArrayList<>());
        }
    }
    public synchronized boolean entrarCine(Cliente cliente) {
        if (!abierto) return false;
        
       
        for (int i = 0; i < numeroColas; i++) {
            List<Cliente> cola = sistemaColas.get(i);
            
            if (cola.size() < maxPorCola) {
                cola.add(cliente);
                System.out.println("LOG: Cliente " + cliente.getId() + " entra a la COLA " + (i+1) 
                        + " (" + cola.size() + "/" + maxPorCola + ")");
                notifyAll(); 
                return true; 
            }
        }
        
       
        System.out.println("Cliente " + cliente.getId() + " se marcha: TODAS LAS COLAS LLENAS.");
        return false;
    }

    public synchronized Cliente despacharCliente(int indiceCola) {
        
   
        if (asientosDisponibles <= 0 || indiceCola < 0 || indiceCola >= numeroColas) {
            return null;
        }

        List<Cliente> colaSeleccionada = sistemaColas.get(indiceCola);
        
        if (colaSeleccionada.isEmpty()) {
            return null; 
        }

     
        Cliente cliente = colaSeleccionada.remove(0);
        
        asientosDisponibles--;
        entradasVendidas++;
        
   
        notifyAll(); 
        
        return cliente;
    }

    public synchronized void cerrarCine() {
        this.abierto = false;
        notifyAll();
        System.out.println("--- CINE CERRADO ---");
    }
    
    public boolean hayAsientosLibres() { return asientosDisponibles > 0; }
    public boolean estaAbierto() { return abierto; }
    public int getEntradasVendidas() { return entradasVendidas; }
    public int getNumeroColas() { return numeroColas; }
   
    public synchronized boolean hayClientesEsperando() {
        for(List<Cliente> c : sistemaColas) {
            if(!c.isEmpty()) return true;
        }
        return false;
    }
}