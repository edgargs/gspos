package mifarma.ptoventa.retiro;


import com.gs.mifarma.componentes.JButtonFunction;
import com.gs.mifarma.componentes.JButtonLabel;
import com.gs.mifarma.componentes.JPanelTitle;
import com.gs.mifarma.componentes.JPanelWhite;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DlgListaLocales extends JDialog {
    private static final Logger log = LoggerFactory.getLogger(DlgListaLocales.class);

    private JPanelWhite pnlFondo = new JPanelWhite();
    private JPanelTitle pnlTitulo = new JPanelTitle();
    private JButtonLabel btnlistalocales = new JButtonLabel();
    private JScrollPane srcLista = new JScrollPane();
    private JTable tblLista = new JTable();
    private JButtonFunction btnaceptar = new JButtonFunction();

    public DlgListaLocales() {
        this(null, "", false);
    }

    public DlgListaLocales(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(338, 354));
        this.getContentPane().setLayout(null);
        pnlFondo.setBounds(new Rectangle(0, 0, 335, 330));
        pnlTitulo.setBounds(new Rectangle(10, 10, 310, 25));
        btnlistalocales.setText("Lista de Locales");
        btnlistalocales.setBounds(new Rectangle(5, 5, 105, 15));
        btnlistalocales.setMnemonic('l');
        srcLista.setBounds(new Rectangle(10, 35, 310, 250));
        btnaceptar.setText("[F1] Aceptar");
        btnaceptar.setBounds(new Rectangle(90, 295, 150, 25));
        pnlTitulo.add(btnlistalocales, null);
        srcLista.getViewport().add(tblLista, null);
        pnlFondo.add(btnaceptar, null);
        pnlFondo.add(srcLista, null);
        pnlFondo.add(pnlTitulo, null);
        this.getContentPane().add(pnlFondo, null);
    }
}
