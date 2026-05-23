package funcion;

public class Cerca extends Estructura{
    private boolean guardianVivo = true;

    public Cerca(Aldea aldea) {
        super("Cerca Principal", 100, 100, aldea);
        setPathDefault("src/Recursos/istockphoto-463562079-1024x1024.jpg");
    }

    public void recibirDaño(int daño) {
        if (isGuardianVivo()){
            daño -= 5; // El guardián reduce el daño recibido
        }
        super.recibirDaño(daño);
    }

    public boolean isGuardianVivo() {
        return guardianVivo;
    }

    public void setGuardianVivo(boolean guardianVivo) {
        this.guardianVivo = guardianVivo;
    }

    

}
