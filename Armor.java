public class Armor{
    private String t;
    
    public Armor (String type){
        this.t = type;
    }
    
    public String type(){
        return t;
    }
    
    public int defense(Monster monster){
        int newAttack = (int) (monster.monsterAttack() * (2/3.0));
        return newAttack;
    }
}