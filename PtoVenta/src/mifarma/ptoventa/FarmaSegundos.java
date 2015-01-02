package mifarma.ptoventa;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JLabel;

import mifarma.common.FarmaConstants;
import mifarma.common.FarmaSearch;
import mifarma.common.FarmaUtility;

import mifarma.ptoventa.administracion.producto.DlgProdImpresion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FarmaSegundos
  extends JLabel implements Runnable
{
    private static final Logger log = LoggerFactory.getLogger(FarmaSegundos.class);  
  GregorianCalendar calendario1 = new GregorianCalendar();
  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


  Date date1 = new Date();
  Thread hilo;
  
  int nCantidad = 0;

  public FarmaSegundos()
  {
    hilo = new Thread(this);


    try
    {
      String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);


      log.info(sysdate);
      Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy HH:mm:ss");

      //calendario1.setGregorianChange(date1);
      calendario1.setTime(date1);


    }catch(SQLException e)
    {
        log.error("",e);
    }
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame();

        FarmaSegundos farmaHora = new FarmaSegundos();
    frame.add(farmaHora);
  }

  public void run()
  {
    while(true)
    {
      try
      {
        Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
        log.error("",e);
      }
        nCantidad++;
      /*   calendario1.add(Calendar.SECOND,1);
      String formato = format.format(calendario1.getTime());*/
      //log.debug(calendario1.getTimeInMillis()+"");
      setText("    Espera de Proceso "+nCantidad + " sg.");
    }
  }

  public void start()
  {
    hilo.start();
  }
  
   public void stop()
  {
     hilo.stop();
  }
}
