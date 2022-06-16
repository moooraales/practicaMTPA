package Cliente.Interfaces.Menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuOpcionJuego extends JFrame implements Runnable{
    JLabel etiqueta;
    FlowLayout layout;
    JFrame ventana;
    JButton boton;
    ActionListener escuchador;
    /**
    * Comprueba cual es el boton seleccionado y realiza la accion del mismo:
    * Jugar contra otro jugador, Listar Jugadores disponibles, 
    * Ver ranking, Ver retos pendientes, Ver retos activos,
    * Aceptar reto, Rechazar reto
    */
    @Override
    public void run() {
        // FRAME
        ventana = new JFrame("Bienvenido");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layout = new FlowLayout(FlowLayout.CENTER, 20, 20);
        ventana.setLayout(layout);

        // DEFINIMOS ESCUCHADOR DE EVENTOS PARA CUANDO HAGAMOS CLICK SOBRE LOS BOTONES
        escuchador = (ActionEvent ae) -> {
            JButton boton1 = (JButton) ae.getSource();
            String nombreBoton = boton1.getText();
            switch (nombreBoton) {
                case "Jugar contra otro jugador":
                    EventQueue.invokeLater(new Cliente.Interfaces.Menus.RetarJugador());
                    this.setVisible(false);
                    break;
                    
                case "Listar Jugadores disponibles":
                    EventQueue.invokeLater( new Cliente.Interfaces.Menus.ListarUsuariosActivos());
                    this.setVisible(false);
                    break;
                    /*
                case "Ver ranking":
                    EventQueue.invokeLater( new Cliente.Interfaces.VerRanking());
                    this.setVisible(false);
                    break;
                    */
                case "Ver retos pendientes":
                    EventQueue.invokeLater( new Cliente.Interfaces.Menus.VerRetosPendientes());
                    this.setVisible(false);
                    break;
                    /*
                case "Ver retos activos":
                    EventQueue.invokeLater( new Cliente.Interfaces.VerRetosAceptados());
                    this.setVisible(false);
                    break;
                    */
                case "Aceptar reto":
                    EventQueue.invokeLater( new Cliente.Interfaces.Menus.AceptarReto());
                    this.setVisible(false);
                    break;
                case "Rechazar reto":
                    EventQueue.invokeLater( new Cliente.Interfaces.Menus.RechazarReto());
                    this.setVisible(false);
                    break;
                    
            }
        };
        ajustarVentana();
        iniciarComponentes();
        iniciarVentana();
    }
    /**
    * Ajustaria el frame y el flow layaut
    */
    public void ajustarVentana(){
        //Frame
        ventana = new JFrame("Bienvenido");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout
        layout = new FlowLayout(FlowLayout.CENTER, 20, 20);
        this.setLayout(layout);
    }
    /**
    * Iniciaria los componentes de la ventana
    */
    public void iniciarComponentes(){
        //Botones
        String titulosBotones[] = {"Jugar contra otro jugador", 
            "Listar Jugadores disponibles", "Ver ranking", 
            "Ver retos pendientes", "Ver retos activos",
            "Aceptar reto", "Rechazar reto"};
        for (String tituloBoton : titulosBotones) {
            boton = new JButton(tituloBoton);
            boton.setSize(new Dimension(80, 100));
            this.add(boton);
            boton.addActionListener(escuchador);

        }

        //Etiquetas
        etiqueta = new JLabel("Elija una de estas " + titulosBotones.length + " opciones.");
        etiqueta.setForeground(Color.BLUE);
        etiqueta.setFont(new Font("Serif", Font.BOLD, 12));
        this.add(etiqueta);
    }
    /**
    * Iniciaria la ventana
    */
    public void iniciarVentana(){
        this.setSize(600, 300);
        this.setVisible(true);
    }
}
