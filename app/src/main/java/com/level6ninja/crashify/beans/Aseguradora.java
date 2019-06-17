package com.level6ninja.crashify.beans;

/**
 *
 * @author Juan Carlos
 */
public class Aseguradora {

    private Integer idAseguradora;
    private String nombre;

    public Aseguradora(){

    }

    public Aseguradora(int idAseguradora, String nombre){
        this.idAseguradora = idAseguradora;
        this.nombre = nombre;
    }

    /**
     * @return the idAseguradora
     */
    public Integer getIdAseguradora() {
        return idAseguradora;
    }

    /**
     * @param idAseguradora the idAseguradora to set
     */
    public void setIdAseguradora(Integer idAseguradora) {
        this.idAseguradora = idAseguradora;
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