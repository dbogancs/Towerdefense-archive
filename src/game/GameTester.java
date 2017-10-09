package game;

/*
*/

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import entities.*;
import entities.obstacles.*;
import map.Field;

class GameTester extends Game
{

	enum State
	{
		RUN,
		PAUSE,
		MENU
	}

	private State state = State.MENU;
	private long timeElapsed = 0;
	
	public GameTester(Difficulty gD, long gTime) throws Exception
	{
		super(gD, gTime, null);
	}
	
	public void runTest(String testName) throws Exception
	{
		if(testName.indexOf(".test") == -1 || !testName.substring(testName.length() - 5, testName.length()).equals(".test"))
			testName = testName + ".test";
		
		LineReader testProgram = new LineReader(testName);
		String line;
		
		while (true)
		{
			line = testProgram.getNextLine();
			if (line==null) return;
			processCommand(line);
		}
	}
	
	private void processCommand(String cmd) throws Exception
	{
		String c = cmd.split(" ")[0];
		if (c.equals("loadmap")) 				loadMap(cmd);
		else if (c.equals("buyboosterrune")) 	buyboosterrune(cmd);
		else if (c.equals("buyobstacle")) 		buyobstacle(cmd);
		else if (c.equals("buyorc")) 			buyorc(cmd);
		else if (c.equals("buytower")) 			buytower(cmd);
		else if (c.equals("buyvsrune")) 		buyvsrune(cmd);
		else if (c.equals("fognow")) 			fognow(cmd);
		else if (c.equals("info")) 				info(cmd);
		else if (c.equals("mainmenu")) 			mainmenu(cmd);
		else if (c.equals("pause")) 			pause(cmd);
		else if (c.equals("place")) 			place(cmd);
		else if (c.equals("setdiff")) 			setdiff(cmd);
		else if (c.equals("setlimit")) 			setlimit(cmd);
		else if (c.equals("showmana")) 			showmana(cmd);
		else if (c.equals("showsettings"))		showsettings(cmd);
		else if (c.equals("spawnnow")) 			spawnnow(cmd);
		else if (c.equals("splitnow")) 			splitnow(cmd);
		else if (c.equals("start")) 			start(cmd);
		else if (c.equals("unpause")) 			unpause(cmd);
		else if (c.equals("wait")) 				waitT(cmd);
		else if (c.equals("savemap")) 			savemap(cmd);
	}
	
	private void loadMap(String cmd){
		String [] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 3 || !command[1].equals("-file"))
			fail = true;
		else
			try {
				initMap(command[2]);
			} catch (Exception e) {
				fail = true;
			}
		
