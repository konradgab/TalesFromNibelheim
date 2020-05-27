package pl.grsrpg.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.grsrpg.logger.Logger;
import pl.grsrpg.player.IPlayer;
import pl.grsrpg.utils.Attribute;
import pl.grsrpg.utils.DiceRoll;
import pl.grsrpg.utils.IOUtils;


public class ActionCoach extends Action {
    @JsonProperty
    protected String name;
    @JsonProperty
    protected float price;
    @JsonProperty
    protected float multiplier;

    @Override
    public boolean execute (IPlayer player){
        textMenu(player);
        return true;
    }

    private int getPrice(IPlayer player, Attribute attribute){
        switch (attribute){
            case AGILITY:
                return (int) (price + (Math.pow(player.getAdditionalAgility() - player.getBaseAgility(),2)) * multiplier);
            case STRENGTH:
                return (int) (price + (Math.pow(player.getAdditionalStrength() - player.getBaseStrength(),2)) * multiplier);
            case MAXHEALTH:
                return (int) (price + (Math.pow(player.getAdditionalMaxHealth() - player.getBaseMaxHealth(),2)) * multiplier);
            case MAGICPOINTS:
                return (int) (price + (Math.pow(player.getAdditionalMagicPoints() - player.getBaseMagicPoints(),2))* multiplier);
            default:
                return 0;
        }
    }



    private void priceList(IPlayer player){
        System.out.println(Logger.CYAN + "1." + " Strength + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.STRENGTH) + Logger.WHITE + ")" );
        System.out.println(Logger.CYAN + "2." + " Agility + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.AGILITY) + Logger.WHITE + ")" );
        System.out.println(Logger.CYAN + "3." + " Magic Points + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.MAGICPOINTS) + Logger.WHITE + ")" );
        System.out.println(Logger.CYAN + "4." + " Max Health + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.MAXHEALTH) +  Logger.WHITE + ")" );
    }

    private void increase(IPlayer player){
        System.out.println("1. Strength + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.STRENGTH) + Logger.WHITE + ")" );
        System.out.println("2. Agility + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.AGILITY) + Logger.WHITE + ")" );
        System.out.println("3. Magic Points + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.MAGICPOINTS) + Logger.WHITE + ")" );
        System.out.println("4. Max Health + 1  (" + Logger.RED + "price: " + getPrice(player, Attribute.MAXHEALTH) +  Logger.WHITE + ")" );
    }

    private boolean checkPlayerGold(IPlayer player, Attribute attribute){
        switch (attribute){
            case AGILITY:
                return player.removeGold(getPrice(player,Attribute.AGILITY));
            case STRENGTH:
                return (player.removeGold(getPrice(player,Attribute.STRENGTH)));
            case MAXHEALTH:
                return(player.removeGold(getPrice(player,Attribute.MAXHEALTH)));
            case MAGICPOINTS:
                return(player.removeGold(getPrice(player,Attribute.MAGICPOINTS)));
            default:
                return false;
        }
    }

    private void improveAttributes(IPlayer player, Attribute choice , int number){
        switch (choice){
            case AGILITY:
                player.setBaseAgility(player.getBaseAgility() + number );
                break;
            case STRENGTH:
                player.setBaseStrength(player.getBaseStrength() + number );
                break;
            case MAXHEALTH:
                player.setBaseMaxHealth(player.getBaseMaxHealth() + number );
                break;
            case MAGICPOINTS:
                player.setBaseMagicPoints(player.getBaseMagicPoints() + number );
                break;
        }
    }

    private void textMenu(IPlayer player){
        int temp = 0;
        boolean luckyMan = false;
        if(DiceRoll.luckyRoll()) luckyMan = true;
        System.out.println("You meet " + name + " on your way." );
        System.out.println("Hello. Do you want to improve your attributes for a small fee?" );
        System.out.println("1. Yes / 2. No" );
        temp = IOUtils.getScanner().nextInt();
        while(temp == 1){
        priceList(player);
        Attribute choice = Attribute.fromId(IOUtils.getScanner().nextInt());
        while(choice == null) choice = Attribute.fromId(IOUtils.getScanner().nextInt());
        if(checkPlayerGold(player, choice)){
            if(!luckyMan){
                System.out.println("Training was successful. Do you want to see your attributes now?" );
                improveAttributes(player, choice, 1);
            }
            else{
                improveAttributes(player, choice, 2);
                System.out.println("Wow you're very smart. Training was successful. Do you want to see your attributes now?");
                luckyMan = false;
            }
            System.out.println("1. Yes / 2. No" );
            do temp = IOUtils.getScanner().nextInt();
            while(temp != 1 && temp != 2);
            if(temp == 1) player.getInfo();
        }
        else System.out.println("Sorry you don't have gold enough.");
        System.out.println("Do you want to continue training?" );
        System.out.println("1. Yes / 2. No" );
        do temp = IOUtils.getScanner().nextInt();
        while(temp != 1 && temp != 2);
        }
        System.out.println("See you soon!" );
    }

    @JsonIgnore
    @Override
    public String getInfo() {
        return "Meet Coach: "+Logger.CYAN+name+Logger.RESET;
    }
}