package funcion;


public class Cazador extends Personaje {
    private Animal animalObjetivo; // Referencia al animal que el cazador está atacando
    private int dañoAtaque; // Daño que el cazador inflige al atacar

    public Cazador(String nombre, Aldea aldea) {
        super(nombre,"cazador", aldea);
        dañoAtaque = 25; // Ejemplo de daño que inflige el cazador
        setPathDefault("src/Recursos/9174031.png");
        //TODO Auto-generated constructor stub
    }

    @Override
    public void mover() {
        // TODO desplazar elemento y cambiar animación a "caminando"
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public void realizarAccion() {
        // Realiza accion específica
        /*Si existe al menos 1 animal activo en los alrededores, ataca un animal.  
    2. Si no hay animales activos, patrulla.  
    3. Si no puede atacar ni patrullar por falta de energía, descansa. */
    getAldea().getVentana().agregarLog("El cazador realiza la acción " + getAccionActual());
    switch (getAccionActual()) {
        case "atacar":
            // Lógica para atacar al animal objetivo
            if (animalObjetivo != null) {
                animalObjetivo.recibirDaño(getDañoAtaque()); // Reduce la salud del animal por el ataque
                if (!animalObjetivo.isVivo()){
                    /*Lobo aporta 6 unidades de carne.  
                    o Jabalí aporta 10 unidades de carne.  
                    o Oso aporta 16 unidades de carne.  */
                    switch (animalObjetivo.getTipo()) {
                        case "lobo":
                            getAldea().setComidaAnimalDisponible(getAldea().getComidaAnimalDisponible()+6);
                            break;
                        case "jabali":
                            getAldea().setComidaAnimalDisponible(getAldea().getComidaAnimalDisponible()+10);
                            break;
                        case "oso":
                            getAldea().setComidaAnimalDisponible(getAldea().getComidaAnimalDisponible()+16);
                            break;
                        default:
                            break;
                    }
                    getAldea().getVentana().actualizarRecursos();
                }
                this.setEnergia(this.getEnergia() - 25); // Reduce energía por atacar
            }
            break;
        case "patrullar":
            // Lógica para patrullar
            // Probabilidad de spawnear animal
            if (Math.random() <= 0.2) { 
                getAldea().getVentana().agregarLog("Se encontró un animal al patrullar");
                getAldea().agregarAnimal();
                
            }
             this.setEnergia(this.getEnergia() - 10); // Reduce energía por patrullar
            break;
        case "descansar":
            // Lógica para descansar
            descansar();
            break;
        default:
            break;
    }
    getAldea().getVentana().actualizarRecursos();
    }


    @Override
    public void determinarObjetivo() {
        /*Si existe al menos 1 animal activo en los alrededores, ataca un animal.  
    2. Si no hay animales activos, patrulla.  
    3. Si no puede atacar ni patrullar por falta de energía, descansa. */
        if (this.getAldea().getAnimalesActivos().size() > 0 && this.getEnergia() >= 25) {
            // Objetivo: atacar animal (podría ser la posición del animal más cercano o alguna otra lógica)
            Animal animalObjetivo = this.getAldea().obtenerAnimalCercano(this.getLabelGUI().getLocation());
            this.setAnimalObjetivo(animalObjetivo);
            this.setObjetivo(animalObjetivo.getLabelGUI().getLocation());
            this.setAccionActual("atacar");
            getAldea().getVentana().agregarLog("Cazador " + this.getNombre() + " va a atacar al animal" + animalObjetivo.getNombre());
        }
        else if (this.getEnergia() >= 10) {
            // Objetivo: patrullar (podría ser una ruta predefinida o alguna otra lógica)
            this.setObjetivo(this.getAldea().obtenerPuntoPatrulla(this.getLabelGUI().getLocation()));
            this.setAccionActual("patrullar");
            getAldea().getVentana().agregarLog("Cazador " + this.getNombre() + " va a patrullar");
        }
        else {
            // Objetivo: descansar
            this.setObjetivo(this.getLabelGUI().getLocation()); // Podría ser una posición específica para descansar
            this.setAccionActual("descansar");
            getAldea().getVentana().agregarLog("Cazador " + this.getNombre() + " va a descansar");
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
