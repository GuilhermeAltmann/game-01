package com.jeremiasgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.Camera;
import com.jeremiasgames.world.World;

public class Player extends Entity{

	public boolean right,up,left,down;
	private final int RIGHT_DIR = 0, LEFT_DIR = 1; 
	private int dir = RIGHT_DIR;
	public double speed = 2;
	
	public double life = 100, maxLife = 100;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public static BufferedImage playerDamage = Game.spritesheet.getSprite(0, 17, 16, 17);
	
	public int ammo = 0;
	
	public boolean shoot = false;
	
	public int damageFrames = 0;
	public boolean isDamaged = false;
	
	private boolean hasGun = false;
	
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
		
		
		if(isDamaged) {
			
			damageFrames++;
			
			if(damageFrames >= 10) {
				
				damageFrames = 0;
				isDamaged = false;
			}
		}
		
		if(shoot) {
			shoot = false;
			
			if(hasGun && ammo > 0) {
				
				ammo--;
				
				shoot = false;
				
				int px = 0;
				int py = 8;
				int dx = 0;
				if(dir == RIGHT_DIR) {
					px = 18;
					dx = 1;
				}else{
					px = -8;
					dx = -1;
				}
				
				BulletShoot bullet = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
				Game.bullets.add(bullet);
			}
		}
		
		if(life <= 0 ) {
			
			Game.initGame();
		}
		
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, (World.WIDTH * 16) - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, (World.HEIGHT * 17)  - Game.HEIGHT);
	}
	
	public void getWeapon() {
		
		hasGun = true;
	}
	
	@Override
	public void render(Graphics g) {
		
		if(!isDamaged) {
			
			if(dir == RIGHT_DIR) {
				
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
				if(hasGun) {
					g.drawImage(Entity.GUN_RIGHT, this.getX() + 8 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
			}else if(dir == LEFT_DIR){
				
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				
				if(hasGun) {
					g.drawImage(Entity.GUN_LEFT, this.getX() - 8 - Camera.x, this.getY() + 1 - Camera.y, null);
				}
			}
		}else {
			
			g.drawImage(playerDamage, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		
		
	}

}
