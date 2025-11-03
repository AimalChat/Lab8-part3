import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;

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
    private Player user;
    private int weightLimit;
    private static final ArrayList<Item> items = Item.getItemList();
    private Stack<Room> history = new Stack<>();//30
    
    public Game() 
    {
        user = new Player("The Chosen One");
        weightLimit = user.getWeightLimit();
        createRooms();
        parser = new Parser();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
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
        lab.addItem(items.get(0));
        outside.addItem(items.get(3));
        outside.addItem(items.get(1));
        office.addItem(items.get(1));
        theater.addItem(items.get(4));
        pub.addItem(items.get(2));
        
        //initialize starting room.
        user.setRoom(outside);
        history.push(user.getCurrentRoom());//30
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
        System.out.println(user.getCurrentRoom().getLongDescription());
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

        String commandWord = command.getCommandWord();//31
        
        switch(commandWord) 
        {
            case "help" -> printHelp();
            case "go" -> goRoom(command);
            case "quit" -> wantToQuit = quit(command);
            case "look" -> lookAround();
            case "use" -> use(command);
            case "details" -> showCommandDetails();
            case "back" -> goBack();
            case "investigate" -> investigate();
            case "take" -> take(command);
            case "drop" -> drop(command);
            case "inventory" -> inventory();
            case "profile" -> getProfile();
            default -> System.out.println("I don't know what you mean...");
        }

        return wantToQuit;
    }

    // implementations of user commands:
    
    private void getProfile()
    {
        System.out.println(user.getProfileString());
    }
    
    private void inventory()
    {
        System.out.println("Inventory: \n");
        for(Item item : user.getInventory())
            {
                System.out.println(item.itemDetails());
            }
    }
    
    /** 
     * "use" was entered. Here we print a message saying the
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
        boolean found = false;
        Iterator<Item> it = user.getInventory().iterator();
        while(it.hasNext())
        {
            Item itemInInventory = it.next();
            if(itemInInventory.getItemName().equals(item))
            {
                System.out.println("You have used: " + item + " successfully.");
                if(itemInInventory.getItemName().equals("potion"))
                {
                    user.setWeightLimit(weightLimit + 984);
                    System.out.println("The potion has made you able to carry around "
                    + user.getWeightLimit() + " lbs. WOW, you are on steroids.");
                    int newWeight = user.getCurrentWeight() - itemInInventory.getWeight();
                    user.setCurrentWeight(newWeight);
                }
                it.remove();
                found = true;
                break;//stops if a match is found!
            }
        }
        if(!found)
        {
            System.out.println("There is no " + item + " here to use.");
        }
    }
    
    private void take(Command command)
    {
        String item = command.getSecondWord();
        boolean found = false;
        Iterator<Item> it = user.getCurrentRoom().getItemsInRoom().iterator();
        while(it.hasNext())
        {
            Item itemAvailable = it.next();
            if(itemAvailable.getItemName().equals(item))
            {
                found = true;
                if(user.getCurrentWeight() + itemAvailable.getWeight() <= weightLimit)
                {
                    user.getInventory().add(itemAvailable);
                    int newWeight = user.getCurrentWeight() + itemAvailable.getWeight();
                    user.setCurrentWeight(newWeight);
                    it.remove();
                    System.out.println("You have picked up " + item + ".");
                    break;
                }else
                {
                    System.out.println("You cannot pick up " + item + "!");
                    System.out.println("Your inventory is too full!");
                    break;
                }
            }
        }
        if(!found)
        {
            System.out.println("There is no "+ item + " to pick in this room!");
        }
    }
    
    private void drop(Command command)
    {
        String item = command.getSecondWord();
        boolean found = false;
        Iterator<Item> it = user.getInventory().iterator();
        while(it.hasNext())
        {
            Item itemDropped = it.next();
            if(itemDropped.getItemName().equals(item))
            {
                user.getCurrentRoom().getItemsInRoom().add(itemDropped);
                int newWeight = user.getCurrentWeight() - itemDropped.getWeight();
                user.setCurrentWeight(newWeight);
                it.remove();
                System.out.println("You have dropped " + item +" succesfully.");
                found = true;
            }
        }
        if(!found)
        {
            System.out.println("There is not an item called " + item + 
            " you can drop.");
        }
    }
    
    private void investigate()
    {
        System.out.println("You closely inspect your surroundings here.");
        if(user.getCurrentRoom().getItemsInRoom().isEmpty())
        {
            System.out.println("You have not found anything.");
        }else
        {
            for(Item item : user.getCurrentRoom().getItemsInRoom())
            {
                System.out.println(item.itemDetails());
            }
        }
    }
    
    private void goBack()//30
    {
        if(history.size() <= 1){
            System.out.println("Go back where?");
        }else{
            history.pop();
            user.setRoom(history.peek());//currentRoom = history.peek();
            System.out.println("You move back to the previous room.");
            System.out.println(user.getCurrentRoom().getLongDescription());
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
        Room nextRoom = user.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            user.setRoom(nextRoom);
            history.push(user.getCurrentRoom());//30
            System.out.println(user.getCurrentRoom().getLongDescription());
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
        System.out.println(user.getCurrentRoom().getLongDescription() +"\n"+
        user.getCurrentRoom().getItemsString());
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
