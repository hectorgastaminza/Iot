package servlet;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BackendConfig {
	static final String BACKEND_URL = "http://localhost:8888/";
	static final String USER_RECOVERY = BACKEND_URL + "user/recovery";
	static final String USER_SIGNUP = BACKEND_URL + "/user/signup";
	
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
	static <T> void serializerTest(T var, ObjectMapper objMap, T classType){
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
