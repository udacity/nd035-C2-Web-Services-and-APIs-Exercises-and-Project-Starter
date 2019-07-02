package com.example.consuming;

import com.dataaccess.webservicesserver.NumberToWordsResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@SpringBootApplication
public class ConsumingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		NumberClient numberClient = new NumberClient();

		//create and setup marshaller
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

		//provide location to the ObjectFacory
		marshaller.setContextPath("com.dataaccess.webservicesserver");

		//add marshaller to the client
		numberClient.setMarshaller(marshaller);
		numberClient.setUnmarshaller(marshaller);

		//retrieve the word format of the number
		NumberToWordsResponse response = numberClient.getWords("3454");

		//print to screen
		System.out.println("Response is: " + response.getNumberToWordsResult());
	}

}
