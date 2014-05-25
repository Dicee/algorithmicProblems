package codingame.harder;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Drones {

	public static Scanner in = new Scanner(System.in);
	public static int nbPlayers, id, nbDrones, nbZones;
	public static Zone[] zones;
	public static Team[] teams;
	public static Map<Integer,Point> myDrones;
	
	public static class Zone implements Comparable<Zone> {
		public Point pos;
		public int master;
		public int biggestTroup, biggestEnemyTroup, nbTeams;
		public int[] troups;
		
		public Zone(Point pos) {
			this.pos = pos;
		}
		
		public boolean contains(Point p) {
			return p.distance(pos) <= 100;
		}
		
		public void resetTroups() {
			troups            = new int[nbPlayers];
			biggestTroup      = 0;
			biggestEnemyTroup = 0;
			nbTeams           = 0;
		}
		
		public void enter(int team) {
		    if (troups[team] == 0)
		        nbTeams++;
			int t = ++troups[team];
			if (t > biggestTroup)
				biggestTroup = t;
			if (team != id && t > biggestEnemyTroup)
				biggestEnemyTroup = t;
		}
		
		public void leave(int team) {
			if (troups[team]-- == 1)
				nbTeams--;
			if (team == master)
				biggestTroup--;
		}
		
		@Override
		public int compareTo(Zone z) {
			if (master == id && z.master == id) 
				return Integer.compare(biggestTroup - biggestEnemyTroup,z.biggestTroup - z.biggestEnemyTroup);			
			return Integer.compare(biggestTroup,z.biggestTroup);
		}
		
		/*public boolean isThreatened() {
			for ()
		}*/
	}
	
	public static class Team {
		public Point[] drones;
		
		public Team() {
			drones = new Point[nbDrones];
		}
	}
	
    public static void main(String args[]) {
    	init();
    	myDrones  = new HashMap<>();
    	int tours = 0;
    	Map<Integer,String> moves;
    	
        while (true) {       
        	long begin = System.currentTimeMillis();
        	readInput();
        	if (tours < 25) 
        		moves = rushToNearestZone(myDrones,zones);
        	else
        		moves = bestDomination();
        	for (int i=0 ; i<nbDrones ; i++)
        	    if (moves.get(i) != null)
        		    System.out.println(moves.get(i));
        		else
        		    System.out.println(myDrones.get(i).x + " " + myDrones.get(i).y);
        	tours++;
        	System.err.println(String.format("Time elapsed : %d ms",System.currentTimeMillis() - begin));
        }
    }
    
    private static Map<Integer,String> bestDomination() {
    	//We put in moves the drones which we directly know where to send them
    	Map<Integer,String> moves         = new HashMap<>();
    	Map<Integer,Point>  notDominating = new HashMap<>();    	
    	
    	/*We determine for each drone if the zone it occupies is
    	 *dominated or not by our team and store it in one of the two
    	 *maps according to the result.
    	 */
    	for (int i=0 ; i<nbDrones ; i++) {
    	    boolean found = false;
    	    Point drone = myDrones.get(i);
    		for (int j=0 ; j<nbZones && !found ; j++) {    
    			Zone zone = zones[j];
    			if (found = zone.contains(drone) && zone.master == id) {
    				/*if (zone.biggestTroup > zone.biggestEnemyTroup + 1) {
    					notDominating.put(i,drone);
    					zone.leave(id);
    				} else     	*/				
    				moves.put(i,drone.x + " " + drone.y);
    			}
    		}
    		if (!found)
    		    notDominating.put(i,drone);
         }
    	
    	System.err.println("Defend : " + moves.keySet());
    	System.err.println("Make a move : " + notDominating.keySet());
    	//We use the surplus drones of the dominated zones to conquere undominated ones
    	/*for (Map.Entry<Integer,Point> entry : dominating.entrySet()) {
    		
    	}*/
    	
    	List<Zone> undominatedZones = getUndominatedOccupiedZones();
    	if (undominatedZones.isEmpty()) 
    	    for (Map.Entry<Integer,Point> entry : notDominating.entrySet()) 
			    moves.put(entry.getKey(),entry.getValue().x + " " + entry.getValue().y);
    	else {    
    	    Zone bestZone = undominatedZones.get(0);
    	
    	    //We cannot invade of the occupied yet undominated zones
    	    System.err.println("Not dominating : " + notDominating.size() + ", least diff : " + (bestZone.biggestTroup - bestZone.troups[id]) + ", master : " + bestZone.master);
    	    if (notDominating.size() < bestZone.biggestTroup - bestZone.troups[id]) {
    	    	//Zone[] emptyZones = (Zone[]) getEmptyZones().toArray();
    		   /* if (emptyZones.length != 0) 
    			    moves.putAll(rushToNearestZone(notDominating,emptyZones));
    		     else */
    			    moves.putAll(reinforceDominatedZones(notDominating));    		
    	    } else {
    		    //We pick a zone to invade so that it minimizes the sum of the distances
    		    //from the invaders to the center of the zone
    		    double min = Double.MAX_VALUE;
    		    for (Zone z : undominatedZones) {
    			    double dist = 0;
    		    	for (Integer i : notDominating.keySet())
    			    	dist += z.pos.distance(myDrones.get(i));
    		    	if (dist < min) {
    			    	min      = dist;
    				    bestZone = z;
    		    	}
    			    moves.putAll(invade(notDominating,bestZone));
    		    }
    	    }
    	}    	
    	return moves;
	}

	private static Map<Integer,String> invade(Map<Integer,Point> invaders, Zone zone) {
		Map<Integer,String> result = new HashMap<>();
		for (Map.Entry<Integer,Point> entry : invaders.entrySet()) {
			Point headTo = headTo(entry.getValue(),zone);
			result.put(entry.getKey(),headTo.x + " " + headTo.y);
		}
		System.err.println("Invade " + result.keySet());
		return result;
	}

	private static Map<Integer,String> reinforceDominatedZones(Map<Integer,Point> notDominating) {
		List<Zone> dominatedZones = new ArrayList<>();
		for (Zone z : zones)
			if (z.master == id)
				dominatedZones.add(z);
		
		Map<Integer,String> result = new HashMap<>();
		for (Map.Entry<Integer,Point> entry : notDominating.entrySet()) {
			//We sort the list so that the weakest defense is the first index
			Collections.sort(dominatedZones);
			Zone zone = dominatedZones.get(0);
			zone.enter(id);
			Point headTo = headTo(entry.getValue(),zone);
			result.put(entry.getKey(),headTo.x + " " + headTo.y);
		}
		System.err.println("Reinforce " + result.keySet());
		return result;
	}

	public static List<Zone> getUndominatedOccupiedZones() {
    	List<Zone> result = new ArrayList<>();
    	for (Zone zone : zones) {
    	    /*boolean fairOccupation   = 
    	        zone.troups[id] != 0 && zone.master != id && zone.troups[id] == zone.biggestEnemyTroup;
    	    boolean unfairOccupation =
    	        zone.troups[id] != 0 && zone.troups[id] != zone.biggestEnemyTroup;*/
    	    boolean occupation = zone.troups[id] != 0 && zone.master != id;
    		if (zone.master == -1 || occupation || zone.biggestTroup == 0)
    			result.add(zone);
    	}
    	
    	//We select only the zones with a minimal biggestTroup (easier ton invade)
    	Collections.sort(result);
    	if (!result.isEmpty()) {
    	    int minBiggestTroup = result.get(0).biggestTroup;
    	
    	    Iterator<Zone> it = result.iterator();
    	    while (it.hasNext())
    		    if (it.next().biggestTroup != minBiggestTroup)
    			    it.remove();
    	}
    	return result;
    }    
	
	public static Map<Integer,String> rushToNearestZone(Map<Integer,Point> drones, Zone[] zones) {
    	boolean[] taken            = new boolean[zones.length];
    	int nDrones                = drones.size();
    	int n                      = Math.min(nDrones,zones.length);
    	int i                      = 0;
    	Map<Integer,String> result = new HashMap<>();
    	
    	for (Map.Entry<Integer,Point> entry : drones.entrySet()) {
    		int zone     = nearestZone(entry.getValue(),taken);
    		taken[zone]  = true;
    		//When nbDrones > nbZones
    		Point headTo;
    		if (i++ >= n - 1) 
    			headTo = headTo(entry.getValue(),zones[nearestZone(entry.getValue())]);    			
    		 else 
    			headTo = headTo(entry.getValue(),zones[zone]);
    		result.put(entry.getKey(),headTo.x + " " + headTo.y);  	    			
    	}
    	System.err.println("Rush " + result);
    	return result;
    }   
    
    private static int nearestZone(Point p) {
    	int result = 0;
    	double min = Double.MAX_VALUE;
    	
		for (int i=0 ; i<nbZones ; i++) {
			double dist = p.distance(zones[i].pos);
			if (min > dist) {
				min    = dist;
				result = i;
			}
		}
		return result;
	}

	private static int nearestZone(Point p, boolean[] taken) {
    	int result = 0;
    	double min = Double.MAX_VALUE;
    	
		for (int i=0 ; i<nbZones ; i++) {
			double dist = p.distance(zones[i].pos);
			if (!taken[i] && min > dist) {
				min    = dist;
				result = i;
			}
		}
		return result;
	}

	private static Point headTo(Point origin, Zone goal) {
    	if (goal.contains(origin))
    		return origin;
    	
    	Point goTo   = goal.pos;
    	int dirX     = goTo.x - origin.x;
    	int dirY     = goTo.y - origin.y;
    	double norm  = Math.sqrt(dirX*dirX + dirY*dirY);
    	double dist  = Math.min(norm - 90,100);
    	return new Point((int) (origin.x + dist*dirX/norm),(int) (origin.y + dist*dirY/norm));
    }

	private static void readInput() {
		for (Zone zone : zones){
    		zone.master = in.nextInt();
    		zone.resetTroups();
		}
    	
    	for (int i=0 ; i<nbPlayers ; i++) 
    	    for (int j=0 ; j<nbDrones ; j++) {
    	    	Point pos          = new Point(in.nextInt(),in.nextInt());
    		    teams[i].drones[j] = pos;
    		    if (i == id)
    		        myDrones.put(j,pos);
    		    //We count the number of adversaries occupying each zone in order
    		    //to know how much forces are needed to invade it
    		    for (Zone zone : zones)
    		    	if (zone.contains(pos)) {
    		    		zone.enter(i);
    		    		break;
    		    	}
    	    }
	}

	private static void init() {
		nbPlayers = in.nextInt();
    	id        = in.nextInt();
    	nbDrones  = in.nextInt();
    	nbZones   = in.nextInt();
    	zones     = new Zone[nbZones];
    	teams     = new Team[nbPlayers];
    	
    	for (int i=0 ; i<nbZones ; i++)
    		zones[i] = new Zone(new Point(in.nextInt(),in.nextInt()));
    	
    	for (int i=0 ; i<nbPlayers ; i++)
    		teams[i] = new Team();
	}
}