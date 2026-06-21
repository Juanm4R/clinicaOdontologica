package repository;
import model.Paciente;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioPaciente implements IRepositorio<Paciente> {
    private HashMap<Long, Paciente> pacientes = new HashMap<>();
    private Long generadorId = 1L;

    @Override
    public void guardar(Paciente paciente) {
        if (paciente.getId() == null) {
            paciente.setId(generadorId++);
        }
        pacientes.put(paciente.getId(), paciente);
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
    }

    @Override
    public void eliminar(Long id) {
        pacientes.remove(id);
    }
}