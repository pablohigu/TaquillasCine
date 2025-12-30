package entregaPSP.Cine;

public class Taquilla extends Thread{
	    private String nombre; 
	    private Cine cine;    
	    private int tiempoMinVenta; 
	    private int tiempoMaxVenta;

	    public Taquilla(String nombre, Cine cine, int tiempoMinSegundos, int tiempoMaxSegundos) {
	        this.nombre = nombre;
	        this.cine = cine;
	        this.tiempoMinVenta = tiempoMinSegundos * 1000;
	        this.tiempoMaxVenta = tiempoMaxSegundos * 1000;
	    }

	    @Override
	    public void run() {
	        System.out.println( this.nombre + " ABIERTA y lista para vender.");

	        while (true) {
	          
	            Cliente cliente = cine.venderEntrada();
	            if (cliente == null) {
	                break;
	            }

	            try {
	               
	                long tiempoProceso = (long) (Math.random() * (tiempoMaxVenta - tiempoMinVenta + 1) + tiempoMinVenta);
	                
	                System.out.println(this.nombre + " atendiendo al Cliente " + cliente.getId() + 
	                                   " (" + (tiempoProceso/1000) + "s simulados)...");
	                
	               
	                Thread.sleep(tiempoProceso);
	                
	                System.out.println(this.nombre + " ha vendido entrada al Cliente " + cliente.getId());

	            } catch (InterruptedException e) {
	               
	                System.out.println("⚠️ " + this.nombre + " interrumpida.");
	                break;
	            }
	        }

	        System.out.println( this.nombre + " CERRADA.");
	    }
	}
