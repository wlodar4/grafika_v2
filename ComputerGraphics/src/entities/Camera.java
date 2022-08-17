package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleArountPlayer = 0;
	
	
	private Vector3f position = new Vector3f(1,25,50);
	private float pitch = 10;
	private float yaw ;
	private float roll;
	
	private Player player;
	
	public Camera(Player player){
		this.player = player;
	}
	
	public void move(){
		calculateZomm();
		calculatePitch();
		calculateAngle();
		float horizontalDistance = CalculateHorizontalDistance();
		float verticalDistance = CalculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleArountPlayer);
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
	
	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleArountPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance;
	}
	
	private float CalculateHorizontalDistance() {
		return (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float CalculateVerticalDistance() {
		return (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	
	
	private void calculateZomm() {
		float zoomLevel = Mouse.getDWheel()*0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY()* 0.1f;
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngle() {
		if(Mouse.isButtonDown(0)) {
			float angleChange = Mouse.getDX() * 0.3f;
			angleArountPlayer -= angleChange;
		}
	}
	

}
