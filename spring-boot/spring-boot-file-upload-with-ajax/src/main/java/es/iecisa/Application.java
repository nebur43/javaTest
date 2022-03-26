package es.iecisa;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.pruebas.ftps.FTPSender;



@SpringBootApplication
public class Application {	

  public static void main(String[] args) {
	  System.out.println(" ********************* hola ****");
	  
	  try {
//		App.main(null);
		  FTPSender.main(null);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  System.out.println(" ********************* fin ****");
	  
//	  String port = "9090";
//	  if (args!=null && args.length!=0) {
//		  port = args[0];
//	  }
//	  
//	  
//	  SpringApplication app = new SpringApplication(Application.class);
//      app.setDefaultProperties(Collections.singletonMap("server.port", port));
//      app.run(args);
	  
//    SpringApplication.run(Application.class, args);
  }

}
