/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package funcion;

import java.awt.Point;

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
    private javax.swing.JLabel labelGUI;
    private Aldea aldea; // Referencia a la aldea para interactuar con otros personajes y el entorno
    private Point objetivo; // Posición objetivo para moverse
    private TorreDefensa torreObjetivo;
    private Personaje personajeObjetivo;
    private String accionActual;
    private String pathDefault; //Ubicación de la imagen


    
    public Animal(String nombre, String tipo, int vida, int fuerzaAtaque, Aldea aldea) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vida = vida;
        this.fuerzaAtaque = fuerzaAtaque;
        this.vivo = true;
        this.aldea = aldea;
        this.accionActual = "";
    }

    // Métodos abstractos que deben implementar las clases concretas
    public void atacar(){
        switch (getAccionActual()) {
            case "atacar cerca":
                getAldea().getCercaPrincipal().recibirDaño(getFuerzaAtaque());
                getAldea().getVentana().agregarLog("El animal " + this.getNombre() + " ataca la cerca principal");
                break;
            case "atacar torre":
                if (torreObjetivo != null) {
                    torreObjetivo.recibirDaño(getFuerzaAtaque());
                    if (torreObjetivo.isDestruida()){
                        getAldea().eliminarTorre(torreObjetivo);
                    }
                }
                getAldea().getVentana().agregarLog("El animal " + this.getNombre() + " ataca una torre de defensa");
                break;
            case "atacar personaje":
                if (personajeObjetivo != null) {
                    personajeObjetivo.recibirDaño(getFuerzaAtaque());
                }
                getAldea().getVentana().agregarLog("El animal " + this.getNombre() + " ataca un personaje");
                break;
            default:
                break;
        }
    }
    
    public void recibirDaño(int daño){
        vida -= daño;
        if (vida <= 0) {
            vivo = false;
            vida = 0;
        }
    }

    public void determinarObjetivo(){
/*Primero atacan la cerca si la cerca tiene resistencia mayor a 0.  
2. Si la cerca ya está destruida, atacan torres vivas.  
3. Si no hay cerca ni torres vivas, atacan personajes vivos. */
    TorreDefensa torreMenosVida = getAldea().obtenerTorreMenosVida();
    if (getAldea().getCercaPrincipal().getResistenciaActual() > 0) {
        setTorreObjetivo(null);
        setPersonajeObjetivo(null);
        setAccionActual("atacar cerca");
        setObjetivo(new Point(getAldea().getCercaPrincipal().getLabelGUI().getLocation().x + getAldea().getVentana().getLABEL_SIZE()*4, getAldea().getCercaPrincipal().getLabelGUI().getLocation().y + getAldea().getVentana().getLABEL_SIZE()*4));
    } else if (getAldea().getTorres().size() > 0 && torreMenosVida!=null) {
        
        setAccionActual("atacar torre");
        setTorreObjetivo(torreMenosVida);
        setPersonajeObjetivo(null);
        setObjetivo(torreMenosVida.getLabelGUI().getLocation());
    } else {
        Personaje personajeMenosVida = getAldea().obtenerPersonajeMenosVida();
        setAccionActual("atacar personaje");
        setTorreObjetivo(null);
        setPersonajeObjetivo(personajeMenosVida);
        setObjetivo(personajeMenosVida.getLabelGUI().getLocation());
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public javax.swing.JLabel getLabelGUI() {
        return labelGUI;
    }

    public void setLabelGUI(javax.swing.JLabel labelGUI) {
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

    public TorreDefensa getTorreObjetivo() {
        return torreObjetivo;
    }

    public void setTorreObjetivo(TorreDefensa torreObjetivo) {
        this.torreObjetivo = torreObjetivo;
    }

    public Personaje getPersonajeObjetivo() {
        return personajeObjetivo;
    }

    public void setPersonajeObjetivo(Personaje personajeObjetivo) {
        this.personajeObjetivo = personajeObjetivo;
    }

    public String getAccionActual() {
        return accionActual;
    }

    public void setAccionActual(String accionActual) {
        this.accionActual = accionActual;
    }

    public String getPathDefault() {
        return pathDefault;
    }

    public void setPathDefault(String pathDefault) {
        this.pathDefault = pathDefault;
    }
    

}
