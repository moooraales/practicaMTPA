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

public class MenuInicial extends JFrame implements Runnable{
    JLabel etiqueta;
    FlowLayout layout;
    JFrame ventana;
    JButton boton;
    ActionListener escuchador;

    
    @Override
    public void run() {
        escuchador = (ActionEvent ae) -> {
            try {
                Servidor servidor = Servidor.getServidor();
                JButton boton1 = (JButton) ae.getSource();
                String nombreBoton = boton1.getText();
                switch (nombreBoton) {
                    case "Login":
                        EventQueue.invokeLater(new Cliente.Interfaces.Menus.Login());
                        this.setVisible(false);
                        break;
                    case "Registro":
                        EventQueue.invokeLater( new Cliente.Interfaces.Menus.Registro());
                        this.setVisible(false);
                        break;
                    case "Salir":
                        this.setVisible(false);
                        System.exit(0);
                        break;
                }
            } catch (IOException ex) {
                MostrarMensaje.mostrarError("Imposible conectar con el servidor");
                Logger.getLogger(MenuInicial.class.getName()).log(Level.SEVERE, null, ex);
                this.setVisible(false);
            }
        };
        ajustarVentana();
        iniciarComponentes();
        iniciarVentana();
    }
  
    public void ajustarVentana(){
        //Frame
        ventana = new JFrame("Bienvenido");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout
        layout = new FlowLayout(FlowLayout.CENTER, 20, 20);
        this.setLayout(layout);
    }
   
    public void iniciarComponentes(){
        //Botones
        String titulosBotones[] = {"Login", "Registro", "Salir"};
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
   
    public void iniciarVentana(){
        this.setSize(400, 200);
        this.setVisible(true);
    }
}
