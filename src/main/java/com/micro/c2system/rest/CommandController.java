package com.micro.c2system.rest;

import com.micro.c2system.pojo.Command;

public interface CommandController {
public void sendCommand(String tenantId,String Machine, Command Command);
void sendCommand(String policy);
}
