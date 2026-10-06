import java.util.Random;

// esta clase simula la descarga de un archivo en su propio hilo
// hereda de Thread, asi cada Descarga ES un hilo
public class Descarga extends Thread {

    // de momento no tocamos esto, sirve para ajustar la duracion de los bloques si hiciera falta
    private static final int AJUSTE_BLOQUE = 1;

    private String nombreArchivo;
    private int tiempoBloque; // cuanto tarda cada uno de los 10 bloques, en ms
    private long tiempoTotal; // cuanto ha tardado la descarga entera

    // el constructor recibe el nombre del archivo y le pone nombre al hilo ademas elige al azar cuanto va a tardar cada bloque (entre 100 y 500 ms)
    public Descarga(String nombreArchivo) {
        super("Descarga-" + nombreArchivo);

        this.nombreArchivo = nombreArchivo;

        Random random = new Random();
        this.tiempoBloque = 100 + random.nextInt(401);
    }

    // aqui es donde se descarga el archivo
    public void run() {
        long inicio = System.currentTimeMillis();

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(tiempoBloque * AJUSTE_BLOQUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[" + nombreArchivo + "] " + (i * 10) + "%");
        }

        tiempoTotal = System.currentTimeMillis() - inicio;
        System.out.println("[" + nombreArchivo + "] completada en " + tiempoTotal + " ms");
    }

    public long getTiempoTotal() {
        return tiempoTotal;
    }
    public String getNombreArchivo() {
        return nombreArchivo;
    }
}