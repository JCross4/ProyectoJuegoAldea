package funcion;

import java.awt.Point;

public class ThreadMovimientoAnimal extends Thread{
    private Animal animal;
    private boolean running;
    private boolean turnoFinalizado;

    public ThreadMovimientoAnimal(Animal animal) {
        this.animal = animal;
        this.running = true;
        this.turnoFinalizado = true;
    }

    @Override
    public void run() {
        int currentX;
        int currentY;
        Point puntoOriginal = animal.getLabelGUI().getLocation();
        while (running) {
            try {
                if (animal.getLabelGUI().getLocation().equals(animal.getObjetivo()) && !animal.getObjetivo().equals(puntoOriginal)) {
                    // El personaje ha llegado a su objetivo, puede realizar una acción o determinar un nuevo objetivo
                    animal.atacar();
                    System.out.println(animal.getNombre() + " ha llegado a su objetivo en (" + animal.getObjetivo().x + ", " + animal.getObjetivo().y + ")");
                    Thread.sleep(800); // Simula el tiempo que tarda en realizar la acción
                    //Volver a posición inicial
                    animal.setAccionActual("ninguna");
                    animal.setObjetivo(puntoOriginal);
                    turnoFinalizado = true;
                }
                else{
                turnoFinalizado = false;
                Thread.sleep(400); // Simula el tiempo entre movimientos
                currentX = animal.getLabelGUI().getX();
                currentY = animal.getLabelGUI().getY();
                if (currentX < animal.getObjetivo().x) {
                    currentX += animal.getAldea().getVentana().getLABEL_SIZE(); // Mueve a la derecha
                } else if (currentX > animal.getObjetivo().x) {
                    currentX -= animal.getAldea().getVentana().getLABEL_SIZE(); // Mueve a la izquierda
                } else if (currentY < animal.getObjetivo().y) {
                    currentY += animal.getAldea().getVentana().getLABEL_SIZE(); // Mueve hacia abajo
                } else if (currentY > animal.getObjetivo().y) {
                    currentY -= animal.getAldea().getVentana().getLABEL_SIZE(); // Mueve hacia arriba
                }
                animal.getAldea().getVentana().moverAnimal(animal, currentX, currentY); // El personaje se mueve hacia su objetivo
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void stopThread() {
        running = false;
    }

    public void resumeThread() {
        running = true;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public boolean isTurnoFinalizado() {
        return turnoFinalizado;
    }

    public void setTurnoFinalizado(boolean turnoFinalizado) {
        this.turnoFinalizado = turnoFinalizado;
    }

}
