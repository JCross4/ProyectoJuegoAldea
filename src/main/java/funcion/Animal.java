/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcion;

/**
 *
 * @author 23jic
 */
public abstract class Animal {

    // Atributos comunes a todos los animales
    private String tipo;
    private int vida;
    private int fuerzaAtaque;
    private boolean vivo;

    // Métodos abstractos que deben implementar las clases concretas
    public abstract void atacar();
    public abstract void recibirDaño(int daño);
    public abstract boolean estaVivo();

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }
    public int getFuerzaAtaque() {
        return fuerzaAtaque;
    }
    public void setFuerzaAtaque(int fuerzaAtaque) {
        this.fuerzaAtaque = fuerzaAtaque;
    }
    

}
