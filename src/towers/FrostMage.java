package towers;

public class FrostMage extends Tower{
    public FrostMage(int x, int y, int id, int towerType , int[][] road) {
        super(x, y, id, towerType,road);
    }
    public void upgrate1() {
        this.setCooldown(20);
    }

    public void upgrate2() {
        this.addDmg(10);
    }

    public void upgrate3() {
        //   this.addDmg(10);
    }
}

