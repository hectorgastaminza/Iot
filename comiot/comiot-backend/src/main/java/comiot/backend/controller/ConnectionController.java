package comiot.backend.controller;

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
import comiot.core.protocol.mqtt.MqttConnectionConfiguration;

@RestController
public class ConnectionController {
	
	@Autowired
	public UserModel userModel;
	
	@RequestMapping("/connection/get")
	public ResponseEntity<MqttConnectionConfiguration> getDeviceGet(@RequestParam(value="userpk", defaultValue="-1") int userpk) {
		MqttConnectionConfiguration mqttconfig = userModel.getConnection(userpk);
		return new ResponseEntity<MqttConnectionConfiguration>(mqttconfig, (mqttconfig != null)? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/connection/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MqttConnectionConfiguration> getConnectionUpdate(@RequestParam(value="userpk", defaultValue="-1") int userpk,
			@RequestBody MqttConnectionConfiguration mqttconfig) {
	    System.out.println("UserPK: " + userpk);
	    boolean retval = userModel.updateConnection(userpk, mqttconfig);
	    return new ResponseEntity<MqttConnectionConfiguration>(mqttconfig, retval? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}