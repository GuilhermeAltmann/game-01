package com.jeremiasgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.security.auth.x500.X500Principal;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.Camera;
import com.jeremiasgames.world.World;

public class Player extends Entity{

	public boolean right,up,left,down;
	private final int RIGHT_DIR = 0, LEFT_DIR = 1; 
	private int dir = RIGHT_DIR;
	public double speed = 2;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public Player(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		for(int i= 0; i < rightPlayer.length; i++)
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 17);
		
		for(int i= 0; i < leftPlayer.length; i++)
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 17, 16, 17);
	}
	
	@Override
	public void tick() {
		moved = false;
		if(right && World.isFree((int)(this.getX() + speed), this.getY())) {
			
			moved = true;
			dir = RIGHT_DIR;
			this.setX(this.getX() + speed);
		}else if(left && World.isFree((int)(this.getX() - speed), this.getY())) {
			
			moved = true;
			dir = LEFT_DIR;
			this.setX(this.getX() - speed);
		}
		
		if(up && World.isFree(this.getX(), (int)(this.getY() - speed))) {
			
			moved = true;
			this.setY(this.getY() - speed);
		}else if(down && World.isFree(this.getX(), (int)(this.getY() + speed))) {
			
			moved = true;
			this.setY(this.getY() + speed);
		}
		
		if(moved) {
			
			frames++;
			
			if(frames == maxFrames) {
				
				frames = 0;
				index++;
				
				if(index > maxIndex)
					index = 0;
			}
			
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, (World.WIDTH * 16) - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, (World.HEIGHT * 17)  - Game.HEIGHT);
	}
	 
	
	@Override
	public void render(Graphics g) {
		
		if(dir == RIGHT_DIR) {
			
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}else if(dir == LEFT_DIR){
			
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
	}

}
