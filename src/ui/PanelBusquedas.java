package ui;
import model.Turno;
import service.ServicioTurno;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelBusquedas extends JPanel {
    private ServicioTurno servicioTurno;
    private DefaultTableModel modeloTabla;
    private JTextField txtIdOdontologo;

    public PanelBusquedas(ServicioTurno servicioTurno) {
        this.servicioTurno = servicioTurno;
        setLayout(new BorderLayout(10, 10));

        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.setBorder(BorderFactory.createTitledBorder("Criterios de Búsqueda"));
        panelNorte.add(new JLabel("ID Odontólogo:"));
        txtIdOdontologo = new JTextField(10);
        panelNorte.add(txtIdOdontologo);

        JButton btnBuscar = new JButton("Buscar Turnos");
        JButton btnLimpiar = new JButton("Mostrar Todos");
        panelNorte.add(btnBuscar); panelNorte.add(btnLimpiar);

        add(panelNorte, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID Turno", "Paciente", "Odontólogo", "Detalle Completo"}, 0);
        JTable tablaResultados = new JTable(modeloTabla);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            try {
                Long idOd = Long.parseLong(txtIdOdontologo.getText().trim());
                List<Turno> resultado = servicioTurno.buscarTurnosPorOdontologo(idOd);
                modeloTabla.setRowCount(0);
                for (Turno t : resultado) modeloTabla.addRow(new Object[]{t.getId(), t.getPaciente().getNombreCompleto(), t.getOdontologo().getApellido(), t.toString()});
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID Inválido"); }
        });

        btnLimpiar.addActionListener(e -> {
            txtIdOdontologo.setText(""); modeloTabla.setRowCount(0);
            for (Turno t : servicioTurno.listarTodos()) modeloTabla.addRow(new Object[]{t.getId(), t.getPaciente().getNombreCompleto(), t.getOdontologo().getApellido(), t.toString()});
        });

        this.addHierarchyListener(e -> { if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) btnLimpiar.doClick(); });
    }
}