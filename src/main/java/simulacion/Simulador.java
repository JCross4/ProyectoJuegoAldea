package simulacion;


//     TEMPORALMENTE EN ALDEA

public class Simulador {
    //Se encarga de gestionar los ciclos y el orden de las acciones
    private int cicloActual;
    private int maxCiclos;  //30
    //Condiciones de victoria al final del maximo de ciclos
    private int minPersonajesVivosVictoria; //3
    private int minResistenciaCercaVictoria; //1
    private int minComidaTotal; //20
    private int minTorresEnPie; //1
    //Condiciones de derrota al final de cada ciclo
    private int minPersonajesVivosDerrota; //0
    private int minResistenciaCercaDerrota; //0
    private int minTorresEnPieDerrota; //0
    private int minComidaDerrota; //0
    private int maxTurnosMinComida; //3

    public Simulador(int maxCiclos) {
        this.cicloActual = 0;
        this.maxCiclos = maxCiclos;
    }

    public void iniciarSimulacion() {
        while (cicloActual < maxCiclos) {
            //TODO Realizar acciones segun el orden determinado
            cicloActual++;
        }
    }

    private void verificarCondicionesVictoria() {
        //TODO Verificar condiciones de victoria al final del maximo de ciclos
    }

    private void verificarCondicionesDerrota() {
        //TODO Verificar condiciones de derrota al final de cada ciclo
    }
}
