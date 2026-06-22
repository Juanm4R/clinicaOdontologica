package ui;
import model.*;
import service.*;
import exception.ClinicaException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelTurnos extends JPanel {
    private ServicioTurno servicioTurno;
    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;
    private DefaultTableModel modeloTabla;
    private JTable tablaTurnos;
    private JTextField txtIdTurno, txtFechaHora, txtMotivoUrgencia;
    private JComboBox<Paciente> comboPacientes;
    private JComboBox<Odontologo> comboOdontologos;
    private JComboBox<String> comboTipoTurno;

    public PanelTurnos(ServicioTurno sT, ServicioPaciente sP, ServicioOdontologo sO) {
        this.servicioTurno = sT; this.servicioPaciente = sP; this.servicioOdontologo = sO;
        setLayout(new BorderLayout(10, 10));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        JPanel panelCampos = new JPanel(new GridLayout(7, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createTitledBorder("Agendado de Turno Médico"));

        panelCampos.add(new JLabel("ID Turno:")); txtIdTurno = new JTextField(); panelCampos.add(txtIdTurno);
        panelCampos.add(new JLabel("Seleccionar Paciente:")); comboPacientes = new JComboBox<>(); panelCampos.add(comboPacientes);
        panelCampos.add(new JLabel("Seleccionar Odontólogo:")); comboOdontologos = new JComboBox<>(); panelCampos.add(comboOdontologos);
        panelCampos.add(new JLabel("Fecha (aaaa-MM-dd HH:mm):")); txtFechaHora = new JTextField(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))); panelCampos.add(txtFechaHora);
        panelCampos.add(new JLabel("Tipo de Turno:")); comboTipoTurno = new JComboBox<>(new String[]{"Regular", "Urgente"}); panelCampos.add(comboTipoTurno);
        panelCampos.add(new JLabel("Motivo de Urgencia:")); txtMotivoUrgencia = new JTextField(); txtMotivoUrgencia.setEnabled(false); panelCampos.add(txtMotivoUrgencia);

        JButton btnAsignar = new JButton("Agendar Turno");
        panelIzquierdo.add(panelCampos, BorderLayout.CENTER); panelIzquierdo.add(btnAsignar, BorderLayout.SOUTH);
        add(panelIzquierdo, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new String[]{"ID Turno", "Paciente", "Odontólogo", "Fecha/Hora", "Detalles"}, 0);
        tablaTurnos = new JTable(modeloTabla);
        add(new JScrollPane(tablaTurnos), BorderLayout.CENTER);

        comboTipoTurno.addActionListener(e -> {
            boolean esUrgente = comboTipoTurno.getSelectedItem().equals("Urgente");
            txtMotivoUrgencia.setEnabled(esUrgente);
            if (!esUrgente) txtMotivoUrgencia.setText("");
        });

        btnAsignar.addActionListener(e -> agendarTurno());

        this.addHierarchyListener(e -> {
            if ((e.getChangeFlags() & java.awt.event.HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                recargarCombos(); actualizarTabla();
            }
        });
    }

    private void recargarCombos() {
        comboPacientes.removeAllItems(); for (Paciente p : servicioPaciente.listarTodos()) comboPacientes.addItem(p);
        comboOdontologos.removeAllItems(); for (Odontologo o : servicioOdontologo.listarTodos()) comboOdontologos.addItem(o);
    }

    private void agendarTurno() {
        try {
            Long id = Long.parseLong(txtIdTurno.getText().trim());
            Paciente p = (Paciente) comboPacientes.getSelectedItem();
            Odontologo o = (Odontologo) comboOdontologos.getSelectedItem();
            LocalDateTime fecha = LocalDateTime.parse(txtFechaHora.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            Turno nuevoTurno = comboTipoTurno.getSelectedItem().equals("Urgente")
                    ? new TurnoUrgente(id, p, o, fecha, txtMotivoUrgencia.getText())
                    : new TurnoRegular(id, p, o, fecha);

            servicioTurno.registrarTurno(nuevoTurno);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Turno agendado exitosamente.");

        } catch (ClinicaException ex) {
            JOptionPane.showMessageDialog(this, "Error de registro: " + ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados: Revise el formato de fecha e ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Turno t : servicioTurno.listarTodos()) {
            modeloTabla.addRow(new Object[]{t.getId(), t.getPaciente().getNombreCompleto(), t.getOdontologo().getApellido(), t.getFechaHora(), t.toString()});
        }
    }
}