package funcion;

public class TorreDefensa extends Estructura{
    private int dañoAtaque; //15
    private Animal animalObjetivo;

    public TorreDefensa(String nombre, Aldea aldea) {
        super(nombre, 60, 60, aldea);
        this.dañoAtaque = 15;
        setPathDefault("src/Recursos/rpg-map-symbols-round-tower-clipart-xl.png");
    }

    public void realizarAtaque(){
        if (animalObjetivo != null){
            animalObjetivo.recibirDaño(getDañoAtaque()); // Reduce la salud del animal por el ataque
            getAldea().getVentana().agregarLog("La torre " + this.getNombre() + " ataca al animal: " + animalObjetivo.getNombre());
        }
    }
    
    public void determinarObjetivo(){
        if (this.getAldea().getAnimalesActivos().size() > 0) {
            animalObjetivo = getAldea().obtenerAnimalMasVida();
        }
        else
            animalObjetivo = null;
    }

    public int getDañoAtaque() {
        return dañoAtaque;
    }

    public void setDañoAtaque(int dañoAtaque) {
        this.dañoAtaque = dañoAtaque;
    }
    
}