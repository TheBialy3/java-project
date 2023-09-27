package objects;

public class Card {
   private int id, iconID;
    private String name, description;
    private boolean unlocked;

    public Card(int id, String name, String description, int iconID, boolean unlocked) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.iconID =iconID;
        this.unlocked=unlocked;
    }

    public int getId() {
        return id;
    }

    public int getIconID() {
        return iconID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
