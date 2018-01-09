public class Hero{
    private int x_pos;
    private int y_pos;
    private int health;
    private Weapon weaponType; 
    private Armor armorType;

    public Hero(){
        weaponType = new Weapon("dagger", 10, 30);
        armorType = null;
        health = 100;
        x_pos = 0;
        y_pos = 9;
    }
    
    public Weapon getWeapon(){
        return weaponType;
    }
    
    public Weapon setWeapon(Weapon weapon){
        weaponType = weapon;
        return weaponType;
    }
    
    public Armor getArmor(){
        return armorType;
    }
    
    public Armor setArmor(Armor armor){
        armorType = armor;
        return armorType;
    }
    
    public int takeDamage(Monster monster){
        health -= monster.monsterAttack();
        return health;
    }
    
    public int getHealth(){
        return health;
    }
    
    public int restoreHealth(){
        health = 100;
        return health;
    }
    
    public int getX(){
        return x_pos;
    }
    
    public int getY(){
        return y_pos;
    }
    
    public int moveXLeft(){
        x_pos -= 1;
        return x_pos;
    }
    
    public int moveYUp(){
        y_pos -= 1;
        return y_pos;
    }
    
    public int moveYDown(){
        y_pos += 1;
        return y_pos;
    }
    
    public int moveXRight(){
        x_pos += 1;
        return x_pos;
    }
}