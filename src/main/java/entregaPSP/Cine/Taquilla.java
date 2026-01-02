package entregaPSP.Cine;

public class Taquilla extends Thread {
    private String nombre;
    private Cine cine;
    private int tiempoMinVenta;
    private int tiempoMaxVenta;

    public Taquilla(String nombre, Cine cine, int tiempoMinVenta, int tiempoMaxVenta) {
        
        this.nombre = nombre;
        this.cine = cine;
        this.tiempoMinVenta = tiempoMinVenta;
        this.tiempoMaxVenta = tiempoMaxVenta;
    }

    @Override
    public void run() {
        System.out.println(this.nombre + " ABIERTA.");

        while (cine.estaAbierto() || cine.hayClientesEsperando()) {
            
            if (!cine.hayAsientosLibres()) {
                break;
            }

          
            int totalColas = cine.getNumeroColas();
            int colaElegida = (int) (Math.random() * totalColas);

            
            Cliente cliente = cine.despacharCliente(colaElegida);

            if (cliente != null) {
               
                try {
                    System.out.println("Procesando: " + this.nombre + " atiende a Cliente " 
                                       + cliente.getId() + " de la COLA " + (colaElegida + 1));
                    
                    long tiempo = (long) (Math.random() * (tiempoMaxVenta - tiempoMinVenta + 1) + tiempoMinVenta);
                    Thread.sleep(tiempo);
                    
                    System.out.println("💰 " + this.nombre + " vendió entrada a Cliente " + cliente.getId());
                    
                } catch (InterruptedException e) {
                    break;
                }
            } else {
              
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {}
            }
        }
        System.out.println(this.nombre + " CERRADA.");
    }
}