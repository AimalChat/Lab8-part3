import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 7.0
 */

public class CommandWords
{
    // A constant array that holds all valid command words.
    private static final String[] validCommands = {
        "go", 
        "quit", 
        "help", 
        "look", 
        "use", 
        "details", 
        "back", 
        "investigate", 
        "take", 
        "drop",
        "inventory",
        "profile"
    };
    
    private static final HashMap<String,Integer> LENGTH = new HashMap<>();
    static//21
    {
        LENGTH.put("go",2);
        LENGTH.put("quit",1);
        LENGTH.put("help",1);
        LENGTH.put("look",1);
        LENGTH.put("use",2);
        LENGTH.put("details",1);
        LENGTH.put("back",1);//26
        LENGTH.put("investigate",1);
        LENGTH.put("take",2);//33
        LENGTH.put("drop",2);//33
        LENGTH.put("inventory",1);//36
        LENGTH.put("profile",1);
    }
    
    private static final HashMap<String,String> DESCRIPTION = new HashMap<>();
    static//21
    {
        DESCRIPTION.put("go", "to move around, must specify direction after 'go'.");
        DESCRIPTION.put("quit", "to exit game.");
        DESCRIPTION.put("help", "displays game context & valid commands.");
        DESCRIPTION.put("look", "to examine current surroundings.");
        DESCRIPTION.put("use", "to utilize an item");
        DESCRIPTION.put("details", "this very message shows length and a brief explaination of all commands.");
        DESCRIPTION.put("back", "to go back into the previous room.");//26
        DESCRIPTION.put("investigate", "to inspect surroundings for any items.");
        DESCRIPTION.put("take","to grab an item in the current location.");//33
        DESCRIPTION.put("drop","to drop an item from your inventory in your current location.");//33
        DESCRIPTION.put("inventory","to see the contents of your inventory.");//36
        DESCRIPTION.put("profile","to see current stats.");
    }

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }
    
    public static String[] getValidCommands()
    {
        Arrays.sort(validCommands);
        return validCommands;
    }
    
    public static String showCommandWords()
    {
        StringBuilder listCommands = new StringBuilder();
        listCommands.append("     ");
        for(String command : getValidCommands())
        {
            listCommands.append(command + "  ");
        }
        String listOfCommands = listCommands.toString();
        System.out.println("Your command words are:");
        return listOfCommands;
    }
    
    public static void commandDetails()//21
    {
        StringBuilder commandDetails = new StringBuilder();
        for(String command : getValidCommands())
        {
            commandDetails.append("\n");
            commandDetails.append(command + ": "+"\n");
            commandDetails.append("Length: requires " + LENGTH.get(command)+ " word(s)" +"\n" 
            + "Use: " + DESCRIPTION.get(command)+"\n");
            commandDetails.append("\n");
        }
        String commandsDetails = commandDetails.toString();
        System.out.println(commandsDetails);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public static boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString)) {
                return true;
            }
        }
        // if we get here, the string was not found in the commands
        return false;
    }
}
