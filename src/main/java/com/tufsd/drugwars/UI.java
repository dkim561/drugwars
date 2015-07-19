package com.tufsd.drugwars;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.text.NumberFormat;
import java.util.Arrays;

/**
 * Drug Wars interface
 * @author Drug Lords Abigail (Team BADASSNESS!!!!!!)
 *
 *
 */
public class UI
{
    /**
     * Shows the Drug Wars logo and waits for the user to press enter.
     */
    public static void splashscreen()
    {
        String logo = " ____                    __        __             \n"+
                      "|  _ \\ _ __ _   _  __ _  \\ \\      / /_ _ _ __ ___ \n"+
                      "| | | | '__| | | |/ _` |  \\ \\ /\\ / / _` | '__/ __|\n"+
                      "| |_| | |  | |_| | (_| |   \\ V  V / (_| | |  \\__ \\\n"+
                      "|____/|_|   \\__,_|\\__, |    \\_/\\_/ \\__,_|_|  |___/\n"+
                      "                  |___/                           \n"+
                      "                   ";

        String creds = "From the Sleepy Hollow High School Coding Club!\n"+
                       "Presidents: Colin Atkinson, Katie Brennan\n"+
                       "Advisor: Daniel Larkin\n"+
                       "https://github.com/shhs-coding-club/drugwars\n";

        System.out.println(logo);
        System.out.println(creds);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Press enter to continue...");
        scanner.nextLine();
        startScreen();
    }

    /**
     * Shows the initial text and initializes and runs a new Game.
     */
    public static void initText()
    {
        String name;
        Scanner scan = new Scanner(System.in);
        System.out.println ("You owe $10,000 to a loan shark. You've got 30 days to earn it back by selling drugs. Start now.");
        System.out.print("What is your name? ");
        name = scan.nextLine();
        Game game = new Game(name);
        game.run();
    }

    /**
     * Lets the user decide if they want to start a new game or quit.
     */
    public static void startScreen ()
    {
        String[] opts = {"New Game", "Quit"};
        String select = menuGen(opts);

        System.out.println (select);
        if (select.equals("New Game"))
        {
            initText();
        }
        else if (select.equals("Quit") )
        {
            System.exit(0);
        }
        else
        {
            System.out.println ("Unknown Option");
            System.exit(0);
        }
    }

    /**
     * Generates a menu for users from an array.
     *
     * @param opts an array of Strings containing menu options
     * @return the string value of the selected menu option
     */
    public static String menuGen (String[] opts)
    {
        Scanner menuScanner = new Scanner (System.in);
        int menuChoice;
        System.out.println();
        for (int i = 0; i < opts.length; i++)
        {
            System.out.println((i+1)+". "+opts[i]);
        }
        
        System.out.print("> ");

        menuChoice = menuScanner.nextInt();

        return opts[menuChoice-1];
    }

    public static void playerInfo(Player player)
    {
        System.out.println();
        System.out.println("Name: " + player.name);
        System.out.println("Health: " + player.health);
        System.out.println("Inventory: " + player.inv);
        NumberFormat money = NumberFormat.getCurrencyInstance();

        System.out.println("Debt: " + money.format(player.debt));
        System.out.println("Cash: " + money.format(player.money));
        System.out.println();

    }
    
    public static void outputInventory (Player player)
    {
        System.out.print("Player Inventory: ");
        for (Drug whatever: player.inv.keySet())
        {
            System.out.print( " " + whatever + " " + player.inv.get(whatever));
            
            

        }
    }
    
    public static Location locationMenu()
    {
       String[] new1 = Location.names();
        //String result = Location.valueOf(new1);
       String[] new2 = new String[new1.length];
       for (int i=0; i<new1.length; i++)
       {
           new2[i]= new1[i];
        }
      new2[new2.length-1] = "Cancel";
      String val = menuGen(new2);
    
     if (val.equals("Cancel"))
        return null;
     else 
         return Location.valueOf(val);
    }
    
    public static Drug drugMenu(Set<Drug> myset, Location loc)
    {
        //Set<Drug> myset = player.inv.keySet();
        String[] new1 = new String[myset.size()+1];
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        int i = 0;
        for(Drug drug : myset)
        {
            new1[i] = drug.toString() + " (" + formatter.format(drug.price + loc.prices.get(drug)) + ")";
            i++;
        }
        new1[myset.size()]= "Cancel";
        //String[] new1 = myset.toArray(new String[myset.size()]);
        String val = menuGen(new1).split(" ")[0];
        if(val.equals("Cancel"))
            return null;
        else
            return Drug.valueOf(val);
    }
    
    public static int qtyMenu(Drug d, int max)
    {
        System.out.print("How many " + d + " would you like to sell? [0-" + max + "]: ");
        Scanner scan = new Scanner(System.in);
        int qty = scan.nextInt();
        if(qty >= 0 && qty <= max)
        {
            return qty;
        }
        
        return 0;
    }
    
    public static void gameMenu(Player player, Location loc, Game g)
    {
        System.out.println("Game Turn " + (g.turns + 1));
        String[] options ={"Location", "Buy", "Sell", "Quit"};
        
        String val = menuGen(options);
        
        if(val.equals("Location"))
        {
            Location l = locationMenu();
            if (l != null)
            {
                g.setLocation(l);
            }   
        }
        else if(val.equals("Sell"))
        {
            // Arbitrarily assumes selling atm, so will be fixed later
            Drug d = drugMenu(player.inv.keySet(), loc);
            if (d != null)
            {
                int max = player.inv.get(d);
                int num = qtyMenu(d, max);
                player.sellDrugs(d, num, loc);
           
            }
         playerInfo(player);
        }
        
        else if(val.equals("Buy"))
        {
            Drug d = drugMenu(new HashSet<Drug>(Arrays.asList(Drug.values())), loc);
            if (d != null)
                {
            int max = (int)(player.money / (d.price + loc.prices.get(d)));
            int num = qtyMenu(d, max);
            player.buyDrugs(d, num, loc);
         }
            playerInfo(player);
        }
        else if(val.equals("Quit"))
        {
            System.exit(0);
        }
       }
    }
  
    
 

   
