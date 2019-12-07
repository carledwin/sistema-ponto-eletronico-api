package com.wordpress.carledwinti.sistema.ponto.eletronico.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	public static String getNewBCrypt(String pass) {
		if(pass == null) {
			return pass;
		}
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(pass);
	}
}
