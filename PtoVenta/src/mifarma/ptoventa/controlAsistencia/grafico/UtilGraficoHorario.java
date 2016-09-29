package mifarma.ptoventa.controlAsistencia.grafico;

import java.awt.Color;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;

import mifarma.common.FarmaUtility;
import mifarma.ptoventa.controlAsistencia.reference.ConstantsControlAsistencia;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

 /**
  * Copyright (c) 2015 MIFARMA S.A.C.<br>
  * <br>
  * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
  * Nombre de la Aplicación : UtilGraficoHorario.java<br>
  * <br>
  * Histórico de Creación/Modificación<br>
  * CHUANES                    Creación<br>
  * EMAQUERA      15.10.2015   Modificacion<br>
  * <br>
  * @author Cesar Huanes<br>
  * @version 1.0<br>
  *
  */
public class UtilGraficoHorario {
    
    private JFreeChart chart;
    private ChartPanel chartPanel;
    IntervalCategoryDataset dataset;
    private ArrayList vListaDatos = new ArrayList();

    public void UtilGraficoHorario(){
    }
    
    public void setData(ArrayList vLista){
        vListaDatos = vLista;
    }

    public ChartPanel getGraficoHorario() {
        dataset = createDataset();
        chart = createChart(dataset, null);
        chartPanel = createChartPanel(chart);
        return chartPanel;
    }

    private ChartPanel createChartPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
       
        chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2),
                                                                BorderFactory.createLineBorder(Color.black)));
        chartPanel.setRefreshBuffer(true);
        return chartPanel;
    }
    
    class MyToolTipGenerator extends IntervalCategoryToolTipGenerator {

            DateFormat format;

            private MyToolTipGenerator(String value, DateFormat format) {
                super(value, format);
                this.format = format;
            }

            @Override
            public String generateToolTip(CategoryDataset cds, int row, int col) {
                final String s = super.generateToolTip(cds, row, col);
                TaskSeriesCollection tsc = (TaskSeriesCollection) cds;
                StringBuilder sb = new StringBuilder(s);
                for (int i = 0; i < tsc.getSubIntervalCount(row, col); i++) {
                    sb.append(format.format(tsc.getStartValue(row, col, i)));
                    sb.append("-");
                    sb.append(format.format(tsc.getEndValue(row, col, i)));
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            }
        }
    
    private IntervalCategoryDataset createDataset() {
            TaskSeriesCollection dataset = new TaskSeriesCollection();
            TaskSeries am;
            String hora="";
            am = new TaskSeries("Hora");
            Task t1=null;
            if(vListaDatos.size()>0){
            hora = FarmaUtility.getValueFieldArrayList(vListaDatos, 0,1).toString().trim();
            if(hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_DESCANSO) ||hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_SUBSIDIO)||
                       hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_VACACIONES)||hora.equalsIgnoreCase(ConstantsControlAsistencia.NOM_CESADO)||hora.equalsIgnoreCase("")){
            hora="00:00"+"-"+"00:00";  
            }
        
            int horaInicio=Integer.parseInt(hora.substring(0, 2));
            int minInicio=Integer.parseInt(hora.substring(3, 5));
            int horaFin=Integer.parseInt(hora.substring(6, 8));
            int minFin=Integer.parseInt(hora.substring(9, 11));
            t1 = new Task(hora, date(0,0), date(23,59));//persona 1
            if(horaInicio==horaFin){
                t1.addSubtask(new Task("", date(horaInicio,minInicio), date(horaInicio,minFin)));//quimico  
            }else{
                if(horaFin>horaInicio){
                    t1.addSubtask(new Task("", date(horaInicio,minInicio), date(horaFin,minFin)));//quimico
                }else{
                    t1.addSubtask(new Task("", date(horaInicio,minInicio), date(23,59)));//quimico 
                    t1.addSubtask(new Task("", date(0,0), date(horaFin,minFin)));//quimico  
                }
            }
            
            am.add(t1);
            
            
            dataset.add(am);
            }
        
            return dataset;
        }
    
    private JFreeChart createChart(IntervalCategoryDataset dataSet,String title) {
          IntervalCategoryDataset xyDataset = dataSet;
           JFreeChart jFreeChart = ChartFactory.createGanttChart("",
               "", "Horas", xyDataset, true, true, true);
           CategoryPlot plot = jFreeChart.getCategoryPlot();
          plot.setOrientation(PlotOrientation.VERTICAL);
          plot.getRenderer().setBaseToolTipGenerator(
               new MyToolTipGenerator(
               "{0}, {1}: ", DateFormat.getTimeInstance(DateFormat.SHORT)));
           
           return jFreeChart;
       }
    
    private Date date(int hour, int minute) {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(2015, Calendar.JUNE, 1, hour, minute, 0);
            return calendar.getTime();
        }

}
