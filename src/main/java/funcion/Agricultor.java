package funcion;


public class Agricultor extends Personaje {
    private Parcela parcelaObjetivo;

    public Agricultor(String nombre, Aldea aldea) {
        super(nombre, "agricultor", aldea);
        this.setPathDefault("src/Recursos/10444746.png");
    }

    @Override
    public void mover() {
        // TODO desplazar elemento y cambiar animación a "caminando"
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public void realizarAccion() {
        // realiza accion específica
        /*Si existe una parcela vacía, siembra una parcela.  
2. Si existe una parcela sembrada lista para cosechar, cosecha.  
3. Si todas las parcelas están ocupadas pero ninguna lista para cosechar, cuida cultivos.  
4. Si no puede hacer ninguna acción anterior, descansa.  */
        getAldea().getVentana().agregarLog("El agricultor realiza la acción " + getAccionActual());
        switch (getAccionActual()) {
            case "sembrar":
                // Acción de sembrar
                parcelaObjetivo.cultivar();
                this.setEnergia(this.getEnergia() - 15); // Reduce energía por sembrar
                
                break;
            case "cosechar":
                // Acción de cosechar
                parcelaObjetivo.cosechar();
                this.setEnergia(this.getEnergia() - 15); // Reduce energía por cosechar
                getAldea().getVentana().actualizarRecursos();
                break;
            case "cuidar":
                // Acción de cuidar
                parcelaObjetivo.cuidar();
                this.setEnergia(this.getEnergia() - 10); // Reduce energía por cuidar
                break;
            case "descansar":
                // Acción de descansar
                descansar();
                break;
            default:
                break;
        }
        getAldea().actualizarLabelsParcelas();
        getAldea().getVentana().actualizarRecursos();
        parcelaObjetivo = null;

    }


    @Override
    public void determinarObjetivo() {
        /*Si existe una parcela vacía, siembra una parcela.  
2. Si existe una parcela sembrada lista para cosechar, cosecha.  
3. Si todas las parcelas están ocupadas pero ninguna lista para cosechar, cuida cultivos.  
4. Si no puede hacer ninguna acción anterior, descansa.  */
        Parcela parcelaParaSembrar = null;
        Parcela parcelaParaCosechar = null;
        Parcela parcelaParaCuidar = null;
        for (Parcela parcela : this.getAldea().getParcelasCultivo().reversed()) {
            if (parcela.getEstado().equals("vacía")) {
                // Objetivo: sembrar parcela
                parcelaParaSembrar = parcela;
            }
            if (parcela.getEstado().equals("lista para cosechar")) {
                // Objetivo: cosechar parcela
                parcelaParaCosechar = parcela;
            }
            if (parcela.getEstado().equals("sembrada") && parcela.getCiclosParaCosechar() > 0) {
                // Objetivo: cuidar parcela
                parcelaParaCuidar = parcela;
            }
        }
        if (parcelaParaSembrar != null && this.getEnergia() >= 15) {
            this.setObjetivo(parcelaParaSembrar.getLabelGUI().getLocation());
            this.setAccionActual("sembrar");
            parcelaObjetivo = parcelaParaSembrar;
            getAldea().getVentana().agregarLog("Agricultor " + this.getNombre() + " va a sembrar la parcela " + parcelaObjetivo.getNombre());
        }
        else if (parcelaParaCosechar != null && this.getEnergia() >= 15) {
            this.setObjetivo(parcelaParaCosechar.getLabelGUI().getLocation());
            this.setAccionActual("cosechar");
            parcelaObjetivo = parcelaParaCosechar;
            getAldea().getVentana().agregarLog("Agricultor " + this.getNombre() + " va a cosechar la parcela " + parcelaObjetivo.getNombre());
        }
        else if (parcelaParaCuidar != null && this.getEnergia() >= 10) {
            // Objetivo: cuidar cultivos
            this.setObjetivo(parcelaParaCuidar.getLabelGUI().getLocation());
            this.setAccionActual("cuidar");
            parcelaObjetivo = parcelaParaCuidar;
            getAldea().getVentana().agregarLog("Agricultor " + this.getNombre() + " va a cuidar la parcela " + parcelaObjetivo.getNombre());
        }
        else {
            // Objetivo: descansar
            this.setObjetivo(this.getLabelGUI().getLocation()); 
            this.setAccionActual("descansar");
            getAldea().getVentana().agregarLog("Agricultor " + this.getNombre() + " va a descansar");
        }
    }


}
