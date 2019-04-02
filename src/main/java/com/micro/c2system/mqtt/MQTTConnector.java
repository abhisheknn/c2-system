package com.micro.c2system.mqtt;

import java.util.Optional;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.springframework.stereotype.Component;

import com.micro.c2system.common.Constants;
import com.mirco.mqtt.MQTTAsync;

@Component
public class MQTTConnector {

	private Optional<IMqttAsyncClient> mqttAsyncClient;
	public Optional<IMqttAsyncClient> getMqttAsyncClient() {
		return mqttAsyncClient;
	}

	public MQTTConnector() {
		mqttAsyncClient=MQTTAsync.getClient("tcp://"+Constants.MQTTBROKER, Constants.C2SYSTEMCLIENTIDPUBLISHER);
		mqttAsyncClient.ifPresent((publisher)->{
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(false);
			options.setConnectionTimeout(10);
			try {
				MQTTAsync.connect(options, publisher, null, null);
			} catch (MqttSecurityException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
		});
	}
}
