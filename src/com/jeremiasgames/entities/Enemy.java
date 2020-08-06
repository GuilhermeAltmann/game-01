package com.jeremiasgames.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.World;

public class Enemy extends Entity{

	private double speed = 1;
	
	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick() {
		
		
			
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

}
