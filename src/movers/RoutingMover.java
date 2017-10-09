package movers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import map.*;
import entities.Entity;
import entities.Unit;

//lista epito osztaly, tovabb oroklik
//konkret megvalositas: konstruktor egyszer keres egy utvonalat
//unit ahhoz tartja magat, elf ember torp ilyen
public class RoutingMover extends Mover implements Cloneable {
	// --------- TAGVÁLTOZÓK -------- //
	protected List<Field> path = null;
	int nextFieldIndex = 0;

	// konstruktor
	public RoutingMover(Entity e, Field dest, double s, Position p) { // Mover(Entity e, Field dest, int s, Map.Position p)
		super(e, dest, s, p);
		buildPath();
	}

	public RoutingMover(RoutingMover other) {
		super(other);
		path = null;
		buildPath();
	}

	protected void buildPath() {
		/*
		 * entity field-je es dest kozott epit egy listat a path-ba
		 */
		
		Field start=e.getField(); //start
		Field dest=destination; //destination
		List<Field> queue=new ArrayList<Field>(); //queue of vertices for BFS
		Map<Field, Field> predecessor=new HashMap<Field, Field>(); //maps a node to its predecessor in the BFS tree
		queue.add(start); //adding start node
		int i=0; //queue index counter
		path=new ArrayList<Field>(); //path to be built

		while(true)
		{
			Field current=queue.get(i++); //get element from queue
			if(current.equals(dest)) //we found the destination
			{
				Field toAdd=dest; //we add destination node and its predecessors to the path
				while(toAdd!=null)
				{
					path.add(0, toAdd); //push_front
					toAdd=predecessor.get(toAdd); //add predecessor
				}
				path.remove(0); //we don't need the start node
				break;
			}
			
			List<Field> neighbors=new ArrayList<Field>();
			neighbors.add(current.down);
			neighbors.add(current.up);
			neighbors.add(current.left);
			neighbors.add(current.right);
			Collections.shuffle(neighbors);
			
			
			for(int ii=0; ii < neighbors.size(); ii++)
			{
				//add neighbors
				if(neighbors.get(ii)!=null && (neighbors.get(ii).road) && !(queue.contains((neighbors.get(ii))) )) //exists, is road and not already visited
				{
					predecessor.put(neighbors.get(ii), current); // set predecessor
					queue.add(neighbors.get(ii)); //add to queue
				}
			}
			if(i==queue.size()) //if a path exists, this should never occur
			{
				System.out.println("Could not build list!");
				break;
			}
		}

//      //Controlling route:		
//		Field f;
//		int ii=0;
//		System.out.println("Path:");
//		while(ii<path.size())
//		{
//			f=path.get(ii++);
//			System.out.println(f.toString());
//		}
//		System.out.println("----------------------");
		
	}

	// dt ido alatti mozgatast vegrehajttatja
	// ha hamis a visszateres, akkor elerte a dest mezot
	public boolean move(long dt) {
		if(e.getField() == destination)
			return false;
		
		// directionXY ujraszamolasa path felhasznalasaval
		if(path == null) System.out.println("aaaaaaaa");
		Field nextField = path.get(nextFieldIndex);
		Field currentField = e.getField();
		if(nextField == currentField.right){
			directionX = Position.posMax + (Position.posMax - position.x);
			directionY = -position.y;
		} else  if(nextField == currentField.left){
			directionX = -Position.posMax + (-Position.posMax - position.x);
			directionY = -position.y;
		} else if(nextField == currentField.up){
			directionX = -position.x;
			directionY = -Position.posMax + (-Position.posMax - position.y);
		} else  if(nextField == currentField.down){
			directionX = -position.x;
			directionY = Position.posMax + (Position.posMax - position.y);
		}
		
		double x = Math.sqrt(directionX*directionX + directionY * directionY);
		directionX /= x;
		directionY /= x;
		
		// új pozíció kiszámítása
		/*Position posDifference = new Position((byte) (dt * speed * directionX),
				(byte) (dt * speed * directionY));
		position.x = (byte) ((byte) position.x + (byte) posDifference.x);
		position.y = (byte) ((byte) position.y + (byte) posDifference.y);*/
		
		double posX = position.x + dt * speed * directionX;
		double posY = position.y + dt * speed * directionY;
		
		// ha nem lehet oda mozogni, mert pObstacle van azon a mezon, akkor nem
		// mozgunk
		// nem valtunk mezot

		// mezőváltás
		if ((Math.abs(posX) > Position.posMax)
				|| (Math.abs(posY) > Position.posMax)) {
			Field newField = null;
			if (posX < -Position.posMax){
				newField = currentField.left;
				// PObstacle-on ne menj at!
				if(nextField.getPObstacle() != null) return true;
				position = new Position((byte)(2*Position.posMax + posX), (byte) posY);
			}
			else if (posX > Position.posMax){
				newField = currentField.right;
				if(nextField.getPObstacle() != null) return true;
				position = new Position((byte)(-2*Position.posMax + posX), (byte) posY);
			}
			if (posY > Position.posMax){
				newField = currentField.down;
				if(nextField.getPObstacle() != null) return true;
				position = new Position((byte) posX, (byte)(-2*Position.posMax + posY));
			}
			if (posY < -Position.posMax){
				newField = currentField.up;
				if(nextField.getPObstacle() != null) return true;
				position = new Position((byte) posX, (byte)(2*Position.posMax + posY));
			}

			// if new Field is road
			/*
			 * field.removeEntity(e); e.setField(newField);
			 * newField.registerUnit((Unit) e);
			 */
			// update directions
			currentField.removeEntity(e);
			newField.registerUnit((Unit)e);
			
			e.setField(newField);
			nextFieldIndex++;
		} else {
			position.x = (byte) posX;
			position.y = (byte) posY;
		}
		
		boolean returnValue = e.getField() != destination;
		return returnValue;
	}

	public String toString() {
		return "RoutingMover(path = "
				+ (path == null ? "null" : path.toString()) + ")";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}