package com.vitoria.ai.frauddetection;

import com.vitoria.ai.frauddetection.weka.DeteccaoDeFraudeBancaria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FraudDetectionApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(FraudDetectionApiApplication.class, args);

		DeteccaoDeFraudeBancaria detector = new DeteccaoDeFraudeBancaria();

		try {

			detector.definirAtributos();
			detector.adicionarExemplos();
			detector.treinamentoModelo();

			System.out.println(detector.classificarTransacao(5000, "internacional"));
			System.out.println(detector.classificarTransacao(150, "nacional"));
			System.out.println(detector.classificarTransacao(12000, "internacional"));
			System.out.println(detector.classificarTransacao(2500, "nacional"));
			System.out.println(detector.classificarTransacao(8000, "internacional"));
			System.out.println(detector.classificarTransacao(500, "nacional"));
			System.out.println(detector.classificarTransacao(9500, "internacional"));
			System.out.println(detector.classificarTransacao(300, "nacional"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}