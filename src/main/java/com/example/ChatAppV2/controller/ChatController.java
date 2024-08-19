package com.example.ChatAppV2.controller;

import com.example.ChatAppV2.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message recMessage(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        message.setContent(message.getSender() + " joined the chat");
        return message;
    }
}
