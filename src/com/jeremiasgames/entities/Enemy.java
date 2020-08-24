package com.jeremiasgames.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.Camera;
import com.jeremiasgames.world.World;

public class Enemy extends Entity{

	private double speed = 1;
	
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
	
	private BufferedImage[] sprites;
	
	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		
		super(x, y, width, height, null);
		
		sprites = new BufferedImage[2];
		sprites[0] = Game.spritesheet.getSprite(112, 17, 16, 17);
		sprites[1] = Game.spritesheet.getSprite((112 + 16), 17, 16, 17);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick() {
		
		
		if(!isColiddingWithPlayer()) {
			
			
			if(this.getX() < Game.player.getX() && World.isFree((int) (this.getX() + speed), this.getY()) && !isConflict((int) (this.getX() + speed), this.getY())) {
				
				this.setX(this.getX() + speed);
			}else if(this.getX() > Game.player.getX() && World.isFree((int) (this.getX()- speed), this.getY()) && !isConflict((int) (this.getX()- speed), this.getY())) {
				
				this.setX(this.getX() - speed);
			}
			
			if(this.getY() > Game.player.getY() && World.isFree(this.getX(), (int) (this.getY() - speed)) && !isConflict(this.getX(), (int) (this.getY() - speed))) {
				
				this.setY(this.getY() - speed);
			}else if(this.getY() < Game.player.getY() && World.isFree(this.getX(), (int) (this.getY() + speed)) && !isConflict(this.getX(), (int) (this.getY() + speed))) {
				
				this.setY(this.getY() + speed);
			}
		}else {
			
			Game.player.life--;
			
			if(Game.rand.nextInt(100) < 10) {
				
				Game.player.life--;
				Game.player.isDamaged = true;
				
			}
		}
		

		frames++;
		
		if(frames == maxFrames) {
			
			frames = 0;
			index++;
			
			if(index > maxIndex)
				index = 0;
		}
	}
	
	public boolean isColiddingWithPlayer() 
	{
		
		Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), World.TILE_SIZE_X, World.TILE_SIZE_Y);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 17);
		
		return enemyCurrent.intersects(player);
	}
	
	public boolean isConflict(int xnext, int ynext) {
		
		Rectangle enemyCurrent = new Rectangle(xnext, ynext, World.TILE_SIZE_X, World.TILE_SIZE_Y);
		
		for(Enemy enemy: Game.enemies) {
			
			if(enemy == this) {
				
				continue;
			}else {
				
				Rectangle enemyRectangle = new Rectangle(enemy.getX(), enemy.getY(), World.TILE_SIZE_X, World.TILE_SIZE_Y);
				
				if(enemyCurrent.intersects(enemyRectangle)) {
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public void render(Graphics g) {
		
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
