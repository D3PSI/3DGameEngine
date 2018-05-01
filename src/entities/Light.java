package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {

	private Vector3f position;
	private Vector3f colours;
	
	public Light(Vector3f position, Vector3f colours) {
		this.position = position;
		this.colours = colours;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColour() {
		return colours;
	}

	public void setColours(Vector3f colours) {
		this.colours = colours;
	}
	
	
}
