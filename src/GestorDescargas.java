// esta es la clase principal, la que tiene el main
// crea las descargas, las lanza en hilos, espera a que terminen y da el informe

public class GestorDescargas {

    public static void main(String[] args) {

        // n 2: los nombres pueden llegar por linea de comandos,
        // si no se pasa ninguno usamos los 4 de siempre
        String[] archivos;
        if (args.length > 0) {
            archivos = args;
        } else {
            archivos = new String[] { "cuarzos.png", "meditacion.mp4", "mantras.mp3", "horoscopo.pdf" };
        }

        // creamos una Descarga por cada archivo
        Descarga[] descargas = new Descarga[archivos.length];
        for (int i = 0; i < archivos.length; i++) {
            descargas[i] = new Descarga(archivos[i]);
        }

        long inicio = System.currentTimeMillis();

        // primero arrancamos TODAS las descargas, y despues ya esperamos
        for (Descarga d : descargas) {
            d.start();
        }

        // n 2: arrancamos el monitor, que vigila mientras las descargas avanzan
        Thread hiloMonitor = new Thread(new Monitor(descargas), "Monitor");
        hiloMonitor.start();

        // n 3: buscamos meditacion.mp4 y mantras.mp3 para el instalador
        Descarga meditacion = buscarDescarga(descargas, "meditacion.mp4");
        Descarga mantras = buscarDescarga(descargas, "mantras.mp3");

        Thread hiloInstalador = null;
        if (meditacion != null && mantras != null) {
            Instalador instalador = new Instalador(meditacion, mantras);
            hiloInstalador = new Thread(instalador, "Instalador");
            hiloInstalador.start();
        }

        // n 3: el main espera a meditacion.mp4 como mucho 3 segundos
        if (meditacion != null) {
            try {
                meditacion.join(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (meditacion.isAlive()) {
                System.out.println("[Main] meditacion.mp4 sigue en segundo plano");
            }
        }

        for (Descarga d : descargas) {
            try {
                d.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            hiloMonitor.join();
            if (hiloInstalador != null) {
                hiloInstalador.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long fin = System.currentTimeMillis();
        long tiempoReal = fin - inicio;

        long acumuladoSerie = 0;
        for (Descarga d : descargas) {
            acumuladoSerie += d.getTiempoTotal();
        }

        System.out.println("Todas las descargas han terminado Franco N.");
        System.out.println("Tiempo real: " + tiempoReal + " ms");
        System.out.println("Si se hubieran descargado una detrás de otra: " + acumuladoSerie + " ms");
    }

    // busca en el array la descarga que tenga ese nombre de archivo, o null si no esta
    private static Descarga buscarDescarga(Descarga[] descargas, String nombreArchivo) {
        for (Descarga d : descargas) {
            if (d.getNombreArchivo().equals(nombreArchivo)) {
                return d;
            }
        }
        return null;
    }
}