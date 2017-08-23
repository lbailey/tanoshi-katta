package com.tanoshi.codestar.earth;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.InputStream;

import net.coobird.thumbnailator.*;

public final class Pixelate {
	
	private Pixelate(){}
	
	public static BufferedImage getPixelated(BufferedImage im, BufferedImage pattern) throws IOException {
		BufferedImage image = resize(im);
		return hardMix(image, pattern);
	}
	
	public static BufferedImage resize(BufferedImage im) throws IOException {
	    return Thumbnails.of(im).size(300, 300).asBufferedImage();
	}
	
	public static int saturate(int base, double gray, int level) {
		int saturated = (int) (-gray * level) + (base * (1+level));
		return saturated < 0 ? 0 : saturated > 255 ? 255 : saturated;
	}
	
	public static int colorDodge(int base, int blend) {
		return  blend == 255 ? blend : Math.min(255, ((base << 8) / (255 - blend))); 
	}
	
	public static int colorBurn(int base, int blend) {
		return  blend == 0 ? blend : Math.max(0, (255 - ((255 - base << 8) / blend)));   
	}
	
	public static int vividLight(int base, int blend) {
		return blend < 128 ? colorBurn(base,(2 * blend)) : colorDodge(base,(2 *(blend - 128)));
	}
	
	public static int hardMix(int base, int blend) {
		return vividLight(base,blend) < 128 ? 0 : 255;
	}

	public static BufferedImage hardMix(BufferedImage base, BufferedImage blend) {
		return rgbPixelate(base, blend);
	}
	
	
	private static BufferedImage rgbPixelate(BufferedImage image, BufferedImage blend) {
	
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final byte[] pattern = ((DataBufferByte) blend.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = blend.getAlphaRaster() != null;
	
		BufferedImage blended = new BufferedImage(width, height, hasAlphaChannel ? BufferedImage.TYPE_4BYTE_ABGR : BufferedImage.TYPE_3BYTE_BGR);


		int r1, g1, b1, r2, g2, b2, rc, gc, bc,rgb;
		double gray;
		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
		 final int pixelLength = 4;
		 for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
			int argb = 0;
			argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
			argb += ((int) pixels[pixel + 1] & 0xff); // blue
			argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
			argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
			result[row][col] = argb;
		
			blended.setRGB(col, row, argb);
		
			col++;
			if (col == width) {
				 col = 0;
				 row++;
			}
		 }
		} else {
		 final int pixelLength = 3;
		 for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
			int argb = 0;
			argb += -16777216; // 255 alpha
			
			// base, satellite image
			b1 = ((int) pixels[pixel] & 0xff);
			g1 = ((int) pixels[pixel + 1] & 0xff);
			r1 = ((int) pixels[pixel + 2] & 0xff);
		
			// pattern image
			b2 = ((int) pattern[pixel] & 0xff);
			g2 = ((int) pattern[pixel + 1] & 0xff);
			r2 = ((int) pattern[pixel + 2] & 0xff);
			
			// blend mode Hard Mix
			bc = hardMix(b1,b2);
			gc = hardMix(g1,g2);
			rc = hardMix(r1,r2);
			
			// cheat saturate by removing gray instead of adding
			gray = 0.2989*rc + 0.5870*gc + 0.1140*bc; 
			bc = saturate(bc, gray, 400);
			gc = saturate(gc, gray, 400);
			rc = saturate(rc, gray, 400);
		
			/*
			argb += bc; // blue
			argb += (gc << 8); // green
			argb += (rc << 16); // red
			result[row][col] = argb;
			*/
			
			rgb = (rc << 16) | (gc << 8) | bc;
			blended.setRGB(col, row, rgb);
					 
					 
			col++;
			if (col == width) {
				 col = 0;
				 row++;
			}
		 }
		}
		return blended;
	}
	
	public static String getBase64(BufferedImage image) throws IOException {
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		byte[] imageInByteArray = baos.toByteArray();
		baos.close();
		return javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
	}

}