package funcion;

public class Constructor extends Personaje {

    public Constructor(String nombre, int posicion, Aldea aldea) {
        super(nombre, posicion, "constructor", aldea);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void mover() {
        // TODO desplazar elemento y cambiar animación a "caminando"
        throw new UnsupportedOperationException("Unimplemented method 'mover'");
    }

    @Override
    public void realizarAccion() {
        // TODO realiza accion específica
        throw new UnsupportedOperationException("Unimplemented method 'realizarAccion'");
    }

    @Override
    public void comer() {
        // TODO recupera energia y reduce comida disponible
        throw new UnsupportedOperationException("Unimplemented method 'comer'");
    }

    @Override
    public void descansar() {
        // TODO recupera energia y cambia animación a "descansando"
        throw new UnsupportedOperationException("Unimplemented method 'descansar'");
    }

    @Override
    public void determinarObjetivo() {
        // TODO Auto-generated method stub
        getAldea().obtenerTorreCercana(this);
        System.out.println("Constructor " + getNombre() + " determina objetivo: " + getObjetivo());
        throw new UnsupportedOperationException("Unimplemented method 'determinarObjetivo'");
    }


}
