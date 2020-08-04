package com.jeremiasgames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jeremiasgames.main.Game;
import com.jeremiasgames.world.Camera;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6*16, 0, 16, 17);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7*16, 0, 16, 17);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6*16, 17, 16, 17);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7*16, 17, 16, 17);
	
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
	}
	
	public void tick() {

	}
}
