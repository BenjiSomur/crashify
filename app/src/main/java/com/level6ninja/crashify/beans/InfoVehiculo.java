package com.level6ninja.crashify.beans;

public class InfoVehiculo {
    private String numPlacas;
    private String modelo;
    private String year;
    private String color;
    private String marca;
    private String aseguradora;

    public InfoVehiculo(){ }

    /**
     * @return the numPlacas
     */
    public String getNumPlacas() {
        return numPlacas;
    }

    /**
     * @param numPlacas the numPlacas to set
     */
    public void setNumPlacas(String numPlacas) {
        this.numPlacas = numPlacas;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the aseguradora
     */
    public String getAseguradora() {
        return aseguradora;
    }

    /**
     * @param aseguradora the aseguradora to set
     */
    public void setAseguradora(String aseguradora) {
        this.aseguradora = aseguradora;
    }
}
