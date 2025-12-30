package entregaPSP.Cine;

public class SimulacionCine {

	    public static void main(String[] args) {
	        
	        // --- 1. CONFIGURACIÓN DE LA SIMULACIÓN ---
	        int AFORO_TOTAL = 200;
	        int MINUTOS_ANTES_PELICULA = 30; // La simulación dura 30 "ciclos"
	        
	        // Ritmo de llegada de clientes (por minuto)
	        int LLEGADA_MIN_CLIENTES = 10;
	        int LLEGADA_MAX_CLIENTES = 15;
	        
	        // Tiempo que tarda la taquilla en vender (en segundos simulados)
	        // Como 1 minuto real = 1 segundo de simulación, escalamos los tiempos.
	        // Si 60s (1 min) -> 1000ms
	        // Entonces 30s -> 500ms
	        int TIEMPO_VENTA_MIN = 20; 
	        int TIEMPO_VENTA_MAX = 30;
	        
	        // Factor de conversión para que la demo sea rápida
	        // (Convertimos los tiempos de "segundos reales" a "milisegundos de demo")
	        int FACTOR_ESCALA = 1000 / 60; // Aprox 16ms por segundo simulado
	        
	        System.out.println("--- INICIO DE SIMULACIÓN CINE ANTIGUO ---");
	        System.out.println("Aforo: " + AFORO_TOTAL);
	        System.out.println("Tiempo hasta la peli: " + MINUTOS_ANTES_PELICULA + " minutos.");
	        
	        // --- 2. CREACIÓN DE OBJETOS ---
	        
	        // Creamos el Monitor (Cine)
	        Cine cine = new Cine(AFORO_TOTAL);
	        
	        // Creamos los hilos Taquilla
	        // Pasamos los tiempos escalados para que la simulación sea fluida
	        Taquilla t1 = new Taquilla("Taquilla 1", cine, TIEMPO_VENTA_MIN * FACTOR_ESCALA, TIEMPO_VENTA_MAX * FACTOR_ESCALA);
	        Taquilla t2 = new Taquilla("Taquilla 2", cine, TIEMPO_VENTA_MIN * FACTOR_ESCALA, TIEMPO_VENTA_MAX * FACTOR_ESCALA);
	        
	        // Iniciamos las taquillas (empiezan a esperar clientes)
	        t1.start();
	        t2.start();
	        
	        // --- 3. BUCLE PRINCIPAL (El paso del tiempo) ---
	        
	        int idCliente = 1; // Contador para dar IDs únicos
	        
	        
	        for (int minuto = 1; minuto <= MINUTOS_ANTES_PELICULA; minuto++) {
	           
	            if (!cine.hayAsientosLibres()) {
	                System.out.println("¡SOLD OUT! Se acabaron las entradas en el minuto " + minuto);
	                break;
	            }

	            System.out.println("\n--- MINUTO " + minuto + " ---");
	         
	            int numNuevosClientes = (int) (Math.random() * (LLEGADA_MAX_CLIENTES - LLEGADA_MIN_CLIENTES + 1) + LLEGADA_MIN_CLIENTES);
	            
	            System.out.println(">>> Llegan " + numNuevosClientes + " personas a la cola.");
	      
	            for (int i = 0; i < numNuevosClientes; i++) {
	                cine.entrarCine(new Cliente(idCliente++));
	            }
	            
	         
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        cine.cerrarCine();
	        
	        
	        try {
	            t1.join();
	            t2.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.println("\n=========================================");
	        System.out.println("       ESTADÍSTICAS FINALES");
	        System.out.println("=========================================");
	        System.out.println("Asientos totales: " + AFORO_TOTAL);
	        System.out.println("Entradas vendidas: " + cine.getEntradasVendidas());
	        System.out.println("Gente que se quedó sin entrada (en cola): " + cine.getGenteQuedaSinEntrada());
	        
	        if (cine.hayAsientosLibres()) {
	            System.out.println("Resultado: Han sobrado butacas. ¡Necesitamos mejor marketing!");
	        } else {
	            System.out.println("Resultado: ¡Lleno absoluto!");
	        }
	    }
	}

