package application;

import java.util.ArrayList;

import device.Device;

public class Place {
	private String mqttTopic;
	private String description;
	private ArrayList<Device> devices;
	
	public Place() {
		devices = new ArrayList<>();
	}
	
	public String getTopic() {
		return mqttTopic;
	}
	
	public void setTopic(String topic) {
		this.mqttTopic = topic;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
