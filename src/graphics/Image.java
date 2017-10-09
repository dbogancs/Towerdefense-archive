package graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image 
{
	private BufferedImage picture;
	
	public Image(String file) throws IOException
	{
		picture = ImageIO.read(new File("img/" + file + ".png"));
	}
	
	public void draw(Graphics2D g, int x, int y)
	{
		g.drawImage(picture,x,y,null);
	}
}