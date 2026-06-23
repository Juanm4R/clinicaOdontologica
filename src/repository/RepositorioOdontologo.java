package repository;
import model.Odontologo;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioOdontologo implements IRepositorio<Odontologo> {
    private HashMap<Long, Odontologo> odontologos = new HashMap<>();
    private Long generadorId = 1L;
    private final String ARCHIVO = "odontologos.dat";

    public RepositorioOdontologo() { cargarDesdeArchivo(); }

    @Override
    public void guardar(Odontologo odontologo) {
        if (odontologo.getId() == null) {
            odontologo.setId(generadorId++);
        }
        odontologos.put(odontologo.getId(), odontologo);
        guardarEnArchivo();
    }

    @Override
    public Odontologo buscar(Long id) {
        return odontologos.get(id);
    }

    @Override
    public List<Odontologo> buscarTodos() {
        return new ArrayList<>(odontologos.values());
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        odontologos.put(odontologo.getId(), odontologo);
        guardarEnArchivo();
    }

    @Override
    public void eliminar(Long id) {
        odontologos.remove(id);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(odontologos);
        } catch (IOException e) { System.err.println("Error al guardar odontólogos: " + e.getMessage()); }
    }

    @SuppressWarnings("unchecked")
    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            odontologos = (HashMap<Long, Odontologo>) ois.readObject();
            for (Long idGuardado : odontologos.keySet()) {
                if (idGuardado >= generadorId) {
                    generadorId = idGuardado + 1;
                }
            }
        } catch (Exception e) { System.err.println("Error al cargar odontólogos: " + e.getMessage()); }
    }
}