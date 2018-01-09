import java.util.*;
import java.io.*;
public class Driver{
    public static boolean game = true;
    public static Object [][] grid = new Object [10][10];
    public static String [][] stringGrid = new String [10][10];
    private int x;
    private int y;
    public static void main(String args[]){
        int count = 0;
        Monster[] monsters = new Monster[6];
        for(int i = 0; i < 6; i++){
            monsters[i] = new Monster();
        }

        Potion[] potions = new Potion[2];
        for(int i = 0; i < 2; i++){
            potions[i] = new Potion();
        }
        Scanner kbReader = new Scanner(System.in);

        Hero hero = new Hero();
        Farmer farmer = new Farmer();
        Monster monster = new Monster();
        Potion potion = new Potion();

        stringGrid[hero.getY()][hero.getX()] = "hero";
        grid[hero.getY()][hero.getX()] = hero;

        stringGrid[farmer.getY()][farmer.getX()] = "farmer";
        grid[farmer.getY()][farmer.getX()] = farmer;

        stringGrid[monster.getY()][monster.getX()] = "monster";
        grid[monster.getY()][monster.getX()] = monsters[0];
        stringGrid[monster.getY()][monster.getX()] = "monster";
        grid[monster.getY()][monster.getX()] = monsters[1];
        stringGrid[monster.getY()][monster.getX()] = "monster";
        grid[monster.getY()][monster.getX()] = monsters[2];
        stringGrid[monster.getY()][monster.getX()] = "monster";
        grid[monster.getY()][monster.getX()] = monsters[3];
        stringGrid[monster.getY()][monster.getX()] = "monster";
        grid[monster.getY()][monster.getX()] = monsters[4];
        stringGrid[monster.getY()][monster.getX()] = "monster";
        grid[monster.getY()][monster.getX()] = monsters[5];

        stringGrid[potion.getY()][potion.getX()] = "potion";
        grid[potion.getY()][potion.getX()] = potions[0];
        stringGrid[potion.getY()][potion.getX()] = "potion";
        grid[potion.getY()][potion.getX()] = potions[1];

        System.out.print("");
        printMap();
        boolean state = true;
        while(state){
            System.out.print("Move your Hero (W is up, S is down, A is left, and D is right.): ");
            String movement = kbReader.next();            
            if(movement.equals("w")){
                stringGrid[hero.getY()][hero.getX()] = null;
                grid[hero.getY()][hero.getX()] = null;
                hero.moveYUp();
                if(hero.getY() < 0){
                    hero.moveYDown();
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    System.out.println("You must stay on the grid.");
                }else{
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    while(){
                        System.out.print("You've encountered an object. Would you like to engage? (yes or no):");
                        String answer = kbReader.next();
                        if(answer.equals("yes")){
                            checkTypeUp(hero, monster, farmer, potion);
                            while(checkTypeUp(hero, monster, farmer, potion).equals("monster")){
                                System.out.println(monster.toString());
                                System.out.print("Attack(a) or flee(f): ");
                                String choice = kbReader.next();
                                if(choice.equals("a")){
                                    System.out.print("You hit the monster!");
                                    monster.takeDamage(hero.getWeapon());
                                    System.out.print("The monster has attacked back.");
                                    hero.takeDamage(monster);
                                    System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    if(monster.monsterHealth() <= 0){
                                        System.out.println("This monster has been eliminated.");
                                        count++;
                                        break;
                                    }
                                    if(hero.getHealth() <= 0){
                                        System.out.println("You have been eliminated.");
                                        break;
                                    }
                                }else if(choice.equals("f")){
                                    flee(monster);
                                    if(flee(monster) == true){
                                        System.out.println("You have evaded the monster.");
                                        break;
                                    }else{
                                        System.out.println("You were unable to evade the monster, and you've been attacked.");
                                        hero.takeDamage(monster);
                                        System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    }
                                }else{
                                    System.out.println("This is not a valid choice. Only lowercase a and f can be chosen."); 
                                }
                            }
                            while(checkTypeUp(hero, monster, farmer, potion).equals("potion")){
                                if(hero.getHealth() == 100){
                                    System.out.print("You've interacted with a health potion, but your health is already at max.");
                                    break;
                                }else{
                                    System.out.println("You've interacted with a health potion. Your health has been restored to the max.");
                                    hero.restoreHealth();
                                    break;
                                }
                            }
                            while(checkTypeUp(hero, monster, farmer, potion).equals("farmer")){
                                if(count >= 2 && hero.getArmor() == null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My sheep have been taken… My family is gone and I have nothing left except for bronze amor I have buried… /nOnly a true hero will receive this.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeArmor = kbReader.next();
                                    if(takeArmor.equals("a")){
                                        hero.setArmor(new Armor("bronze armor"));
                                        System.out.print("You have acquired bronze armor! Attacks will now only do 2/3 of their original damage.");
                                        break;
                                    }else if(takeArmor.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else if(count >= 4 && hero.getArmor() != null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My family and I would like to aid you in your quest to kill the monsters… Please take this sword.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeWeapon = kbReader.next();
                                    if(takeWeapon.equals("a")){
                                        hero.setWeapon(new Weapon("Sword", 30, 50));
                                        System.out.print("You have acquired a sword! Your attacks will now do much more damage.");
                                        break;
                                    }else if(takeWeapon.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else{
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"I'm just a farmer! Leave me be!\"");
                                    break;
                                }
                            }
                        }else if (answer.equals("no")){
                            break;
                        }else{
                            System.out.print("Your answer does not match the expected format.");
                        }
                    }
                }
            }else if(movement.equals("a")){
                stringGrid[hero.getY()][hero.getX()] = null;
                grid[hero.getY()][hero.getX()] = null;
                hero.moveXLeft();
                if(hero.getX() < 0){
                    hero.moveXRight();
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    System.out.println("You must stay on the grid.");
                }else{
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    while(){
                        System.out.print("You've encountered an object. Would you like to engage? (yes or no):");
                        String answer = kbReader.next();
                        if(answer.equals("yes")){
                            checkTypeLeft(hero, monster, farmer, potion);
                            while(checkTypeLeft(hero, monster, farmer, potion).equals("monster")){
                                System.out.println(monster.toString());
                                System.out.print("Attack(a) or flee(f): ");
                                String choice = kbReader.next();
                                if(choice.equals("a")){
                                    System.out.print("You hit the monster!");
                                    monster.takeDamage(hero.getWeapon());
                                    System.out.print("The monster has attacked back.");
                                    hero.takeDamage(monster);
                                    System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    if(monster.monsterHealth() <= 0){
                                        System.out.println("This monster has been eliminated.");
                                        count++;
                                        break;
                                    }
                                    if(hero.getHealth() <= 0){
                                        System.out.println("You have been eliminated.");
                                        break;
                                    }
                                }else if(choice.equals("f")){
                                    flee(monster);
                                    if(flee(monster) == true){
                                        System.out.println("You have evaded the monster.");
                                        break;
                                    }else{
                                        System.out.println("You were unable to evade the monster, and you've been attacked.");
                                        hero.takeDamage(monster);
                                        System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    }
                                }else{
                                    System.out.println("This is not a valid choice. Only lowercase a and f can be chosen."); 
                                }
                            }
                            while(checkTypeLeft(hero, monster, farmer, potion).equals("potion")){
                                if(hero.getHealth() == 100){
                                    System.out.print("You've interacted with a health potion, but your health is already at max.");
                                    break;
                                }else{
                                    System.out.println("You've interacted with a health potion. Your health has been restored to the max.");
                                    hero.restoreHealth();
                                    break;
                                }
                            }
                            while(checkTypeLeft(hero, monster, farmer, potion).equals("farmer")){
                                if(count >= 2 && hero.getArmor() == null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My sheep have been taken… My family is gone and I have nothing left except for bronze amor I have buried… /nOnly a true hero will receive this.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeArmor = kbReader.next();
                                    if(takeArmor.equals("a")){
                                        hero.setArmor(new Armor("bronze armor"));
                                        System.out.print("You have acquired bronze armor! Attacks will now only do 2/3 of their original damage.");
                                        break;
                                    }else if(takeArmor.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else if(count >= 4 && hero.getArmor() != null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My family and I would like to aid you in your quest to kill the monsters… Please take this sword.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeWeapon = kbReader.next();
                                    if(takeWeapon.equals("a")){
                                        hero.setWeapon(new Weapon("Sword", 30, 50));
                                        System.out.print("You have acquired a sword! Your attacks will now do much more damage.");
                                        break;
                                    }else if(takeWeapon.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else{
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"I'm just a farmer! Leave me be!\"");
                                    break;
                                }
                            }
                        }else if (answer.equals("no")){
                            break;
                        }else{
                            System.out.print("Your answer does not match the expected format.");
                        }
                    }
                }
            }else if(movement.equals("s")){
                stringGrid[hero.getY()][hero.getX()] = null;
                grid[hero.getY()][hero.getX()] = null;
                hero.moveYDown();
                if(hero.getY() > 9){
                    hero.moveYUp();
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    System.out.println("You must stay on the grid.");
                }else{
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    while(){
                        System.out.print("You've encountered an object. Would you like to engage? (yes or no):");
                        String answer = kbReader.next();
                        if(answer.equals("yes")){
                            checkTypeDown(hero, monster, farmer, potion);
                            while(checkTypeDown(hero, monster, farmer, potion).equals("monster")){
                                System.out.println(monster.toString());
                                System.out.print("Attack(a) or flee(f): ");
                                String choice = kbReader.next();
                                if(choice.equals("a")){
                                    System.out.print("You hit the monster!");
                                    monster.takeDamage(hero.getWeapon());
                                    System.out.print("The monster has attacked back.");
                                    hero.takeDamage(monster);
                                    System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    if(monster.monsterHealth() <= 0){
                                        System.out.println("This monster has been eliminated.");
                                        count++;
                                        break;
                                    }
                                    if(hero.getHealth() <= 0){
                                        System.out.println("You have been eliminated.");
                                        break;
                                    }
                                }else if(choice.equals("f")){
                                    flee(monster);
                                    if(flee(monster) == true){
                                        System.out.println("You have evaded the monster.");
                                        break;
                                    }else{
                                        System.out.println("You were unable to evade the monster, and you've been attacked.");
                                        hero.takeDamage(monster);
                                        System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    }
                                }else{
                                    System.out.println("This is not a valid choice. Only lowercase a and f can be chosen."); 
                                }
                            }
                            while(checkTypeDown(hero, monster, farmer, potion).equals("potion")){
                                if(hero.getHealth() == 100){
                                    System.out.print("You've interacted with a health potion, but your health is already at max.");
                                    break;
                                }else{
                                    System.out.println("You've interacted with a health potion. Your health has been restored to the max.");
                                    hero.restoreHealth();
                                    break;
                                }
                            }
                            while(checkTypeDown(hero, monster, farmer, potion).equals("farmer")){
                                if(count >= 2 && hero.getArmor() == null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My sheep have been taken… My family is gone and I have nothing left except for bronze amor I have buried… /nOnly a true hero will receive this.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeArmor = kbReader.next();
                                    if(takeArmor.equals("a")){
                                        hero.setArmor(new Armor("bronze armor"));
                                        System.out.print("You have acquired bronze armor! Attacks will now only do 2/3 of their original damage.");
                                        break;
                                    }else if(takeArmor.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else if(count >= 4 && hero.getArmor() != null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My family and I would like to aid you in your quest to kill the monsters… Please take this sword.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeWeapon = kbReader.next();
                                    if(takeWeapon.equals("a")){
                                        hero.setWeapon(new Weapon("Sword", 30, 50));
                                        System.out.print("You have acquired a sword! Your attacks will now do much more damage.");
                                        break;
                                    }else if(takeWeapon.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else{
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"I'm just a farmer! Leave me be!\"");
                                    break;
                                }
                            }
                        }else if (answer.equals("no")){
                            break;
                        }else{
                            System.out.print("Your answer does not match the expected format.");
                        }
                    }
                }
            }else if(movement.equals("d")){
                stringGrid[hero.getY()][hero.getX()] = null;
                grid[hero.getY()][hero.getX()] = null;
                hero.moveXRight();
                if(hero.getX() > 9){
                    hero.moveXLeft();
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    System.out.println("You must stay on the grid.");
                }else{
                    stringGrid[hero.getY()][hero.getX()] = "hero";
                    grid[hero.getY()][hero.getX()] = hero;
                    while(){
                        System.out.print("You've encountered an object. Would you like to engage? (yes or no):");
                        String answer = kbReader.next();
                        if(answer.equals("yes")){
                            checkTypeRight(hero, monster, farmer, potion);
                            while(checkTypeRight(hero, monster, farmer, potion).equals("monster")){
                                System.out.println(monster.toString());
                                System.out.print("Attack(a) or flee(f): ");
                                String choice = kbReader.next();
                                if(choice.equals("a")){
                                    System.out.print("You hit the monster!");
                                    monster.takeDamage(hero.getWeapon());
                                    System.out.print("The monster has attacked back.");
                                    hero.takeDamage(monster);
                                    System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    if(monster.monsterHealth() <= 0){
                                        System.out.println("This monster has been eliminated.");
                                        count++;
                                        break;
                                    }
                                    if(hero.getHealth() <= 0){
                                        System.out.println("You have been eliminated.");
                                        break;
                                    }
                                }else if(choice.equals("f")){
                                    flee(monster);
                                    if(flee(monster) == true){
                                        System.out.println("You have evaded the monster.");
                                        break;
                                    }else{
                                        System.out.println("You were unable to evade the monster, and you've been attacked.");
                                        hero.takeDamage(monster);
                                        System.out.print("You have " + hero.getHealth() + " lifepoints left.");
                                    }
                                }else{
                                    System.out.println("This is not a valid choice. Only lowercase a and f can be chosen."); 
                                }
                            }
                            while(checkTypeRight(hero, monster, farmer, potion).equals("potion")){
                                if(hero.getHealth() == 100){
                                    System.out.print("You've interacted with a health potion, but your health is already at max.");
                                    break;
                                }else{
                                    System.out.println("You've interacted with a health potion. Your health has been restored to the max.");
                                    hero.restoreHealth();
                                    break;
                                }
                            }
                            while(checkTypeRight(hero, monster, farmer, potion).equals("farmer")){
                                if(count >= 2 && hero.getArmor() == null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My sheep have been taken… My family is gone and I have nothing left except for bronze amor I have buried… /nOnly a true hero will receive this.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeArmor = kbReader.next();
                                    if(takeArmor.equals("a")){
                                        hero.setArmor(new Armor("bronze armor"));
                                        System.out.print("You have acquired bronze armor! Attacks will now only do 2/3 of their original damage.");
                                        break;
                                    }else if(takeArmor.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else if(count >= 4 && hero.getArmor() != null){
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"My family and I would like to aid you in your quest to kill the monsters… Please take this sword.\"");
                                    System.out.print("Accept(a) or Decline(d): ");
                                    String takeWeapon = kbReader.next();
                                    if(takeWeapon.equals("a")){
                                        hero.setWeapon(new Weapon("Sword", 30, 50));
                                        System.out.print("You have acquired a sword! Your attacks will now do much more damage.");
                                        break;
                                    }else if(takeWeapon.equals("d")){
                                        System.out.print("Farmer says: \"I'll keep it then…\"" );
                                        break;
                                    }else{
                                        System.out.println("This is not a valid choice. Only lowercase a and d can be chosen.");
                                    }
                                }else{
                                    System.out.println("You've interacted with a farmer.");
                                    System.out.println("Farmer says: \"I'm just a farmer! Leave me be!\"");
                                    break;
                                }
                            }
                        }else if (answer.equals("no")){
                            break;
                        }else{
                            System.out.print("Your answer does not match the expected format.");
                        }
                    }
                }
            }else{
                System.out.println("This is not a valid movement. Only lowercase w, a, s, and d work to move your hero."); 
            }
            if(grid[hero.getY()][hero.getX()] != null) 
                printMap();
        }

    }

    public int getX(){
        x = (int)(Math.random()*10);
        return x;
    }

    public int getY(){
        y = (int)(Math.random()*10);
        return y;
    }

    public static boolean flee(Monster monster){
        int probability = (int) (Math.random() * 101);
        if(monster.monsterSpeed() == 0){
            if(probability <= 75){
                return true;
            }else{
                return false;
            }
        }else if(monster.monsterSpeed() == 1){
            if(probability <= 50){
                return true;
            }else{
                return false;
            }
        }else if(monster.monsterSpeed() == 2){
            if(probability <= 25){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static String checkTypeUp(Hero hero, Monster monster, Farmer farmer, Potion potion){
        if(grid[hero.getY() - 1][hero.getX()] == monster || grid[hero.getY()][hero.getX() - 1] == monster || grid[hero.getY()][hero.getX() + 1] == monster){
            return "monster";
        }else if(grid[hero.getY() - 1][hero.getX()] == potion || grid[hero.getY()][hero.getX() - 1] == potion || grid[hero.getY()][hero.getX() + 1] == potion){
            return "potion";
        }else if(grid[hero.getY() - 1][hero.getX()] == farmer || grid[hero.getY()][hero.getX() - 1] == farmer || grid[hero.getY()][hero.getX() + 1] == farmer){
            return "farmer";
        }else{
            return null;
        }
    }

    public static String checkTypeDown(Hero hero, Monster monster, Farmer farmer, Potion potion){
        if(grid[hero.getY() + 1][hero.getX()] == monster || grid[hero.getY()][hero.getX() - 1] == monster || grid[hero.getY()][hero.getX() + 1] == monster){
            return "monster";
        }else if(grid[hero.getY() + 1][hero.getX()] == potion || grid[hero.getY()][hero.getX() - 1] == potion || grid[hero.getY()][hero.getX() + 1] == potion){
            return "potion";
        }else if(grid[hero.getY() + 1][hero.getX()] == farmer || grid[hero.getY()][hero.getX() - 1] == farmer || grid[hero.getY()][hero.getX() + 1] == farmer){
            return "farmer";
        }else{
            return null;
        }
    }

    public static String checkTypeLeft(Hero hero, Monster monster, Farmer farmer, Potion potion){
        if(grid[hero.getY() - 1][hero.getX()] == monster || grid[hero.getY()][hero.getX() - 1] == monster || grid[hero.getY() + 1][hero.getX()] == monster){
            return "monster";
        }else if(grid[hero.getY() - 1][hero.getX()] == potion || grid[hero.getY()][hero.getX() - 1] == potion || grid[hero.getY() + 1][hero.getX()] == potion){
            return "potion";
        }else if(grid[hero.getY() - 1][hero.getX()] == farmer || grid[hero.getY()][hero.getX() - 1] == farmer || grid[hero.getY() + 1][hero.getX()] == farmer){
            return "farmer";
        }else{
            return null;
        }
    }

    public static String checkTypeRight(Hero hero, Monster monster, Farmer farmer, Potion potion){
        if(grid[hero.getY() + 1][hero.getX()] == monster || grid[hero.getY() - 1][hero.getX()] == monster || grid[hero.getY()][hero.getX() + 1] == monster){
            return "monster";
        }else if(grid[hero.getY() + 1][hero.getX()] == potion || grid[hero.getY() - 1][hero.getX()] == potion || grid[hero.getY()][hero.getX() + 1] == potion){
            return "potion";
        }else if(grid[hero.getY() + 1][hero.getX()] == farmer || grid[hero.getY() - 1][hero.getX()] == farmer || grid[hero.getY()][hero.getX() + 1] == farmer){
            return "farmer";
        }else{
            return null;
        }
    }

    public static void printMap(){
        for(int k = 0; k<10; k++){
            for(int j = 0; j<10; j++){
                System.out.print(stringGrid[k][j]+"  ");
            }
            System.out.println("");
            System.out.println("");
        }
    }
}