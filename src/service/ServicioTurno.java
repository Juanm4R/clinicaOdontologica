package service;

import exception.DatoInvalidoException;
import exception.TurnoYaReservadoException;
import model.Turno;
import repository.IRepositorio;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Comparator;

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

        LocalDateTime inicioNuevo = turno.getFechaHora();
        LocalDateTime finNuevo = inicioNuevo.plusMinutes((long) turno.calcularDuracionMinutos());

        List<Turno> turnosExistentes = repositorio.buscarTodos();

        boolean superposicion = turnosExistentes.stream()
                .filter(t -> t.getOdontologo().getId().equals(turno.getOdontologo().getId()))
                .anyMatch(t -> {
                    LocalDateTime inicioAgendado = t.getFechaHora();
                    LocalDateTime finAgendado = inicioAgendado.plusMinutes((long) t.calcularDuracionMinutos());
                    return inicioNuevo.isBefore(finAgendado) && finNuevo.isAfter(inicioAgendado);
                });

        if (superposicion) {
            throw new TurnoYaReservadoException("Error: El odontólogo ya tiene un turno ocupado en ese horario.");
        }

        repositorio.guardar(turno);
    }

    public List<Turno> buscarTurnosPorOdontologo(Long idOdontologo) {
        return repositorio.buscarTodos().stream()
                .filter(turno -> turno.getOdontologo().getId().equals(idOdontologo))
                .collect(Collectors.toList());
    }

    public List<Turno> buscarTurnosPorPaciente(Long idPaciente) {
        return repositorio.buscarTodos().stream()
                .filter(turno -> turno.getPaciente().getId().equals(idPaciente))
                .collect(Collectors.toList());
    }

    public Turno buscarTurno(Long id) {
        return repositorio.buscar(id);
    }

    public List<Turno> buscarTurnosPorRangoDeFechas(LocalDateTime inicio, LocalDateTime fin) {
        return repositorio.buscarTodos().stream()
                .filter(turno -> !turno.getFechaHora().isBefore(inicio) && !turno.getFechaHora().isAfter(fin))
                .sorted(Comparator.comparing(Turno::getFechaHora))
                .collect(Collectors.toList());
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