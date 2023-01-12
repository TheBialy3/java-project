package towers;

public class FrostMage extends Tower{
    public FrostMage(int x, int y, int id, int towerType , int[][] road) {
        super(x, y, id, towerType,road);
    }
    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.reduceCooldown(15);
                return;
            case 2:
                this.addDmg(5);
                return;
            case 3:
                this.addRange(100);
                return;
            default:
                return ;
        }
    }

    public int getCost(int upgrade) {
        switch (upgrade) {
            case 1:
                return 100;
            case 2:
                return 150;
            case 3:
                return 200;
            default:
                return 0;
        }
    }

    public String getName(int upgrade) {
        switch (upgrade) {
            case 1:
                return "Attack speed +";
            case 2:
                return "Attack damage +";
            case 3:
                return "Range +";
            default:
                return "null";
        }

    }
}

