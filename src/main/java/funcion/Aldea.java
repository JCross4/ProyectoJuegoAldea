package funcion;

import java.util.ArrayList;

public class Aldea {
    //Contiene personajes, estructuras, recursos y animales
    //Se encarga de gestionar los ciclos y el orden de las acciones
    
    private int cicloActual;
    private int maxCiclos;  //30
    private int agricultoresIniciales; //1 para todos
    private int cazadoresIniciales;
    private int constructoresIniciales;
    private int guardianesIniciales;
    private int leñadoresIniciales;
    private int torresIniciales; //1
    private int parcelasCultivoIniciales; //2
    private int comidaVegetalInicial; //20
    private int comidaAnimalInicial; //10
    private int maderaInicial; //40
    private int arbolesIniciales; //25


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

    private String nombre;
    private int maderaDisponible;
    private int comidaVegetalDisponible;
    private int comidaAnimalDisponible;
    private Cerca cercaPrincipal;
    private ArrayList<Personaje> personajes;
    private ArrayList<TorreDefensa> torres;
    private ArrayList<Animal> animalesActivos;
    private ArrayList<Parcela> parcelasCultivo;
    private int arbolesDisponibles;

    public void iniciarSimulacion() {
        while (cicloActual < maxCiclos) {
            //TODO Realizar acciones segun el orden determinado
            cicloActual++;
        }
    }

    private boolean verificarCondicionesVictoria() {
        //TODO Verificar condiciones de victoria al final del maximo de ciclos
        return false;
    }

    private boolean verificarCondicionesDerrota() {
        //TODO Verificar condiciones de derrota al final de cada ciclo
        return false;
    }

    public boolean verificarCondicionesAgregarPersonaje(){
        //TODO Verificar condiciones para agregar un nuevo personaje segun su tipo
        /*tiene al menos 30 unidades de alimento vegetal o carne combinadas,  
        tiene al menos 20 unidades de madera disponibles,  
        tiene al menos 1 torre viva,  
        tiene la cerca con resistencia mayor a 50,  
        y la cantidad total de habitantes vivos es menor que 12
         */
        return true;
    }

}
