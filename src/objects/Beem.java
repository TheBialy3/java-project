package objects;

public class Beem {

    private int xStart, yStart;
    private int xEnd, yEnd;
    private int beemTimer;
    private boolean active = true;

    public Beem(int xStart, int yCord, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yCord;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        beemTimer = 10;
    }
    public boolean getActive(){
        return active;
    }

    public void timerDown() {
        beemTimer--;
        if(beemTimer<0){
            this.active=false;
        }
    }

    public int getxStart() {

        return xStart;
    }

    public int getyStart() {

        return yStart;
    }

    public int getxEnd() {
        return xEnd;
    }

    public int getyEnd() {
        return yEnd;
    }

    @Override
    public String toString() {
        return "Beem{" +
                "xStart=" + xStart +
                ", yStart=" + yStart +
                "xEnd=" + xEnd +
                ", yEnd=" + yEnd +
                '}';
    }
}
