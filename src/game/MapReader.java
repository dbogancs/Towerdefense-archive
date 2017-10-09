package game;

public class MapReader 
{
	private String mapName;
	private byte x=0;
	private byte y=0;
	String[][] map=null;
	
	
	public MapReader(String mName) throws Exception
	{
		mapName = mName;
		load();
	}
	
	public MapReader() {}
	
	public void print()
	{
		if (map!=null)
		{
			for (int i=0; i<y; i++)
			{
				for (int j=0; j<x; j++)
					System.out.print(map[j][i]);
				System.out.println();
			}
		}
	}
	
	public void load(String mName) throws Exception
	{
		mapName = mName;
		load();
	}

	public String getField(int x, int y)
	{
		return map[x][y];
	}
	
	public byte getX() 		{return x;}
	public byte getY() 		{return y;}


	private void load() throws Exception
	{
		if(mapName.indexOf(".map") == -1 || !mapName.substring(mapName.length() - 4, mapName.length()).equals(".map"))
			mapName = mapName + ".map";
		
		LineReader trd = new LineReader(mapName);
		String line = trd.getNextLine();
		x = (byte)Integer.parseInt(line.split(" ")[0]);
		y = (byte)Integer.parseInt(line.split(" ")[1]);
		map = new String[x][y];
		for (int i=0; i < y; i++)
		{
			if ((line = trd.getNextLine())==null)
			{
				System.out.println("Map isn't correct: row failure!");
				System.exit(-1);
			}
			if (line.length()<x)
			{
				System.out.println("Map isn't correct: column failure!");
				System.exit(-1);
			}
			for (int j=0; j<x; j++)
			{
				map[j][i] = Character.toString(line.charAt(j));
			}
		}
	}
}