package com.tanoshi.codestar.earth;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.net.URL;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import com.tanoshi.codestar.earth.Satellite;

public class SatelliteSet {

  private static final String JSON_IMAGE  = "image";
  private static final String JSON_DATE   = "date";
  private static final String PRE_API     = "https://api.nasa.gov/EPIC/archive/natural/";
  private static final String PATTERN_URL = "http://i.imgur.com/vtpWDld.jpg";

	private HashMap<String,Satellite> pixelEarths;
	private int year, month, day;
	private String apiKey;
	
	public SatelliteSet(){}
	
	public SatelliteSet(int year, int month, int day, String key) {
	  this.year = year;
	  this.month = month;
	  this.day = day;  
	  this.apiKey = key;
	  this.pixelEarths = new LinkedHashMap<String,Satellite>();
	}

	public SatelliteSet init(String jsonResponse) {
		
		JSONArray responseArray = new JSONArray(jsonResponse);
		String base64 = "";

		try {
			URL pUrl = new URL(PATTERN_URL);
			BufferedImage pattern = ImageIO.read(pUrl);
	
			for (int i = 0; i < responseArray.length(); i++) {
				JSONObject current = (JSONObject) responseArray.get(i);
	
				URL satelliteUrl = new URL(epicImage(year, month, day, current.get(JSON_IMAGE).toString()));
				String time = current.get(JSON_DATE).toString().replaceAll("^[^\\s]*\\s","");
	
				BufferedImage satelliteImage = ImageIO.read(satelliteUrl);
				//URL url2 = new URL("http://i.imgur.com/64z7crx.jpg"); //temp
				//BufferedImage satelliteImage = ImageIO.read(url2);	  //temp
				BufferedImage blended = Pixelate.getPixelated(satelliteImage, pattern);
				base64 = Pixelate.getBase64(blended);

				pixelEarths.put(time, new Satellite(time,base64));
			}
		} catch (IOException e) {
			System.out.println("Error McErrorFace");
		}
		return this;
	}
	
	public String toJson() {
  	JSONObject setJson = new JSONObject(); 
    setJson.put("year", year);
    setJson.put("month", month);
    setJson.put("day", day);
		
		JSONArray array = new JSONArray();	
		for(String k : pixelEarths.keySet()){
		  JSONObject satellite = new JSONObject();
			satellite.put("time", k);
			satellite.put("image", pixelEarths.get(k).getImg());
			array.put(satellite);
		}
		
		setJson.put("satellites", array);
				
		return setJson.toString();  
	}
    
	private String epicImage(int y, int m, int d, String imgName) {
  	return PRE_API + year + "/" + String.format("%02d",month) + "/" + String.format("%02d", day) + "/jpg/" + imgName + ".jpg?api_key="+ apiKey;
	}    
}