package Servidor;

import java.util.ArrayList;

public class Usuario{

    private static ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    
    private String nombre;
    private String password;
    private int partidasGanadas;
    private ArrayList<Reto> listaRetos;
    
    public Usuario(){
    }

    public Usuario(String nombre, String password){
        this(nombre, password, 0);
    }

    public Usuario(String nombre, String password, int partidasGanadas){
        this.nombre = nombre;
        this.password = password;
        this.partidasGanadas = partidasGanadas;
        this.listaRetos=new ArrayList<>();
    }

    //Getters y setters

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }
    
    public int getPartidasGanadas(){
        return partidasGanadas;
    }
    
    public void setPartidasGanadas(int partidasGanadas){
        this.partidasGanadas = partidasGanadas;
    }
    
    public ArrayList<Reto> getListaRetos(){
        return listaRetos;
    }
    
    public static ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public static void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        Usuario.listaUsuarios = listaUsuarios;
    }



    //Metodo para aumentar en 1 las partidas ganadas por un usuario
    public void addPartidasGanadas(){
        partidasGanadas++;
    }

    //Metodo para registrar clientes en el servidor
    public static boolean registrar(String nombre, String password){
        return registrar(nombre,password,0);
    }

    public static boolean registrar(String nombre, String password, int partidasGanadas){
        boolean respuesta;
        if(buscarUsuario(nombre)!=null || 
                nombre.length()<2 || 
                !nombre.replaceAll("\\s","").equals(nombre) ||
                partidasGanadas<0){
            respuesta=false;
        }else{
            respuesta=Usuario.getListaUsuarios().add(new Usuario(nombre, password,partidasGanadas));
        }
        return respuesta;
    }  

    //Metodo para que un usuario inicie sesion en el sistema
    public static Usuario login(String nombre, String password){
        Usuario usuario = null;
        if(Cliente.buscarClienteLogueado(nombre)==null){
            for(int i=0; i<getListaUsuarios().size();i++){
                if(getListaUsuarios().get(i).getNombre().equals(nombre)){
                    if(getListaUsuarios().get(i).getPassword().equals(password)){
                        usuario = getListaUsuarios().get(i);
                    }
                }
            }
        }
        return usuario;
    }

    //Busca un reto del usuario que esta retando

    public Reto buscarReto(Usuario retador){
        Reto reto = null;
        for(int i = 0; i < listaRetos.size();i++){
            if(listaRetos.get(i).getLocal().equals(retador) || listaRetos.get(i).getVisitante().equals(retador)){
                reto = listaRetos.get(i);
            }
        }
        return reto;
    }

    //Metodo para a??adir los retos a los dos jugadores
    public boolean addReto(Usuario retador){
        boolean respuesta = false;
        if(buscarReto(retador)==null){
            Reto reto = new Reto(retador,this);
            if(retador.listaRetos.add(reto) && listaRetos.add(reto)){
                respuesta = true;
            }
        }
        return respuesta;
    }

    //Busca a un usuario en una lista
    public static Usuario buscarUsuario(String nombre, ArrayList<Usuario> lista){
        Usuario usuario = null;
        for(int i = 0; i<lista.size();i++){
            if(lista.get(i).getNombre().equals(nombre)){
                usuario = lista.get(i);
            }
        }
        return usuario;
    }

    //Metodo que busca a un usuario en el sistema
    public static Usuario buscarUsuario(String nombre){
        return buscarUsuario(nombre, getListaUsuarios());
    }

    //Metodo que elimina los retos que tiene un usuario
    public void eliminarRetos(){
        for(int i=0; i < this.getListaRetos().size(); i++){
            this.getListaRetos().get(i).eliminar();
        }
    }
    
}
