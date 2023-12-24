import java.util.ArrayList;
import java.util.Collections;

public class Ex7_20220808049 {
    public static void main(String[] args) {

    }
}


//Interfaces
interface Damageable
{
    void takeDamage(int damage);//decrease health
    void takeHealing(int healing);//increase health
    boolean isAlive();//returns true if health > 0
}
interface Caster {
    void castSpell(Damageable target);//Sends intelligence + use method of spell to takeHealing method of target
    void learnSpell(Spell spell);
}
interface Combat extends Damageable{
    void attack(Damageable target);//PlayableCharacter: sends strength + use method of weapon to takeDamage method of target
    void lootWeapon(Weapon weapon);
}
interface Usable{
    int use();
}

//Classes
class Spell implements Usable{
    private int minHeal;
    private int maxHeal;
    private String name;

    public Spell(int minHeal, int maxHeal, String name) {
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
        this.name = name;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    private int heal(){return (int)(Math.random()*(maxHeal-minHeal+1)+minHeal);}
    //+1 will be in random because of max level

    @Override
    public int use() {return heal();}
}
class Weapon implements Usable{

    private int minDamage;
    private int maxDamage;
    private String name;

    public Weapon(int minDamage, int maxDamage, String name) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.name = name;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    private int attack(){
        return (int)(Math.random()*(maxDamage-minDamage+1 )+minDamage);
    }
    @Override
    public int use() {return attack();}
}
class Attributes{
    private int strength;
    private int intelligence;

    public Attributes(){
        this.intelligence=3;
        this.strength=3;
    }

    public Attributes(int strength, int intelligence) {
        this.strength = strength;
        this.intelligence = intelligence;
    }

    public void increaseStrength(int amount){this.strength+=amount;}
    public void increaseIntelligence(int amount){this.intelligence+=amount;}

    public int getStrength() {return strength;}
    public int getIntelligence() {return intelligence;}

    @Override
    public String toString(){
        return "Attributes [Strength= " + strength + " ,intelligence= " + intelligence + " ]";
    }
}
abstract class Character implements Comparable<Character>{
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    public Character(String name,Attributes attributes) {
        this.name = name;
        this.attributes=attributes;
    }

    public int getLevel() {return level;}

    public String  getName() {return name;}

    public abstract void levelUp();

    @Override
    public String toString() {
        return getClass().getName()+" LvL: "+ level+" - " +attributes;
    }

    @Override
    public int compareTo(Character o) {
        return Integer.compare(this.level, o.getLevel());
    }
}

//  non playable Characters
abstract class NonPlayableCharacter extends Character {
    public NonPlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }
}

class Merchant extends NonPlayableCharacter{
    public Merchant(String name){
        super(name,new Attributes(0,0));
    }

    @Override
    public void levelUp() {}//empty
}

class Skeleton extends NonPlayableCharacter implements Combat{

    public Skeleton(String name, Attributes attributes) {
        super(name, new Attributes());
    }

    @Override
    public void takeDamage(int damage) {this.health-=damage;}
    @Override
    public void takeHealing(int healing) {this.health-=healing;}

    @Override
    public boolean isAlive() {return health>0;}

    @Override
    public void attack(Damageable target) {
        target.takeDamage(attributes.getIntelligence()+attributes.getStrength());
    }

    @Override
    public void lootWeapon(Weapon weapon) {}

    @Override
    public void levelUp() {
        this.level++;
        attributes.increaseIntelligence(1);
        attributes.increaseStrength(1);
    }
}

//playable characters
abstract class PlayableCharacter extends Character implements Damageable{
    private boolean inParty;
    private Party party;

    public PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
        inParty=false;
    }

    public boolean isInParty() {return inParty;}

    public void joinParty(Party party) {
        try {
            if(!inParty){
                party.addCharacter(this);
                inParty=true;
                this.party=party;
            }
        }catch (PartyLimitReachedException | AlreadyInPartyException ex){
            System.out.println(ex);
        }

    }


    public void quitParty(){
        if (inParty){
            try{
                party.removeCharacter(this);
                this.inParty=false;
                this.party=null;
            }catch (CharacterIsNotInPartyException ex){
                System.out.println(ex);
            }
        }
    }


}

class Warrior extends PlayableCharacter implements Combat{
    private Usable weapon;

    public Warrior(String name) {
        super(name, new Attributes(4,2));
        this.health=35;
    }

    @Override
    public void takeDamage(int damage) {
        this.health-=damage;
    }

    @Override
    public void takeHealing(int healing) {
        this.health+=healing;
    }

    @Override
    public boolean isAlive() {return health>0;}

    @Override
    public void attack(Damageable target) {
        target.takeDamage(attributes.getStrength()+ weapon.use());
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        this.weapon=weapon;
    }

    @Override
    public void levelUp() {
        this.level++;
        attributes.increaseStrength(2);
        attributes.increaseIntelligence(1);
    }
}

