package com.micro.c2system.command.service;

import com.micro.c2system.pojo.Command;

public interface CommandService {

	public void sendToMQTTBroker(String tenantId,String machine,Command command);

	public void sendPolicy(String policy); 
	
}
