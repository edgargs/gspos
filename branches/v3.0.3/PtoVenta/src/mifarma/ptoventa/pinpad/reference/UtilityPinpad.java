package mifarma.ptoventa.pinpad.reference;


import java.awt.Frame;

import java.io.File;

import java.util.HashMap;

import javax.swing.JDialog;

import mifarma.common.FarmaUtility;
import mifarma.common.FarmaVariables;

import mifarma.ptoventa.caja.reference.VariablesCaja;
import mifarma.ptoventa.pinpad.DlgAnularTransPinpad;
import mifarma.ptoventa.pinpad.mastercard.VariablesPinpadMC;
import mifarma.ptoventa.pinpad.visa.VariablesPinpad;
import mifarma.ptoventa.reference.ConstantsPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityPinpad {
    private static final Logger log = LoggerFactory.getLogger(UtilityPinpad.class);

    static public String convCadNumDec(String numSinForm) {
        String resultado = "";
        try {
            if (numSinForm.length() > 2)
                resultado =
                        numSinForm.substring(0, numSinForm.length() - 2) + "." + numSinForm.substring(numSinForm.length() -
                                                                                                      2);
            else
                resultado = "0." + numSinForm;
        } catch (Exception e) {
            resultado = "";
            log.error("", e);
        }
        return resultado;
    }

    /**
     * valida si existen en la maquina las librerias del pinpad para Masterca y Visa
     * @author Luis Leiva
     * @since 09.01.2014
     */
    static public boolean validarLibrerias() {
        boolean flag = true;
        File f;
        try { //valida los archivos para Visa
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "CAJA_PINPAD.dll");
                flag = f.exists();
            }
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "CONSOLA.TTF");
                flag = f.exists();
            }
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "DLL3500.ini");
                flag = f.exists();
            }
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "mfc80u.dll");
                flag = f.exists();
            }
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "msvcr80.dll");
                flag = f.exists();
            }
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "mswinsck.ocx");
                flag = f.exists();
            }
            if (flag) {
                f = new File(VariablesPinpad.carpeta, "PinPadJava.dll");
                flag = f.exists();
            }

            //valida los archivos para Mastercard
            if (flag) {
                f = new File(VariablesPinpadMC.libpathMC, "lib\\jHcomHeps.dll");
                flag = f.exists();
            }

            //si pasaron todas las validaciones retornar corecto
            if (flag)
                return flag;
            else { //sino mostrar un mensaje indicando que falta alguna libreria y retorna falso
                return mensaje();
            }
        } catch (Exception ex) {
            log.error("", ex);
            return mensaje();
        }
    }

    static private boolean mensaje() {
        FarmaUtility.showMessage(new JDialog(),
                                 "ATENCIÓN: No se encontraron algunas de las librerias requeridas para trabajar con el pinpad\n" +
                "Contacte a Mesa de Ayuda para solucionar el problema", null);
        return false;
    }

    static public void obligarAnularTransaccionPinpad(Frame myParentFrame,
                                                      String texto) { //LLEIVA 02/Dic/2013 hay q tener cuidado con las excepciones de Recetario Magistral
        HashMap<String, String> resultado = new HashMap<String, String>();
        DBPinpad.consFormaPagoPedido2(VariablesCaja.vNumPedVta, resultado);

        //anulacion visa
        if (ConstantsPtoVenta.FORPAG_VISA_PINPAD.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")) ||
            ConstantsPtoVenta.FORPAG_MC_PINPAD.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")) ||
            ConstantsPtoVenta.FORPAG_DINERS.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED")) ||
            ConstantsPtoVenta.FORPAG_AMEX.equalsIgnoreCase(resultado.get("FORMA_PAGO_PED"))) {
            //si posee transaccion VISA o MC, abrir ventana
            FarmaVariables.vAceptar = false;
            while (!FarmaVariables.vAceptar) {
                FarmaUtility.showMessage(new JDialog(), texto, null);

                DlgAnularTransPinpad dlgAnularTransPinpad = new DlgAnularTransPinpad(myParentFrame, "", true);
                dlgAnularTransPinpad.setValores(VariablesCaja.vNumPedVta, VariablesCaja.vValMontoPagadoTarj,
                                                resultado.get("FECHA_TRANS"), resultado.get("NUM_REF_TRANS"),
                                                resultado.get("FORMA_PAGO_PED"), false);
                dlgAnularTransPinpad.setVisible(true);
                //se recoge el resultado de la anulacion de la transaccion
            }
            //vProcesoRecarga = true;
            //return;
        }
    }
}
