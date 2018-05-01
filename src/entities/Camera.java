package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f(0, 10, 0);
	private float pitch;
	private float yaw;
	private float roll;
	private float speed;
	public static final float SENSITIVITY = 0.2f;
	
	public Camera() {
		this.speed = 0.5f;
	}
	
	public void move(){
		
		if (pitch >= 90)
		{
			pitch = 90;
		}
		else if (pitch <= -90)
		{
			pitch = -90;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z += -(float)Math.cos(Math.toRadians(yaw)) * speed;
			position.x += (float)Math.sin(Math.toRadians(yaw)) * speed;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z -= -(float)Math.cos(Math.toRadians(yaw)) * speed;
			position.x -= (float)Math.sin(Math.toRadians(yaw)) * speed;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			yaw -= 1;
		}else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			yaw += 1;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			pitch -= 1;
		}else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			pitch += 1;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			position.y += 1;
		}else if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			position.y -= 1;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.z += (float)Math.sin(Math.toRadians(yaw)) * speed;
			position.x += (float)Math.cos(Math.toRadians(yaw)) * speed;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.z -= (float)Math.sin(Math.toRadians(yaw)) * speed;
			position.x -= (float)Math.cos(Math.toRadians(yaw)) * speed;
			}
		}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}
