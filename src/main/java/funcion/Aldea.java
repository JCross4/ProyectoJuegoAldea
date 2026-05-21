package funcion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JLabel;

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
    final int TORRES_INICIALES = 2;
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
    private boolean personajesListos;
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
    private ArrayList<JLabel> labelArboles = new ArrayList<JLabel>();
    private int turnosSinComida = 0;
    private ArrayList<ThreadMovimiento> hilosMovimiento = new ArrayList<ThreadMovimiento>();
    private ArrayList<ThreadMovimientoAnimal> hilosMovimientoAnimal = new ArrayList<ThreadMovimientoAnimal>();

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
                return new Agricultor("Agricultor" + (personajes.size() + 1), this);
            case "cazador":
                return new Cazador("Cazador" + (personajes.size() + 1), this);
            case "constructor":
                return new Constructor("Constructor" + (personajes.size() + 1), this);
            case "guardián":
                return new Guardian("Guardián" + (personajes.size() + 1), this);
            case "leñador":
                return new Lenador("Leñador" + (personajes.size() + 1), this);
            default:
                throw new IllegalArgumentException("Tipo de personaje no válido: " + tipo);
        }
    }

    public void eliminarPersonaje(Personaje personaje) {
        //TODO Implementar lógica para eliminar un personaje de la aldea, por ejemplo, cuando muere o es eliminado por un animal
        personajes.remove(personaje);
        ventana.eliminarLabel(personaje.getLabelGUI());
        detenerThreadMovimiento(personaje);
    }

    public void eliminarArbol(JLabel labelArbol) {
        //TODO Implementar lógica para eliminar un árbol de la aldea, por ejemplo, cuando es talado por un leñador
        labelArboles.remove(labelArbol);
        ventana.eliminarLabel(labelArbol);
    }

    public void eliminarAnimal(Animal animal){
        animalesActivos.remove(animal);
        ventana.eliminarLabel(animal.getLabelGUI());
        detenerThreadMovimientoAnimal(animal);
    }

    public void detenerThreadMovimiento(Personaje personaje) {
        for (ThreadMovimiento thread : hilosMovimiento) {
            if (thread.getPersonaje() == personaje) {
                thread.stopThread();
                break;
            }
        }
    }

    public void detenerThreadMovimientoAnimal(Animal animal) {
        for (ThreadMovimientoAnimal thread : hilosMovimientoAnimal) {
            if (thread.getAnimal() == animal) {
                thread.stopThread();
                break;
            }
        }
     }

    public void actualizarLabelsParcelas(){
        for (Parcela parcela : getParcelasCultivo()){
            ventana.actualizarLabel(parcela.getLabelGUI(), parcela.getNombre().substring(0, 1) + parcela.getNombre().substring(parcela.getNombre().length() - 1) + " T: " + parcela.getCiclosParaCosechar());
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
        return new TorreDefensa("Torre de Defensa" + (torres.size() + 1), this);
    }

    public Parcela crearParcelaCultivo() {
        //TODO Implementar lógica para crear una nueva parcela de cultivo
        return new Parcela("Parcela de Cultivo" + (parcelasCultivo.size() + 1), this);
    }

    public Animal crearAnimal(String tipo){
        //TODO Implementar lógica para crear un nuevo animal (lobo, oso o jabalí) y agregarlo a la lista de animales activos
        switch (tipo.toLowerCase()) {
            case "lobo":
                return (new Lobo("Lobo" + (animalesActivos.size() + 1), this));
            case "oso":
                return (new Oso("Oso" + (animalesActivos.size() + 1), this));
            case "jabali":
                return(new Jabali("Jabali" + (animalesActivos.size() + 1), this));
            default:
                throw new IllegalArgumentException("Tipo de animal no válido: " + tipo);
        }
    }

    public void verificarCrearAnimal(){
        if (cicloActual % 2 == 0) { // 50% de probabilidad de crear un lobo
            Animal nuevoAnimal = crearAnimal("lobo");
            ThreadMovimientoAnimal threadAnimal = new ThreadMovimientoAnimal(nuevoAnimal);
            hilosMovimientoAnimal.add(threadAnimal);
            animalesActivos.add(nuevoAnimal);
            ventana.crearLabelAnimal(nuevoAnimal);
            threadAnimal.start();
        }
        if (cicloActual % 3 == 0) { // 33% de probabilidad de crear un jabalí
            Animal nuevoAnimal = crearAnimal("jabali");
            ThreadMovimientoAnimal threadAnimal = new ThreadMovimientoAnimal(nuevoAnimal);
            hilosMovimientoAnimal.add(threadAnimal);
            animalesActivos.add(nuevoAnimal);
            ventana.crearLabelAnimal(nuevoAnimal);
            threadAnimal.start();
        }
        if (cicloActual % 5 == 0) { // 20% de probabilidad de crear un oso
            Animal nuevoAnimal =    crearAnimal("oso");
            ThreadMovimientoAnimal threadAnimal = new ThreadMovimientoAnimal(nuevoAnimal);
            hilosMovimientoAnimal.add(threadAnimal);
            animalesActivos.add(nuevoAnimal);
            ventana.crearLabelAnimal(nuevoAnimal);
            threadAnimal.start();
        }
    }

    public Point obtenerTorreCercana(Point punto) {
        //TODO Implementar lógica para determinar el objetivo del personaje según su tipo y la situación actual de la aldea
        //Ejemplo: un agricultor podría dirigirse a una parcela de cultivo, un cazador a un animal activo, etc.
        int x = punto.x;
        int y = punto.y;
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
        return new Point(xObj, yObj);
    }

    public TorreDefensa obtenerTorreMenosVida() {
        TorreDefensa torreMenosVida = null;
        int vidaMinima = Integer.MAX_VALUE;
        for (TorreDefensa torre : torres) {
            if (torre.getResistenciaActual() < vidaMinima && !torre.estaDestruida()) {
                vidaMinima = torre.getResistenciaActual();
                torreMenosVida = torre;
            }
        }
        return torreMenosVida;
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

    public Personaje obtenerPersonajeMenosVida() {
        Personaje personajeMenosVida = null;
        int vidaMinima = Integer.MAX_VALUE;
        for (Personaje personaje : personajes) {
            if (personaje.getSalud() < vidaMinima && personaje.estaVivo()) {
                vidaMinima = personaje.getSalud();
                personajeMenosVida = personaje;
            }
        }
        return personajeMenosVida;
    }

    public Animal obtenerAnimalCercano(Point punto){
        //TODO Implementar lógica para determinar el objetivo del personaje según su tipo y la situación actual de la aldea
        //Ejemplo: un agricultor podría dirigirse a una parcela de cultivo, un cazador a un animal activo, etc.
        if (animalesActivos.size() == 0) {
            return null; // Podría ser una posición específica para descansar o alguna otra lógica
        }
        Animal animalCercano = null;
        int x = punto.x;
        int y = punto.y;
        int xObj = 9999;
        int yObj = 9999;
        for (Animal animal : animalesActivos) {
            int absx = Math.abs(x - animal.getLabelGUI().getLocation().x);
            int absy = Math.abs(y - animal.getLabelGUI().getLocation().y);
            int objx = Math.abs(x - xObj);
            int objy = Math.abs(y - yObj);
            if ((absx + absy) < (objx + objy)) {
                xObj = animal.getLabelGUI().getLocation().x;
                yObj = animal.getLabelGUI().getLocation().y;
                animalCercano = animal;
            }
        }
        return animalCercano;
    }

    public Animal obtenerAnimalMasFuerte(){
        //TODO Implementar lógica para determinar el objetivo del personaje según su tipo y la situación actual de la aldea
        //Ejemplo: un agricultor podría dirigirse a una parcela de cultivo, un cazador a un animal activo, etc.
        if (animalesActivos.size() == 0) {
            return null; // Podría ser una posición específica para descansar o alguna otra lógica
        }
        Animal animalMasFuerte = null;
        int maxFuerza = -1;
        for (Animal animal : animalesActivos) {
            if (animal.getFuerzaAtaque() > maxFuerza) {
                maxFuerza = animal.getFuerzaAtaque();
                animalMasFuerte = animal;
            }
        }
        return animalMasFuerte;
    }

    public Animal obtenerAnimalMasVida(){
        if (animalesActivos.size() == 0) {
            return null; // Podría ser una posición específica para descansar o alguna otra lógica
        }
        Animal animalMasVida = null;
        int maxVida = -1;
        for (Animal animal : animalesActivos) {
            if (animal.getVida() > maxVida) {
                maxVida = animal.getFuerzaAtaque();
                animalMasVida = animal;
            }
        }
        return animalMasVida;
    }

    public Point obtenerArbolCercano(Lenador lenador){
        //TODO Implementar lógica para determinar el objetivo del personaje según su tipo y la situación actual de la aldea
        //Ejemplo: un agricultor podría dirigirse a una parcela de cultivo, un cazador a un animal activo, etc.
        if (arbolesDisponibles == 0) {
            return null; // Podría ser una posición específica para descansar o alguna otra lógica
        }
        
        int x = lenador.getLabelGUI().getLocation().x;
        int y = lenador.getLabelGUI().getLocation().y;
        int xObj = 9999;
        int yObj = 9999;
        for (JLabel labelArbol : labelArboles) {
            int absx = Math.abs(x - labelArbol.getLocation().x);
            int absy = Math.abs(y - labelArbol.getLocation().y);
            int objx = Math.abs(x - xObj);
            int objy = Math.abs(y - yObj);
            if ((absx + absy) < (objx + objy)) {
                xObj = labelArbol.getLocation().x;
                yObj = labelArbol.getLocation().y;
                lenador.setArbolObjetivo(labelArbol);

            }
        }
        return new Point(xObj, yObj);
    }

    public Point obtenerPuntoPatrulla(Point punto){
        //TODO Implementar lógica para determinar el objetivo del personaje según su tipo y la situación actual de la aldea
        //Ejemplo: un agricultor podría dirigirse a una parcela de cultivo, un cazador a un animal activo, etc.
        //Podría ser una ruta predefinida o alguna otra lógica
        return new Point(ventana.getLABEL_SIZE() * 4, ventana.getLABEL_SIZE() * 7); // Ejemplo de punto de patrulla
    }

    public TorreDefensa obtenerTorreDañada(){
        for (TorreDefensa torre : this.getTorres()) {
            if (torre.getResistenciaActual() < torre.getResistenciaMaxima()) {
                // Objetivo: reparar torre
                return torre;
            }
        }
        return null;
    }

    public boolean checkPersonajesListos(){
        while (!isPersonajesListos()){
        for (ThreadMovimiento thread : hilosMovimiento){
            if (!thread.isTurnoFinalizado()) {
                setPersonajesListos(false);
                break;
            }
            setPersonajesListos(true);
        }
        }
        return true;
    }

    public void iniciarThreadsMovimiento() {
        for (ThreadMovimiento thread : hilosMovimiento) {
            thread.start();
        }
    }

    public void reanudarThreadsMovimiento() {
        for (ThreadMovimiento thread : hilosMovimiento) {
            thread.resumeThread();
        }
    }

    public void detenerThreadsMovimiento() {
        for (ThreadMovimiento thread : hilosMovimiento) {
            thread.stopThread();
        }
    }

    public void iniciarSimulacion() {
        cicloActual = 0;
        //Crear personajes, estructuras, recursos y animales iniciales
        cercaPrincipal = new Cerca(this);
        //Crear personajes segun los valores iniciales y agregarlos a la lista de personajes
        ventana.deshabilitarAgregarPersonaje();
        crearPersonajesIniciales();
        //Crear torres de defensa segun los valores iniciales y agregarlos a la lista de torres
        for (int i = 0; i < TORRES_INICIALES; i++) {
            torres.add(crearTorreDefensa());
        }
        arbolesDisponibles = ARBOLES_INICIALES;
        ArrayList<Point> posicionesArboles = new ArrayList<Point>();
        for (int i = 7; i < 14; i++) {
            for (int j = 1; j < 5; j++) {
                posicionesArboles.add(new Point(i, j));
            }
        }
        for (int i = 0; i < ARBOLES_INICIALES; i++) {
            labelArboles.add(ventana.crearLabelArbol(posicionesArboles));
            posicionesArboles.remove(0);
        }
        maderaDisponible = MADERA_INICIAL;
        comidaVegetalDisponible = COMIDA_VEGETAL_INICIAL;
        comidaAnimalDisponible = COMIDA_ANIMAL_INICIAL;
        for (int i = 0; i < PARCELAS_CULTIVO_INICIALES; i++) {
            parcelasCultivo.add(crearParcelaCultivo());
        }
        ventana.actualizarRecursos();
        ventana.crearLabelsIniciales();
        iniciarThreadsMovimiento();
    }

    public void simularCiclo() {
        
        //TODO Implementar lógica para simular un ciclo completo de la aldea, incluyendo las acciones de personajes, animales y estructuras, así como la verificación de condiciones de victoria y derrota
        //Orden de acciones
        /*Personajes
        Constructores. - Agricultores. - Leñadores. - Cazadores. - Guardiánes
        
        --Finalizan los personajes
        Actualizar cultivos sembrados.  
        Generar animales.

        --
        Disparos de torres o personajes de defensa.  
        Ataque de animales sobrevivientes.  
        Alimentar personajes.  
        Actualizar salud y energía.  
        Verificar condiciones para adquirir un nuevo habitante.  
        Si el usuario decide adquirirlo y existen los recursos, registrarlo.  
        Eliminar animales muertos.  
        Mostrar resumen del ciclo
        */
        //TODO  verificar personajes han completado sus acciones?
        // Disparos de torres / personajes defensa

        //Ciclo principal de la simulación
        if (cicloActual <= MAX_CICLOS) {
            //TODO Realizar acciones segun el orden determinado
            cicloActual++;
            ventana.agregarLog("\nCiclo Actual:" + cicloActual);
            ventana.actualizarRecursos();
            setPersonajesListos(false);
            for (Personaje personaje : personajes){ //Los personajes realizan sus acciones y entonces cada uno determina su objetivo
                if (personaje.estaVivo()) {
                    personaje.determinarObjetivo();
                    ventana.agregarLog(personaje.getNombre() + " se dirige a " + personaje.getObjetivo());
                    
                }
            }
            //TODO: Esperar a que todos los personajes hayan completado su accion
            //Variable boolean personajesListos ?
            //checkPersonajesListos();
            //System.out.println("Los personajes han finalizado");


            //Actualizar parcelas cultivadas
            for (Parcela parcela : parcelasCultivo) {
                parcela.cuidar();
            }
            actualizarLabelsParcelas();
            //Generar animales
            verificarCrearAnimal();
            //Disparos de torres o personajes de defensa
            for (TorreDefensa torre : getTorres()){
                if (!torre.isDestruida()){
                    torre.determinarObjetivo();
                    torre.realizarAtaque();
                }
            }

            //Ataque de animales sobrevivientes
            for (Animal animal : animalesActivos){
                if (animal.estaVivo()){
                    animal.determinarObjetivo();
                    animal.atacar();
                }
            }



            if(verificarCondicionesDerrota()) {
                ventana.agregarLog("¡Derrota! La aldea ha caído.");
            }
            
        }
        else if (verificarCondicionesVictoria()) {
            ventana.agregarLog("¡Victoria! La aldea ha resistido.");
        }
        if (verificarCondicionesAgregarPersonaje()) {
            ventana.habilitarAgregarPersonaje();
        }
    }

    private boolean verificarCondicionesVictoria() {
        //TODO Verificar condiciones de victoria al final del maximo de ciclos
        return (personajes.size() >= minPersonajesVivosVictoria &&
                cercaPrincipal.getResistenciaActual() >= minResistenciaCercaVictoria &&
                (comidaVegetalDisponible + comidaAnimalDisponible) >= minComidaTotal &&
                torres.size() >= minTorresEnPie);
    }

    private boolean verificarCondicionesDerrota() {
        //Verificar condiciones de derrota al final de cada ciclo
        if ((comidaVegetalDisponible + comidaAnimalDisponible) <= minComidaDerrota) {
            turnosSinComida++;
            ventana.agregarLog("¡Cuidado! La aldea no tiene comida disponible. Al llegar a  " + maxTurnosMinComida + " perderá la partida.\nTurnos sin comida: " + turnosSinComida);
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

    public void agregarPersonaje(String tipo) {
            if (tipo.equalsIgnoreCase("Automático"))
                {
                    //Determinar el tipo de personaje a agregar según la cantidad de cada tipo de personaje vivo en la aldea
                    Map<String, Integer> counts = new HashMap<>();
                    for (Personaje personaje : personajes) {
                            if (personaje instanceof Agricultor) {
                                counts.put("Agricultor", counts.getOrDefault("Agricultor", 0) + 1);
                            } else if (personaje instanceof Lenador) {
                                counts.put("Leñador", counts.getOrDefault("Leñador", 0) + 1);
                            } else if (personaje instanceof Constructor) {
                                counts.put("Constructor", counts.getOrDefault("Constructor", 0) + 1);
                            } else if (personaje instanceof Guardian) {
                                counts.put("Guardián", counts.getOrDefault("Guardián", 0) + 1);
                            } else if (personaje instanceof Cazador) {
                                counts.put("Cazador", counts.getOrDefault("Cazador", 0) + 1);
                            }
                        }
                    List<String> types = Arrays.asList("Agricultor", "Leñador", "Constructor", "Guardián", "Cazador");
                    for (String t : types) {
                        if (counts.getOrDefault(t, 0) < 2) {
                            tipo = t;
                            break;
                        }
                    }
                    if (tipo.equals("Automático")) { // if none found, default
                        tipo = "Agricultor";
                    }
                }
            Personaje nuevoPersonaje = crearPersonaje(tipo);
            personajes.add(nuevoPersonaje);
            ThreadMovimiento nuevoHilo = new ThreadMovimiento(nuevoPersonaje);
            hilosMovimiento.add(nuevoHilo);
            ventana.crearLabelPersonaje(nuevoPersonaje);
            maderaDisponible-=8;
            if (comidaVegetalDisponible>=10) {
                comidaVegetalDisponible-=10;
                }
            else {
                comidaAnimalDisponible-=(10-comidaVegetalDisponible);
                comidaVegetalDisponible=0;
            }
            nuevoHilo.start();
            ventana.actualizarRecursos();
            ventana.deshabilitarAgregarPersonaje();
    }

    public void agregarTorre() {
        TorreDefensa nuevaTorre = crearTorreDefensa();
        torres.add(nuevaTorre);
        ventana.crearLabelTorre(nuevaTorre);
        ventana.actualizarRecursos();
    }

    public void agregarAnimal(){
        String[] tiposAnimal = {"lobo", "jabali", "oso"};
        int indice = (int) (Math.random()*2.5);
        Animal nuevoAnimal = crearAnimal(tiposAnimal[indice]);
        ThreadMovimientoAnimal nuevoThread = new ThreadMovimientoAnimal(nuevoAnimal);
        hilosMovimientoAnimal.add(nuevoThread);
        getAnimalesActivos().add(nuevoAnimal);
        getVentana().crearLabelAnimal(nuevoAnimal);
        nuevoThread.start();
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

    public ArrayList<JLabel> getLabelArboles() {
        return labelArboles;
    }

    public void setLabelArboles(ArrayList<JLabel> labelArboles) {
        this.labelArboles = labelArboles;
    }

    public boolean isPersonajesListos() {
        return personajesListos;
    }

    public void setPersonajesListos(boolean personajesListos) {
        this.personajesListos = personajesListos;
    }




    
}
