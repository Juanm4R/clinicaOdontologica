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

    public boolean registrarTurno(Turno turno) {
        if (turno.getPaciente() == null || turno.getOdontologo() == null || turno.getFechaHora() == null) {
            return false;
        }
        LocalDateTime inicioNuevo = turno.getFechaHora();
        LocalDateTime finNuevo = inicioNuevo.plusMinutes((long) turno.calcularDuracionMinutos());

        List<Turno> turnosExistentes = repositorio.buscarTodos();
        for (Turno turnoAgendado : turnosExistentes) {
            if (turnoAgendado.getOdontologo().getId().equals(turno.getOdontologo().getId())) {

                LocalDateTime inicioAgendado = turnoAgendado.getFechaHora();
                LocalDateTime finAgendado = inicioAgendado.plusMinutes((long) turnoAgendado.calcularDuracionMinutos());

                boolean sePisan = inicioNuevo.isBefore(finAgendado) && finNuevo.isAfter(inicioAgendado);
                if (sePisan) {
                    return true;
                }
            }
        }
        repositorio.guardar(turno);
        return false;
    }

    public Turno buscarTurno(Long id) {
        return repositorio.buscar(id);
    }

    public List<Turno> listarTodos() {
        return repositorio.buscarTodos();
    }

    public boolean actualizarTurno(Turno turno) {
        if (turno.getId() == null) {
            return false;
        }
        repositorio.actualizar(turno);
        return true;
    }

    public void eliminarTurno(Long id) {
        repositorio.eliminar(id);
    }
}