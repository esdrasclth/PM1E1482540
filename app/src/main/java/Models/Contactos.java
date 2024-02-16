package Models;

import java.sql.Blob;

public class Contactos {
    private Integer id;
    private String pais;
    private String nombres;
    private String telefonos;
    private String notas;
    private Blob imagen;

    public Contactos(Integer id, String pais, String nombres, String telefonos, String notas, Blob imagen){
        this.id = id;
        this.pais = pais;
        this.nombres = nombres;
        this.telefonos = telefonos;
        this.notas = notas;
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }
}
