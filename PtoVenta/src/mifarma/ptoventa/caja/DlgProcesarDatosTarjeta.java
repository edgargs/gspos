package mifarma.ptoventa.caja;

import com.gs.mifarma.worker.JDialogProgress;

import java.awt.Frame;

import mifarma.common.FarmaUtility;

import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2015 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 11g<br>
 * Nombre de la Aplicación : DlgProcesarDatosTarjeta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * KMONCADA      08.04.2015   Creación<br>
 * <br>
 * @author Kenny Moncada<br>
 * @version 1.0<br>
 *
 */
public class DlgProcesarDatosTarjeta extends JDialogProgress {
    
    private static final Logger log = LoggerFactory.getLogger(DlgProcesarDatosTarjeta.class);
    private DlgDatosTarjeta dlgDatos;


    public DlgProcesarDatosTarjeta() {
        super();
    }
    
    public DlgProcesarDatosTarjeta(Frame frame, String string, boolean b) {
        super(frame, string, b);
    }

    
    public void ejecutaProceso() {
        realizaProceso();
    }
    
    private void realizaProceso() {
        if (dlgDatos.buscarInfotarjeta()) {
            if (dlgDatos.hablitarTipoTarjeta()) {
                dlgDatos.habilitaCampos(false);
                if (ConstantsPtoVenta.FORPAG_MC_POS.equalsIgnoreCase(dlgDatos.strCodFormaPago) ||
                    ConstantsPtoVenta.FORPAG_VISA_POS.equalsIgnoreCase(dlgDatos.strCodFormaPago) ||
                    ConstantsPtoVenta.FORPAG_DINNERS_POS.equalsIgnoreCase(dlgDatos.strCodFormaPago) ||
                    ConstantsPtoVenta.FORPAG_AMEX_POS.equalsIgnoreCase(dlgDatos.strCodFormaPago) ||
                    ConstantsPtoVenta.FORPAG_CMR.equalsIgnoreCase(dlgDatos.strCodFormaPago)) {
                    dlgDatos.habilitaCampos(true);
                    dlgDatos.verificarCredDeb();
                }
                
                dlgDatos.lblTipoTarjeta.setText(dlgDatos.desc_prod + "/" + dlgDatos.strDescFormaPago);
                dlgDatos.cmbDebitoCredito.grabFocus();

            } else {
                FarmaUtility.showMessage(this, "Tipo de tarjeta no admitida.", null);
                dlgDatos.inicializarVariables();
                dlgDatos.cont = 0;
                dlgDatos.infoTarj = "";
                dlgDatos.txtNroTarjeta.setEditable(true);
                dlgDatos.txtNroTarjeta.setText("");
            }
        } else {
            dlgDatos.inicializarVariables();
            dlgDatos.cont = 0;
            dlgDatos.infoTarj = "";
            dlgDatos.txtNroTarjeta.setEditable(true);
            dlgDatos.txtNroTarjeta.setText("");
        }
    }

    public void setDlgDatos(DlgDatosTarjeta dlgDatos) {
        this.dlgDatos = dlgDatos;
    }

    public DlgDatosTarjeta getDlgDatos() {
        return dlgDatos;
    }
}
