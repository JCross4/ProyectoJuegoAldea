package gui;

import funcion.Aldea;

public class VentanaPrincipal extends javax.swing.JFrame {
    
    private Aldea aldea;

    public VentanaPrincipal(Aldea aldea) {
        this.aldea = aldea;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Defensa de la Aldea");
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar la ventana

        // Aquí puedes agregar componentes adicionales como botones, paneles, etc.

    }

}