		if (fail)
		{
			System.out.println("Invalid loadmap command!");
			System.exit(-1);
		}
	}
	
	private void buyboosterrune(String cmd)
	{
		if(state == state.MENU)
			return;
		
		boolean fail = false;
		String[] command = cmd.split(" ");
		if (command.length < 3 || !command[1].equals("-type"))
		{
			fail = true;
		}
		else 
		{
			if (command[2].equals("\"hp\"")) 			
				inputHandler.keyPressed('u');
			else if (command[2].equals("\"range\"")) 	
				inputHandler.keyPressed('i');
			else if (command[2].equals("\"speed\"")) 	
				inputHandler.keyPressed('o');
			else if (command[2].equals("\"lifetime\"")) 
				inputHandler.keyPressed('p');
			else if (command[2].equals("\"damage\"")) 	
				inputHandler.keyPressed('z');
			else fail = true;
		}
		if (fail)
		{
			System.out.println("Invalid buyboosterrune command!");
			System.exit(-1);
		}
	}
	
	private void buyobstacle(String cmd)
	{
		if(state == state.MENU)
			return;
		
		boolean fail = false;
		String[] command = cmd.split(" ");
		if (command.length < 3 || !command[1].equals("-type"))
		{
			fail = true;
		}
		else 
		{
			if (command[2].equals("\"physical\"")) 
				inputHandler.keyPressed('h');
			else if (command[2].equals("\"slowing\"")) 	
				inputHandler.keyPressed('j');
			else if (command[2].equals("\"wounding\"")) 
				inputHandler.keyPressed('k');
			else fail = true;
		}
		if (fail)
		{
			System.out.println("Invalid buyobstacle command!");
			System.exit(-1);
		}
	}
	
	private void buyorc(String cmd)
	{
		if(state == state.MENU)
			return;
		
		inputHandler.keyPressed('g');
	}
	
	private void buytower(String cmd)
	{
		if(state == state.MENU)
			return;
		
		boolean fail = false;
		String[] command = cmd.split(" ");

		if (command.length < 3 || !command[1].equals("-type"))
		{
			fail = true;
		}
		else 
		{
			if (command[2].equals("\"arrowshooter\"")) 		
				inputHandler.keyPressed('b');
			else if (command[2].equals("\"rockthrower\"")) 	
				inputHandler.keyPressed('n');
			else if (command[2].equals("\"spellcaster\"")) 	
				inputHandler.keyPressed('m');
			else fail = true;
		}
		if (fail)
		{
			System.out.println("Invalid buytower command!");
			System.exit(-1);
		}

	}
	private void buyvsrune(String cmd)
	{
		if(state == state.MENU)
			return;
		
		boolean fail = false;
		String[] command = cmd.split(" ");
		if (command.length < 3 || !command[1].equals("-type"))
		{
			fail = true;
		}
		else 
		{
			if (command[2].equals("\"elf\"")) 			
				inputHandler.keyPressed('y');
			else if (command[2].equals("\"hobbit\"")) 	
				inputHandler.keyPressed('x');
			else if (command[2].equals("\"dwarf\"")) 	
				inputHandler.keyPressed('c');
			else if (command[2].equals("\"human\""))	
				inputHandler.keyPressed('v');
			else fail = true;
		}
		if (fail)
		{
			System.out.println("Invalid buyvsrune command!");
			System.exit(-1);
		}
	}
	
	private void fognow(String cmd)
	{
		if(state == state.MENU)
			return;
		
		String [] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 4 || !command[1].equals("-coord")){
			fail = true;
		} else{
			try{
				int x = Integer.parseInt(command[2]);
				int y = Integer.parseInt(command[3]);
				
				Field field = topLeftCorner;
				
				while((x--) > 0) field = field.right;
				while((y--) > 0) field = field.down;
				
				Tower tower = field.getTower();
				if(tower != null)
					tower.fogNow();
			} catch (Exception ex){
				fail = true;
			}
		}
		
		if (fail)
		{
			System.out.println("Invalid buyvsrune command!");
			System.exit(-1);
		}
	}

	private void info(String cmd)
	{
		if(state == state.MENU)
			return;
		
		String[] command = cmd.split(" ");
		if(command.length < 4 || !command[1].equals("-coord")){
			System.out.println("Invalid info command!");
			System.exit(-1);
		}
		
		int x = Integer.parseInt(command[2]);
		int y = Integer.parseInt(command[3]);

		Field field = topLeftCorner;

		for (int i=0; i<x;i++) field = field.right;
		for (int i=0; i<y;i++) field = field.down;

		List<Unit> units 			= field.getUnits();
		Tower tower 				= field.getTower();
		TimedObstacle tObstacle 	= field.getTObstacle();
		PhysicalObstacle pObstacle 	= field.getPObstacle();
		List<Bullet> bullets 		= field.getBullets();

		System.out.println("[time = " + timeElapsed + "]");
		
		if(units != null) for (Unit u :units) u.testPrintInfo();
		if(bullets != null) for (Bullet b :bullets) b.testPrintInfo();
		if (tower!=null) tower.testPrintInfo();
		if (tObstacle!=null) tObstacle.testPrintInfo();
		if (pObstacle!=null) pObstacle.testPrintInfo();
	}
	
	private void mainmenu(String cmd)
	{
		System.exit(0); // why not?
		topLeftCorner = null;
		mountDoom = null;
		state = State.MENU;
	}

	private void pause(String cmd)
	{
		if(state == state.MENU)
			return;
		
		state = State.PAUSE;		
	}

	private void place(String cmd)
	{

		if(state == state.MENU)
			return;
		
		String[] command = cmd.split(" ");

		if(command.length < 4 || !command[1].equals("-coord")){
			System.out.println("Invalid place command!");
			System.exit(-1);
		}
		
		int x = Integer.parseInt(command[2]) * Field.fieldSize;
		int y = Integer.parseInt(command[3]) * Field.fieldSize;
		inputHandler.mouseClick(x,y);
		inputHandler.process(this,0);
	}

	private void setdiff(String cmd)
	{
		if(state != state.MENU)
			return;
		
		String[] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 3 || !command[1].equals("-to")){
			fail = true;
		} else {
			if (command[2].equals("\"easy\"")) difficulty = Difficulty.EASY;
			else if (command[2].equals("\"medium\"")) difficulty = Difficulty.MEDIUM;
			else if (command[2].equals("\"hard\"")) difficulty = Difficulty.HARD;
			else fail = true;
		}
		
		if(fail){
			System.out.println("Invalid setdiff command!");
			System.exit(-1);
		}
	}

	private void setlimit(String cmd)
	{
		if(state != state.MENU)
			return;
		
		String[] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 3 || !command[1].equals("-to")){
			fail = true;
		} else try{
			duration = 3600 * Integer.parseInt(command[2]);
		} catch(Exception ex){
			fail = true;
		}
		
		if(fail){
			System.out.println("Invalid setlimit command!");
			System.exit(-1);
		}
	}

	private void showmana(String cmd)
	{
		/// TODO
		//inputHandler.testPrintMana();
	}
	
	private void showsettings(String cmd)
	{
		System.out.println("Time left: " + (duration - timeElapsed));

		System.out.println("Difficulty: " + difficulty);
	}

	private void spawnnow(String cmd)
	{
		if(state == state.MENU)
			return;
		
		String[] command = cmd.split(" ");
		boolean fail = false;

		if(command.length < 6 || !command[1].equals("-type") || !command[3].equals("-coord")){
			fail = true;
		} else try {
			byte x = (byte) Integer.parseInt(command[4]);
			byte y = (byte) Integer.parseInt(command[5]);
			
			Field field = topLeftCorner;
			for (int i=0; i<x;i++) field = field.right;
			for (int i=0; i<y;i++) field = field.down;
			
			Unit newUnit = null;
			if (command[2].equals("\"elf\""))			 newUnit = createElf(field);
			else if (command[2].equals("\"human\""))  	 newUnit = createHuman(field);
			else if (command[2].equals("\"dwarf\"")) 	 newUnit = createHobbit(field);
			else if (command[2].equals("\"hobbit\""))	 newUnit = createDwarf(field);
			else { throw new Exception(); }
			
			newUnit.setField(field);
			field.registerUnit(newUnit);
			
			enemyCounter++;
			registerEntity(newUnit);
			lastCreatureSpawnTime = System.currentTimeMillis();
		} catch (Exception ex){
			ex.printStackTrace();
			fail = true;
		}
		
		if(fail){
			System.out.println("Invalid spawnnow command!");
			System.exit(-1);
		}
	}

	private void splitnow(String cmd)
	{
		if(state == state.MENU)
			return;
		
		String[] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 4 || !command[1].equals("-coord")){
			fail = true;
		} else try {
			byte x = (byte) Integer.parseInt(command[2]);
			byte y = (byte) Integer.parseInt(command[3]);
	
			Field field = topLeftCorner;
			for (int i=0; i<x;i++) field = field.right;
			for (int i=0; i<y;i++) field = field.down;
	
			List<Unit> units = field.getUnits();
	
			/// TODO
			//for (Unit u :units) u.testSplit();
		} catch (Exception ex){
			fail = true;
		}
		
		if(fail){
			System.out.println("Invalid splitnow command!");
			System.exit(-1);
		}
	}

	private void start(String cmd)
	{
		state = state.RUN;
	}

	private void unpause(String cmd)
	{
		state = state.RUN;
	}

	private void waitT(String cmd)
	{
		if(state != state.RUN)
			return;
		
		String[] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 3 || !command[1].equals("-time")){
			fail = true;
		}
		
		int time = 0;
		try{
			time = Integer.parseInt(command[2]);
			
			while(time > 0)
			{
				int t;
				
				if(time > 33){
					t = 33;
					time -= 33;
				} else {
					t = time;
					time = 0;
				}

				super.animate(t);
				timeElapsed += t;
				
				if(mountDoom.getUnits() != null){
					System.out.println("[time = " + timeElapsed + "]");
					System.out.println("Game over!");
					System.exit(0);
					break;
				} else if(timeElapsed >= duration){
					System.out.println("[time = " + timeElapsed + "]");
					System.out.println("You won the game!");
					System.exit(0);
					break;
				}
			}
		} catch (Exception ex){
			fail = true;
		}
		
		if(fail){
			System.out.println("Invalid wait command!");
			System.exit(-1);
		}
	}
	
	private void savemap(String cmd){
		if(state == state.MENU)
			return;
		
		String [] command = cmd.split(" ");
		boolean fail = false;
		
		if(command.length < 3 || !command[1].equals("-file")){
			fail = true;
		} else{
			PrintWriter writer = null;
			try{
				writer = new PrintWriter(command[2]);
	
				Field y = topLeftCorner;
				
				while(y != null){
					Field x = y;
					
					while(x != null){
						char ch = y.road ? 'r' : 'x';
						
						List<Unit> units 			= x.getUnits();
						Tower tower 				= x.getTower();
						TimedObstacle tObstacle 	= x.getTObstacle();
						PhysicalObstacle pObstacle 	= x.getPObstacle();
						List<Bullet> bullets 		= x.getBullets();
						
						if(x == mountDoom){
							ch = 'M';
						} else if(units != null){
							ch = units.get(0).isAttacking() ? 'U' : 'u';
						} else if(tower != null){
							ch = tower.isAttacking() ? 'T' : 't';
						} else if(pObstacle != null){
							ch = 'p';
						} else if(tObstacle != null){
							ch = tObstacle.isAttacking() ? 'O' : 'o';
						} else if(bullets != null){
							ch = '-';
						}
						
						writer.write(ch + " ");
						x = x.right;
					}
					
					writer.write('\n');
					y = y.down;
				}
			} catch (FileNotFoundException e) {
				fail = true;
			} finally{
				if(writer != null)
					writer.close();
			}
		}
		
		if(fail){
			System.out.println("Invalid savemap command!");
			System.exit(-1);
		}
	}
}