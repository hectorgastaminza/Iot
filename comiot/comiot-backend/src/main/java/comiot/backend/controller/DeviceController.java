package comiot.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.application.server.Place;
import comiot.core.device.Device;

@RestController
public class DeviceController {
	
	@Autowired
	public UserModel userModel;

	@RequestMapping("/device/get")
	public ResponseEntity<List<Place>> getDeviceGet(@RequestParam(value="userpk", defaultValue="-1") int userpk) {
		List<Place> places = userModel.placeGetList(userpk);
		return new ResponseEntity<List<Place>>(places, (places != null)? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping("/device/getbypk")
	public ResponseEntity<Device> getDeviceGetByPk(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestParam(value="devicepk", defaultValue="-1") int devicepk) {
		Device device = userModel.deviceGetByPk(userpk, devicepk);
		return new ResponseEntity<Device>(device, (device != null)? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/device/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> getUserSignup(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestBody Device device) {
		System.out.println("Userpk: " + userpk + ". New device: " + device.toString());
		boolean retval = userModel.deviceNew(userpk, device);
		return new ResponseEntity<Boolean>(retval, retval? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/device/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> getDeviceUpdate(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestBody Device device) {
		System.out.println("Userpk: " + userpk + ". Update device: " + device.toString());
		boolean retval = userModel.deviceUpdate(userpk, device);
		return new ResponseEntity<Boolean>(retval, retval? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/device/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> getDeviceDelete(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestParam(value="devicepk", defaultValue="-1") int devicepk) {
		System.out.println("Userpk: " + userpk + ". Delete device: " + devicepk);
		boolean retval = userModel.deviceDeleteByPk(userpk, devicepk);
		return new ResponseEntity<Boolean>(retval, retval? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
