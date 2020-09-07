package com.jeremiasgames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.jeremiasgames.entities.Bullet;
import com.jeremiasgames.entities.BulletShoot;
import com.jeremiasgames.entities.Enemy;
import com.jeremiasgames.entities.Entity;
import com.jeremiasgames.entities.LifePack;
import com.jeremiasgames.entities.Player;
import com.jeremiasgames.entities.Weapon;
import com.jeremiasgames.graficos.Spritesheet;
import com.jeremiasgames.graficos.UI;
import com.jeremiasgames.world.World;

public class Game extends Canvas implements Runnable,KeyListener{

	
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning;
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	private final int SCALE = 4;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static Spritesheet spritesheet;
	public static List<BulletShoot> bullets;
	public static World world;
	
	public static Random rand;
	
	public static Player player;
	
	public UI ui;
	
	public Game() {
		
		rand = new Random();
		
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		ui = new UI();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		initGame();
		
	}
	
	public static void initGame() {
		
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		
		spritesheet = new Spritesheet("/spritesheet.png");
		
		player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 17));
		
		world = new World("/map.png");
		
		entities.add(player);
	}
	
	private void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		
		isRunning = false;
		
		try {
			thread.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) { 
		Game game = new Game();
		game.start();
	}
	
	private void tick() {
	
		for(Iterator<Entity> iterator = Game.entities.iterator(); iterator.hasNext();) {
			
			Entity entity = iterator.next();
			entity.tick();
		}
		
		for(Iterator<BulletShoot> iterator = Game.bullets.iterator(); iterator.hasNext();) {
			
			BulletShoot bullet = iterator.next();
			bullet.tick();
		}
		
		checkItems();
		
		checkBulletLife();

	}
	
	public void checkBulletLife() 
	{
		for(Iterator<BulletShoot> iterator = bullets.iterator(); iterator.hasNext();) 
		{

			BulletShoot bullet = iterator.next();
			
			if(bullet.getCurLife() >= bullet.getLife()) 
			{
				
				iterator.remove();
			}
		}
		
	}
	
	public void checkItems() 
	{
		

		for(Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) 
		{

			Entity entity = iterator.next();
			if(entity instanceof LifePack) 
			{
				
				if(Entity.isColidding(player, entity)) {
					
					Game.player.life += 8;
					
					if(Game.player.life >= 100) {
						
						Game.player.life = 100;
					}
					
					iterator.remove();
				}
			}
			
			if(entity instanceof Bullet) 
			{
				
				if(Entity.isColidding(player, entity)) {
					
					Game.player.ammo+=12;
					
					iterator.remove();
				}
			}
			
			if(entity instanceof Weapon) 
			{
				
				if(Entity.isColidding(player, entity)) {
					
					Game.player.getWeapon();
					
					iterator.remove();
				}
			}
		}
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = image.getGraphics();
		
		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		for(Entity entity: entities) {
			
			entity.render(g);
		}
		
		for(BulletShoot bshoot : bullets) {
		
			bshoot.render(g);
		}
		//g.setColor(Color.CYAN);
		//g.fillRect(20, 20, 80, 80);
		
		//g.setFont(new Font("Arial", Font.BOLD, 20));
		//g.setColor(Color.WHITE);
		//g.drawString("Olá game", 30, 30);
		
		//Graphics2D g2 = (Graphics2D) g;

		ui.render(g, Game.player);
		
		g.dispose();
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		ui.renderPosDraw(g);
		
		bs.show();
	}
	
	@Override
	public void run() {
		
		load();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames  = 0;
		double timer = System.currentTimeMillis();
		
		requestFocus();
		
		while(isRunning) {
			
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				
				tick();
				render();
				
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				
				System.out.println("Fps:" + frames);
				frames = 0;
				timer+= 1000;
			}
		}
		
		stop();
	}
	
	private void load() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			
			player.down = true;
		}else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			
			player.up = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			
			player.down = false;
		}else if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			
			player.up = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_X){
			player.shoot = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
