import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    private int weight;
    private String itemName;
    private String description;
    private static ArrayList<Item> items = new ArrayList<Item>();
    static
    {
        items.add(new Item("potion","a special potion that gives the power to hold more items.", 2));
        items.add(new Item("gun", "an AK-47 to be exact :D", 10));
        items.add(new Item("bicuits", "a buttery biscoff biscuits, curbs hunger.", 2));
        items.add(new Item("feather", "a pigeon feather.", 1));
        items.add(new Item("dumbbell", "a 5 lbs dumbbell, belongs in the gymnasium.", 5));
    }
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String itemName, String description, int weight)
    {
        this.itemName = itemName;
        this.description = description;
        this.weight = weight;
    }
    
    public static ArrayList<Item> getItemList()
    {
        return items;
    }
    
    public String getItemName()
    {
        return itemName;
    }
    
    public String getItemDescription()
    {
        return description;
    }
    
    public String itemDetails()
    {
        String details = "Item: "+ getItemName() + "\n" +
        "Description: " + getItemDescription() + "\n" +
        "Seems to weigh about " + getWeight() + " lbs.\n";
        return details;
    }
    
    public int getWeight()
    {
        return weight;
    }
}