class Cleric extends PlayableCharacter implements Caster{
    private Usable spell;

    public Cleric(String name) {
        super(name,new Attributes(2,4));
        this.health=25;
    }

    @Override
    public void takeDamage(int damage) {this.health-=damage;}

    @Override
    public void takeHealing(int healing) {this.health+=healing;}

    @Override
    public boolean isAlive() {return health>0;}

    @Override
    public void castSpell(Damageable target) {target.takeHealing(attributes.getIntelligence() + spell.use());}

    @Override
    public void learnSpell(Spell spell) {this.spell=spell;}

    @Override
    public void levelUp() {
        this.level++;
        attributes.increaseIntelligence(2);
        attributes.increaseStrength(1);
    }

}

class Paladin extends PlayableCharacter implements Combat,Caster{
    private Usable spell;
    private Usable weapon;

    public Paladin(String name) {
        super(name, new Attributes());
        this.health=30;
    }

    @Override
    public void takeDamage(int damage) {this.health-=damage;}

    @Override
    public void takeHealing(int healing) {this.health+=healing;}

    @Override
    public boolean isAlive() {return health>0;}

    @Override
    public void castSpell(Damageable target) {target.takeHealing(attributes.getIntelligence()+ spell.use());}

    @Override
    public void learnSpell(Spell spell) {this.spell=spell;}

    @Override
    public void levelUp() {
        if (level%2==0){
            attributes.increaseStrength(2);
            attributes.increaseIntelligence(1);
        }else {
            attributes.increaseStrength(1);
            attributes.increaseIntelligence(2);
        }
        this.level++;
    }

    @Override
    public void attack(Damageable target) {
        target.takeDamage(attributes.getStrength()+ weapon.use());
    }

    @Override
    public void lootWeapon(Weapon weapon) {
        this.weapon=weapon;
    }
}


//Party
class Party {
    private static final int partyLimit=8;
    private ArrayList<PlayableCharacter> fighters = new ArrayList<>();

    private ArrayList<PlayableCharacter> healers= new ArrayList<>();
    private int mixedCount=0;//paladin count

    public void addCharacter(PlayableCharacter character)throws AlreadyInPartyException,PartyLimitReachedException{
        int currentMembers= fighters.size()+ healers.size()-mixedCount;
        if (currentMembers==partyLimit)
            throw new PartyLimitReachedException("This party already have 8 person.");
        if (character.isInParty()){
            throw new AlreadyInPartyException("This character already in a party.");
        }
        if (character instanceof Combat)
            fighters.add(character);
        if (character instanceof Caster)
            healers.add(character);
        if (character instanceof Combat && character instanceof Caster)
            mixedCount++;
    }

    public void removeCharacter(PlayableCharacter character)throws CharacterIsNotInPartyException{
        if (!character.isInParty())
            throw new CharacterIsNotInPartyException("This character is not in a party");
        if (character instanceof Combat)
            fighters.remove(character);
        if (character instanceof Caster)
            healers.remove(character);
        if (character instanceof Combat && character instanceof Caster)
            mixedCount--;
    }

    public void partyLevelUp(){
        for (PlayableCharacter ch : fighters) {
            if (!(ch instanceof Paladin))
                ch.levelUp();
        }
        for (PlayableCharacter ch : healers) {
            ch.levelUp();
        }

    }



    @Override
    public String toString(){
        ArrayList<PlayableCharacter> arr= new ArrayList<>();
        for (PlayableCharacter fighter : fighters) {
            if (!(fighter instanceof Paladin))
                arr.add(fighter);
        }
        arr.addAll(healers);
        Collections.sort(arr);
        StringBuilder st = new StringBuilder("Party members:\n");
        for (PlayableCharacter character : arr) {
            st.append("\t").append(character).append("\n");
        }
        return st.toString();
    }
}


//Barrel and Training dummy
class Barrel implements Damageable{
    private int health=30;
    private int capacity =10;

    public void explode(){System.out.println("Explode");}
    public void repair(){System.out.println("Repairing");}


    @Override
    public void takeDamage(int damage) {
        this.health-=damage;
        if (!isAlive())
            explode();
    }

    @Override
    public void takeHealing(int healing) {
        this.health+=healing;
        repair();
    }

    @Override
    public boolean isAlive() {
        return health>0;
    }
}

class TrainingDummy implements Damageable{
    private int health=25;

    @Override
    public void takeDamage(int damage) {
        this.health-=damage;
    }

    @Override
    public void takeHealing(int healing) {
        this.health+=healing;
    }

    @Override
    public boolean isAlive() {
        return health>0;
    }
}
//Exceptions
class PartyLimitReachedException extends Exception{
    PartyLimitReachedException(String message){super(message);}
}

class AlreadyInPartyException extends Exception{
    AlreadyInPartyException(String message){super(message);}
}

class CharacterIsNotInPartyException extends Exception{
    CharacterIsNotInPartyException(String message){super(message);}
}