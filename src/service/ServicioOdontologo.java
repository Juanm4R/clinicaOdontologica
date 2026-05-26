package service;

import model.Odontologo;
import repository.IRepositorio;
import java.util.List;

public class ServicioOdontologo {
    private IRepositorio<Odontologo> repositorio;

    public ServicioOdontologo(IRepositorio<Odontologo> repositorio) {
        this.repositorio = repositorio;
    }

    public void registrarOdontologo(Odontologo odontologo) throws DatoInvalidoException {
        if (odontologo.getMatricula() == null || odontologo.getMatricula().trim().isEmpty()) {
            throw new IllegalArgumentException("Error: La matrícula es obligatoria para registrar un odontólogo.");
        }
        repositorio.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Long id) {
        return repositorio.buscar(id);
    }

    public List<Odontologo> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        if (odontologo.getId() == null) {
            throw new IllegalArgumentException("Error: El odontólogo debe tener un ID para ser actualizado.");
        }
        repositorio.actualizar(odontologo);
    }

    public void eliminarOdontologo(Long id) {
        repositorio.eliminar(id);
    }
}