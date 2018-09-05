package comiot.backend;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

@SpringBootApplication
public class RestServer {
	
    @Autowired
    public UserModel userModel;
    
    public static void main(String[] args) {
        SpringApplication.run(RestServer.class, args);
    }
    
    @PostConstruct
    public void init() {
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting DEMO server");
		/* Launch */
		userModel.run();
		/*
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press 0 to exit");
		while (scanner.nextInt() != 0) {
			
		}
		System.out.println("Closing server... almost done");
		scanner.close();
		userModel.deleteUsers();
		System.out.println("bye!");
		*/
    }

}
