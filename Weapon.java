public class Weapon{
    private String t;
    private int min;
    private int max;
    
    public Weapon (String type, int min_dmg, int max_dmg){
        t = type;
        min = min_dmg;
        max = max_dmg;
    }
    
    public String type(){
        return t;
    }
    
    public int attack(){
        return (int)(Math.random()*(max-min)+min);
    }
    
}