package com.chat.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.chat.entities.Message;
import com.chat.entities.Room;
import com.chat.payload.MessageRequest;
import com.chat.repository.RoomRepository;

@Controller
@CrossOrigin(origins = "${frontend.url}")
public class ChatController {
	
	@Autowired
	private RoomRepository roomRepository;
	
	
	@MessageMapping("/sendMessage/{roomId}")
	@SendTo("/topic/room/{roomId}")
	public Message sendMessage(@DestinationVariable String roomId ,@RequestBody MessageRequest req) {
		Room room = roomRepository.findByRoomId(req.getRoomId());
		Message msg=new Message();
		msg.setContent(req.getContent());
		msg.setSender(req.getSender());
		msg.setTimeStamp(LocalDateTime.now());
		
		if(room!=null) {
			room.getMessages().add(msg);
			roomRepository.save(room);
		}else {
			throw new RuntimeException("room not found");
		}
		return msg;
		
	}

}
