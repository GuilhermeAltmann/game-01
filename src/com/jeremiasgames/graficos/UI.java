package com.jeremiasgames.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.jeremiasgames.entities.Player;

public class UI {

	public void render(Graphics g, Player player) {
		
		g.setColor(Color.RED);
		g.fillRect(8, 6, 50, 8);
		
		g.setColor(Color.GREEN);
		g.fillRect(8, 6, (int)((player.life/player.maxLife)*50), 8);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("Hp: " + (int)(player.life), 29, 12);
	}
	
	public void renderPosDraw(Graphics g) 
	{
		
		
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		g.drawString("Munição: " + Player.ammo, 605, 20);
	}
}
