package zuul;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Class Room - a room in an adventure game.
 *
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Room 
{
    private String description;
    private HashMap <String, Room> exits;
    private ArrayList <Item> itens;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.itens = new ArrayList<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     */
    public void addItens(Item item) {
        this.itens.add(item);
    }

    public String itemAct(int n) {
        Item item = itens.get(n);
        return item.doSomething();
    }
    
    public String listItens() {
        int n = this.itens.size();
        String string = "";
        Item item;
        for(int i = 0; i < n; i++) {
            item = this.itens.get(0);
            string += " ("+ (n-1) + ") " + item.looking();
        }
        return string;
    }

    public int itensLenght() {
        return this.itens.size();
    }

    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) 
    {
        if(north != null)
        this.exits.put("north", north);
        if(east != null)
        this.exits.put("east", east);
        if(south != null)
        this.exits.put("south", south);
        if(west != null)
        this.exits.put("west", west);
        if(up != null)
        this.exits.put("up", up);
        if(down != null)
        this.exits.put("down", down);
        
    }

    public String showExits(){
        String eReturn = " ";
        if(this.exits.containsKey("north")){
            eReturn += " north";
        }
        if(this.exits.containsKey("east")){
            eReturn += " east";
        }
        if(this.exits.containsKey("south")){
            eReturn += " south" ;
        }
        if(this.exits.containsKey("west")){
            eReturn += " west";
        }
        if(this.exits.containsKey("up")){
            eReturn += " up";
        }
        if(this.exits.containsKey("down")){
            eReturn += " down";
        
        }
        return eReturn;
    }

    public Room nextRoom(String direction){
        Room nextRoom = null;

        if(direction.equals("north"))
            nextRoom = this.exits.get("north");
        if(direction.equals("east"))
            nextRoom = this.exits.get("east");
        if(direction.equals("south"))
            nextRoom = this.exits.get("south");
        if(direction.equals("west"))
            nextRoom = this.exits.get("west");
        if(direction.equals("up"))
            nextRoom = this.exits.get("up");
        if(direction.equals("down"))
            nextRoom = this.exits.get("down");
        
        return nextRoom;
    }
    /**
     * Return the description of the room (the one that was defined
     * in the constructor).
     */
    public String getDescription()
    {
        return description;
    }

}
