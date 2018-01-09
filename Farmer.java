public class Farmer{
    private int health;
    private int x;
    private int y;
    
    public Farmer(){
        health = (int)((Math.random()*100) + 1);
    }
    
    public int getX(){
        x = (int)(Math.random()*10);
        return x;
    }
    
    public int getY(){
        y = (int)(Math.random()*10);
        return y;
    }
    
    public int farmerHealth(){
        return health;
    }
}