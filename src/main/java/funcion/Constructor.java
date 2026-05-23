package funcion;

import java.awt.Point;

public class Constructor extends Personaje {
    private TorreDefensa torreObjetivo;

    public Constructor(String nombre, Aldea aldea) {
        super(nombre,"constructor", aldea);
        this.setPathDefault("src/Recursos/10365951.png");
    }

    @Override
    public void mover() {
        // TODO desplazar elemento y cambiar animación a "caminando"
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public void realizarAccion() {
        // Realiza accion específica
        /*1. Si la cerca tiene resistencia menor a 100 y hay al menos 5 unidades de madera, 
repara la cerca.  
2. Si la cerca está en 100 y existe alguna torre con resistencia menor a su máximo y 
hay al menos 4 unidades de madera, repara una torre.  
3. Si la cerca está en 100, todas las torres están al máximo y la aldea tiene menos de 3 
torres, construye una nueva torre si hay al menos 20 unidades de madera.  
4. Si ninguna condición anterior se cumple, descansa.  */
        getAldea().getVentana().agregarLog("El constructor realiza la acción " + getAccionActual());
        switch (getAccionActual()) {
            case "reparar cerca":
                this.getAldea().getCercaPrincipal().reparar(15);
                this.getAldea().setMaderaDisponible(this.getAldea().getMaderaDisponible() - 5);
                this.setEnergia(this.getEnergia() - 20); // Reduce energía por reparar
                break;
            case "reparar torre":
                torreObjetivo.reparar(10);
                this.getAldea().setMaderaDisponible(this.getAldea().getMaderaDisponible() - 4);
                this.setEnergia(this.getEnergia() - 20); // Reduce energía por reparar
                break;
            case "construir torre":
                this.getAldea().agregarTorre();
                this.getAldea().setMaderaDisponible(this.getAldea().getMaderaDisponible() - 20);
                this.setEnergia(this.getEnergia() - 20); // Reduce energía por construir
                break;
            case "descansar":
                descansar();
                break;
            default:
                break;
        }
        getAldea().getVentana().actualizarRecursos();
        torreObjetivo = null; // Reinicia torre objetivo después de realizar acción
    }

    @Override
    public void determinarObjetivo() {
        /*1. Si la cerca tiene resistencia menor a 100 y hay al menos 5 unidades de madera, 
repara la cerca.  
2. Si la cerca está en 100 y existe alguna torre con resistencia menor a su máximo y 
hay al menos 4 unidades de madera, repara una torre.  
3. Si la cerca está en 100, todas las torres están al máximo y la aldea tiene menos de 3 
torres, construye una nueva torre si hay al menos 20 unidades de madera.  
4. Si ninguna condición anterior se cumple, descansa.  */
        TorreDefensa torreParaReparar = null;
        torreParaReparar = this.getAldea().obtenerTorreDañada();
        if (this.getAldea().getCercaPrincipal().getResistenciaActual() < this.getAldea().getCercaPrincipal().getResistenciaMaxima() && this.getAldea().getMaderaDisponible() >= 5 && this.getEnergia() >= 20) {
            // Objetivo: reparar cerca
            this.setObjetivo(new Point(this.getAldea().getCercaPrincipal().getLabelGUI().getLocation().x + getAldea().getVentana().getLABEL_SIZE() * 4, this.getAldea().getCercaPrincipal().getLabelGUI().getLocation().y + getAldea().getVentana().getLABEL_SIZE() * 4));
            setAccionActual("reparar cerca");
            getAldea().getVentana().agregarLog("Constructor " + this.getNombre() + " va a reparar la cerca.");
        } else if (this.getAldea().getCercaPrincipal().getResistenciaActual() == 100 && torreParaReparar != null && this.getAldea().getMaderaDisponible() >= 4 && this.getEnergia() >= 20) {
            // Objetivo: reparar torre
            this.setObjetivo(torreParaReparar.getLabelGUI().getLocation());
            setAccionActual("reparar torre");
            this.torreObjetivo = torreParaReparar;
            getAldea().getVentana().agregarLog("Constructor " + this.getNombre() + " va a reparar la torre " + torreParaReparar.getNombre() + ".");
        } else if (this.getAldea().getCercaPrincipal().getResistenciaActual() == this.getAldea().getCercaPrincipal().getResistenciaMaxima() && torreParaReparar == null && this.getAldea().getTorres().size() < 3 && this.getAldea().getMaderaDisponible() >= 20 && this.getEnergia() >= 20) {
            // Objetivo: construir nueva torre
            this.setObjetivo(new Point(getAldea().getVentana().getLABEL_SIZE()*this.getAldea().getTorres().size(), getAldea().getVentana().getLABEL_SIZE() * 3));
            setAccionActual("construir torre");
            getAldea().getVentana().agregarLog("Constructor " + this.getNombre() + " va a construir una nueva torre.");
        } else {
            // Objetivo: descansar
            this.setObjetivo(getLabelGUI().getLocation());
            setAccionActual("descansar");
            getAldea().getVentana().agregarLog("Constructor " + this.getNombre() + " va a descansar.");
        }
    }


}
