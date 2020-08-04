package com.jeremiasgames.world;

public class Camera {

	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int xCurrent, int xMin, int xMax) 
	{
		
		if(xCurrent > xMax) {
			
			xCurrent = xMax;
		}else if(xCurrent < xMin) {
			
			xCurrent = xMin;
		}
		
		return xCurrent;
	}
} 
