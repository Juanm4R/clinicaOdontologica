package ui;
import service.*;
import repository.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {
    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;
    private ServicioTurno servicioTurno;

    public VentanaPrincipal() {
        servicioPaciente = new ServicioPaciente(new RepositorioPaciente());
        servicioOdontologo = new ServicioOdontologo(new RepositorioOdontologo());
        servicioTurno = new ServicioTurno(new RepositorioTurno());

        setTitle("Sistema de Gestión Integrado - Clínica Odontológica");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Pacientes y Domicilios", new PanelPacientes(servicioPaciente));
        pestañas.addTab("Odontólogos", new PanelOdontologos(servicioOdontologo));
        pestañas.addTab("Asignación de Turnos", new PanelTurnos(servicioTurno, servicioPaciente, servicioOdontologo));
        pestañas.addTab("Búsquedas Avanzadas", new PanelBusquedas(servicioTurno));

        add(pestañas, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cierre seguro. Datos persistidos en archivos .dat");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
            new VentanaPrincipal().setVisible(true);
        });
    }
}