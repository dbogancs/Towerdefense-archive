package movers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.*;
import entities.Entity;
import entities.Unit;
import enumerations.Side;

//lista epito osztaly egyik leszarmazottja
//ha elerte a celjat, keres ujat
//ork hasznalja, mindig ellenre keres utvonalat
public class AggressiveMover extends RoutingMover {

	// konstruktor
	public AggressiveMover(Entity e, int s, Position p) {
		// entity field-je alapjan megkeresem a legkozelebbi ellent
		// annak a field-je legyen a dest
		
		super(e, new Field((byte) 0, (byte) 0), s, p);

		setDestination();
		buildPath(); //we can build the path now
	}

	public AggressiveMover(AggressiveMover other) {
		super(other);
	}
	
	private void setDestination(){
		///FINDING ENEMY //note: should not be exclusively in the constructor
		Field start=e.getField(); //start
		Field dest=null; //destination to be found
		List<Field> queue=new ArrayList<Field>(); //queue of vertices for BFS
		queue.add(start); //adding start node
		int i=0; //queue index counter

		while(true)
		{
			Field current=queue.get(i++); //get element from queue
			boolean enemyFound=false; // true if we found an enemy
			
			List<Unit> list=current.getUnits(); //get units on current field			
			for(int ii=0; ii<list.size(); ii++)
			{
				if(list.get(ii).getSide()==Side.DARK) //an enemy
				{
					dest=current;
					enemyFound=true;
					break;
				}
			}
			if(enemyFound) //we found the destination
				break;
			
			//add neighbors
			if(current.down!=null && current.down.road && !(queue.contains(current.down)) ) //exists, is road and not already visited
			{
				queue.add(current.down); //add to queue
			}
			if(current.up!=null && current.up.road && !(queue.contains(current.up)) )
			{
				queue.add(current.up);
			}
			if(current.right!=null && current.right.road && !(queue.contains(current.right)) )
			{
				queue.add(current.right);
			}
			if(current.left!=null && current.left.road && !(queue.contains(current.left)) )
			{
				queue.add(current.left);
			}
			
			if(i==queue.size()) //if an enemy exists, this should never occur
			{
				//System.out.println("Could not find enemy!");
				dest = current;
				break;
			}
		}
		
		destination=dest; //setting destination to the field found
	}

	public boolean move(long dt) {
		if (!super.move(dt)) {
			// uj celpont keresese a dest-be
			setDestination();
			buildPath();
		}
		
		// sose eri el
		return true;
	}

	public String toString() {
		return "AggressiveMover()";
	}
}