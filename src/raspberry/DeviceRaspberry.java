package raspberry;

import device.eDeviceStates;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.event.PinEventType;

public class DeviceRaspberry3BPlus extends device.Device {
	/** 
	 * http://pi4j.com/usage.html
	 */
	final GpioController gpio;
	GpioPinDigitalOutput myLed;
	
	public DeviceRaspberry3BPlus(int place, int id, RaspiPin pin) {
		super(place, id);
		gpio = GpioFactory.getInstance();
		// provision gpio pins #04 as an output pin and make sure is is set to LOW at startup
		GpioPinDigitalOutput myLed = gpio.provisionDigitalOutputPin(pin,   				// PIN NUMBER
		                                                           "My LED",           	// PIN FRIENDLY NAME (optional)
		                                                           PinState.LOW);      	// PIN STARTUP STATE (optional)		
	}
	
	public boolean reset() {
		setState(eDeviceStates.NONE);
		return true;
	}
	
	public boolean on() {
        // explicitly set a state on the pin object
        myLed.setState(PinState.HIGH);
		setState(eDeviceStates.ON);
		return true;
	}
	
	public boolean off() {
        // explicitly set a state on the pin object
        myLed.setState(PinState.LOW);
		setState(eDeviceStates.OFF);
		return true;
	}
	
	public boolean up() {
		setState(eDeviceStates.ON_VALUE);
		return true;
	}
	
	public boolean down() {
		setState(eDeviceStates.ON_VALUE);
		return true;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

}
