package org.bugsky.tools.json;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jackson {
	
	@Test
	public void testDeserialize() throws JsonParseException, JsonMappingException, IOException
	{
		Room room = new Room();
		room.setName("i'm a room");
		Door door = new Door();
		door.setName("i'm a door");
		room.setDoor(door);
	
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		String json = mapper.writeValueAsString(room);
		
		Room results = mapper.readValue(json,Room.class);
		
		System.out.println(mapper.writeValueAsString(results));
		
		Something<Room> some = new Something<Room>();
		some.setT(room);
		
		json = mapper.writeValueAsString(some);
		System.out.println(json);
		
		some = mapper.readValue(json, new TypeReference<Something<Room>>() { });
		System.out.println(mapper.writeValueAsString(some));
		
		
	}
	
	@Test
	public void testJsonPointer() throws JsonProcessingException, IOException
	{
		Room room = new Room();
		room.setName("i'm a room");
		Door door = new Door();
		door.setName("i'm a door");
		room.setDoor(door);
		
		ObjectMapper mapper = new ObjectMapper(); 
		String json = mapper.writeValueAsString(room);
		
		JsonNode root = mapper.readTree(json);
		System.out.println(root.at("/door/name").asText());
	}
}
class Something<T>
{
	private String name;
	private T t;
	
	public Something(){}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

class Room
{
	private String name;
	private Door door;
	
	public Room()
	{
		
	}
	public Door getDoor() {
		return door;
	}
	public void setDoor(Door door) {
		this.door = door;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

class Door
{
	private String name;

	public Door()
	{
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
