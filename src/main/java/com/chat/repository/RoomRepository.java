package com.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chat.entities.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
	
	public Room findByRoomId(String roomId);

}
