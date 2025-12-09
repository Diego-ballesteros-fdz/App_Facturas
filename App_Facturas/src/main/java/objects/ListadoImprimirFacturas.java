package objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mbuen
 */
public class ListadoImprimirFacturas {
    private int id;
    private String descripcion;

    public ListadoImprimirFacturas(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
