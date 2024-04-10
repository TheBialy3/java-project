package objects;

public class Card {
    private int id, towerType;
    private String name, description;
    private boolean unlocked, active = false, repeat = false;

    public Card(int id, String name, String description, boolean unlocked, int towerType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unlocked = unlocked;
        this.towerType = towerType;
    }

    public Card(int id, String name, String description, boolean unlocked, int towerType, boolean repeat) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.repeat = repeat;
        this.unlocked = unlocked;
        this.towerType = towerType;
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

    public int getTowerType() {
        return towerType;
    }
    public boolean getRepeatable(){
        return repeat;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", towerType=" + towerType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", unlocked=" + unlocked +
                ", active=" + active +
                '}';
    }
}
