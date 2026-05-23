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
    private Point objetivo; // Posición objetivo para moverse
    private String accionActual; // Acción que el personaje está realizando actualmente
    private String pathDefault; // Ruta de la imagen por defecto del personaje

    public Personaje(String nombre, String tipo, Aldea aldea) {
        this.nombre = nombre;
        this.salud = 100;
        this.energia = 100;
        this.vivo = true;
        this.aldea = aldea;
        this.tipo = tipo;
        this.accionActual = "";
    }

    // Métodos abstractos que deben implementar las clases concretas
    public abstract void mover(); //?
    public abstract void realizarAccion();  //Checkear condiciones y realizar acción según indicado
    public abstract void determinarObjetivo();  //Determina el objetivo según su función (ej. torre más cercana, parcela de cultivo, etc.)
    
    public void descansar(){
        this.setEnergia(Math.min(100, this.getEnergia() + 20)); // Recupera energía al descansar
    }; 

    public void comer(){//Recupera energía y reduce comida disponible
        /*Cada personaje consume exactamente 2 unidades de comida por ciclo.  
• La comida se consume en este orden:  
1. alimento vegetal  
2. carne  
• Si no hay suficiente comida para un personaje:  
o pierde 15 de salud  
o pierde 10 de energía adicionales  
• Si sí logra alimentarse:  
o recupera 10 de energía  
o sin superar 100  */
        if (getAldea().getComidaAnimalDisponible() + getAldea().getComidaVegetalDisponible() >= 2){
            if (getAldea().getComidaVegetalDisponible()>=2) {
                getAldea().setComidaVegetalDisponible(getAldea().getComidaVegetalDisponible()-2);
                }
            else {
                getAldea().setComidaAnimalDisponible(getAldea().getComidaAnimalDisponible()-(2 - getAldea().getComidaVegetalDisponible()));
                getAldea().setComidaVegetalDisponible(0);
            }
            getAldea().getVentana().agregarLog("El personaje " + getNombre() + " se alimenta");
            setEnergia(Math.min(100, this.getEnergia() + 10));
        }
        else{
            getAldea().getVentana().agregarLog("El personaje " + getNombre() + " no puede alimentarse y recibe daño y pierde energía");
            recibirDaño(15);
            setEnergia(Math.max(0, this.getEnergia() - 10));
        }

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
    public String getAccionActual() {
    return accionActual;
}
public void setAccionActual(String accionActual) {
    this.accionActual = accionActual;
}
@Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", salud=" + salud + ", energia=" + energia + ", vivo=" + vivo
                + ", tipo=" + tipo + "]";
    }
public String getPathDefault() {
    return pathDefault;
}
public void setPathDefault(String pathDefault) {
    this.pathDefault = pathDefault;
}

    
}
