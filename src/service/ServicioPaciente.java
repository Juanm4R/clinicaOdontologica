package service;

import model.Paciente;
import repository.IRepositorio;
import java.util.List;

public class ServicioPaciente {
    private IRepositorio<Paciente> repositorio;

    public ServicioPaciente(IRepositorio<Paciente> repositorio) {
        this.repositorio = repositorio;
    }

    // 🚨 Principio de Responsabilidad Única y Patrón Experto
    public boolean registrarPaciente(Paciente paciente) {
        if (paciente.getDni() == null || paciente.getDni().trim().isEmpty()) {
            return false;
        }
        for (Paciente p : repositorio.buscarTodos()) {
            if (p.getDni().equals(paciente.getDni())) {
                return false;
            }
        }

        repositorio.guardar(paciente);
        return true;
    }

    public Paciente buscarPaciente(Long id) {
        return repositorio.buscar(id);
    }

    public List<Paciente> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarPaciente(Paciente paciente) {
        if (paciente.getId() != null) {
            repositorio.actualizar(paciente);
        }
    }

    public void eliminarPaciente(Long id) {
        repositorio.eliminar(id);
    }
}