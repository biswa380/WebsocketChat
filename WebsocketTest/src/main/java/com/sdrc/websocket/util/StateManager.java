package com.sdrc.websocket.util;

//import java.util.List;


public interface StateManager {

	Object getValue(String key);
	void setValue(String Key, Object Value);


}