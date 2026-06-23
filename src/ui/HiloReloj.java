package ui;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HiloReloj implements Runnable {
    private JLabel labelDestino;

    public HiloReloj(JLabel labelDestino) {
        this.labelDestino = labelDestino;
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        while (true) {
            try {
                String hora = LocalDateTime.now().format(formatter);

                SwingUtilities.invokeLater(() -> {
                    labelDestino.setText("Sistema Activo | Hora actual: " + hora);
                });

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Hilo de reloj interrumpido.");
                break;
            }
        }
    }
}