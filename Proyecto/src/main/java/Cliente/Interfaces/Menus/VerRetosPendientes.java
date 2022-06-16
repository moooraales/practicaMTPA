package Cliente.Interfaces.Menus;

import Cliente.Servidor;

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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class VerRetosPendientes extends JFrame implements Runnable{
    JLabel etiqueta;
    FlowLayout layout;
    JFrame ventana;
    JButton boton;
    ActionListener escuchador;
    JList lista;
    /**
    * Comprueba cual es el boton seleccionado y realiza la accion del mismo
    * Volver
    * @author Luis Enrique Muñoz
    * @since 1.0
    * @version 1.0
    */
    @Override
    public void run() {
        escuchador = (ActionEvent ae) -> {
            JButton boton1 = (JButton) ae.getSource();
            String nombreBoton = boton1.getText();
            switch(nombreBoton){
                case "Volver":
                    this.setVisible(false);
                    EventQueue.invokeLater( new Cliente.Interfaces.Menus.MenuOpcionJuego());
                    break;
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
        ventana = new JFrame("Lista de jugadores ");
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
        try {
            //Lista
            DefaultListModel model = dameRetosPendientes();
            lista = new JList(model);
            lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lista.getSelectedIndex();
            JScrollPane barraDesplazamiento = new JScrollPane(lista);
            barraDesplazamiento.setBounds(10,30,200,110);
            
            //Barra de desplazamiento
            this.add(barraDesplazamiento);
            this.setSize(400, 200);
            this.setVisible(true);
            
        } catch (IOException ex) {
            Logger.getLogger(ListarUsuariosActivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Botones
        String titulosBotones[] = {"Volver"};

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
    /**
    * Generaria un DefaultListModel con los retos pendientes
    * @author Luis Enrique Muñoz
    * @return DefaultListModel con los usuarios activos 
    * @throws java.io.IOException
    * @since 1.0
    * @version 1.0
    */
    public DefaultListModel dameRetosPendientes() throws IOException{
        Servidor servidor = Cliente.Servidor.getServidor();
        servidor.enviar("listarRetos;");
        String usuarios [] = servidor.recibir();

        DefaultListModel model = new DefaultListModel<>();
        if(usuarios[0].equals("false")){
            model.addElement("No hay retos pendientes");
        }else{
            for(int i=0;i<usuarios.length;i++){
                if(usuarios[i].equals("Pendiente")){
                    model.addElement(usuarios[i-1]);
                }
                i++;
            }
        }
       
        return model;
    }
}
