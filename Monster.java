public class Monster{
    private int health;
    private int attack;
    private int speed;
    private int x;
    private int y;
    
    public Monster(){
        this.health = (int)((Math.random()*100) + 1);
        this.attack = (int)((Math.random()*30) + 1);
        this.speed = (int)(Math.random()*4);
        health +=1;
        attack +=1;
    }
    
    public int getX(){
        x = (int)(Math.random()*10);
        return x;
    }
    
    public int getY(){
        y = (int)(Math.random()*10);
        return y;
    }
    
    public int monsterSpeed(){
        return speed;
    }
    
    public int monsterAttack(){
        return attack;
    }
    
    public int monsterHealth(){
        return health;
    }
    
    public int takeDamage(Weapon weapon){
        health -= weapon.attack();
        return health;
    }
    
    public String toString(){
      return "Type: Monster" +
      "/nHealth: " + monsterHealth() +
      "/nAttack: " + monsterAttack() +
      "/nSpeed: " + monsterSpeed();
    }
}