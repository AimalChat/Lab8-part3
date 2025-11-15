import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Write a description of class TransporterRoom here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TransporterRoom extends Room
{
    // instance variables - replace the example below with your own
    private static Random randomGen = new Random();

    /**
     * Constructor for objects of class TransporterRoom
     */
    public TransporterRoom(String description)
    {
        super(description);
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public static Random getRandom()
    {
        return randomGen;
    }
    
    /**
     * Picks a random room already traveled to by user through the history stack
     * and returns it for it to be set.
     */
    public static Room getRandomRoom(Stack history)
    {
        int historySize = history.size();
        ArrayList<Room> rooms = new ArrayList<>(history);
        Room nextRoom= rooms.get(randomGen.nextInt(history.size()-1));
        return nextRoom;
    }
}
