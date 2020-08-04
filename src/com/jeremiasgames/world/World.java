package com.jeremiasgames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jeremiasgames.entities.Bullet;
import com.jeremiasgames.entities.Enemy;
import com.jeremiasgames.entities.Entity;
import com.jeremiasgames.entities.LifePack;
import com.jeremiasgames.entities.Weapon;
import com.jeremiasgames.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	
	public static final int TILE_SIZE_X = 16;
	public static final int TILE_SIZE_Y = 17;
	
	public World(String path) {
		
		try {
			
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			
			WIDTH = map.getWidth();
			
			HEIGHT =  map.getHeight();
			
			int mapWidth = WIDTH * HEIGHT;
			
			int[] pixels = new int[mapWidth];
			
			tiles = new Tile[mapWidth];
			
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			
			int pos = 0;
			int pixel = 0;
			
			for(int x = 0; x < WIDTH; x++) {
				
				for (int y = 0; y < HEIGHT; y++) {
					
					pos = x + (y * WIDTH);
			
					pixel = pixels[pos];
					
					tiles[pos] = new FloorTile(x*16, y*17, Tile.TILE_FLOOR);
					
					if(pixel == Tile.TILE_FLOOR_COLOR) {
						
						tiles[pos] = new FloorTile(x*16, y*17, Tile.TILE_FLOOR);
					}else if(pixel == Tile.TILE_WALL_COLOR) {
						
						tiles[pos] = new WallTile(x*16, y*17, Tile.TILE_WALL);
					}else if(pixel == 0xFF0015FF) {
						
						Game.player.setX(x*16);
						Game.player.setY(y*17);
					}else if(pixel == 0xFFFF0400) {
						
						Game.entities.add(new Enemy(x*16,y*17, 16, 17, Entity.ENEMY_EN));
					}else if(pixel == 0xFFFF6100) {
						
						Game.entities.add(new Weapon(x*16,y*17, 16, 17, Entity.WEAPON_EN));
					}else if(pixel == 0xFFFF00AE) {
						
						Game.entities.add(new LifePack(x*16,y*17, 16, 17, Entity.LIFEPACK_EN));
					}else if(pixel == 0xFFFFE500) {
						
						Game.entities.add(new Bullet(x*16,y*17, 16, 17, Entity.BULLET_EN));
					}
					
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext) 
	{
		
		int x1 = xnext / TILE_SIZE_X;
		int y1 = ynext / TILE_SIZE_Y;
		
		int x2 = (xnext + TILE_SIZE_X - 1) / TILE_SIZE_X;
		int y2 = y1;
		
		int x3 = x1;
		int y3 = (ynext + TILE_SIZE_Y - 1) / TILE_SIZE_Y;
		
		int x4 = x2;
		int y4 = y3;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile ||
				tiles[x2 + (y2*World.WIDTH)] instanceof WallTile || 
				tiles[x3 + (y3*World.WIDTH)] instanceof WallTile ||
				tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x / 16;
		int ystart = Camera.y / 16;
		
		int xfinal = xstart + (Game.WIDTH / 16);
		int yfinal = ystart + (Game.HEIGHT / 16);
		for(int x = xstart; x < xfinal; x++) {
			for (int y = ystart; y < yfinal; y++) {
				if(x < 0 ||  y < 0 || x >= WIDTH || y >= HEIGHT)
					continue;
				
				Tile tile = tiles[x + (y * WIDTH)];
				tile.render(g);
			}
		}
	}
}
