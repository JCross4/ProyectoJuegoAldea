package funcion;

public class Parcela extends Estructura {
    private String estado; // "vacía", "sembrada", "lista para cosechar"
    private int ciclosParaCosechar; // Número de ciclos necesarios para que la parcela esté lista para cosechar
    
    
    public Parcela(String nombre) {
        super(nombre, 50, 50);
        this.estado = "vacía";
        this.ciclosParaCosechar = 0;
    }

    public void cultivar() {
        if (estado.equals("vacía")) {
            estado = "sembrada";
            ciclosParaCosechar = 3; // Ejemplo: tarda 3 ciclos en estar lista para cosechar
        }
    }
}
