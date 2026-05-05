package funcion;

public class Parcela extends Estructura {
    private String estado; // "vacía", "sembrada", "lista para cosechar"
    private int ciclosParaCosechar; // Número de ciclos necesarios para que la parcela esté lista para cosechar
    
    public Parcela(String nombre) {
        super(nombre, 50, 50);
        this.estado = "vacía";
        this.ciclosParaCosechar = 0;
    }


}
