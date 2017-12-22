package com.as;

import com.as.model.SUser;
import com.as.util.PasswordHelper;

public class PasswordTest {
	
	public void testName() throws Exception {
		SUser u=new SUser();
		u.setPassword("123456");
		u.setUsername("yang");
		new PasswordHelper().encryptPassword(u);
		System.out.println(u.getPassword());
	}
}
