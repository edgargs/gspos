package com.gs.hacom.dcs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gs.hacom.dcs.dao.FacadeDaemon;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent;
import com.gs.opengts.opt.servers.calamp.CalAmpEvent2;
import com.gs.opengts.util.StringTools;

public class KKMultiDataThread extends Thread {
	
	private static final Logger logger = LogManager.getLogger(KKMultiDataThread.class);
	
	private DatagramPacket peticion = null;
	private DatagramSocket socketUDP = null;
	
    public KKMultiDataThread() {
    	
    }
    
	public KKMultiDataThread(DatagramSocket socketUDP, DatagramPacket udpClient) {
		logger.info("Nuevo Hilo "+Calendar.getInstance().getTimeInMillis());
		this.socketUDP = socketUDP;
		this.peticion = udpClient;
	}

	public void run() {
				
        try  {
                       
        	logger.debug("Datagrama recibido del host: " + peticion.getAddress());
        	logger.debug(" la data: " + StringTools.toHexString(peticion.getData()));
        	logger.debug(" desde el puerto remoto: " + peticion.getPort());
 
			
			// Grabamos en BBDD
			CalAmpEvent2 myEvent = (new Util()).getEvent(peticion.getData());
			FacadeDaemon facadeDaemon = new FacadeDaemon();
			boolean stateSave = facadeDaemon.saveEvent(myEvent);
			
			if (stateSave) {
				// Construimos el DatagramPacket para enviar la respuesta
				DatagramPacket respuesta = new DatagramPacket(peticion.getData(), peticion.getLength(),
						peticion.getAddress(), peticion.getPort());

				// Enviamos la respuesta, que es un eco
				socketUDP.send(respuesta);
			} else {
				logger.warn("No hay conexion con BBDD");
			}
         
        } catch (Exception e) {
           logger.error("",e);
        }
    }
	
	
	void handlePacket(byte[] line) throws IOException{
		 /* handle packet, and get response */
        if ((line != null) ) {
            //try {
                byte response[] = getHandlePacket(line);

                if ((response != null) && (response.length > 0)) {
                    /*if (this.client.isTCP()) {

                        // TCP: Send response over socket connection
                        Print.logDebug("TCP Response 0x%s", StringTools.toHexString(response));
                        this._tcpWrite(output, response);    // TCP write: synchronous
                    } else*/ {

                        // UDP: Send response via datagram ('ServerSocketThread.this.datagramSocket' is non-null)
                        //int rp = this._getRemotePort(clientHandler.getResponsePort());

                        this.sendUDPResponse(peticion.getAddress(), peticion.getPort(), response);
                    }
                } else {

                    // Print.logInfo("No response requested");
                }

                /*if (clientHandler.terminateSession()) {
                    break;
                }*/
            /*} catch (Throwable t) {

                // the ClientPacketHandler can terminate this session
                //Print.logException("Unexpected exception: ", t);

                //break;
            }*/
        }
	}
	
	
	private void sendUDPResponse(InetAddress address, int port, byte[] buffer) throws IOException {
		// Construimos el DatagramPacket para enviar la respuesta
        DatagramPacket respuesta =
          new DatagramPacket(buffer, buffer.length,
        		  address, port);

        // Enviamos la respuesta, que es un eco
        socketUDP.send(respuesta);
	}

	public byte[] getHandlePacket(byte[] arrby) {
        if (arrby == null) {
            //Print.logError((String)"Packet is null", (Object[])new Object[0]);
            return null;
        }
             
        System.out.println((String)("\n" + (new Util()).getEvent(arrby).toString()));
        
        byte[] buffer = "0123456789".getBytes();
	    return buffer;
	}
	
	
}
