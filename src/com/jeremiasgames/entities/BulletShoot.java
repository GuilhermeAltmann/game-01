package com.jeremiasgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.Camera;

public class BulletShoot extends Entity{
	
	private int dx;
	private int dy;
	private int spd = 4;
	
	private int life = 30;
	private int curLife = 0;


	public BulletShoot(double x, double y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		
		this.setX(this.getX() + dx * spd);
		this.setY(this.getY() + dy * spd);
		
		curLife++;
	}
	
	public int getCurLife() {
		return curLife;
	}

	public void setCurLife(int curLife) {
		this.curLife = curLife;
	}
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	
	public void render(Graphics g) {
		
		g.setColor(Color.YELLOW);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}
}
