package entregaPSP.Cine;

public class SimulacionCine {

    public static void main(String[] args) {
        
       
        int AFORO_TOTAL = 50;           
        int NUM_TAQUILLAS = 2;         
        int NUM_COLAS = 4;             
        int MAX_PERSONAS_COLA = 10;     
        
    
        int TASA_CREACION_CLIENTES = 500; 
        int TIEMPO_VENTA_MIN = 1000;
        int TIEMPO_VENTA_MAX = 2000;
        
        System.out.println("--- INICIO  ---");
        System.out.println("Config: " + NUM_COLAS + " colas de max " + MAX_PERSONAS_COLA + " personas.");
        
        Cine cine = new Cine(AFORO_TOTAL, NUM_COLAS, MAX_PERSONAS_COLA);

        Taquilla[] taquillas = new Taquilla[NUM_TAQUILLAS];
        for (int i = 0; i < NUM_TAQUILLAS; i++) {
            taquillas[i] = new Taquilla("Taquilla " + (i + 1), cine, TIEMPO_VENTA_MIN, TIEMPO_VENTA_MAX);
            taquillas[i].start();
        }
        
      
        int idCliente = 1;
       
        while (cine.hayAsientosLibres()) {
            
            Cliente nuevoCliente = new Cliente(idCliente++);
            cine.entrarCine(nuevoCliente);
            
            try {
                Thread.sleep(TASA_CREACION_CLIENTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       
        cine.cerrarCine();
        
        try {
            for (Taquilla t : taquillas) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\n--- ESTADÍSTICAS FINALES ---");
        System.out.println("Entradas vendidas: " + cine.getEntradasVendidas());
        int totalClientesGenerados = idCliente - 1;
        int clientesSinEntrada = totalClientesGenerados - cine.getEntradasVendidas();
        System.out.println("Se quedaron sin entrada: " + clientesSinEntrada);
    }
}