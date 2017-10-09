package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;


public class LineReader 
{
	private Vector<String> tomb;
	private int activeLine;

	public String getNextLine()
	{
		if (tomb.equals(null))
			return null;
		if (tomb.size() <= activeLine)
			return null;
		return tomb.get(activeLine++);
	}
	
	public void reset()
	{
		activeLine = 0;
	}
	
	private void readFile(String fileName) throws Exception
	{
		BufferedReader input = null;

		try{
			input = new BufferedReader(new FileReader(fileName));
		} catch(Exception ex){
			throw ex;
		}
       
	    tomb = new Vector<String>();
        String line;
	        
		while((line = input.readLine())!= null)
		{
			if (line.length()>0)
				tomb.add(line);
		}
		input.close();
	}
	
	public LineReader(String fileName) throws Exception
	{
		activeLine = 0;
		tomb = null;

		readFile(fileName);
	}
}
