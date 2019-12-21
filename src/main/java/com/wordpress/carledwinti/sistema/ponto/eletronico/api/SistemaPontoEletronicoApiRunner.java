package com.wordpress.carledwinti.sistema.ponto.eletronico.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SistemaPontoEletronicoApiRunner {

	public static void main(String[] args) {
		SpringApplication.run(SistemaPontoEletronicoApiRunner.class, args);
	}

}
