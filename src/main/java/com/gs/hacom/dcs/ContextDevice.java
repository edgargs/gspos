package com.gs.hacom.dcs;

import java.net.DatagramPacket;

import hacom.pe.datos.entidades.Event;

public class ContextDevice {

	private StrategyDevice strategy;
	
	public ContextDevice(StrategyDevice strategy) {
		this.strategy = strategy;
	}
	
	public byte[] createPacket_ACK(boolean bl) {
		return strategy.createPacket_ACK(bl);
	}

	public Event getEvent(byte[] arrby) {
		return strategy.getEvent(arrby);
	}

	public Event getEvent(DatagramPacket peticion) {
		return strategy.getEvent(peticion);
	}
}
