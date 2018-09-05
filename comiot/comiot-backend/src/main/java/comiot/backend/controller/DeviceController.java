package comiot.backend.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comiot.backend.UserModel;
import comiot.core.database.DBConnector;
import comiot.core.database.mysql.ConnectorMysql;
import comiot.core.device.Device;

@RestController
public class DeviceController {
	
	@Autowired
	public UserModel userModel;

	@RequestMapping("/device/get")
	public List<Device> getDeviceGet(@RequestParam(value="userpk", defaultValue="-1") int userpk) {
		return deviceGet(userpk);
	}
	
	@RequestMapping("/device/new")
	public boolean getDeviceNew(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestParam(value="device", defaultValue="-1") int device,
			@RequestParam(value="place", defaultValue="-1") int place) {
		return false;
	}
	
	@RequestMapping("/device/update")
	public boolean getDeviceUpdate(@RequestParam(value="userpk", defaultValue="-1") int userpk, 
			@RequestParam(value="device", defaultValue="-1") int device,
			@RequestParam(value="place", defaultValue="-1") int place) {
		return false;
	}
	
	private List<Device> deviceGet(int userPk){
		List<Device> devices = null;
		
		try {
			Connection conn = ConnectorMysql.getConnection();
			devices = DBConnector.devicesGetByUserPk(conn, userPk);		
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return devices;
	}
}
