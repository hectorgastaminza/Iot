package comiot.client.raspberry.device;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import comiot.core.device.eDeviceStates;

public class DeviceRaspberryGpio extends comiot.core.device.Device {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int PIN_FIRST = 0;
	public static final int PIN_LAST = 31;
	
	/** 
	 * http://pi4j.com/usage.html
	 * http://pi4j.com/apidocs/com/pi4j/io/gpio/RaspiPin.html
	 */
	final GpioController gpio;
	GpioPinDigitalOutput myLed;

	public DeviceRaspberryGpio(int place, int id, int pin) {
		super(place, id);
		Pin myPin = ((pin >= PIN_FIRST) && (pin <= PIN_LAST)) ? RaspiPin.getPinByAddress(pin) : RaspiPin.GPIO_04;
		
		gpio = GpioFactory.getInstance();
		// provision gpio pins #04 as an output pin and make sure is is set to LOW at startup
		myLed = gpio.provisionDigitalOutputPin(myPin, "My LED", PinState.LOW);
		// configure the pin shutdown behavior; these settings will be
		// automatically applied to the pin when the application is terminated
		// ensure that the LED is turned OFF when the application is shutdown
		myLed.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	}
	
	@Override
	public boolean on() {
		myLed.high();
		return super.on();
	}
	
	@Override
	public boolean off() {
		myLed.low();
		return super.off();
	}
	
	@Override
	public boolean reset() {
		myLed.low();
		return super.reset();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		gpio.shutdown();
	}

}
