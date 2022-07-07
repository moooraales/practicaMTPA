package Servidor;

import Servidor.errorException;
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
                System.out.println("LISTA DE USUARIOS REGISTRADOS: ");
                alUsuario = Usuario.getListaUsuarios();
                mostrarUsuarios(alUsuario);
                break;
            case "2":
                System.out.println("LISTA DE USUARIOS CONECTADOS: ");
                alUsuario = Cliente.listarUsuariosLogueados();
                mostrarUsuarios(alUsuario);
                break;
            case "3":
                System.out.println("LISTA DE RETOS ACTIVOS: ");
                imprimirRetos();
                break;
            case "4":
                System.out.println("LISTA DE SALAS ACTIVAS: ");
                imprimirSalas();
                break;
            case "5":
                System.out.println("RANKING DE USUARIOS: ");
                alUsuario = Ranking.getRanking().getUsuarios();
                mostrarUsuarios(alUsuario);
                break;
            case "6":
                System.out.println("CARGANDO USUARIOS...");
                UsuarioDatosPersistentes.cargarUsuarios();
                break;
            case "7":
                System.out.println("GUARDANDO USUARIOS...");
                UsuarioDatosPersistentes.guardarUsuarios();
                break;
            case "8":
                System.out.println("INICIANDO SERVIDOR...");
                PrincipalServidor.arrancado = true;
                break;
            case "9":
                System.out.println("SERVIDOR PAUSADO...");
                PrincipalServidor.arrancado = false;
                break;
            case "10":
                System.out.println("SERVIDOR DETENIDO");
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
            System.out.println("LOG: "+new errorException().toString()+":"+ex.getMessage());
        }
    }
}

//Metodo que muestra los retos que se encuentran activos actualmente en el servidor
public void imprimirRetos(){
    ArrayList<Reto> lista = Reto.listarRetosActivos();
    if(lista.isEmpty()){
        System.out.println("--------------");
        System.out.println("No hay registros");
    }else{
        for(int i=0;i<lista.size();i++){
            System.out.println("--------------");
            System.out.print("Retador: ");
            System.out.println(lista.get(i).getLocal().getNombre());
            System.out.print("Retado: ");
            System.out.println(lista.get(i).getVisitante().getNombre());
            System.out.print("Estado partida: ");
            System.out.println(lista.get(i).getEstado());
        }
    }
}

//Metodo que muestra los datos de las salas de partida que se encuentran activas
public void imprimirSalas(){
    ArrayList<Reto> lista = Reto.listarRetosActivos();
    Sala sala;
    if(lista.isEmpty()){
        System.out.println("--------------");
        System.out.println("No hay registros");
    }else{
        for(int i=0;i<lista.size();i++){
            sala = lista.get(i).getSala();
            System.out.println("--------------");
            System.out.print("Local: ");
            System.out.print(lista.get(i).getLocal().getNombre());
            System.out.print("(");
            System.out.print(sala.getPuntosLocal());
            System.out.println(")");
            System.out.print("Visitante: ");
            System.out.print(lista.get(i).getVisitante().getNombre());
            System.out.print("(");
            System.out.print(sala.getPuntosVisitante());
            System.out.println(")");
        }
    }
}
}
