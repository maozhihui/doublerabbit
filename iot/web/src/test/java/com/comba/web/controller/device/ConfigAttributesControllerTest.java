//package com.comba.web.controller.device;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class ConfigAttributesControllerTest {
//
//	private MockMvc mockMvc;
//	@Autowired
//	WebApplicationContext webApplicationContext;
//
//	@Before
//	public void setUp() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//
//	@Test
//	public void testList() {
//		try {
//			this.mockMvc.perform(get("/configAttributes/list")).andDo(print()).andExpect(status().isOk())
//			.andExpect(forwardedUrl("/WEB-INF/views/device/configAttributes/configAttributes_list.jsp"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testGetAuditDataByPage() {
//		try {
//			this.mockMvc.perform(get("/configAttributes/datasByPage")).andDo(print()).andExpect(status().isOk())
//			.andExpect(content().string(containsString("pageNo")));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testDatasByExport() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUserAdd() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUserCheck() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testAddConfigAttributes() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdateConfigAttributes() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteGuser() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGet_ConfigAttributesData() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryConfigAttributes() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetConfigAttributes() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRespConfigAttributes() {
////		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testValidateConfigAttributeName() {
////		fail("Not yet implemented");
//	}
//
//}
