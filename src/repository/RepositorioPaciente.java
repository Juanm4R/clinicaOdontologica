package repository;
import model.Paciente;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioPaciente implements IRepositorio<Paciente> {
    private HashMap<Long, Paciente> pacientes = new HashMap<>();
    private Long generadorId = 1L;
    private final String ARCHIVO = "pacientes.dat";

    public RepositorioPaciente() { cargarDesdeArchivo(); }

    @Override
    public void guardar(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(generadorId++);
        }
        pacientes.put(paciente.getId(), paciente);
        guardarEnArchivo();
    }

    @Override
    public Paciente buscar(Long id) {
        return pacientes.get(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return new ArrayList<>(pacientes.values());
    }

    @Override
    public void actualizar(Paciente paciente) {
        if (paciente.getId() == null || !pacientes.containsKey(paciente.getId())) {
            throw new IllegalArgumentException("No se puede actualizar un paciente que no existe en la base de datos.");
        }
        pacientes.put(paciente.getId(), paciente);
        guardarEnArchivo();
    }

    @Override
    public void eliminar(Long id) {
        pacientes.remove(id);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(pacientes);
        } catch (IOException e) { System.err.println("Error al serializar pacientes: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            pacientes = (HashMap<Long, Paciente>) ois.readObject();
            for (Long idGuardado : pacientes.keySet()) {
                if (idGuardado >= generadorId) {
                    generadorId = idGuardado + 1;
                }
            }
        } catch (Exception e) { System.err.println("Error al deserializar pacientes: " + e.getMessage()); }
    }
}