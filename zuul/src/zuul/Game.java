package zuul;

import java.io.File;
import java.util.ArrayList;
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
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ManipulaArquivoTexto arquivo;
    private File meuArquivo;
    private ArrayList<String> texto;
    private ArrayList<String> progresso;
    private boolean retomou;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game(boolean vaiRetomar) 
    {
        this.arquivo = new ManipulaArquivoTexto("zuul");
        this.texto = new ArrayList<>();
        this.retomou = false;
        if(vaiRetomar == true){
            this.progresso = new ArrayList<>();
            this.retomou = true;
        }
        this.meuArquivo = arquivo.getArquivo();
        createRooms();
        parser = new Parser();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, officeBasement, officeAttic;
        // create the itens
        Item banana = new Item("Delicious banana", "That was wonderful, i was really hungry!");
        Item mirror = new Item("Really big mirror", "Hm.. my hair is not that bad..");
        Item ball = new Item("Soccer ball", "If my knee wasn't that hurt, i would play with that a little..");
        Item glassOfWater = new Item("Cold glass of water","It feels like an oasis!!");
        Item book = new Item("Interesting book", "Luciana told us about this book in an class!");
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        officeBasement = new Room("in the office basement");
        officeAttic = new Room("in the office attic");
        
        
        // initialise room exits
        outside.setExits(null, theatre, lab, pub, null, null);
        theatre.setExits(null, null, null, outside, null, null);
        pub.setExits(null, outside, null, null, null, null);
        lab.setExits(outside, office, null, null, null, null);
        office.setExits(null, null, null, lab, officeAttic, officeBasement );
        officeBasement.setExits(null, null, null, null, office, null);
        officeAttic.setExits(null, null, null, null, null, office);
        
        // initialize room itens
        outside.addItens(ball);
        lab.addItens(book);
        pub.addItens(glassOfWater);
        officeAttic.addItens(mirror);
        theatre.addItens(banana);
        currentRoom = outside;
        printWelcome();
        if(this.retomou == true){
            this.progresso = this.arquivo.lerArquivoLinhas();
            Iterator<String>it = this.progresso.iterator();
            while(it.hasNext()) {
                String linha = it.next();
                String[] palavras = linha.split(";");
                Command comando = new Command("a", "b");

                if(palavras[0].equals("quit") || palavras[0].equals("search")){  
                }else{
                    comando.setCommandWord(palavras[0]);
                    comando.setSecondWord(palavras[1]);
                    processCommand(comando);
                } 
            }
            
        }
    }

    

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        

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
        System.out.println("Welcome to Adventure!");
        System.out.println("Adventure is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + currentRoom.getDescription());
        System.out.println("This room itens are: " + currentRoom.listItens());
        System.out.print("Exits: ");
        System.out.print(currentRoom.showExits());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        CommandWords commandWords = new CommandWords();
        String commandWord = command.getCommandWord();
        String secondWord = command.getSecondWord();

        switch (commandWords.verifyCommand(commandWord)){
            case 1:
                texto.add(commandWord+";"+secondWord+"\n");
                arquivo.salvarDados(texto);
                printHelp();
            break;
            case 2:
                texto.add(commandWord+";"+secondWord+"\n");
                arquivo.salvarDados(texto);
                goRoom(command);
            break;
            case 3:
                texto.add(commandWord+"\n");
                arquivo.salvarDados(texto);
                wantToQuit = commandWords.quit(command);
            break;
            case 4:
                texto.add(commandWord+";"+secondWord+"\n");
                arquivo.salvarDados(texto);
                useItem(command);
            break;
            case 5:
                texto.add(commandWord+";"+secondWord+"\n");
                arquivo.salvarDados(texto);
                searchCallback(currentRoom);
            break;
            case 0:
            break;
        }
    
        return wantToQuit;
    }

    private void searchCallback(Room currentRoom) {
        System.out.println("This room itens are: " + currentRoom.listItens());
        System.out.print("Exits: ");
        System.out.print(currentRoom.showExits());
        System.out.println();
    }

    private void useItem(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("I can't see this item..");
            return;
        }
     
        String numero = command.getSecondWord();
        int numeroConvertido = Integer.parseInt(numero);

        if(numeroConvertido >= currentRoom.itensLenght()){
            System.out.println("That's not an valid item");
            return;
        }else
        System.out.println(currentRoom.itemAct(numeroConvertido));
    }


    // implementations of user commands:

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
        System.out.println("Your command words are:");
        System.out.println("   go quit help act search");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
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
        Room nextRoom = currentRoom.nextRoom(direction);
        
        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println("You are " + currentRoom.getDescription());
            System.out.println("This room itens are: " + currentRoom.listItens());
            System.out.print("Exits: ");
            System.out.print(currentRoom.showExits());
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    
}
