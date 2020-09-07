package com.jeremiasgames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.Camera;
import com.jeremiasgames.world.World;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 17);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 17);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6*16, 17, 16, 17);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7*16, 17, 16, 17);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(128, 0, 16, 17);
	public static BufferedImage GUN_LEFT  = Game.spritesheet.getSprite(144, 0, 16, 17);
	
	private double x;
	private double y;
	private int width;
	private int height;
	
	private BufferedImage sprite;
	
	public Entity(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public int getX() {
		return (int) x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int) y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.x, getY() - Camera.y, null);
		//g.setColor(Color.red);
		//g.fillRect(getX() - Camera.x, getY() - Camera.y, width, height);
	}
	
	public void tick() {

	}
	
	public static boolean isColidding(Entity e1, Entity e2) 
	{
		
		Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
}
