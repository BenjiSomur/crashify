package com.level6ninja.crashify.beans;

public class Evidencia {
    private Integer idEvidencia;
    private Integer idReporte;
    private byte [] bytes;

    public Evidencia(Integer idEvidencia, Integer idReporte, byte[] bytes) {
        this.idEvidencia = idEvidencia;
        this.idReporte = idReporte;
        this.bytes = bytes;
    }

    public Evidencia() {
    }


    /**
     * @return the idEvidencia
     */
    public Integer getIdEvidencia() {
        return idEvidencia;
    }

    /**
     * @param idEvidencia the idEvidencia to set
     */
    public void setIdEvidencia(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    /**
     * @return the idReporte
     */
    public Integer getIdReporte() {
        return idReporte;
    }

    /**
     * @param idReporte the idReporte to set
     */
    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    /**
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
