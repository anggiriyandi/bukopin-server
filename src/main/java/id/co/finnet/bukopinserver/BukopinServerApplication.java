package id.co.finnet.bukopinserver;

import org.jpos.q2.Q2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BukopinServerApplication {

	public static void main(String[] args) {
                Q2 q2 = new Q2();
                q2.start();
                
		SpringApplication.run(BukopinServerApplication.class, args);
	}
}