package comiot.core.device;

import java.util.Scanner;

public class KeyReadNonBlocking implements Runnable {

	private Scanner scanner;
	private boolean newKey = false;
	private String key;
	
	public boolean isNewKey() {
		return newKey;
	}
	
	public String getKey() {
		newKey = false;
		return key;
	}
	
	public KeyReadNonBlocking(Scanner scanner) {
		this.scanner = scanner;
	}
	
	@Override
	public void run() {
		if(scanner != null) {
			while(true) {
				key = scanner.nextLine();
				newKey = true;
			}
		}
	}

}
