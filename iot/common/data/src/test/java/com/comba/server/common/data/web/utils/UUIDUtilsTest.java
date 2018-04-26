//package com.comba.server.common.data.web.utils;
//
//import static org.junit.Assert.*;
//
//import java.util.UUID;
//
//import org.junit.Test;
//
//public class UUIDUtilsTest {
//
//	private final static String MAX_UUID_STR = "ffffffffffffffffffffffffffffffff";
//	private final static UUID MAX_UUID = UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff");
//
//	@Test
//    public void testUUIDConvertStr(){
//		assertEquals(MAX_UUID_STR, UUIDUtils.toUUID(MAX_UUID));
//	}
//
//	@Test
//	public void testStrConvertUUID(){
//		assertEquals(MAX_UUID, UUIDUtils.toUUID(MAX_UUID_STR));
//	}
//
//	@Test
//	public void testUUIDNotEquals(){
//		assertNotEquals(MAX_UUID, UUIDUtils.create());
//	}
//
//	@Test
//	public void testStrConvertUUIDFailed(){
//		String uuidStr = "abcdefg";
//		assertNull(UUIDUtils.toUUID(uuidStr));
//	}
//
//	@Test
//	public void testUUIDConvertStrFailed(){
//		UUID uuid = null;
//		assertTrue(UUIDUtils.toUUID(uuid).isEmpty());
//	}
//}
