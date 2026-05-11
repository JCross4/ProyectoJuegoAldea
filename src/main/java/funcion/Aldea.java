package funcion;

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Point;
import gui.VentanaPrincipal;

public class Aldea {
    //Contiene personajes, estructuras, recursos y animales
    //Se encarga de gestionar los ciclos y el orden de las acciones

    //Valores iniciales y constantes
    final int MAX_CICLOS = 30;
    final int MAX_HABITANTES = 12;
    final int AGRICULTORES_INICIALES = 1;
    final int CAZADORES_INICIALES = 1;
    final int CONSTRUCTORES_INICIALES = 1;
    final int GUARDIANES_INICIALES = 1;
    final int LEÑADORES_INICIALES = 1;
    final int TORRES_INICIALES = 1;
    final int PARCELAS_CULTIVO_INICIALES = 2;
    final int COMIDA_VEGETAL_INICIAL = 20;
    final int COMIDA_ANIMAL_INICIAL = 10;
    final int MADERA_INICIAL = 40;
    final int ARBOLES_INICIALES = 25;

    //Condiciones de victoria al final del maximo de ciclos
    final int minPersonajesVivosVictoria = 3; //3
    final int minResistenciaCercaVictoria = 1; //1
    final int minComidaTotal = 20; //20
    final int minTorresEnPie = 1; //1
    //Condiciones de derrota al final de cada ciclo
    final int minPersonajesVivosDerrota = 0; //0
    final int minResistenciaCercaDerrota = 0; //0
    final int minTorresEnPieDerrota = 0; //0
    final int minComidaDerrota = 0; //0
    final int maxTurnosMinComida = 3; //3

    //Atributos de la aldea
    private int cicloActual;
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
    private int turnosSinComida = 0;
    private ArrayList<ThreadMovimiento> hilosMovimiento = new ArrayList<ThreadMovimiento>();

    private VentanaPrincipal ventana;

    
    public Aldea() {
        ventana = new VentanaPrincipal(this);
        nombre = "Aldea";
        personajes = new ArrayList<Personaje>();
        torres = new ArrayList<TorreDefensa>();
        animalesActivos = new ArrayList<Animal>();
        parcelasCultivo = new ArrayList<Parcela>();
    }

    public Personaje crearPersonaje(String tipo) {
        //TODO Implementar lógica para crear un nuevo personaje
        switch (tipo.toLowerCase()) {
            case "agricultor":
                return new Agricultor("Agricultor" + (personajes.size() + 1), 0, this);
            case "cazador":
                return new Cazador("Cazador" + (personajes.size() + 1), 0, this);
            case "constructor":
                return new Constructor("Constructor" + (personajes.size() + 1), 0, this);
            case "guardián":
                return new Guardian("Guardián" + (personajes.size() + 1), 0, this);
            case "leñador":
                return new Lenador("Leñador" + (personajes.size() + 1), 0, this);
            default:
                throw new IllegalArgumentException("Tipo de personaje no válido: " + tipo);
        }
    }

