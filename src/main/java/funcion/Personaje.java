/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcion;

import javax.swing.JLabel;
import java.awt.Point;

/**
 *
 * @author 23jic
 */
public abstract class Personaje {
    // Atributos comunes a todos los personajes
    private String nombre;
    private int salud;
    private int energia;
    private boolean vivo;
    private String tipo;
    private javax.swing.JLabel labelGUI;
    private Aldea aldea; // Referencia a la aldea para interactuar con otros personajes y el entorno
    private Point objetivo = new Point(0,0); // Posición objetivo para moverse


    // Métodos abstractos que deben implementar las clases concretas
    public abstract void mover();
    public abstract void realizarAccion();
    public abstract void comer();
    public abstract void descansar();
    public abstract void determinarObjetivo();


    public Personaje(String nombre, String tipo, Aldea aldea) {
        this.nombre = nombre;
        this.salud = 100;
        this.energia = 100;
        this.vivo = true;
        this.aldea = aldea;
        this.tipo = tipo;
    }

    public void recibirDaño(int daño){
        salud -= daño;
        if(salud <= 0){
            vivo = false;
            salud = 0;
        }
    }

    public boolean estaVivo(){
        return vivo;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getSalud() {
        return salud;
    }
    public void setSalud(int salud) {
        this.salud = salud;
    }
    public int getEnergia() {
        return energia;
    }
    public void setEnergia(int energia) {
        this.energia = energia;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public boolean isVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public JLabel getLabelGUI() {
        return labelGUI;
    }

    public void setLabelGUI(JLabel labelGUI) {
        this.labelGUI = labelGUI;
    }
    
    public Point getObjetivo() {
        return objetivo;
    }
    public void setObjetivo(Point objetivo) {
        this.objetivo = objetivo;
    }
    public Aldea getAldea() {
        return aldea;
    }
    public void setAldea(Aldea aldea) {
        this.aldea = aldea;
    }
@Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", salud=" + salud + ", energia=" + energia + ", vivo=" + vivo
                + ", tipo=" + tipo + "]";
    }
    
}
