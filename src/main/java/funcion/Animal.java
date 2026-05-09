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
    private String nombre;
    private String tipo;
    private int vida;
    private int fuerzaAtaque;
    private boolean vivo;



    
    public Animal(String nombre, String tipo, int vida, int fuerzaAtaque) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vida = vida;
        this.fuerzaAtaque = fuerzaAtaque;
        this.vivo = true;
    }

    // Métodos abstractos que deben implementar las clases concretas
    public abstract void atacar();
    
    public void recibirDaño(int daño){
        vida -= daño;
        if (vida <= 0) {
            vivo = false;
            vida = 0;
        }
    }

    public boolean estaVivo(){
        return vivo;
    }

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
    public boolean isVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    @Override
    public String toString() {
        return "Animal [tipo=" + tipo + ", vida=" + vida + ", fuerzaAtaque=" + fuerzaAtaque + ", vivo=" + vivo + "]";
    }
    

}
