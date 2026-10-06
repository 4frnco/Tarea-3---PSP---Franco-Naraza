// esta clase simula la instalacion, pero no puede empezar hasta que terminen las descargas de meditacion.mp4 y mantras.mp3 (nivel 3)
// implementa Runnable, al igual que Monitor, pero lo importante es que es distinta a Descarga
public class Instalador implements Runnable {

    private Descarga meditacion;
    private Descarga mantras;

    // recibe las dos descargas concretas a las que tiene que esperar
    public Instalador(Descarga meditacion, Descarga mantras) {
        this.meditacion = meditacion;
        this.mantras = mantras;
    }

    // espera (con join) a que esas dos descargas terminen, y luego "instala"
    public void run() {
        try {
            meditacion.join();
            mantras.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Instalador] Meditación y mantras listos: instalando...");

        try {
            Thread.sleep(1000); // simulamos que la instalacion tarda un poco
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[Instalador] Instalación terminada");
    }
}