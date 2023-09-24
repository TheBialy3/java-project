package objects;

public class Cards {
   private int id, iconID;
    private String name, description;

    public Cards(int id, String name, String description, int iconID) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.iconID =iconID;
    }
}