    public void crearPersonajesIniciales() {
        for (int i = 0; i < AGRICULTORES_INICIALES; i++) {
            Personaje nuevoPersonaje = crearPersonaje("agricultor");
            personajes.add(nuevoPersonaje);
            hilosMovimiento.add(new ThreadMovimiento(nuevoPersonaje));
        }
        for (int i = 0; i < CAZADORES_INICIALES; i++) {
            Personaje nuevoPersonaje = crearPersonaje("cazador");
            personajes.add(nuevoPersonaje);
            hilosMovimiento.add(new ThreadMovimiento(nuevoPersonaje));
        }
        for (int i = 0; i < CONSTRUCTORES_INICIALES; i++) {
            Personaje nuevoPersonaje = crearPersonaje("constructor");
            personajes.add(nuevoPersonaje);
            hilosMovimiento.add(new ThreadMovimiento(nuevoPersonaje));
        }
        for (int i = 0; i < GUARDIANES_INICIALES; i++) {
            Personaje nuevoPersonaje = crearPersonaje("guardián");
            personajes.add(nuevoPersonaje);
            hilosMovimiento.add(new ThreadMovimiento(nuevoPersonaje));
        }
        for (int i = 0; i < LEÑADORES_INICIALES; i++) {
            Personaje nuevoPersonaje = crearPersonaje("leñador");
            personajes.add(nuevoPersonaje);
            hilosMovimiento.add(new ThreadMovimiento(nuevoPersonaje));
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

    public void crearAnimal(String tipo){
        //TODO Implementar lógica para crear un nuevo animal (lobo, oso o jabalí) y agregarlo a la lista de animales activos
        switch (tipo.toLowerCase()) {
            case "lobo":
                animalesActivos.add(new Lobo("Lobo" + (animalesActivos.size() + 1)));
                break;
            case "oso":
                animalesActivos.add(new Oso("Oso" + (animalesActivos.size() + 1)));
                break;
            case "jabali":
                animalesActivos.add(new Jabali("Jabali" + (animalesActivos.size() + 1)));
                break;
            default:
                throw new IllegalArgumentException("Tipo de animal no válido: " + tipo);
        }
    }

    public void verificarCrearAnimal(){
        if (cicloActual % 2 == 0) { // 50% de probabilidad de crear un lobo
            crearAnimal("lobo");
        }
        if (cicloActual % 3 == 0) { // 33% de probabilidad de crear un jabalí
            crearAnimal("jabali");
        }
        if (cicloActual % 5 == 0) { // 20% de probabilidad de crear un oso
            crearAnimal("oso");
        }
    }

    public void obtenerTorreCercana(Personaje personaje) {
        //TODO Implementar lógica para determinar el objetivo del personaje según su tipo y la situación actual de la aldea
        //Ejemplo: un agricultor podría dirigirse a una parcela de cultivo, un cazador a un animal activo, etc.
        int x = personaje.getLabelGUI().getLocation().x;
        int y = personaje.getLabelGUI().getLocation().y;
        int xObj = 9999;
        int yObj = 9999;
        for (TorreDefensa torre : torres) {
            int absx = Math.abs(x - torre.getLabelGUI().getLocation().x);
            int absy = Math.abs(y - torre.getLabelGUI().getLocation().y);
            int objx = Math.abs(x - xObj);
            int objy = Math.abs(y - yObj);
            if ((absx + absy) < (objx + objy)) {
                xObj = torre.getLabelGUI().getLocation().x;
                yObj = torre.getLabelGUI().getLocation().y;
            }
        }
        personaje.setObjetivo(new Point(xObj, yObj));
    }

    public void obtenerPersonajeCercano(Animal animal){
        //TODO Implementar lógica para determinar el objetivo del animal según la situación actual de la aldea
        //Ejemplo: un lobo podría dirigirse al personaje más cercano, un oso a la torre de defensa más cercana, etc.
        int x = animal.getLabelGUI().getLocation().x;
        int y = animal.getLabelGUI().getLocation().y;
        int xObj = 9999;
        int yObj = 9999;
        for (Personaje personaje : personajes) {
            int absx = Math.abs(x - personaje.getLabelGUI().getLocation().x);
            int absy = Math.abs(y - personaje.getLabelGUI().getLocation().y);
            int objx = Math.abs(x - xObj);
            int objy = Math.abs(y - yObj);
            if ((absx + absy) < (objx + objy)) {
                xObj = personaje.getLabelGUI().getLocation().x;
                yObj = personaje.getLabelGUI().getLocation().y;
            }
        }
        animal.setObjetivo(new Point(xObj, yObj));
    }

    public void iniciarThreadsMovimiento() {
        for (ThreadMovimiento thread : hilosMovimiento) {
            thread.start();
        }
    }
    public void detenerThreadsMovimiento() {
        for (ThreadMovimiento thread : hilosMovimiento) {
            thread.stopThread();
        }
    }

    public void iniciarSimulacion() {
        cicloActual = 1;
        //Crear personajes, estructuras, recursos y animales iniciales
        cercaPrincipal = new Cerca();
        //Crear personajes segun los valores iniciales y agregarlos a la lista de personajes
        
        crearPersonajesIniciales();
        //Crear torres de defensa segun los valores iniciales y agregarlos a la lista de torres
        for (int i = 0; i < TORRES_INICIALES; i++) {
            torres.add(crearTorreDefensa());
        }
        arbolesDisponibles = ARBOLES_INICIALES;
        maderaDisponible = MADERA_INICIAL;
        comidaVegetalDisponible = COMIDA_VEGETAL_INICIAL;
        comidaAnimalDisponible = COMIDA_ANIMAL_INICIAL;
        for (int i = 0; i < PARCELAS_CULTIVO_INICIALES; i++) {
            parcelasCultivo.add(crearParcelaCultivo());
        }
        

        Scanner scanner = new Scanner(System.in);
        //Ciclo principal de la simulación
        while (cicloActual <= MAX_CICLOS) {
            //TODO Realizar acciones segun el orden determinado
            ventana.actualizarRecursos();
            System.out.println("Ciclo " + cicloActual);
            System.out.println("Personajes: " + personajes);
            System.out.println("Torres: " + torres);
            System.out.println("Animales Activos: " + animalesActivos);
            System.out.println("Parcelas de Cultivo: " + parcelasCultivo);
            verificarCrearAnimal();
            if(verificarCondicionesDerrota()) {
                System.out.println("¡Derrota! La aldea ha caído.");
                break;
            }
            scanner.nextLine();
            cicloActual++;
        }
        if (verificarCondicionesVictoria()) {
            System.out.println("¡Victoria! La aldea ha resistido.");
        }
        scanner.close();
    }

    private boolean verificarCondicionesVictoria() {
        //TODO Verificar condiciones de victoria al final del maximo de ciclos
        return (personajes.size() >= minPersonajesVivosVictoria &&
                cercaPrincipal.getResistenciaActual() >= minResistenciaCercaVictoria &&
                (comidaVegetalDisponible + comidaAnimalDisponible) >= minComidaTotal &&
                torres.size() >= minTorresEnPie);
    }

    private boolean verificarCondicionesDerrota() {
        //TODO Verificar condiciones de derrota al final de cada ciclo
        if ((comidaVegetalDisponible + comidaAnimalDisponible) <= minComidaDerrota) {
            turnosSinComida++;
            System.out.println("¡Cuidado! La aldea no tiene comida disponible. Al llegar a  " + maxTurnosMinComida + " perderá la partida.\nTurnos sin comida: " + turnosSinComida);
        } else {
            turnosSinComida = 0; // Reiniciar el contador si hay comida disponible
        }
        return (personajes.size() <= minPersonajesVivosDerrota ||
                cercaPrincipal.getResistenciaActual() <= minResistenciaCercaDerrota &&
                torres.size() <= minTorresEnPieDerrota  ||
                turnosSinComida >= maxTurnosMinComida);
    }

    public boolean verificarCondicionesAgregarPersonaje(){
        //TODO Verificar condiciones para agregar un nuevo personaje segun su tipo
        /*tiene al menos 30 unidades de alimento vegetal o carne combinadas,  
        tiene al menos 20 unidades de madera disponibles,  
        tiene al menos 1 torre viva,  
        tiene la cerca con resistencia mayor a 50,  
        y la cantidad total de habitantes vivos es menor que 12
         */
        return ((comidaVegetalDisponible + comidaAnimalDisponible) >= 30 &&
                maderaDisponible >= 20 &&
                torres.size() >= 1 &&
                cercaPrincipal.getResistenciaActual() > 50 &&
                personajes.size() < MAX_HABITANTES);
    }

    public VentanaPrincipal getVentana() {
        return ventana;
    }

    public void setVentana(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }

    public int getCicloActual() {
        return cicloActual;
    }

    public int getMaderaDisponible() {
        return maderaDisponible;
    }

    public void setMaderaDisponible(int maderaDisponible) {
        this.maderaDisponible = maderaDisponible;
    }

    public int getComidaVegetalDisponible() {
        return comidaVegetalDisponible;
    }

    public void setComidaVegetalDisponible(int comidaVegetalDisponible) {
        this.comidaVegetalDisponible = comidaVegetalDisponible;
    }

    public int getComidaAnimalDisponible() {
        return comidaAnimalDisponible;
    }

    public void setComidaAnimalDisponible(int comidaAnimalDisponible) {
        this.comidaAnimalDisponible = comidaAnimalDisponible;
    }

    public Cerca getCercaPrincipal() {
        return cercaPrincipal;
    }

    public void setCercaPrincipal(Cerca cercaPrincipal) {
        this.cercaPrincipal = cercaPrincipal;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    public ArrayList<TorreDefensa> getTorres() {
        return torres;
    }

    public void setTorres(ArrayList<TorreDefensa> torres) {
        this.torres = torres;
    }

    public ArrayList<Animal> getAnimalesActivos() {
        return animalesActivos;
    }

    public void setAnimalesActivos(ArrayList<Animal> animalesActivos) {
        this.animalesActivos = animalesActivos;
    }

    public ArrayList<Parcela> getParcelasCultivo() {
        return parcelasCultivo;
    }

    public void setParcelasCultivo(ArrayList<Parcela> parcelasCultivo) {
        this.parcelasCultivo = parcelasCultivo;
    }

    public int getArbolesDisponibles() {
        return arbolesDisponibles;
    }

    public void setArbolesDisponibles(int arbolesDisponibles) {
        this.arbolesDisponibles = arbolesDisponibles;
    }
    public int getTurnosSinComida() {
        return turnosSinComida;
    }

    public void setTurnosSinComida(int turnosSinComida) {
        this.turnosSinComida = turnosSinComida;
    }




    
}
