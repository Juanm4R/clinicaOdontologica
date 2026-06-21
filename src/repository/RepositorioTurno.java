package repository;
import model.Turno;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioTurno implements IRepositorio<Turno> {
    private HashMap<Long, Turno> turnos = new HashMap<>();
    private Long generadorId = 1L;

    @Override
    public void guardar(Turno turno) {
        if (turno.getId() == null) {
            turno.setId(generadorId++);
        }
        turnos.put(turno.getId(), turno);
    }

    @Override
    public Turno buscar(Long id) {
        return turnos.get(id);
    }

    @Override
    public List<Turno> buscarTodos() {
        return new ArrayList<>(turnos.values());
    }

    @Override
    public void actualizar(Turno turno) {
        turnos.put(turno.getId(), turno);
    }

    @Override
    public void eliminar(Long id) {
        turnos.remove(id);
    }
}