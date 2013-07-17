package mifarma.ptoventa.hilos;

import mifarma.ptoventa.convenioBTLMF.reference.UtilityConvenioBTLMF;
import mifarma.ptoventa.convenioBTLMF.reference.VariablesConvenioBTLMF;
import mifarma.ptoventa.ventas.reference.UtilityVentas;


public class SubProcesosConvenios extends Thread{
    public SubProcesosConvenios() {
    }

    public void run() {
        try {
            if (!VariablesConvenioBTLMF.vValidaPrecio) {
                Thread.sleep(0);
            } else {
                System.out.println("INICIO HILO CONVENIOS");
                VariablesConvenioBTLMF.vNew_Prec_Conv = 
                        UtilityConvenioBTLMF.Conv_Buscar_Precio();
                VariablesConvenioBTLMF.vValidaPrecio = false;
                System.out.println("VariablesConvenioBTLMF.vNew_Prec_Conv:" + 
                                   VariablesConvenioBTLMF.vNew_Prec_Conv);
                System.out.println("FIN HILO CONVENIOS");
            }
        }
        // si el subproceso se interrumpió durante su inactividad, imprimir rastreo de la pila
        catch (InterruptedException excepcion) {
            excepcion.printStackTrace();
        }

    }
}
