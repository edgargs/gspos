package com.gs.hacom.dcs;

import hacom.pe.datos.entidades.Event;

public interface StrategyDevice {

	byte[] createPacket_ACK(boolean bl);

	Event getEvent(byte[] arrby);

}
