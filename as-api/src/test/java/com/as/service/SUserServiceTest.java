package com.as.service;

import static org.junit.Assert.*;

import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.as.model.SUser;
import com.as.service.SUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SUserServiceTest {

	@Test
	public void testAA() {
		SUser sUser=new SUser();
		sUser.setUsername("aa");
		sUser.setPassword("bb");
		Map<String, Object> s = sUserService.login(sUser);
	}

	@Resource
	private SUserService sUserService;
}
