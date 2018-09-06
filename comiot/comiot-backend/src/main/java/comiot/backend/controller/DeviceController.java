package comiot.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.device.Device;

@RestController
public class DeviceController {
	
	@Autowired
	public UserModel userModel;

	@RequestMapping("/device/get")
	public List<Device> getDeviceGet(@RequestParam(value="userpk", defaultValue="-1") int userpk) {
		return userModel.deviceGetList(userpk);
	}
	
	@ResponseBody @RequestMapping("/device/new")
	public boolean getDeviceNew(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestBody Device device) {
		return userModel.deviceNew(userpk, device);
	}
	
	@ResponseBody @RequestMapping("/device/update")
	public boolean getDeviceUpdate(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestBody Device device) {
		return userModel.deviceUpdate(userpk, device);
	}
	
	@ResponseBody @RequestMapping("/device/delete")
	public boolean getDeviceDelete(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestBody Device device) {
		return userModel.deviceDelete(userpk, device);
	}
}
