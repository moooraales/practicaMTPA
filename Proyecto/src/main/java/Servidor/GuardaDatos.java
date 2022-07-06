
package Servidor;

public class GuardaDatos extends Thread{
    public GuardaDatos(){
        this.start();
    }

    /*Con el objetivo de tener en todo momento los datos lo más actualizados posibles,
     * hacemos que los datos de los usuarios se guarden cada 15 segundos  */
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep(15000);
                UsuarioDatosPersistentes.guardarUsuarios();
            }catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}