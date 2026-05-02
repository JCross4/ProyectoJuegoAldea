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

    // Métodos abstractos que deben implementar las clases concretas
    public abstract void recibirDaño(int daño);
    public abstract void reparar(int cantidad);
    public abstract boolean estaDestruida();

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

    
    
}
