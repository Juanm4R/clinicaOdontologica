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
        // Regla de negocio: El paciente debe tener DNI sí o sí
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El DNI es obligatorio para registrar un paciente.");
        }
        repositorio.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id) {
        return repositorio.buscar(id);
    }

    public List<Paciente> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarPaciente(Paciente paciente) {
        if (paciente.getId() == null) {
            throw new IllegalArgumentException("Error: El paciente debe tener un ID para ser actualizado.");
        }
        repositorio.actualizar(paciente);
    }

    public void eliminarPaciente(Long id) {
        repositorio.eliminar(id);
    }
}