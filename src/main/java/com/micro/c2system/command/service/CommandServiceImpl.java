package com.micro.c2system.command.service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.micro.c2system.mqtt.MQTTConnector;
import com.micro.c2system.pojo.Command;
import com.mirco.mqtt.MQTTAsync;

@Component
public class CommandServiceImpl implements CommandService {

	@Autowired
	MQTTConnector mqttConnector;
	Gson gson= new GsonBuilder().disableHtmlEscaping().create();
	
	@Override
	public void sendToMQTTBroker(String tenantId,String machine, Command command) {
		mqttConnector.getMqttAsyncClient().ifPresent((publisher)->{
			String commandJson=gson.toJson(command);
			MqttMessage msg =  new MqttMessage(commandJson.getBytes()); 
	    	msg.setQos(0);
	    	msg.setRetained(true);
			MQTTAsync.publish(publisher, tenantId+"/"+machine, msg);
		});
	}

}
