package com.gs.mifarma.worker;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class JFarmaWorker extends SwingWorker<String, Integer>{
    
    int i=0;
    JProgressBar jpbProceso;
    JLabel lblAuxOut;
    JDialogProgress jDialogProgress;
    
    boolean bEstadoProceso;
    String vEjecutaProceso = "0";
    
    public JFarmaWorker() {
        super();
    }

    JFarmaWorker(JDialogProgress jDialogProgress, JLabel jLabel,
                 JProgressBar jProgressBar) {
        jpbProceso = jProgressBar;
        lblAuxOut = jLabel;
        this.jDialogProgress = jDialogProgress;
    }

    @Override
    protected String doInBackground() throws Exception {
        //Inicia hilo
        iniciaProceso();
        //Espera termine
        while(i<=100 && !isCancelled() && bEstadoProceso ){
                Thread.sleep(1000);
                publish(i + 1);
                i++;
            
            if(i==100){
                if(!vEjecutaProceso.trim().equalsIgnoreCase("T")){
                    i = 1;
                }
            }
        }
        return "Operacion finalizada";
    }
    
    @Override
    public void done(){
        try {
            lblAuxOut.setText(get());
            Thread.sleep(1000);
            if(lblAuxOut.getText().trim().equals("Operacion finalizada")){                
                jDialogProgress.cerrar();
                vEjecutaProceso = "0";  
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
    
    void iniciaProceso(){
        bEstadoProceso = true;
        new Thread(){
            public void run(){
                jDialogProgress.ejecutaProceso();
                vEjecutaProceso = "T";
                bEstadoProceso = false;
            }
        }.start();
    }
    
    @Override
    protected void process(List<Integer> chunks) {
        if(jpbProceso != null){
            jpbProceso.setValue(chunks.get(0));
        }
        setProgress(chunks.get(0));
    }

}
