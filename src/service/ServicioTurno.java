package service;

import model.Turno;
import repository.IRepositorio;
import java.util.List;

public class ServicioTurno {
    private IRepositorio<Turno> repositorio;

    public ServicioTurno(IRepositorio<Turno> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarTurno(Turno turno) {
        if (turno.getPaciente() == null) {
            throw new IllegalArgumentException("Error: El turno debe tener un paciente asignado.");
        }
        if (turno.getOdontologo() == null) {
            throw new IllegalArgumentException("Error: El turno debe tener un odontólogo asignado.");
        }
        if (turno.getFechaHora() == null) {
            throw new IllegalArgumentException("Error: El turno debe tener una fecha y hora asignadas.");
        }

        List<Turno> turnosExistentes = repositorio.buscarTodos();
        for (Turno turnoAgendado : turnosExistentes) {
            if (turnoAgendado.getOdontologo().getId().equals(turno.getOdontologo().getId())) {
                if (turnoAgendado.getFechaHora().equals(turno.getFechaHora())) {
                    throw new IllegalArgumentException("El odontólogo ya posee un turno reservado para esa fecha y hora.");
                }
            }
        }

        repositorio.guardar(turno);
    }

    public Turno buscarTurno(Long id) {
        return repositorio.buscar(id);
    }

    public List<Turno> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarTurno(Turno turno) {
        if (turno.getId() == null) {
            throw new IllegalArgumentException("Error: El turno debe tener un ID para ser actualizado.");
        }
        repositorio.actualizar(turno);
    }

    public void eliminarTurno(Long id) {
        repositorio.eliminar(id);
    }
}