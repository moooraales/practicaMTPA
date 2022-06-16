package Servidor;



import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {



    public Cliente(){
    }
    
    public Cliente(Socket socketCliente){
        this.cliente = socketCliente;
        this.usuario = null;
        this.start();
    }
    
}
