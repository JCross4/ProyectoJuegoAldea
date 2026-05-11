/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcion;

/**
 *
 * @author 23jic
 */
public abstract class Estructura {
    
    // Atributos comunes a todas las estructuras
    private String nombre;
    private int resistenciaActual;
    private int resistenciaMaxima;
    private boolean destruida;
    private javax.swing.JLabel labelGUI;


    public Estructura(String nombre, int resistenciaActual, int resistenciaMaxima) {
        this.nombre = nombre;
        this.resistenciaActual = resistenciaActual;
        this.resistenciaMaxima = resistenciaMaxima;
        this.destruida = false;
    }

    // Métodos abstractos que deben implementar las clases concretas
    public void recibirDaño(int daño)
    {
        resistenciaActual -= daño;
        if(resistenciaActual <= 0){
            destruida = true;
            resistenciaActual = 0;
        }
    }

    public void reparar(int cantidad){
        if(!destruida){
            resistenciaActual += cantidad;
            if(resistenciaActual > resistenciaMaxima){
                resistenciaActual = resistenciaMaxima;
            }
        }
    }
    
    public boolean estaDestruida(){
        return isDestruida();
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getResistenciaActual() {
        return resistenciaActual;
    }
    public void setResistenciaActual(int resistenciaActual) {
        this.resistenciaActual = resistenciaActual;
    }
    public int getResistenciaMaxima() {
        return resistenciaMaxima;
    }
    public void setResistenciaMaxima(int resistenciaMaxima) {
        this.resistenciaMaxima = resistenciaMaxima;
    }
    public boolean isDestruida() {
        return destruida;
    }
    public void setDestruida(boolean destruida) {
        this.destruida = destruida;
    }

    @Override
    public String toString() {
        return "Estructura [nombre=" + nombre + ", resistenciaActual=" + resistenciaActual + ", resistenciaMaxima="
                + resistenciaMaxima + ", destruida=" + destruida + "]";
    }

    public javax.swing.JLabel getLabelGUI() {
        return labelGUI;
    }

    public void setLabelGUI(javax.swing.JLabel labelGUI) {
        this.labelGUI = labelGUI;
    }

    
    
}
