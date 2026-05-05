package repository;
import java.util.List;

public interface IRepositorio<T> {
    void guardar(T entidad);
    T buscar(Long id);
    List<T> buscarTodos();
    void actualizar(T entidad);
    void eliminar(Long id);
}
