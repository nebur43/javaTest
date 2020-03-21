package es.iecisa;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
	  String port = "9090";
	  if (args!=null && args.length!=0) {
		  port = args[0];
	  }
	  
	  
	  SpringApplication app = new SpringApplication(Application.class);
      app.setDefaultProperties(Collections.singletonMap("server.port", port));
      app.run(args);
	  
//    SpringApplication.run(Application.class, args);
  }

}
