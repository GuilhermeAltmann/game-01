package com.jeremiasgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jeremiasgames.main.Game;

public class Tile {
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 17);
	public static int TILE_FLOOR_COLOR = 0xFF000000;
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 17);
	public static int TILE_WALL_COLOR = 0xFFFFFFFF;
	
	private BufferedImage sprite;
	private int x,y;
	
	
	public Tile(int x, int y, BufferedImage sprite) {
		
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
