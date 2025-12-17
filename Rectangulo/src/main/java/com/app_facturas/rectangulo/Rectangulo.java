/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.app_facturas.rectangulo;

/**
 *
 * @author diego
 */
public class Rectangulo {

    /**
     * valor longitud del lado horizontal
     */
    private double ancho;
    /**
     * valor longitud del lado vertical
     */
    private double alto;

    /**
     * Constructor de la clase Rectangulo. Indica el objeto
     * con el ancho
     * @param ancho El ancho longitud horizontal
     * @param alto  El alto longitud vertical
     * @throws IllegalArgumentException si el ancho o el alto es negativo
     */
    public Rectangulo(double ancho, double alto) {
        if(ancho<0||alto<0)
            throw new IllegalArgumentException("El alto o el ancho no pueden ser negativos");
        this.ancho = ancho;
        this.alto = alto;
    }

    /**
     * Metodo que calcula el area
     * @return  double area
     * @see #calcularArea() 
     */
    public double calcularArea() {
        return ancho * alto;
    }

    /**
     *  Método para calcular el perímetro
     * @return devuelve el périmetro
     * @see #calcularPerimetro() 
     */
    public double calcularPerimetro() {
        return 2 * (ancho + alto);
    }

    public static void main(String[] args) {
        Rectangulo rectangulo = new Rectangulo(5, 3);

        System.out.println("Área: " + rectangulo.calcularArea());
        System.out.println("Perímetro: " + rectangulo.calcularPerimetro());
    }
}
