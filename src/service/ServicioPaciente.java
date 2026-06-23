package service;

import exception.DatoInvalidoException;
import exception.PacienteNoEncontradoException;
import model.Paciente;
import repository.IRepositorio;
import java.util.stream.Collectors;
import java.util.List;

public class ServicioPaciente {
    private IRepositorio<Paciente> repositorio;

    public ServicioPaciente(IRepositorio<Paciente> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarPaciente(Paciente paciente) throws DatoInvalidoException {
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            throw new DatoInvalidoException("Error: El DNI es obligatorio para registrar un paciente.");
        }
        repositorio.guardar(paciente);
    }

    public Paciente buscarPaciente(Long id) throws PacienteNoEncontradoException {
        Paciente encontrado = repositorio.buscar(id);
        if (encontrado == null) {
            throw new PacienteNoEncontradoException("No se encontró ningún paciente con el ID: " + id);
        }
        return encontrado;
    }

    public List<Paciente> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarPaciente(Paciente paciente) throws DatoInvalidoException, PacienteNoEncontradoException {
        if (paciente.getId() == null) {
            throw new DatoInvalidoException("Error: El paciente debe tener un ID para ser actualizado.");
        }
        if (repositorio.buscar(paciente.getId()) == null) {
            throw new PacienteNoEncontradoException("No se puede actualizar: el paciente con ID " + paciente.getId() + " fue eliminado o no existe en la base de datos.");
        }
        repositorio.actualizar(paciente);
    }

    public List<Paciente> listarPacientesOrdenados() {
        return repositorio.buscarTodos().stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public void eliminarPaciente(Long id) throws PacienteNoEncontradoException {
        if (repositorio.buscar(id) == null) {
            throw new PacienteNoEncontradoException("No se puede eliminar: el paciente con ID " + id + " no existe.");
        }
        repositorio.eliminar(id);
    }
}