import java.util.ArrayList;

/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int weightLimit = 15;
    private int currentWeight = 0;
    private String name;
    private Room currentRoom;
    private ArrayList<Item> inventory = new ArrayList<>();

    /**
     * Constructor for objects of class Player
     */
    public Player(String name)
    {
        name = this.name;
    }
    
    public void setRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
    
    public int getCurrentWeight()
    {
        return currentWeight;
    }
    
    public void setCurrentWeight(int newWeight)
    {
        currentWeight = newWeight;
    }
    
    public int getWeightLimit()
    {
        return weightLimit;
    }
    
    public void setWeightLimit(int newWeightLimit)
    {
        weightLimit = newWeightLimit;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public ArrayList<Item> getInventory()
    {
        return inventory;
    }
    
    public String getProfileString()
    {
        String profile = "Profile: \n" + 
        "Name: " + getName() + "\n" +
        "Current Inventory Space: " + getCurrentWeight() + "/" + getWeightLimit() +
        "\n";
        return profile;
    }
    
    public String getInventoryString()
    {
        String details = "Inventory: " + "\n";
        for(Item item : inventory)
        {
            details += item.itemDetails();
        }
        return details;
    }
}
