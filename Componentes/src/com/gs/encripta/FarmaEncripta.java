package com.gs.encripta;
import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder; 

/** 
 * @author mpriale
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FarmaEncripta {

	public static void main(String[] args) {
		
		String texto = "";
		 
		try { 
			 
			texto = args[0];
			
		} catch (Exception e) {
			
			System.out.println("Ingrese el texto a encriptar: java UsersCrypto [TEXTO]");
		}
		
		System.out.println("**********FarmaEncripta**********");
		System.out.println("Texto a Encriptar   : " + texto);
		
		String textoEncriptado = encripta(texto);
		System.out.println("Texto Encriptado    : (" + textoEncriptado + ")");

		String textoDesncriptado = desencripta(textoEncriptado);
		System.out.println("Texto Desencriptado : (" + textoDesncriptado + ")");

		System.out.println("**********MiFarma -2006**********");
		/*String textoDesencriptado = desencripta(textoEncriptado);
		
		System.out.println(textoDesencriptado);*/
		
	}
	
	private static String encripta(String texto){
		
		String textoEncriptado = "";

		BASE64Encoder encoder = new BASE64Encoder();
		
		textoEncriptado = encoder.encode(texto.getBytes());

		return textoEncriptado;
	
	}
	
	public static String desencripta(String textoEncriptado){
		
		String texto = "";
	
		BASE64Decoder decoder = new BASE64Decoder();
		
		try {
			texto = new String(decoder.decodeBuffer(textoEncriptado));
		} catch (IOException e) {
		}
	
		return texto;
	
	}
	
}
