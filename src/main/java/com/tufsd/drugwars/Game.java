package com.tufsd.drugwars;
import java.util.Random;
import java.util.EnumMap;
/**
 * A class to represent a game session.
 *
 * @author SHHS Coding Club
 * @version 20141105
 */
public class Game
{
    /**
     * Constructor for objects of class Game
     */
    public Player player;

    public final double RATE = .1;
    public int turns;
    
    Location currentLocation;

    public Game(String name)
    {
        currentLocation = Location.BLOCK;
        initPrices();
        EnumMap<Drug, Integer> inventory = new EnumMap<Drug, Integer>(Drug.class);
        // initialise instance variables
        player = new Player(name);
        player.inv = inventory;

        turns = 0;
    }

    public double addInterest()
    {
        player.debt = (player.debt*Math.pow(Math.E, (RATE*turns)));
        return player.debt;
    }

    public void run()
    {
        initPrices();
        gameLoop();
    }
     
    public boolean policeEncounter ()
    {
        double random = Math.random() * 10 + 1;
        if (random == 1)
            return true;
        else
            return false;

    }

    private void initPrices()
    {
        Location.BLOCK.prices.put(Drug.REEFER, -1.0);
        Location.BLOCK.prices.put(Drug.XANAX, 20.0);
        Location.BLOCK.prices.put(Drug.ADDERALL, 7.0);
        Location.BLOCK.prices.put(Drug.COKE, 5.0);
        Location.BLOCK.prices.put(Drug.HEROIN, 10.0);
        
        Location.MCDONALDS.prices.put(Drug.REEFER, 5.0);
        Location.MCDONALDS.prices.put(Drug.XANAX, -2.0);
        Location.MCDONALDS.prices.put(Drug.ADDERALL, 4.0);
        Location.MCDONALDS.prices.put(Drug.COKE, 10.0);
        Location.MCDONALDS.prices.put(Drug.HEROIN, 10.0);
        
        Location.KOREANCHURCH.prices.put(Drug.REEFER, -8.0);
        Location.KOREANCHURCH.prices.put(Drug.XANAX, 12.0);
        Location.KOREANCHURCH.prices.put(Drug.ADDERALL, 4.0);
        Location.KOREANCHURCH.prices.put(Drug.COKE, 6.0);
        Location.KOREANCHURCH.prices.put(Drug.HEROIN, 8.0);
        
        Location.BOX.prices.put(Drug.REEFER, 13.0);
        Location.BOX.prices.put(Drug.XANAX, 20.0);
        Location.BOX.prices.put(Drug.ADDERALL, 400.0);
        Location.BOX.prices.put(Drug.COKE, 30.0);
        Location.BOX.prices.put(Drug.HEROIN, 10.0);
    }
    
    public void setLocation (Location newLocation)
    {
        currentLocation = newLocation;
        
        turns += 1;
    }
    
    public void gameLoop()
    {
        while(turns < 30)
        {
            addInterest();
            UI.playerInfo(player);
            System.out.println(currentLocation);
            UI.gameMenu(player, currentLocation, this);
        }
        System.out.println();
        if(player.money >= player.debt)
            System.out.println("You win!");
        else
            System.out.println("You got whacked!");
        System.out.println("Thank you for playing!");
    }
    
    
}