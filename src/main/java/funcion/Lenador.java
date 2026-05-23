package funcion;

import javax.swing.JLabel;

public class Lenador extends Personaje {
    private JLabel arbolObjetivo;


    public Lenador(String nombre, Aldea aldea) {
        super(nombre,"lenador", aldea);
        setPathDefault("src/Recursos/3611374.png");
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
        getAldea().getVentana().agregarLog("El leñador realiza la acción " + getAccionActual());
        if (this.getAccionActual().equals("cortar") && this.getEnergia() >= 20 && getAldea().getArbolesDisponibles() != 0) {
            // Realiza acción de cortar árbol
            if (this.arbolObjetivo != null) {
                getAldea().setMaderaDisponible(getAldea().getMaderaDisponible()+8);
                this.getAldea().eliminarArbol(this.arbolObjetivo);
                this.getAldea().setArbolesDisponibles(this.getAldea().getArbolesDisponibles() - 1);
                getAldea().getVentana().actualizarRecursos();
                this.setEnergia(this.getEnergia() - 20); // Reduce energía por cortar
            }
        }
        else if (this.getAccionActual().equals("descansar")) {
            // Realiza acción de descansar
            descansar();
        }
        this.arbolObjetivo = null;
        getAldea().getVentana().actualizarRecursos();
    }

    @Override
    public void determinarObjetivo() {
        // TODO Auto-generated method stub
        /*Si quedan árboles disponibles, corta 1 árbol.  
2. Si no quedan árboles, descansa. */
        if (this.getAldea().getArbolesDisponibles() > 0 && this.getEnergia() >= 20) {
            // Objetivo: cortar árbol (podría ser la posición del árbol más cercano o alguna otra lógica)
            this.setObjetivo(this.getAldea().obtenerArbolCercano(this));
            this.setAccionActual("cortar");
            getAldea().getVentana().agregarLog("Leñador " + this.getNombre() + " va a cortar un árbol");
        }
        else {
            // Objetivo: descansar
            this.setObjetivo(this.getLabelGUI().getLocation()); // Podría ser una posición específica para descansar
            this.setAccionActual("descansar");
            getAldea().getVentana().agregarLog("Leñador " + this.getNombre() + " va a descansar");
        }
    }

    public JLabel getArbolObjetivo() {
        return arbolObjetivo;
    }

    public void setArbolObjetivo(JLabel arbolObjetivo) {
        this.arbolObjetivo = arbolObjetivo;
    }

    

}
