package comiot.backend;

import java.util.Scanner;

import comiot.core.application.server.Server;

public class Main {
	
	/*
	public static void main(String[] args) {

	}
	*/
	public static void oldBackend() {
		Server comiotBackend = new Server();
		System.out.println("Welcome to COMIOT");
		System.out.println("Starting DEMO server");
		/* Launch */
		comiotBackend.run();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Press 0 to exit");
		while (scanner.nextInt() != 0) {
			
		}
		System.out.println("Closing server... almost done");
		scanner.close();
		comiotBackend.deleteUsers();
		System.out.println("bye!");
	}
}
