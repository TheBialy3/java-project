package objects;

public class Card {
   private int id;
    private String name, description;
    private boolean unlocked, active=false;

    public Card(int id, String name, String description,  boolean unlocked) {
        this.id=id;
        this.name=name;
        this.description=description;

        this.unlocked=unlocked;
    }

    public int getId() {
        return id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
