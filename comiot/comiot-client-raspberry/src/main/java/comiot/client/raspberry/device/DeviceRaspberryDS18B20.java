package comiot.client.raspberry.device;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;

import comiot.core.device.Device;

/**
 * DS18B20 Digital temperature sensor
 * Based on https://www.javatips.net/api/pi4j-master/pi4j-device/src/main/java/com/pi4j/component/temperature/impl/TmpDS18B20DeviceType.java
 */
public class DeviceRaspberryDS18B20 extends Device implements ActionListener{
	
	public static final int READ_TEMP_PERIOD_MIN = 1;
	public static final int READ_TEMP_PERIOD_MAX = 10;
	
	private final int TEMPERATURE_INVALID_VALUE = -1;
	private final String deviceDir = "/sys/bus/w1/devices/28-800000293533";
    private int temperatureLastValid = TEMPERATURE_INVALID_VALUE;
    private Timer readingTimer;

	public DeviceRaspberryDS18B20(int place, int id, int periodMin) {
		super(place, id);

		int periodmSec =  (periodMin * 60 * 1000);
		
		readingTimer = new Timer(periodmSec, this);
	}
	
	@Override
	public boolean on() {
		readingTimer.start();
		actionPerformed(null);
		return super.on();
	}
	
	@Override
	public boolean off() {
		readingTimer.stop();
		return super.off();
	}
	
	@Override
	public boolean reset() {
		temperatureLastValid = TEMPERATURE_INVALID_VALUE;
		readingTimer.stop();
		return super.reset();
	}
    
	@Override
	public void actionPerformed(ActionEvent e) {
		readTemperature();
		
		if(temperatureLastValid > TEMPERATURE_INVALID_VALUE) {
			valueChange(temperatureLastValid);
		}
	}
    
	void readTemperature() {
		try {

			String temporatureStringValue = getTemporatureStringValue();
			int temperatureNewValue = parseValue(temporatureStringValue);
			if(temperatureNewValue > TEMPERATURE_INVALID_VALUE)
			{
				temperatureLastValid = temperatureNewValue;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public String getTemporatureStringValue() throws IOException {
        return new String(Files.readAllBytes(new File(deviceDir, "w1_slave").toPath()));
    }
    
    private final Pattern TEMP_VALUE_PATTERN = Pattern.compile("(?s).*crc=[0-9a-f]+ (?<success>[A-Z]+).*t=(?<temp>-?[0-9]+)");
    /**
     * # Typical reading
	 * # 73 01 4b 46 7f ff 0d 10 41 : crc=41 YES
	 * # 73 01 4b 46 7f ff 0d 10 41 t=23187
	 * 
     */
    private  int parseValue(final String value) throws Exception {
        int result = -1;
        final Matcher matcher = TEMP_VALUE_PATTERN.matcher(value.trim());
        if (matcher.matches()) {
            if (matcher.group("success").equals("YES")) {
                int tempValue = Integer.valueOf(matcher.group("temp"));
                tempValue = tempValue / 1000; 
                
                if(tempValue > 0)
                {
                	result = tempValue;
                }
            }
        }
        
        return result;
    }
}
