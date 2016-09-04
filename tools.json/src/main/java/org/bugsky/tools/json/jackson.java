package org.bugsky.tools.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class jackson {
	
	/**
	 * Test  JavaType and TypeReference
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
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
		
		some = mapper.readValue(json, TypeFactory.defaultInstance().constructParametricType(Something.class, Room.class));
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
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(10);
		list.add(11);
		door.setWindows(list);
		
		ObjectMapper mapper = new ObjectMapper(); 
		String json = mapper.writeValueAsString(room);
		
		JsonNode root = mapper.readTree(json);
		System.out.println(root.at("/door/name").asText());
		
		JsonNode child = root.at("/door/erro");
		System.out.println(child.toString());
		
		// functionally similar to serializing value into JSON and parsing JSON as tree(just like above), but more efficient
		root = mapper.valueToTree(room);
		System.out.println(root.at("/door/name").asText());
		System.out.println(root.at("/door/windows/1").asText());	
	}
	
	public void testJavaType() throws JsonProcessingException, IOException
	{
		String jsonstr = "\"json\"";
		ObjectMapper mapper = new ObjectMapper(); 
		String json = mapper.readValue(jsonstr, String.class);
		System.out.println(json);
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
	private List<Integer> windows;

	public Door()
	{
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getWindows() {
		return windows;
	}

	public void setWindows(List<Integer> windows) {
		this.windows = windows;
	}
}
