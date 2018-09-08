package servlet;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BackendConfig {
	static final String BACKEND_URL = "http://localhost:8888";
	static final String USER_RECOVERY = BACKEND_URL + "/user/recovery";
	static final String USER_SIGNUP = BACKEND_URL + "/user/signup";
	static final String USER_LOGIN = BACKEND_URL + "/user/login";
	static final String USER_UPDATE = BACKEND_URL + "/user/update";
	static final String DEVICE_GET = BACKEND_URL + "/device/get";
	static final String DEVICE_GET_BY_PK = BACKEND_URL + "/device/getbypk";
	static final String DEVICE_CREATE = BACKEND_URL + "/device/new";
	static final String DEVICE_UPDATE = BACKEND_URL + "/device/update";
	static final String DEVICE_DELETE = BACKEND_URL + "/device/delete";
	static final String CONNECTION_GET = BACKEND_URL + "/connection/get";
	static final String CONNECTION_UPDATE = BACKEND_URL + "/connection/update";
	
	
	
	static <T> HttpEntity<String> getHttpEntity(T body) throws JsonProcessingException {
		ObjectMapper objMap = new ObjectMapper();
		String strBody = objMap.writeValueAsString(body);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");

		return new HttpEntity <String> (strBody, httpHeaders);
	}
	
	
	/*
	serializerTest(new User(), objMap, User.class);
	serializerTest(new DBRecord(), objMap, DBRecord.class);
	serializerTest(new Device(), objMap, Device.class);
	serializerTest(new MqttConnectionConfiguration(), objMap, MqttConnectionConfiguration.class);
	*/
	static <T> void serializerTest(T var, T classType){
		ObjectMapper objMap = new ObjectMapper();
		String auxStr = null;
		T aux = null;
		try {
			auxStr = objMap.writeValueAsString(var);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		try {
			aux = objMap.readValue(auxStr, (Class<T>) classType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		aux.toString();
	}
}
