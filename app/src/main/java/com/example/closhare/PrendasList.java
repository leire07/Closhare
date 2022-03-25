package com.example.closhare;

public class PrendasList {

    String fotoPrenda;
    int nombreColor;


    public PrendasList(String fotoPrenda, int nombreColor) {
        this.fotoPrenda = fotoPrenda;
        this.nombreColor = nombreColor;
    }

    public String getFotoPrenda() {
        return fotoPrenda;
    }

    public void setFotoPrenda(String fotoPrenda) {
        this.fotoPrenda = fotoPrenda;
    }

    public int getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(int nombreColor) {
        this.nombreColor = nombreColor;
    }
}
