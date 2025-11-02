# Lab8-part-2
Part 2 of Lab 8 for prog101

19. It would be nice to define that CommandWords class is responsible for producing (but not printing!) 
the list of command words, but that the Game class should decide how it is presented to the user. 
Change the ShowAll from CommandWords to not print the list of commands, but only produce and 
return it. Make sure that your program still works as before.

19b.
//commandWords class
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

//game class
/**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println(CommandWords.showCommandWords());
    }

20. If the game has a lot of commands, it might be useful in the getCommandList method to return the list
 sorted alphabetically. Implement that feature using the static sort method of the Arrays class in the 
java.util package.

20b.
//in commandWords class
public static String[] getValidCommands()
    {
        Arrays.sort(validCommands);
        return validCommands;
    }

21. How might the CommandWords class provide more detail on each command? For instance, a list of 
command words, whether each has a second word, and what each command does. Add a method to your
version of CommandWords to provide this and add a command word to the game whose use will call 
that method and displays what it returns.

21b.We could add further fields to a command such as int length and String Utility. Then we simply just return
those fields with accessor methods and print them out in the CommandWords class.
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
        "go", "quit", "help", "look", "eat", "details"
    };
    private static final HashMap<String,Integer> LENGTH = new HashMap<>();
    static 
    {
        LENGTH.put("go",2);
        LENGTH.put("quit",1);
        LENGTH.put("help",1);
        LENGTH.put("look",1);
        LENGTH.put("eat",1);
        LENGTH.put("details",1);
    }
    private static final HashMap<String,String> DESCRIPTION = new HashMap<>();
    static
    {
        DESCRIPTION.put("go", "to move around, must specify direction after 'go'.");
        DESCRIPTION.put("quit", "to exit game.");
        DESCRIPTION.put("help", "displays game context & valid commands.");
        DESCRIPTION.put("look", "to examine current surroundings.");
        DESCRIPTION.put("eat", "to consume an item");
        DESCRIPTION.put("details", "this very message shows length and a brief explaination of all commands.");
    }
    public static void commandDetails()
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
//In game class
/**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if (commandWord.equals("look")) {
            lookAround();
        }else if (commandWord.equals("eat")) {
            eat();
        }else if (commandWord.equals("details")) {//21
            showCommandDetails();
        }
        return wantToQuit;
    }
    private void showCommandDetails()//21
    {
        parser.getCommands().commandDetails();
    }
    
22. Find out what the model-view-controller pattern is. You can do a web search to get information, or you 
can use any other sources you find. How is it related to the topic discussed here? What does it suggest? 
How could it be applied to this project? (Only discuss its application to this project, as an actual 
implementation would be an advanced challenge exercise.) 

22b.

23. Extend either your adventure project or the zuul-better project so that a room can contain a single item. Items have a description and a weight. When creating rooms and setting their exits, items for this game should also be created. When a player enters a room, information about an item in this room should be 
displayed. 

23b.
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
        items.add(new Item("magic cookie","a special cookie that gives the power to hold more items.", 2));
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
    
    public int getWeight()
    {
        return weight;
    }
}
//Room Class
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
//Game class
    }
   /** 
     * "look" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void lookAround() 
    {
        System.out.println(currentRoom.getLongDescription() +"\n"+
        currentRoom.getItemsString());
    }
    
24. How should the information be produced about an item present in a room? Which class should produce 
the string describing the item? Which class should print it? Why? Explain in writing. If answering this 
exercise makes you feel you should change your implementation, go ahead and make the changes.

24b.I believe the string should be made by the Room class itself, not the game or Item class becasue the
only entity that should know the list of items in a room currently inhabbited by the player is the room
itself. The room knows itself enough to be able to return whatever values it is storing, not the game, not
Item but the Room class.
 
25. Modify the project so that a room can hold any number of items. Use a collection to do this. Make sure 
the room has an addItem method that places an item into the room. Make sure all items get shown 
when a player enters a room. 

25b. It has been done in my attempt at Q23.

26. Implement a back command. This command does not have a second word. Entering the back command 
takes the player into the previous room they were in.

import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 7.0
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private static final int WEIGHTLIMIT = 15;
    private static final ArrayList<Item> items = Item.getItemList();
    private ArrayList<Room> history = new ArrayList<>();//26
    
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    public void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        theater.setExit("west", outside);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        //initialise items
        pub.addItem(items.get(0));
        currentRoom = outside;
        history.add(currentRoom);//26
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if (commandWord.equals("look")) {
            lookAround();
        }else if (commandWord.equals("use")) {
            use();
        }else if (commandWord.equals("details")) {//21
            showCommandDetails();
        }else if (commandWord.equals("use")) {
            use(command);
        }else if(commandWord.equals("back")) {//26
            goBack();
        }
        

        return wantToQuit;
    }

    // implementations of user commands:
    
    public void goBack()//26
    {
        if(history.size() > 1){
            history.remove(history.size() - 1);
            currentRoom = history.get(history.size() - 1);
            lookAround();
        }else{
            System.out.println("Go back where?");
        }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println(CommandWords.showCommandWords());
    }
    
    /** 
     * "eat" was entered. Here we print a message saying the
     * player has eaten something.
     */
    private void use(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Use what?");
            return;
        }
        
        String item = command.getSecondWord();
        
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            history.add(currentRoom);//26
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
        }
    }
    
    /** 
     * "look" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void lookAround() 
    {
        System.out.println(currentRoom.getLongDescription() +"\n"+
        currentRoom.getItemsString());
    }
    
    /** 
     * "eat" was entered. Here we print a message saying the
     * player has eaten something.
     */
    private void use() 
    {
        System.out.println("You have eaten now and are now freed of the painful");
        System.out.println("hunger plaguing your mind");
    }
    
    private void showCommandDetails()//21
    {
        parser.getCommands().commandDetails();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            // signal that we want to quit
            return true;  
        }
    }
}


