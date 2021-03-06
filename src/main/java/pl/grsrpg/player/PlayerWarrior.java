package pl.grsrpg.player;

import pl.grsrpg.logger.Logger;
import pl.grsrpg.fightmanager.WarriorFightManager;
import pl.grsrpg.utils.Attribute;
import pl.grsrpg.utils.DiceRoll;
import pl.grsrpg.entity.Entity;

public class PlayerWarrior extends Player {
    private static final int startMaxHealth = 120;
    private static final int startStrength = 30;
    private static final int startAgility = 20;
    private static final int startMagicPoints = 20;
    private static final int startEquipmentCapacity = 5;

    public PlayerWarrior(String name) {
        super(name, startMaxHealth, startStrength, startAgility, startMagicPoints, startEquipmentCapacity);
        this.fightManager = new WarriorFightManager(this);
    }

    public PlayerWarrior(){
        this.fightManager = new WarriorFightManager(this);
    }

    public float knockdown() {
        this.fightMagicPoints -= 5;
        if (DiceRoll.rollPrivate(1, 6) >= 4) {
            System.out.println("You dealt additional damage " + (2.5F * this.getAdditionalStrength() + 0.4F * this.getBaseStrength()) + "and knockdown enemy.");
            return (2.5F * this.getAdditionalStrength() + 0.2F * this.getBaseStrength());
        }

        return 2 * this.getAdditionalStrength() + 0.3F * this.getBaseStrength();
    }

    public float cleave(Entity entity) {
        this.fightMagicPoints -= 5;
        if (this.health < this.getMaxHealth() * 0.3) {
            System.out.println("You deal amage " + 2.5F * (this.getStrength() * 0.4) + ".");
            return 2.5F * (this.getStrength() * 0.4F);
        }
        if (entity.getHealth() < entity.getBaseMaxHealth() * 0.2) {
            System.out.println("AMAZING! You dealt " + (entity.getHealth()) + " damage.");
            return entity.getHealth();
        }
        System.out.println("You dealt " + (2F * (this.getStrength())) + " damage.");
        return 2F * (this.getStrength());
    }

    public void blessingOfTheShield() {
        this.fightMagicPoints -= 5;
    }

    public static int getStartMaxHealth() {
        return startMaxHealth;
    }

    public static int getStartStrength() {
        return startStrength;
    }

    public static int getStartAgility() {
        return startAgility;
    }

    public static int getStartMagicPoints() {
        return startMagicPoints;
    }

    public static int getStartEquipmentCapacity() {
        return startEquipmentCapacity;
    }

    public static String getStartDescription() {
        return " Start Attributes: \n" +
                "  Max Health: " + Logger.YELLOW + startMaxHealth + "\n" + Logger.RESET +
                "  Strength: " + Logger.YELLOW + startStrength + "\n" + Logger.RESET +
                "  Agility: " + Logger.YELLOW + startAgility + "\n" + Logger.RESET +
                "  Magic Points: " + Logger.YELLOW + startMagicPoints + "\n" + Logger.RESET +
                "  Equipment Capacity: " + Logger.YELLOW + startEquipmentCapacity + Logger.RESET;
    }

    @Override
    public int getStartAttribute(Attribute attribute) {
        switch (attribute) {
            case AGILITY:
                return PlayerWarrior.getStartAgility();
            case STRENGTH:
                return PlayerWarrior.getStartStrength();
            case MAXHEALTH:
                return PlayerWarrior.getStartMaxHealth();
            case MAGICPOINTS:
                return PlayerWarrior.getStartMagicPoints();
            default:
                return 0;
        }
    }
}
