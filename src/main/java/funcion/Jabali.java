package funcion;

public class Jabali extends Animal {

    public Jabali(String nombre) {
        super(nombre, "Jabali", 60, 15);
    }

    @Override
    public void atacar() {
        // TODO realiza ataque específico y cambia animación a "atacando"
        throw new UnsupportedOperationException("Unimplemented method 'atacar'");
    }


}
