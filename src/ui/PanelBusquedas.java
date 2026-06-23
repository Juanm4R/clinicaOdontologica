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

        modeloTabla = new DefaultTableModel(new String[]{"ID Turno", "Paciente", "Odontólogo", "Detalle Completo"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tablaResultados = new JTable(modeloTabla);
        add(new JScrollPane(tablaResultados), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            String input = txtIdOdontologo.getText().trim();
            if(input.isEmpty()){
                JOptionPane.showMessageDialog(this, "Debe ingresar un ID para buscar.", "Atención", JOptionPane.WARNING_MESSAGE);
                return; // Cortamos la ejecución antes de que llegue al parseLong
            }
            try {
                Long idOd = Long.parseLong(input);
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