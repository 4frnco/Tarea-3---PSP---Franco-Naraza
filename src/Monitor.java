// esta clase vigila las descargas mientras van avanzando (nivel 2)
public class Monitor implements Runnable {
    // implementa Runnable, al reves que Descarga, que hereda de Thread
    private Descarga[] descargas;

    // recibe el array de descargas que tiene que vigilar
    public Monitor(Descarga[] descargas) {
        this.descargas = descargas;
    }

    // cada 500 ms mira cuantas descargas siguen vivas y lo imprime
    // termina solo cuando ya no queda ninguna viva
    public void run() {
        while (true) {
            int vivas = contarVivas();

            if (vivas == 0) {
                System.out.println("[Monitor] No queda ninguna descarga en curso");
                break;
            }

            System.out.println("[Monitor] Descargas en curso: " + vivas);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // cuenta cuantas descargas del array siguen vivas (isAlive)
    private int contarVivas() {
        int contador = 0;
        for (Descarga d : descargas) {
            if (d.isAlive()) {
                contador++;
            }
        }
        return contador;
    }
}