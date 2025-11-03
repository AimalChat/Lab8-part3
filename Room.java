import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 7.0
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> itemsInRoom;

    /**
     * Create a room described "description". Initially, it has no exits. 
     * "description" is something like "a kitchen" or "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String,Room>();
        itemsInRoom = new ArrayList<Item>();
    }
    
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null) {
            exits.put("north", north);
        }
        if(east != null) {
            exits.put("east", east);
        }
        if(south != null) {
            exits.put("south", south);
        }
        if(west != null) {
            exits.put("west", west);
        }
    }
    
    public void setExit(String direction, Room nextRoom)
    {
        exits.put(direction, nextRoom);
    }
    
    public void addItem(Item item)
    {
        itemsInRoom.add(item);
    }
    
    public ArrayList<Item> getItemsInRoom()
    {
        return itemsInRoom;
    }
    
    public String getItemsString()
    {
        List<String> listOfItems = new ArrayList<>();
        StringBuilder itemsString = new StringBuilder();
        itemsString.append("You see: ");
        if(itemsInRoom.isEmpty())
        {
            itemsString.append("nothing of value here.");
            return itemsString.toString().trim();
        }else
        {
            for(Item item : itemsInRoom)
            {
                //itemsString.append(item.getItemName() + ", ");
                listOfItems.add(item.getItemName());
            }
            itemsString.append(String.join(", ",listOfItems));
            return itemsString.toString();
            //itemsString.deleteCharAt(itemsString.length() - 2);
            //return itemsString.toString().trim();
        }
    }
    
    public String getExitString()
    {
        Set<String> directions = exits.keySet();
        StringBuilder exitStringv1 = new StringBuilder();
        exitStringv1.append("Exits: ");
        for(String direction : directions)
        {
            exitStringv1.append(direction + ", ");
        }
        exitStringv1.deleteCharAt(exitStringv1.length() - 2);
        String exitStringv2 = exitStringv1.toString();
        return exitStringv2;
    }
    
    /**
    * Return a description of the room’s exits,
    * for example, "Exits: north west".
    * @return A description of the available exits.
    * FOR PART 1 OF LAB.
    */
    public void getExitString(Room currentRoom)
    {
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        Set<String> directions = exits.keySet();
        StringBuilder exitString = new StringBuilder();
        for(String direction : directions)
        {
            exitString.append(direction + ", ");
        }
        exitString.deleteCharAt(exitString.length() - 2);
        System.out.print(exitString);
    }
    
    /** * 
     * Return a description of the room’s exits, 
     * for example, "Exits: north west". 
     * @return A description of the available exits. 
    */
     
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
}
