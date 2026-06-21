package repository;
import model.Odontologo;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class RepositorioOdontologo implements IRepositorio<Odontologo> {
    private HashMap<Long, Odontologo> odontologos = new HashMap<>();
    private Long generadorId = 1L;

    @Override
    public void guardar(Odontologo odontologo) {
        if (odontologo.getId() == null) {
            odontologo.setId(generadorId++);
        }
        odontologos.put(odontologo.getId(), odontologo);
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
        if (odontologo.getId() == null || !odontologos.containsKey(odontologo.getId())) {
            throw new IllegalArgumentException("El odontólogo no existe en el registro.");
        }
        odontologos.put(odontologo.getId(), odontologo);
    }

    @Override
    public void eliminar(Long id) {
        odontologos.remove(id);
    }
}