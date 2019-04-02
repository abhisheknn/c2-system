package com.micro.c2system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.micro.c2system.command.service.CommandService;
import com.micro.c2system.pojo.Command;

@RestController
@RequestMapping("/command")
public class CommandControllerImpl implements CommandController {

	@Autowired
	private CommandService commandService;
	
	@Override
	@RequestMapping(value="/send/{tenantId}/{machine}",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendCommand(@PathVariable(value="tenantId") String tenantId,@PathVariable(value="machine") String machine, @RequestBody Command command) {
		 commandService.sendToMQTTBroker(tenantId,machine,command);
	}
	
	@Override
	@RequestMapping(value="/send/policy",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendCommand( @RequestBody String policy) {
		commandService.sendPolicy(policy);
	}

}
