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


public class FarmaHora
  extends JLabel implements Runnable
{
  GregorianCalendar calendario1 = new GregorianCalendar();
  SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


  Date date1 = new Date();
  Thread hilo;

  public FarmaHora()
  {
    hilo = new Thread(this);


    try
    {
      String sysdate = FarmaSearch.getFechaHoraBD(FarmaConstants.FORMATO_FECHA_HORA);


      System.out.println(sysdate);
      Date date1 = FarmaUtility.getStringToDate(sysdate,"dd/MM/yyyy HH:mm:ss");

      //calendario1.setGregorianChange(date1);
      calendario1.setTime(date1);


    }catch(SQLException e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame();

        FarmaHora farmaHora = new FarmaHora();
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
        // TODO
      }
         calendario1.add(Calendar.SECOND,1);
      String formato = format.format(calendario1.getTime());

      //System.out.println(calendario1.getTimeInMillis()+"");

      setText(formato);
    }
  }

  public void start()
  {
    hilo.start();
  }
}
