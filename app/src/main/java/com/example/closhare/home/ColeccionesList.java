package com.example.closhare.home;

public class ColeccionesList {

    String nombreColeccion;
    String[] fotosColeccion;

    public ColeccionesList(String nombreColeccion, String[] fotosColeccion) {
        this.nombreColeccion = nombreColeccion;
        this.fotosColeccion = fotosColeccion;
    }

    public String getNombreColeccion() {
        return nombreColeccion;
    }

    public void setNombreColeccion(String nombreColeccion) {
        this.nombreColeccion = nombreColeccion;
    }

    public String[] getFotosColeccion() {
        return fotosColeccion;
    }

    public void setFotosColeccion(String[] fotosColeccion) {
        this.fotosColeccion = fotosColeccion;
    }
}
