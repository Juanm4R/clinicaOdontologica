package repository;
import model.Paciente;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioPaciente implements IRepositorio<Paciente> {
    private HashMap<Long, Paciente> pacientes = new HashMap<>();

    @Override
    public void guardar(Paciente paciente) {
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
        pacientes.put(paciente.getId(), paciente);
    }

    @Override
    public void eliminar(Long id) {
        pacientes.remove(id);
    }
}