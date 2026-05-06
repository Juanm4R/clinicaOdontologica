package service;

import model.Odontologo;
import repository.IRepositorio;
import java.util.List;

public class ServicioOdontologo {
    private IRepositorio<Odontologo> repositorio;

    public ServicioOdontologo(IRepositorio<Odontologo> repositorio) {
        this.repositorio = repositorio;
    }

    public boolean registrarOdontologo(Odontologo odontologo) {
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty()) {
            return false;
        }

        for (Odontologo o : repositorio.buscarTodos()) {
            if (o.getMatricula().equals(odontologo.getMatricula())) {
                return false;
            }
        }

        repositorio.guardar(odontologo);
        return true;
    }

    public Odontologo buscarOdontologo(Long id) {
        return repositorio.buscar(id);
    }

    public List<Odontologo> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        if (odontologo.getId() != null) {
            repositorio.actualizar(odontologo);
        }
    }

    public void eliminarOdontologo(Long id) {
        repositorio.eliminar(id);
    }
}