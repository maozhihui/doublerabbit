package com.comba.mqtt.message.comba;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataParserTest {

	@Test
	public void testConvertToKvEntry() {
		String orginStr = "eyJ0eXBlIjoidGVzdCIsImRhdGEiOiIxIn0=";
		DataParser.convertToKvEntry(orginStr);
	}

}
