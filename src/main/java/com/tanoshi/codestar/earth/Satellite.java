package com.tanoshi.codestar.earth;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Satellite {

	private String time;
	private String b64;

	public Satellite(String time, String b64) { 
		this.time = time;
		this.b64 = b64;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getImg() {
		return b64;
	}
}