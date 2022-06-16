package Cliente.Interfaces.Menus;

import Cliente.Servidor;
import Cliente.Interfaces.MostrarMensaje;
import Cliente.OperacionString;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registro extends JFrame implements Runnable {
    JLabel etiqueta, usr, psw;
    JTextField usuario;
    JPasswordField password;
    FlowLayout layout;
    JFrame ventana;
    JButton boton;
    ActionListener escuchador;
    /**
    * Comprueba cual es el boton seleccionado y realiza la accion del mismo
    * Registro
    * @author Luis Enrique Muñoz
    * @since 1.0
    * @version 1.0
    */
    @Override
    public void run() {
        escuchador = (ActionEvent ae) -> {
            try {
                Boolean ok;
                String [] cadena;
                Servidor servidor = Servidor.getServidor();
                JButton boton1 = (JButton) ae.getSource();
                String user = usuario.getText();
                String psw1 = password.getText();
                String nombreBoton = boton1.getText();
                if ("Registro".equals(nombreBoton)) {
                    ok = OperacionString.validarNombre(user);
                    if (ok == true) {
                        servidor.registrar(user, psw1);
                        cadena = servidor.recibir();
                        if(cadena[0].equals("true")){
                            EventQueue.invokeLater(new Cliente.Interfaces.Menus.Login());
                            this.setVisible(false);
                        }else if(cadena[0].equals("false")){
                            MostrarMensaje.mostrarError(cadena[1]);
                            EventQueue.invokeLater(new Cliente.Interfaces.Menus.MenuInicial());
                            this.setVisible(false);
                        }
                    } else {
                        MostrarMensaje.mostrarError("El formato del nombre es incorrecto.\n");
                    }
                }
            }catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
        ventana = new JFrame("Registrate a continuacion");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Layout
        this.setLayout(null);
    }
    /**
    * Inicia los componentes de la ventana
    */
    public void iniciarComponentes(){
        //Texto
        usr = new JLabel("User");
        usr.setBounds(100, 30, 160, 25);
        this.add(usr);
        psw = new JLabel("Password");
        psw.setBounds(300, 30, 320, 25);
        this.add(psw);
        usuario = new JTextField("#(nombre de usuario)");
        password = new JPasswordField("contraseña");
        usuario.setBounds(100, 100, 160, 25);
        password.setBounds(300, 100, 280, 25);
        this.add(usuario);
        this.add(password);
        
        //Botones
        JButton login = new JButton("Registro");
        this.add(login);
        login.setBounds(360, 220, 220, 25);
        login.addActionListener(escuchador);
        this.add(login);
    }
    /**
    * Inicia la ventana
    */
    public void iniciarVentana(){
        this.setSize(800, 400);
        this.setVisible(true);
    }
}

