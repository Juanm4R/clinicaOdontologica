package service;

import exception.DatoInvalidoException;
import exception.OdontologoNoEncontradoException;
import exception.PacienteNoEncontradoException;
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
            throw new DatoInvalidoException("Error: La matrícula es obligatoria para registrar un odontólogo.");
        }
        repositorio.guardar(odontologo);
    }

    public Odontologo buscarOdontologo(Long id) throws OdontologoNoEncontradoException {
        Odontologo encontrado = repositorio.buscar(id);
        if (encontrado == null) {
            throw new OdontologoNoEncontradoException("No se encontró ningún odontólogo con el ID: " + id);
        }
        return encontrado;
    }

    public List<Odontologo> listarTodos() {
        return repositorio.buscarTodos();
    }

    public void actualizarOdontologo(Odontologo odontologo) throws DatoInvalidoException, OdontologoNoEncontradoException {
        if (odontologo.getId() == null) {
            throw new DatoInvalidoException("Error: El odontólogo debe tener un ID para ser actualizado.");
        }
        if (repositorio.buscar(odontologo.getId()) == null) {
            throw new OdontologoNoEncontradoException("No se puede actualizar: el odontologo con ID " + odontologo.getId() + " fue eliminado o no existe en la base de datos.");
        }
        repositorio.actualizar(odontologo);
    }

    public void eliminarOdontologo(Long id) throws OdontologoNoEncontradoException {
        if (repositorio.buscar(id) == null) {
            throw new OdontologoNoEncontradoException("No se puede eliminar: el odontólogo con ID " + id + " no existe.");
        }
        repositorio.eliminar(id);
    }
}