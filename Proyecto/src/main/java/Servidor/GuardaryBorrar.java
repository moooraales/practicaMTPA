package Servidor;

public class GuardaryBorrar extends Thread{

    /*Esta clase sirve para que sea llamada frecuentemente, su objetivo
     * es facilitar las operaciones que se realizan a la vez, en este caso
     * guardar los datos y borrar un reto*/
    public GuardaryBorrar(){
        this.start();
    }

    @Override
    public void run(){
        new GuardaDatos();
        new EliminarReto();
    }
    
}
