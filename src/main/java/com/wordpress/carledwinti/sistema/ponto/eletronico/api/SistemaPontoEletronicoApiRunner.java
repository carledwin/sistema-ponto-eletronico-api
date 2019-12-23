package com.wordpress.carledwinti.sistema.ponto.eletronico.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SistemaPontoEletronicoApiRunner {

	private static final Logger LOG = LoggerFactory.getLogger(SistemaPontoEletronicoApiRunner.class);

	public static void main(String[] args) {

		LOG.info("Starting the Application SPE ... \n Environment: " + System.getenv("spring.profiles.active"));
		SpringApplication.run(SistemaPontoEletronicoApiRunner.class, args);

	}

}
