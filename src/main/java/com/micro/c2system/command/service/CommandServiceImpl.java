package com.micro.c2system.command.service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.reflect.TypeToken;
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
	Type mapType= new TypeToken<Map<String,Object>>() {}.getType();
	
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

	@Override
	public void sendPolicy(String policy) {
		Map<String,Object> policyObj=gson.fromJson(policy, mapType);
		List<Map<String, String>> machines= (List<Map<String, String>>) policyObj.get("machines");
		List<Map<String, String>> rules= (List<Map<String, String>>) policyObj.get("rules");
		mqttConnector.getMqttAsyncClient().ifPresent((publisher)->{
			MqttMessage msg =  new MqttMessage(gson.toJson(rules).getBytes()); 
	    	msg.setQos(0);
	    	msg.setRetained(true);
			for(Map<String, String> machine :machines) {
				MQTTAsync.publish(publisher, machine.get("tenant_Id")+"/"+machine.get("mac_address"), msg);
			}
		});
	}

}
