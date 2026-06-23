package repository;
import model.Turno;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioTurno implements IRepositorio<Turno> {
    private HashMap<Long, Turno> turnos = new HashMap<>();
    private Long generadorId = 1L;
    private final String ARCHIVO = "turnos.dat";

    public RepositorioTurno() { cargarDesdeArchivo(); }

    @Override
    public void guardar(Turno turno) {
        if (turno.getId() == null) {
            turno.setId(generadorId++);
        }
        turnos.put(turno.getId(), turno);
        guardarEnArchivo();
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
        guardarEnArchivo();
    }
    @Override
    public void eliminar(Long id) {
        turnos.remove(id);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(turnos);
        } catch (IOException e) { System.err.println("Error al guardar turnos: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            turnos = (HashMap<Long, Turno>) ois.readObject();
            for (Long idGuardado : turnos.keySet()) {
                if (idGuardado >= generadorId) {
                    generadorId = idGuardado + 1;
                }
            }
        } catch (Exception e) { System.err.println("Error al cargar turnos: " + e.getMessage()); }
    }
}