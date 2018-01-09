
public class Potion{
    private int x;
    private int y;
    public Potion(){
    }
    
    public int getX(){
        x = (int)(Math.random()*10);
        return x;
    }
    
    public int getY(){
        y = (int)(Math.random()*10);
        return y;
    }
}
