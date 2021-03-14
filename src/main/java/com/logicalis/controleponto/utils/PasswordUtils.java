package com.logicalis.controleponto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.logicalis.controleponto.implementandoService.EmpresaServiceImpl;

public class PasswordUtils {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	public PasswordUtils() {
	}
	
	/**
	 * Gera um hosh utilizando o BCrypt
	 * 
	 */
	 
	 public static String gerarBCrypt(String senha) {
		if(senha == null) {
			return senha;
		} 
		
		log.info("Gerando hash com o BCrypt");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.encode(senha);
		
	 }
	 
	 public static boolean senhaValida(String senha, String senhaEncoded) {
		 BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
		 return cryptPasswordEncoder.matches(senha, senhaEncoded);
	 }
}