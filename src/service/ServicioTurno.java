package service;

import model.Turno;
import repository.IRepositorio;
import java.util.List;
import java.time.LocalDateTime;

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

        LocalDateTime inicioNuevo = turno.getFechaHora();
        LocalDateTime finNuevo = inicioNuevo.plusMinutes((long) turno.calcularDuracionMinutos());

        List<Turno> turnosExistentes = repositorio.buscarTodos();

        for (Turno turnoAgendado : turnosExistentes) {
            if (turnoAgendado.getOdontologo().getId().equals(turno.getOdontologo().getId())) {

                // turno YA AGENDADO
                LocalDateTime inicioAgendado = turnoAgendado.getFechaHora();
                LocalDateTime finAgendado = inicioAgendado.plusMinutes((long) turnoAgendado.calcularDuracionMinutos());
                
                boolean sePisan = inicioNuevo.isBefore(finAgendado) && finNuevo.isAfter(inicioAgendado);

                if (sePisan) {
                    throw new IllegalArgumentException("Error: El odontólogo ya tiene un turno ocupado entre "
                            + inicioAgendado.toLocalTime() + " y " + finAgendado.toLocalTime() + ".");
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