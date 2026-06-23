package ui;
import model.Odontologo;
import service.ServicioOdontologo;
import exception.OdontologoNoEncontradoException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelOdontologos extends JPanel {
    private ServicioOdontologo servicioOdontologo;
    private DefaultTableModel modeloTabla;
    private JTable tablaOdontologos;
    private JTextField txtId, txtNombre, txtApellido, txtMatricula;

    public PanelOdontologos(ServicioOdontologo servicioOdontologo) {
        this.servicioOdontologo = servicioOdontologo;
        setLayout(new BorderLayout(10, 10));

        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createTitledBorder("Datos del Profesional"));

        panelCampos.add(new JLabel("ID Odontólogo:")); txtId = new JTextField(); txtId.setEnabled(false); panelCampos.add(txtId);
        panelCampos.add(new JLabel("Nombre:")); txtNombre = new JTextField(); panelCampos.add(txtNombre);
        panelCampos.add(new JLabel("Apellido:")); txtApellido = new JTextField(); panelCampos.add(txtApellido);
        panelCampos.add(new JLabel("N° Matrícula:")); txtMatricula = new JTextField(); panelCampos.add(txtMatricula);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnGuardar = new JButton("Guardar / Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnGuardar); panelBotones.add(btnEliminar);

        panelIzquierdo.add(panelCampos, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotones, BorderLayout.SOUTH);
        add(panelIzquierdo, BorderLayout.WEST);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Matrícula"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaOdontologos = new JTable(modeloTabla);
        add(new JScrollPane(tablaOdontologos), BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> guardarOdontologo());
        btnEliminar.addActionListener(e -> eliminarOdontologo());

        tablaOdontologos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaOdontologos.getSelectedRow();
                if (fila >= 0) {
                    txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
                    txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                    txtApellido.setText(modeloTabla.getValueAt(fila, 2).toString());
                    txtMatricula.setText(modeloTabla.getValueAt(fila, 3).toString());
                }
            }
        });
        actualizarTabla();
    }

    private void guardarOdontologo() {
        if (txtMatricula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La matrícula es obligatoria.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Long id = null;
            if (!txtId.getText().trim().isEmpty()) {
                id = Long.parseLong(txtId.getText().trim());
            }

            Odontologo o = new Odontologo(id, txtNombre.getText(), txtApellido.getText(), txtMatricula.getText());
            if (id != null && servicioOdontologo.listarTodos().stream().anyMatch(od -> od.getId().equals(o.getId()))) {
                servicioOdontologo.actualizarOdontologo(o);
                JOptionPane.showMessageDialog(this, "Odontólogo actualizado correctamente.");
            } else {
                servicioOdontologo.registrarOdontologo(o);
                JOptionPane.showMessageDialog(this, "Odontólogo registrado. Matrícula asignada.");
            }
            limpiarFormulario(); actualizarTabla();
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage()); }
    }

    private void eliminarOdontologo() {
        int fila = tablaOdontologos.getSelectedRow();
        if (fila >= 0) {
            Long id = (Long) modeloTabla.getValueAt(fila, 0);
            try { servicioOdontologo.eliminarOdontologo(id); actualizarTabla(); limpiarFormulario(); }
            catch (OdontologoNoEncontradoException ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un odontólogo de la tabla para eliminar.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Odontologo o : servicioOdontologo.listarTodos()) {
            modeloTabla.addRow(new Object[]{o.getId(), o.getNombre(), o.getApellido(), o.getMatricula()});
        }
    }
    private void limpiarFormulario() { txtId.setText(""); txtNombre.setText(""); txtApellido.setText(""); txtMatricula.setText(""); }
}