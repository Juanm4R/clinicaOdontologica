package service;
import model.Paciente;
import repository.IRepositorio;
import java.util.List;

public class ServicioPaciente {
    private IRepositorio<Paciente> repositorio;

    public ServicioPaciente(IRepositorio<Paciente> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarPaciente(Paciente paciente) {
        // Lógica de validación
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El paciente debe tener un DNI válido.");
        }
        repositorio.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id) {
        return repositorio.buscar(id);
    }

    public List<Paciente> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void eliminarPaciente(Long id) {
        repositorio.eliminar(id);
    }
}
