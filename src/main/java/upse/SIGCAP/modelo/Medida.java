/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Medida {

    private int med_id;
    private String med_nombre;
    private double med_ancho;
    private double med_alto;
    private String med_unidad;
    private String med_tipo;
    private String med_estado;

    public Medida() {
    }

    public Medida(int med_id, String med_nombre, double med_ancho,
                  double med_alto, String med_unidad,
                  String med_tipo, String med_estado) {
        this.med_id = med_id;
        this.med_nombre = med_nombre;
        this.med_ancho = med_ancho;
        this.med_alto = med_alto;
        this.med_unidad = med_unidad;
        this.med_tipo = med_tipo;
        this.med_estado = med_estado;
    }

    public int getMed_id() { return med_id; }
    public void setMed_id(int med_id) { this.med_id = med_id; }

    public String getMed_nombre() { return med_nombre; }
    public void setMed_nombre(String med_nombre) { this.med_nombre = med_nombre; }

    public double getMed_ancho() { return med_ancho; }
    public void setMed_ancho(double med_ancho) { this.med_ancho = med_ancho; }

    public double getMed_alto() { return med_alto; }
    public void setMed_alto(double med_alto) { this.med_alto = med_alto; }

    public String getMed_unidad() { return med_unidad; }
    public void setMed_unidad(String med_unidad) { this.med_unidad = med_unidad; }

    public String getMed_tipo() { return med_tipo; }
    public void setMed_tipo(String med_tipo) { this.med_tipo = med_tipo; }

    public String getMed_estado() { return med_estado; }
    public void setMed_estado(String med_estado) { this.med_estado = med_estado; }

}//fin clase
