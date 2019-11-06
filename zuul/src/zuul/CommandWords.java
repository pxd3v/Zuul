package zuul;
/*
 * This class is the main class of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class CommandWords
{
    // a constant array that holds all valid command words
    private static final String validCommands[] = {
        "go", "quit", "help", "act", "search"
    };

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * Return true if it is, false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    public boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }

    public int verifyCommand (String commandWord) {
        if (commandWord.equals("help"))
            return 1;
        else if (commandWord.equals("go"))
            return 2;
        else if (commandWord.equals("quit"))
            return 3;
        else if (commandWord.equals("act"))
            return 4;
        else if (commandWord.equals("search"))
            return 5;
        return 0;
    }
}
