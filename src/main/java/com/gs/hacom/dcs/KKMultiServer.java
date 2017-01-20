package com.gs.hacom.dcs;
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KKMultiServer {
	
	private static final Logger logger = LogManager.getLogger(KKMultiServer.class);
	
    public static void main(String[] args) throws IOException {
    	String strPort = "";
	    if (args.length == 0) {
	    	strPort = "20700";
	    } else if (args.length == 1) {
	    	strPort = args[0];
	    } else if (args.length != 1) {
	        logger.error("Usage: java KKMultiServer <port number>");
	        System.exit(1);
	    }

	    
        int portNumber = Integer.parseInt(strPort);
        boolean listening = true;
        boolean isUDP = true;
        if (!isUDP) {
	        /*try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
	            while (listening) {
		            new KKMultiServerThread(serverSocket.accept()).start();
		        }
		    } catch (IOException e) {
	            System.err.println("Could not listen on port " + portNumber);
	            System.exit(-1);
	        }*/
        } else {
	        try (DatagramSocket datagramSocket = new DatagramSocket(portNumber)) { 
	            while (listening) {
	            	byte           bufer[] = new byte[1024];
	        		DatagramPacket peticion  = new DatagramPacket(bufer, bufer.length);
	        		datagramSocket.receive(peticion);
		            new KKMultiDataThread(datagramSocket,peticion).start();
		        }
		    } catch (IOException e) {
	            logger.error("Could not listen on port " + portNumber);
	            System.exit(-1);
	        }
        }
    }
}
