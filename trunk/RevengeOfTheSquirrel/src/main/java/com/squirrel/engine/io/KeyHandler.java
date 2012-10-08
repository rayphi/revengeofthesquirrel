package com.squirrel.engine.io;

public interface KeyHandler {
	void pressed(Integer keyCode);
	void typed(Integer keyCode);
	void released(Integer keyCode);
}
