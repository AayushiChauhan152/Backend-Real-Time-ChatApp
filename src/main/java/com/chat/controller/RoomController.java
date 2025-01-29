package com.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entities.Message;
import com.chat.entities.Room;
import com.chat.repository.RoomRepository;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = "${frontend.url}")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;
	
	
	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody Map<String, String> json ) {
		if(roomRepository.findByRoomId(json.get("roomId"))!=null) {
			return ResponseEntity.badRequest().body("Room already exists!!");
		}
		Room room=new Room();
		room.setRoomId(json.get("roomId"));
		System.out.println(json.get("roomId"));
		roomRepository.save(room);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(room);	
	}
	
	@GetMapping("/{roomId}")
	public ResponseEntity<?> joinRoom(@PathVariable String roomId){
		if(roomRepository.findByRoomId(roomId)==null) {
			return ResponseEntity.badRequest().body("Room doesn't exist!!");
		}
		Room room = roomRepository.findByRoomId(roomId);
		return ResponseEntity.ok(room);
	}
	
//	get messages of room
	@GetMapping("/{roomId}/messages")
	public ResponseEntity<List> getMessages(@PathVariable String roomId){
		Room room = roomRepository.findByRoomId(roomId);
		if(room==null) {
			return ResponseEntity.badRequest().build();
		}
		
//		get messages
		List<Message> messages = room.getMessages();
		return ResponseEntity.ok(messages);
	}
	
	
}
