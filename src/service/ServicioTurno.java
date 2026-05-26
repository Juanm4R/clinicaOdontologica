package service;

import exception.DatoInvalidoException;
import exception.TurnoYaReservadoException;
import model.Turno;
import repository.IRepositorio;
import java.util.List;

public class ServicioTurno {
    private IRepositorio<Turno> repositorio;

    public ServicioTurno(IRepositorio<Turno> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarTurno(Turno turno) throws DatoInvalidoException, TurnoYaReservadoException {
        if (turno.getPaciente() == null) {
            throw new DatoInvalidoException("Error: El turno debe tener un paciente asignado.");
        }
        if (turno.getOdontologo() == null) {
            throw new DatoInvalidoException("Error: El turno debe tener un odontólogo asignado.");
        }
        if (turno.getFechaHora() == null) {
            throw new DatoInvalidoException("Error: El turno debe tener una fecha y hora asignadas.");
        }

        repositorio.guardar(turno);
    }

    public Turno buscarTurno(Long id) {
        return repositorio.buscar(id);
    }

    public List<Turno> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarTurno(Turno turno) throws DatoInvalidoException {
        if (turno.getId() == null) {
            throw new DatoInvalidoException("Error: El turno debe tener un ID para ser actualizado.");
        }
        repositorio.actualizar(turno);
    }

    public void eliminarTurno(Long id) {
        repositorio.eliminar(id);
    }
}