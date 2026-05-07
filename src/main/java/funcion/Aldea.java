package funcion;

import java.util.ArrayList;
import java.util.Scanner;

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



    
    public Aldea() {
        this.maxCiclos = 30;
        this.agricultoresIniciales = 1;
        this.cazadoresIniciales = 1;
        this.constructoresIniciales = 1;
        this.guardianesIniciales = 1;
        this.leñadoresIniciales = 1;
        this.torresIniciales = 1;
        this.parcelasCultivoIniciales = 2;
        this.comidaVegetalInicial = 20;
        this.comidaAnimalInicial = 10;
        this.maderaInicial = 40;
        this.arbolesIniciales = 25;
        this.nombre = "Aldea";
        personajes = new ArrayList<Personaje>();
        torres = new ArrayList<TorreDefensa>();
        animalesActivos = new ArrayList<Animal>();
        parcelasCultivo = new ArrayList<Parcela>();
    }

    public Personaje crearPersonaje(String tipo) {
        //TODO Implementar lógica para crear un nuevo personaje
        switch (tipo.toLowerCase()) {
            case "agricultor":
                return new Agricultor("Agricultor" + (personajes.size() + 1), 0);
            case "cazador":
                return new Cazador("Cazador" + (personajes.size() + 1), 0);
            case "constructor":
                return new Constructor("Constructor" + (personajes.size() + 1), 0);
            case "guardián":
                return new Guardian("Guardián" + (personajes.size() + 1), 0);
            case "leñador":
                return new Lenador("Leñador" + (personajes.size() + 1), 0);
            default:
                throw new IllegalArgumentException("Tipo de personaje no válido: " + tipo);
        }
    }

    public void crearPersonajesIniciales() {
        for (int i = 0; i < agricultoresIniciales; i++) {
            personajes.add(crearPersonaje("agricultor"));
        }
        for (int i = 0; i < cazadoresIniciales; i++) {
            personajes.add(crearPersonaje("cazador"));
        }
        for (int i = 0; i < constructoresIniciales; i++) {
            personajes.add(crearPersonaje("constructor"));
        }
        for (int i = 0; i < guardianesIniciales; i++) {
            personajes.add(crearPersonaje("guardián"));
        }
        for (int i = 0; i < leñadoresIniciales; i++) {
            personajes.add(crearPersonaje("leñador"));
        }
    }

    public TorreDefensa crearTorreDefensa() {
        //TODO Implementar lógica para crear una nueva torre de defensa
        return new TorreDefensa("Torre de Defensa" + (torres.size() + 1));
    }

    public Parcela crearParcelaCultivo() {
        //TODO Implementar lógica para crear una nueva parcela de cultivo
        return new Parcela("Parcela de Cultivo" + (parcelasCultivo.size() + 1));
    }

    public void iniciarSimulacion() {
        cicloActual = 0;
        //Crear personajes, estructuras, recursos y animales iniciales
        Cerca cercaPrincipal = new Cerca();
        //Crear personajes segun los valores iniciales y agregarlos a la lista de personajes
        
        crearPersonajesIniciales();
        //Crear torres de defensa segun los valores iniciales y agregarlos a la lista de torres
        for (int i = 0; i < torresIniciales; i++) {
            torres.add(crearTorreDefensa());
        }
        arbolesDisponibles = arbolesIniciales;
        maderaDisponible = maderaInicial;
        comidaVegetalDisponible = comidaVegetalInicial;
        comidaAnimalDisponible = comidaAnimalInicial;
        for (int i = 0; i < parcelasCultivoIniciales; i++) {
            parcelasCultivo.add(crearParcelaCultivo());
        }


        Scanner scanner = new Scanner(System.in);
        //Ciclo principal de la simulación
        while (cicloActual < maxCiclos) {
            //TODO Realizar acciones segun el orden determinado
            System.out.println("Ciclo " + cicloActual);
            System.out.println("Personajes: " + personajes);
            System.out.println("Torres: " + torres);
            scanner.nextLine();
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
