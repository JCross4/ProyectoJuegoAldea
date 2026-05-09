package funcion;

public class Oso extends Animal {

    
    public Oso(String nombre) {
        super(nombre, "Oso", 100, 25);
    }

    @Override
    public void atacar() {
        // TODO realiza ataque específico y cambia animación a "atacando"
        throw new UnsupportedOperationException("Unimplemented method 'atacar'");
    }

}
    