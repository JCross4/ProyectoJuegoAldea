package funcion;


public class Cazador extends Personaje {
    private Animal animalObjetivo; // Referencia al animal que el cazador está atacando
    private int dañoAtaque; // Daño que el cazador inflige al atacar

    public Cazador(String nombre, Aldea aldea) {
        super(nombre,"cazador", aldea);
        dañoAtaque = 25; // Ejemplo de daño que inflige el cazador
        //TODO Auto-generated constructor stub
    }

    @Override
    public void mover() {
        // TODO desplazar elemento y cambiar animación a "caminando"
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public void realizarAccion() {
        // TODO realiza accion específica
        //throw new UnsupportedOperationException("Unimplemented method 'realizarAccion'");
        /*Si existe al menos 1 animal activo en los alrededores, ataca un animal.  
    2. Si no hay animales activos, patrulla.  
    3. Si no puede atacar ni patrullar por falta de energía, descansa. */
    switch (getAccionActual()) {
        case "atacar":
            // Lógica para atacar al animal objetivo
            if (animalObjetivo != null) {
                animalObjetivo.recibirDaño(getDañoAtaque()); // Reduce la salud del animal por el ataque
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
            this.setEnergia(Math.min(100, this.getEnergia() + 30)); // Recupera energía al descansar
            break;
        default:
            break;
    }
    }

    @Override
    public void comer() {
        // TODO recupera energia y reduce comida disponible
        throw new UnsupportedOperationException("Unimplemented method 'comer'");
    }

    @Override
    public void descansar() {
        // TODO recupera energia y cambia animación a "descansando"
        throw new UnsupportedOperationException("Unimplemented method 'descansar'");
    }

    @Override
    public void determinarObjetivo() {
        // TODO Auto-generated method stub
        /*Si existe al menos 1 animal activo en los alrededores, ataca un animal.  
    2. Si no hay animales activos, patrulla.  
    3. Si no puede atacar ni patrullar por falta de energía, descansa. */
        if (this.getAldea().getAnimalesActivos().size() > 0 && this.getEnergia() >= 25) {
            // Objetivo: atacar animal (podría ser la posición del animal más cercano o alguna otra lógica)
            Animal animalObjetivo = this.getAldea().obtenerAnimalCercano(this.getLabelGUI().getLocation());
            this.setAnimalObjetivo(animalObjetivo);
            this.setObjetivo(animalObjetivo.getLabelGUI().getLocation());
            this.setAccionActual("atacar");
        }
        else if (this.getEnergia() >= 10) {
            // Objetivo: patrullar (podría ser una ruta predefinida o alguna otra lógica)
            this.setObjetivo(this.getAldea().obtenerPuntoPatrulla(this.getLabelGUI().getLocation()));
            this.setAccionActual("patrullar");
        }
        else {
            // Objetivo: descansar
            this.setObjetivo(this.getLabelGUI().getLocation()); // Podría ser una posición específica para descansar
            this.setAccionActual("descansar");
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
