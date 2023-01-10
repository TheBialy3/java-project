package towers;


public class Archer extends Tower{
    public Archer(int x, int y, int id, int towerType, int[][] road) {
        super(x, y, id, towerType,road);
    }
    public void upgrate1() {
        this.setCooldown(15);
    }

    public void upgrate2() {
        this.addDmg(10);
    }

    public void upgrate3() {
        //   this.addDmg(10);
    }
}
