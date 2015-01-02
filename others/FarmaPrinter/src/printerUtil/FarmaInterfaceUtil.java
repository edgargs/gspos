/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package printerUtil;

/**
 *
 * @author Cesar Huanes
 * @since  11/08/2014
 */
public interface FarmaInterfaceUtil {
  String guion(int maxPoint);
 String espacioBlanco(int numEspacio);
 String alineaMontos(String texto);
 String alineaDetalle(String codigo, String descripcion, String cant, String preUni,String desc, String importe);
String getPathLogo(String codMarca);
}
