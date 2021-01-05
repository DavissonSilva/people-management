package com.logicalis.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.logicalis.controleponto.utils.PasswordUtils;

public class PassawordUtilsTest {

	private static final String SENHA ="123456";
	private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
	
	@Test
	public void testSenhaNula() throws Exception{
		assertNull(PasswordUtils.gerarBCrypt(null));
		
	}
	@Test
	public void testGerarHashSenha() throws Exception{
		String hash = PasswordUtils.gerarBCrypt(SENHA);
		assertTrue(bCryptEncoder.matches(SENHA, hash));
	}
}
