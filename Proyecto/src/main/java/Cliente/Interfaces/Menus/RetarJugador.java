package Cliente.Interfaces.Menus;

import Cliente.Servidor;
import Cliente.Interfaces.MostrarMensaje;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RetarJugador extends JFrame implements Runnable {
    String jugador;
    Servidor servidor;
    JLabel etiqueta;
    JTextField usuario;
    FlowLayout layout;
    JFrame ventana;
    JButton boton;
    ActionListener escuchador;
    /**
    * Comprueba cual es el boton seleccionado y realiza la accion del mismo
    * Jugar, volver
    * @author Luis Enrique Muñoz
    * @since 1.0
    * @version 1.0
    */
    @Override
    public void run() {
        escuchador = (ActionEvent ae) -> {
            try {
                servidor = Servidor.getServidor();
                JButton boton1 = (JButton) ae.getSource();
                String nombreBoton = boton1.getText();
                switch (nombreBoton) {
                    case "Jugar":
                        String user = usuario.getText();
                        String [] respuesta = Cliente.MenuOpcionJuego.enviarReto(user);
                        if(respuesta[0].equals("false")){
                            MostrarMensaje.mostrarError(respuesta[1]);
                        }else{
                            MostrarMensaje.mostrarMensaje("Reto enviado.\n "
                                    + "Espere que el otro jugador acepte para jugar");
                            this.setVisible(false);
                        }
                        break;
                    case "Volver":
                        this.setVisible(false);
                        EventQueue.invokeLater(new Cliente.Interfaces.Menus.MenuOpcionJuego());
                        break;
                }
            }catch (IOException ex) {
                Logger.getLogger(RetarJugador.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        ajustarVentana();
        iniciarComponentes();
        iniciarVentana();
    } 
    /**
    * Ajustaria el frame y el flow layaut
    * @author Luis Enrique Muñoz
    * @since 1.0
    * @version 1.0
    */
    public void ajustarVentana(){
        //Frame
        ventana = new JFrame(this.jugador);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout
        layout = new FlowLayout(FlowLayout.CENTER, 20, 20);
        this.setLayout(layout);
    }
    /**
    * Iniciaria los componentes de la ventana
    * @author Luis Enrique Muñoz
    * @since 1.0
    * @version 1.0
    */
    public void iniciarComponentes(){
        //Texto
        usuario = new JTextField("#(nombre de usuario)");
        this.add(usuario);
        
        //Botones
        String titulosBotones[] = {"Jugar", "Volver"};

        for (String tituloBoton : titulosBotones) {
            boton = new JButton(tituloBoton);
            boton.setSize(new Dimension(80, 100));
            this.add(boton);
            boton.addActionListener(escuchador);
        }
        //Etiqueta
        etiqueta = new JLabel("Elija una de estas " + titulosBotones.length + " opciones.");
        etiqueta.setForeground(Color.BLUE);
        etiqueta.setFont(new Font("Serif", Font.BOLD, 12));
        this.add(etiqueta);
        
        JLabel etiqueta2 = new JLabel(this.jugador);
        etiqueta2.setForeground(Color.BLUE);
        etiqueta2.setFont(new Font("Serif", Font.BOLD, 12));
        this.add(etiqueta2);
    }
    /**
    * Iniciaria la ventana
    * @author Luis Enrique Muñoz
    * @since 1.0
    * @version 1.0
    */
    public void iniciarVentana(){
        this.setSize(400, 200);
        this.setVisible(true);
    }
}
