package funcion;

public class Cerca extends Estructura{
    private boolean guardianVivo;

    public Cerca(Aldea aldea) {
        super("Cerca Principal", 100, 100, aldea);
    }

    public boolean isGuardianVivo() {
        return guardianVivo;
    }

    public void setGuardianVivo(boolean guardianVivo) {
        this.guardianVivo = guardianVivo;
    }

    

}
