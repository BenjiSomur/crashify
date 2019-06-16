package com.level6ninja.crashify.beans;

public class Marca {
    private Integer idMarca;
    private String nombre;

    public Marca(){

    }

    public Marca(int idMarca, String nombre){
        this.idMarca = idMarca;
        this.nombre = nombre;
    }

    /**
     * @return the idMarca
     */
    public Integer getIdMarca() {
        return idMarca;
    }

    /**
     * @param idMarca the idMarca to set
     */
    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
