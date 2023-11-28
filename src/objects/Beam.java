package objects;

public class Beam {

    private int xStart, yStart;
    private int xEnd, yEnd;
    private int beamTimer;
    private boolean active = true;

    public Beam(int xStart, int yCord, int xEnd, int yEnd) {
        this.xStart = xStart;
        this.yStart = yCord;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        beamTimer = 8;
    }
    public boolean getActive(){
        return active;
    }

    public void timerDown() {
        beamTimer--;
        if(beamTimer <0){
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


}
