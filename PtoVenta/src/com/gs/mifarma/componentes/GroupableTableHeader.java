package com.gs.mifarma.componentes;

/*
 * (swing1.1beta3)
 * 
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;


/**
 * GroupableTableHeader
 *
 * @version 1.0 10/20/98
 * @author Nobuo Tamemasa
 */

public class GroupableTableHeader extends JTableHeader {
  private static final String uiClassID = "GroupableTableHeaderUI";
  protected Vector columnGroups = null;
    
  public GroupableTableHeader(TableColumnModel model) {
    super(model);
    setUI(new GroupableTableHeaderUI());
    setReorderingAllowed(false);
  }
  public void updateUI(){
   setUI(new GroupableTableHeaderUI());
  }
  
  public void setReorderingAllowed(boolean b) {
    reorderingAllowed = false;
  }
    
  public void addColumnGroup(ColumnGroup g) {
    if (columnGroups == null) {
      columnGroups = new Vector();
    }
    columnGroups.addElement(g);
  }

  public Enumeration getColumnGroups(TableColumn col) {
    if (columnGroups == null) return null;
    Enumeration e = columnGroups.elements();
    while (e.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup)e.nextElement();
      Vector v_ret = (Vector)cGroup.getColumnGroups(col,new Vector());
      if (v_ret != null) { 
  return v_ret.elements();
      }
    }
    return null;
  }
  
  public void setColumnMargin() {
    if (columnGroups == null) return;
    //Revisado por ERIOS 09.06.2015
    int columnMargin = 0;//getColumnModel().getColumnMargin();
    Enumeration e = columnGroups.elements();
    while (e.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup)e.nextElement();
      cGroup.setColumnMargin(columnMargin);
    }
  }

    private class GroupableTableHeaderUI extends BasicTableHeaderUI {

        public void paint(Graphics g, JComponent c) {
            Rectangle clipBounds = g.getClipBounds();
            if (header.getColumnModel() == null)
                return;
            ((GroupableTableHeader)header).setColumnMargin();
            int column = 0;
            Dimension size = header.getSize();
            Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
            Hashtable h = new Hashtable();
            int columnMargin = header.getColumnModel().getColumnMargin();

            Enumeration enumeration = header.getColumnModel().getColumns();
            while (enumeration.hasMoreElements()) {
                cellRect.height = size.height;
                cellRect.y = 0;
                TableColumn aColumn = (TableColumn)enumeration.nextElement();
                Enumeration cGroups = ((GroupableTableHeader)header).getColumnGroups(aColumn);
                if (cGroups != null) {
                    int groupHeight = 0;
                    while (cGroups.hasMoreElements()) {
                        ColumnGroup cGroup = (ColumnGroup)cGroups.nextElement();
                        Rectangle groupRect = (Rectangle)h.get(cGroup);
                        if (groupRect == null) {
                            groupRect = new Rectangle(cellRect);
                            Dimension d = cGroup.getSize(header.getTable());
                            groupRect.width = d.width;
                            groupRect.height = d.height;
                            h.put(cGroup, groupRect);
                        }
                        paintCell(g, groupRect, cGroup);
                        groupHeight += groupRect.height;
                        cellRect.height = size.height - groupHeight;
                        cellRect.y = groupHeight;
                    }
                }
                //Revisado por ERIOS 09.06.2015
                cellRect.width = aColumn.getWidth();// + columnMargin;
                if (cellRect.intersects(clipBounds)) {
                    paintCell(g, cellRect, column);
                }
                cellRect.x += cellRect.width;
                column++;
            }
        }

        private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
            TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            //revised by Java2s.com
            renderer = new DefaultTableCellRenderer() {
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                                   boolean hasFocus, int row, int column) {
                        JLabel header = new JLabel();
                        header.setForeground(table.getTableHeader().getForeground());
                        header.setBackground(table.getTableHeader().getBackground());
                        header.setFont(table.getTableHeader().getFont());

                        header.setHorizontalAlignment(JLabel.CENTER);
                        header.setText(value.toString());
                        header.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                        return header;
                    }

                };
            Component c =
                renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false, false, -1,
                                                       columnIndex);

            c.setBackground(UIManager.getColor("control"));

            rendererPane.add(c);
            rendererPane.paintComponent(g, c, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
        }

        private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
            TableCellRenderer renderer = cGroup.getHeaderRenderer();
            //revised by Java2s.com
            // if(renderer == null){
            //      return ;
            //    }

            Component component =
                renderer.getTableCellRendererComponent(header.getTable(), cGroup.getHeaderValue(), false, false, -1,
                                                       -1);
            rendererPane.add(component);
            rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height,
                                        true);
        }

        private int getHeaderHeight() {
            int height = 0;
            TableColumnModel columnModel = header.getColumnModel();
            for (int column = 0; column < columnModel.getColumnCount(); column++) {
                TableColumn aColumn = columnModel.getColumn(column);
                TableCellRenderer renderer = aColumn.getHeaderRenderer();
                //revised by Java2s.com
                //Revisado por ERIOS 08.06.2015
                int cHeight = 20;
                if (renderer != null) {
                    Component comp =
                        renderer.getTableCellRendererComponent(header.getTable(), aColumn.getHeaderValue(), false, false,
                                                               -1, column);
                    cHeight = comp.getPreferredSize().height;
                }
                
                Enumeration e = ((GroupableTableHeader)header).getColumnGroups(aColumn);
                if (e != null) {
                    while (e.hasMoreElements()) {
                        ColumnGroup cGroup = (ColumnGroup)e.nextElement();
                        cHeight += cGroup.getSize(header.getTable()).height;
                    }
                }
                height = Math.max(height, cHeight);
            }
            return height;
        }

        private Dimension createHeaderSize(long width) {
            TableColumnModel columnModel = header.getColumnModel();
            width += columnModel.getColumnMargin() * columnModel.getColumnCount();
            if (width > Integer.MAX_VALUE) {
                width = Integer.MAX_VALUE;
            }
            return new Dimension((int)width, getHeaderHeight());
        }

        public Dimension getPreferredSize(JComponent c) {
            long width = 0;
            Enumeration enumeration = header.getColumnModel().getColumns();
            while (enumeration.hasMoreElements()) {
                TableColumn aColumn = (TableColumn)enumeration.nextElement();
                width = width + aColumn.getPreferredWidth();
            }
            return createHeaderSize(width);
        }
    }
}

/*
 * (swing1.1beta3)
 *
 */


/*
 * (swing1.1beta3)
 *
 */


