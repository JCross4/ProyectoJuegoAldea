package funcion;

import java.awt.Point;

public class Guardian extends Personaje {
    private Animal animalObjetivo;
    private int dañoAtaque;

    public Guardian(String nombre, Aldea aldea) {
        super(nombre,"guardian", aldea);
        dañoAtaque = 20;
        setPathDefault("src/Recursos/5723525.png");
    }

    @Override
    public void mover() {
        // TODO desplazar elemento y cambiar animación a "caminando"
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public void realizarAccion() {
        /*Si hay animales activos, ataca al animal con mayor fuerza de ataque.  
2. Si no hay animales activos, vigila la entrada.  
3. Si no tiene energía suficiente, descansa */
    getAldea().getVentana().agregarLog("El guardián " + getNombre() + " realiza la acción " + getAccionActual());
    switch (getAccionActual()) {
        case "atacar":
            if (animalObjetivo != null) {
                animalObjetivo.recibirDaño(getDañoAtaque()); // Reduce la salud del animal por el ataque
                this.setEnergia(this.getEnergia() - 15); // Reduce energía por atacar
            }
            break;
        case "vigilar":
            //Vigilar ???
            setEnergia(getEnergia()-5);
            break;
        case "descansar":
            descansar();
            break;    
        default:
            break;
    }
    setAnimalObjetivo(null);
    getAldea().getVentana().actualizarRecursos();
    }


    @Override
    public void descansar() {
        // TODO recupera energia y cambia animación a "descansando"
        this.setEnergia(Math.min(100, this.getEnergia() + 30));
    }

    @Override
    public void determinarObjetivo() {
        /*Si hay animales activos, ataca al animal con mayor fuerza de ataque.  
2. Si no hay animales activos, vigila la entrada.  
3. Si no tiene energía suficiente, descansa */
        if (this.getAldea().getAnimalesActivos().size() > 0 && this.getEnergia() >= 30) {
            // Objetivo: atacar animal con mayor fuerza de ataque
            animalObjetivo = this.getAldea().obtenerAnimalMasFuerte();
            this.setObjetivo(animalObjetivo.getLabelGUI().getLocation());
            this.setAccionActual("atacar");
            getAldea().getVentana().agregarLog("Guardián " + this.getNombre() + " va a atacar al animal " + animalObjetivo.getNombre());
        }
        else if (this.getEnergia() >= 15) {
            // Objetivo: vigilar la entrada 
            setObjetivo(new Point(getAldea().getVentana().getLABEL_SIZE()*4, getAldea().getVentana().getLABEL_SIZE()*4));
            this.setAccionActual("vigilar");
            getAldea().getVentana().agregarLog("Guardián " + this.getNombre() + " va a vigilar");
        }
        else {
            // Objetivo: descansar
            this.setObjetivo(this.getLabelGUI().getLocation()); // Podría ser una posición específica para descansar
            this.setAccionActual("descansar");
            getAldea().getVentana().agregarLog("Guardián " + this.getNombre() + " va a descansar");
        }
    }

    public Animal getAnimalObjetivo() {
        return animalObjetivo;
    }

    public void setAnimalObjetivo(Animal animalObjetivo) {
        this.animalObjetivo = animalObjetivo;
    }

    public int getDañoAtaque() {
        return dañoAtaque;
    }

    public void setDañoAtaque(int dañoAtaque) {
        this.dañoAtaque = dañoAtaque;
    }


}
