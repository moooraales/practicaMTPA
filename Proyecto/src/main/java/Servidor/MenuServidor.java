package Servidor;

import Servidor.Excepcion.GeneralError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuServidor extends Thread{
    public MenuServidor(){
        this.start();
    }

//Metodo que crea el hilo destinado a gestionar el menu del Servidor
@Override
    public void run(){
        Scanner sc = new Scanner (System.in);
        String opc;
        do{
            imprimirMenu();
            opc = sc.nextLine();
            menuOpcion(opc);
        }while(!opc.equals("0"));
    }

//Metodo que imprime el menu y lo muestra por pantalla
public void imprimirMenu(){
    System.out.println("--LISTA DE OPCIONES--");
    System.out.println("1 - MOSTRAR LISTA USUARIOS");
    System.out.println("2 - MOSTRAR USUARIOS CONECTADOS");
    System.out.println("3 - MOSTRAR RETOS");
    System.out.println("4 - MOSTRAR PARTIDAS EN CURSO");
    System.out.println("5 - MOSTRAR RANKING DE USUARIOS");
    System.out.println("6 - CARGAR DATOS DE USUARIOS");
    System.out.println("7 - GUARDAR DATOS DE USUARIOS");
    System.out.println("8 - ARRANCAR EL SERVIDOR");
    System.out.println("9 - PAUSAR EL SERVIDOR");
    System.out.println("10 - DETENER EL SERVIDOR");
    System.out.println("0 - SALIR");
    System.out.print("ELIJA UNA OPCION: ");
}

//Ejecutamos la opcion que se seleccione
public void menuOpcion(String opc){
    System.out.println("MENU SERVIDOR");
    ArrayList<Usuario> alUsuario;
        switch(opc){
            case "1":
                System.out.println("--Usuarios registrados--");
                alUsuario = Usuario.getListaUsuarios();
                mostrarUsuarios(alUsuario);
                break;
            case "2":
                System.out.println("--Usuarios conectados--");
                alUsuario = Cliente.listarUsuariosLogueados();
                imprimirUsuarios(alUsuario);
                break;
            case "3":
                System.out.println("--Ver retos--");
                imprimirRetos();
                break;
            case "4":
                System.out.println("--Ver salas en curso--");
                imprimirSalas();
                break;
            case "5":
                System.out.println("--Ranking--");
                alUsuario = Ranking.getRanking().getUsuarios();
                mostrarUsuarios(alUsuario);
                break;
            case "6":
                System.out.println("--Cargar usuarios--");
                UsuarioDB.cargarUsuarios();
                break;
            case "7":
                System.out.println("--Guardar usuarios--");
                UsuarioDB.guardarUsuarios();
                break;
            case "8":
                System.out.println("--Iniciar servicio--");
                PrincipalServidor.arrancado = true;
                break;
            case "9":
                System.out.println("--Servicio en Modo mantenimiento--");
                System.out.println("--No se aceptarán clientes nuevos--");
                PrincipalServidor.arrancado = false;
                break;
            case "10":
                System.out.println("--Detener servicio--");
                PrincipalServidor.arrancado = false;
                detenerEjecucion();
                break;
            case "0":
                System.exit(0);
                break;
            default:
                System.out.println("--ELIJA UNA OPCION VALIDA--");
                break;
        }
}

//Muestra los usuarios del sistema
public void mostrarUsuarios(ArrayList<Usuario> lista){
    if(lista.isEmpty()){
        System.out.println("--------------");
        System.out.println("No se encuentran usuarios, lista vacia");
    }else{
        for(int i=0;i<lista.size();i++){
            System.out.println("--------------");
            System.out.print("Usuario: ");
            System.out.println(lista.get(i).getNombre());
            System.out.print("Partidas ganadas: ");
            System.out.println(lista.get(i).getPartidasGanadas());
        }
    }
}

//Detiene la ejecución del servidor
public void detenerEjecucion(){
    ArrayList<Cliente> alCliente = Cliente.getListaClientesActivos();
    for(int i=0;i<alCliente.size();i++){
        try {
            alCliente.get(i).desconectar();
        } catch (IOException ex) {
            System.out.println("LOG: "+new GeneralError().toString()+":"+ex.getMessage());
        }
    }
}

}
