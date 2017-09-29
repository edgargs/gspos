package com.gs.hacom.dcs;

import java.net.DatagramPacket;

import hacom.pe.datos.entidades.Event;

public interface StrategyDevice {

	byte[] createPacket_ACK(boolean bl);

	Event getEvent(byte[] arrby);

	Event getEvent(DatagramPacket peticion);

}
