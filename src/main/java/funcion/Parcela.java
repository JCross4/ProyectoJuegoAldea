package funcion;

public class Parcela extends Estructura {
    private String estado; // "vacía", "sembrada", "lista para cosechar"
    private int ciclosParaCosechar; // Número de ciclos necesarios para que la parcela esté lista para cosechar
    
    
    public Parcela(String nombre, Aldea aldea) {
        super(nombre, 50, 50, aldea);
        this.estado = "vacía";
        this.ciclosParaCosechar = 0;
        setPathDefault("src/Recursos/Oak_Farming_Plot.png");
    }

    public void cultivar() {
        if (estado.equals("vacía")) {
            getAldea().getVentana().agregarLog("Se siembra la parcela " + getNombre());
            estado = "sembrada";
            ciclosParaCosechar = 3; // Ejemplo: tarda 3 ciclos en estar lista para cosechar
        }
    }

    public void cuidar() {
        if (estado.equals("sembrada") && ciclosParaCosechar > 0) {
            getAldea().getVentana().agregarLog("Se reduce en 1 los ciclos para cosechar " + getNombre() + " Ciclos para cosechar: " + getCiclosParaCosechar());
            ciclosParaCosechar--;
            if (ciclosParaCosechar == 0) {
                estado = "lista para cosechar";
            }
        }
    }
    
    public void cosechar() {
        if (estado.equals("lista para cosechar")) {
            getAldea().getVentana().agregarLog("Se cosecha la parcela " + getNombre() + " y se obtiene 12 comida vegetal");
            estado = "vacía";
            ciclosParaCosechar = 0;
            getAldea().setComidaVegetalDisponible(getAldea().getComidaVegetalDisponible() + 12);
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCiclosParaCosechar() {
        return ciclosParaCosechar;
    }

    public void setCiclosParaCosechar(int ciclosParaCosechar) {
        this.ciclosParaCosechar = ciclosParaCosechar;
    }

    
}
