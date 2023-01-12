package towers;

public class MineFactory extends Tower {
    public MineFactory(int x, int y, int id, int towerType , int[][] road) {
        super(x, y, id, towerType,road);

    }

    public void upgrade(int upgrade) {
        switch (upgrade) {
            case 1:
                this.reduceCooldown(10);
                return;
            case 2:
                this.addDmg(5);
                return;
            case 3:
                this.reduceCooldown(20);
                return;
        }
        return;
    }

    public int getCost(int upgrade) {
        switch (upgrade) {
            case 1:
                return 100;
            case 2:
                return 150;
            case 3:
                return 200;
        }
        return 0;
    }

    public String getName(int upgrade) {
        switch (upgrade) {
            case 1:
                return "Attack speed +";
            case 2:
                return "Attack damage +";
            case 3:
                return "Attack speed ++";
        }
        return "";
    }
}