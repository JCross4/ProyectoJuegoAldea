package funcion;

public class Lobo extends Animal    {


    
    public Lobo(String nombre) {
        super(nombre, "Lobo", 40, 10);
    }

    @Override
    public void atacar() {
        // TODO realiza ataque específico y cambia animación a "atacando"
        throw new UnsupportedOperationException("Unimplemented method 'atacar'");
    }

}
