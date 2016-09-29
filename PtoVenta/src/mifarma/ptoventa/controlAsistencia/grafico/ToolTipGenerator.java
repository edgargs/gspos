package mifarma.ptoventa.controlAsistencia.grafico;

import java.text.DateFormat;

import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.gantt.TaskSeriesCollection;

 /**
  * Copyright (c) 2015 MIFARMA S.A.C.<br>
  * <br>
  * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
  * Nombre de la Aplicación : ToolTipGenerator.java<br>
  * <br>
  * Histórico de Creación/Modificación<br>
  * CHUANES                    Creación<br>
  * EMAQUERA      15.10.2015   Modificacion<br>
  * <br>
  * @author Cesar Huanes<br>
  * @version 1.0<br>
  *
  */
public class ToolTipGenerator extends  IntervalCategoryToolTipGenerator{
    DateFormat format;
    /* ********************************************************************** */
    /*                        CONSTRUCTORES                                   */
    /* ********************************************************************** */    
    public ToolTipGenerator(String value, DateFormat format) {
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
