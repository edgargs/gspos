package mifarma.ptoventa.otros.reference;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright (c) 2006 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : UtilityOtros.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * JCALLO      24.10.2008   Creación<br>
 * <br>
 * @author Edgar Rios Navarro<br>
 * @version 1.0<br>
 *
 */

public class UtilityOtros {

    private static final Logger log = LoggerFactory.getLogger(UtilityOtros.class);

    /**
     * Metodo encargado de validar formato de correo.
     *@autor jcallo
     *@since 02.10.2008
     */
    public static boolean validarEmail(String email) {

        boolean b = Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);

        return b;
    }

    /**
     * Metodo encargado de validacion del documento de identificacion ( DNI, CARNE DE EXTRANJERIA)     *
     *@autor jcallo
     *@since 06.10.2008
     *@param String docIden , String valoresValidos
     */
    public static boolean validarDocIndentificacion(String docIden, String docValidos) {
        log.debug("doc a validar :" + docIden);
        log.debug("validar con :" + docValidos);
        boolean flag = false;
        String paramDocVal = docValidos;
        if (paramDocVal != null) {
            String valores[] = paramDocVal.split(",");
            log.debug("valores :  " + valores);
            for (int i = 0; i < valores.length; i++) {
                //log.debug("izq : "+Integer.parseInt( valores[i].trim() )+" doc: "+docIden+", docIden length: "+docIden.length());
                if (Integer.parseInt(valores[i].trim()) == docIden.length()) {
                    log.debug("ok");
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * generar html del documento a imprimir
     *@autor jcallo
     *@since 06.10.2008
     *@param String CantItems , String ipServidor, FarmaTableModel farmaTableModel
     */
    /*public static String generarHtmlImprimir(int cantItems, String ipServidor, FarmaTableModel farmaTableModel,
    										 String pDni, String pNombCliente) {
        StringBuffer htmlGenerado = new StringBuffer(ConstantsOtros.IMP_INI_HTML_IMPRESION);
        //agregando el logo
        htmlGenerado.append("<tr> <td colspan='4'> ")
			.append("<img src=file://///")
			.append(ipServidor+ConstantsOtros.IMP_RUTA_IMG_CABECERA+" width='300' height='90'></td>")
			.append("</tr> ");
        //agregando TITULO
        htmlGenerado.append("<tr> <td colspan='4' align='center' class='titulo'> ")
		.append("HISTORICO MEDIDA DE PRESIONES</td>")		
		.append("</tr> ");

        //agregando DNI
        htmlGenerado.append("<tr><td colspan='4' class='cliente'><strong>DNI : </strong> "+pDni+"<BR>");

      	//agregando NOMBRE DEL CLIENTE
        htmlGenerado.append("<strong>CLIENTE : </strong> "+pNombCliente+"</td>")		
		.append("</tr> ");


        //agregando historico
        htmlGenerado.append("<tr><td colspan='4'><table width='100%' border='0' height='120'>");

        //agregando titulo del historico
        htmlGenerado.append("<tr class='histcab'> <td>Local</TD>")
		.append("<td align='right'>Max. Sistolica</TD>")
		.append("<td align='right'>Min. Diastolica</TD>")
		.append("<td>Fecha Registro</TD>")
		.append("</tr> ");



        //agregando historico
        int total = Math.min(cantItems, farmaTableModel.getRowCount());

        for ( int i=0 ; i<total; i++ ){
        	htmlGenerado.append("<tr class='historico'> <td> "+farmaTableModel.getValueAt(i, 0).toString()+"</TD>")
    		.append("<td align='right'> "+farmaTableModel.getValueAt(i, 1).toString()+" mmHg</TD>")
    		.append("<td align='right'> "+farmaTableModel.getValueAt(i, 2).toString()+" mmHg</TD>")
    		.append("<td> "+farmaTableModel.getValueAt(i, 3).toString()+"</TD>")
    		.append("</tr> ");
        }

        //fin historico
        htmlGenerado.append("</table></td></tr>");

        //agregando CONSEJO
        htmlGenerado.append("<tr class='msgfinal'> <td colspan='4' align='center'> ")
		.append("<i>MENSAJE O CONSEJO MI FARMA QUE SE MOSTRARA</i></td>")		
		.append("</tr> ");

        //agrenado fin del HTML
        htmlGenerado.append(ConstantsOtros.IMP_C_FIN_MSG);

        return htmlGenerado.toString();
    }*/

}


