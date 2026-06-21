package ui;
import model.Paciente;
import model.Domicilio;
import service.ServicioPaciente;
import exception.DatoInvalidoException;
import exception.PacienteNoEncontradoException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class PanelPacientes extends JPanel {
    private ServicioPaciente servicioPaciente;
    private DefaultTableModel modeloTabla;
    private JTable tablaPacientes;
    private JTextField txtId, txtNombre, txtApellido, txtDni;
    private JTextField txtDomId, txtCalle, txtNumero, txtLocalidad, txtProvincia;

    public PanelPacientes(ServicioPaciente servicioPaciente) {
        this.servicioPaciente = servicioPaciente;
        setLayout(new BorderLayout(10, 10));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        JPanel panelCampos = new JPanel(new GridLayout(9, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createTitledBorder("Información Completa del Paciente"));

        panelCampos.add(new JLabel("ID Paciente:")); txtId = new JTextField(); panelCampos.add(txtId);
        panelCampos.add(new JLabel("Nombre:")); txtNombre = new JTextField(); panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Apellido:")); txtApellido = new JTextField(); panelCampos.add(txtApellido);
        panelCampos.add(new JLabel("DNI:")); txtDni = new JTextField(); panelCampos.add(txtDni);

        panelCampos.add(new JLabel("ID Domicilio:")); txtDomId = new JTextField(); panelCampos.add(txtDomId);
        panelCampos.add(new JLabel("Calle:")); txtCalle = new JTextField(); panelCampos.add(txtCalle);
        panelCampos.add(new JLabel("Número:")); txtNumero = new JTextField(); panelCampos.add(txtNumero);
        panelCampos.add(new JLabel("Localidad:")); txtLocalidad = new JTextField(); panelCampos.add(txtLocalidad);
        panelCampos.add(new JLabel("Provincia:")); txtProvincia = new JTextField(); panelCampos.add(txtProvincia);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnGuardar = new JButton("Guardar / Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        panelIzquierdo.add(panelCampos, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);
        add(panelIzquierdo, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre Completo", "DNI", "Domicilio"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPacientes = new JTable(modeloTabla);
        add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());

        tablaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaPacientes.getSelectedRow();
                if (fila >= 0) {
                    Long id = (Long) modeloTabla.getValueAt(fila, 0);
                    try {
                        Paciente p = servicioPaciente.buscarPaciente(id);
                        txtId.setText(p.getId().toString()); txtNombre.setText(p.getNombre());
                        txtApellido.setText(p.getApellido()); txtDni.setText(p.getDni());
                        txtDomId.setText(p.getDomicilio().getId().toString()); txtCalle.setText(p.getDomicilio().getCalle());
                        txtNumero.setText(p.getDomicilio().getNumero()); txtLocalidad.setText(p.getDomicilio().getLocalidad());
                        txtProvincia.setText(p.getDomicilio().getProvincia());
                    } catch (Exception ex) {}
                }
            }
        });
        actualizarTabla();
    }

    private void guardarPaciente() {
        if (txtId.getText().trim().isEmpty() || txtDni.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos ID y DNI.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Long id = Long.parseLong(txtId.getText().trim());
            Long domId = txtDomId.getText().trim().isEmpty() ? id : Long.parseLong(txtDomId.getText().trim());
            Domicilio dom = new Domicilio(domId, txtCalle.getText(), txtNumero.getText(), txtLocalidad.getText(), txtProvincia.getText());
            Paciente p = new Paciente(id, txtNombre.getText(), txtApellido.getText(), txtDni.getText(), LocalDate.now(), dom);

            if (servicioPaciente.listarTodos().stream().anyMatch(pac -> pac.getId().equals(id))) {
                servicioPaciente.actualizarPaciente(p);
            } else {
                servicioPaciente.registrarPaciente(p);
            }
            limpiarFormulario();
            actualizarTabla();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    private void eliminarPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila >= 0) {
            Long id = (Long) modeloTabla.getValueAt(fila, 0);
            try { servicioPaciente.eliminarPaciente(id); actualizarTabla(); limpiarFormulario(); }
            catch (PacienteNoEncontradoException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Paciente p : servicioPaciente.listarTodos()) {
            modeloTabla.addRow(new Object[]{p.getId(), p.getNombreCompleto(), p.getDni(), p.getDomicilio().toString()});
        }
    }

    private void limpiarFormulario() {
        txtId.setText(""); txtNombre.setText(""); txtApellido.setText(""); txtDni.setText("");
        txtDomId.setText(""); txtCalle.setText(""); txtNumero.setText(""); txtLocalidad.setText(""); txtProvincia.setText("");
    }
}