package Clinica;

public class Domicilio {
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;

    public Domicilio(String calle, Integer numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        // Retornamos un texto formateado con los datos
        return calle + " " + numero + ", " + localidad + " - " + provincia;
    }
}
