/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcion;

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
    private int posicion;
    private String tipo;


    // Métodos abstractos que deben implementar las clases concretas
    public abstract void mover();
    public abstract void realizarAccion();
    public abstract void comer();
    public abstract void descansar();
    public abstract void recibirDaño(int daño);
    public abstract boolean estaVivo();
    
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
    public int getPosicion() {
        return posicion;
    }
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
}