28. Test your new command. Does it work as expected? Also, test cases where the command is used 
incorrectly. For example, what does your program do if a player types a second word after the back 
command? Does it behave sensibly? 

Yes it does! It returns a message saying "Go back where?" in case of it being impossible and it works
if you do it again and again.

29. What does your program do if you type “back” twice? Is this behavior sensible?

Yes, it works as I stated previously!

30. Challenge exercise Implement the back command so that using it repeatedly takes you back several rooms,
all the way to the beginning of the game if used often enough. Use a Stack to do this. (You may need to 
find out about stacks. Look at the Java library documentation.)

import java.util.ArrayList;
import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 7.0
 */
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private static final int WEIGHTLIMIT = 15;
    private static final ArrayList<Item> items = Item.getItemList();
    private Stack<Room> history = new Stack<>();//30
    
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    public void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        theater.setExit("west", outside);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        office.setExit("west", lab);
        //initialise items
        pub.addItem(items.get(0));
        currentRoom = outside;
        history.push(currentRoom);//30
    }

    /**
     *  Main play routine. Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }else if (commandWord.equals("look")) {
            lookAround();
        }else if (commandWord.equals("use")) {
            use();
        }else if (commandWord.equals("details")) {//21
            showCommandDetails();
        }else if (commandWord.equals("use")) {
            use(command);
        }else if(commandWord.equals("back")) {//26
            goBack();
        }
        

        return wantToQuit;
    }

    // implementations of user commands:
    
    public void goBack()//30
    {
        if(history.size() <= 1){
            System.out.println("Go back where?");
        }else{
            history.pop();
            currentRoom = history.peek();
            lookAround();
        }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println(CommandWords.showCommandWords());
    }
    
    /** 
     * "eat" was entered. Here we print a message saying the
     * player has eaten something.
     */
    private void use(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Use what?");
            return;
        }
        
        String item = command.getSecondWord();
        
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            history.push(currentRoom);//30
            System.out.println(currentRoom.getLongDescription());
            System.out.println();
        }
    }
    
    /** 
     * "look" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private void lookAround() 
    {
        System.out.println(currentRoom.getLongDescription() +"\n"+
        currentRoom.getItemsString());
    }
    
    /** 
     * "eat" was entered. Here we print a message saying the
     * player has eaten something.
     */
    private void use() 
    {
        System.out.println("You have eaten now and are now freed of the painful");
        System.out.println("hunger plaguing your mind");
    }
    
    private void showCommandDetails()//21
    {
        parser.getCommands().commandDetails();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            // signal that we want to quit
            return true;  
        }
    }
}


31. Replace the sequence of if-else tests in the processCommand method of your Game class with an 
equivalent switch statement. In the default case, as well as printing a “command not recognized” message,
 print the list of available commands.


