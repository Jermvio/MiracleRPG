package knikita.model;

public class items extends Model {
    public  int item_id;

    public String name;

    public int durability;

    public boolean usable;

    public int damage;

    public long emoji_id;

    public String type;

    public int defence;

    public items() {
        super();
    }

    public items(int item_id, String name, int durability, boolean usable, int damage, long emoji_id, String type, int defence) {
        super();
        this.item_id = item_id;
        this.name = name;
        this.durability = durability;
        this.usable = usable;
        this.damage = damage;
        this.emoji_id = emoji_id;
        this.type = type;
        this.defence = defence;
    }

    public items setItem_id(int item_id) {
        this.item_id = item_id;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setEmoji_id(long emoji_id) {
        this.emoji_id = emoji_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getName() {
        return name;
    }

    public int getDurability() {
        return durability;
    }

    public boolean isUsable() {
        return usable;
    }

    public int getDamage() {
        return damage;
    }

    public long getEmoji_id() {
        return emoji_id;
    }

    public String getType() {
        return type;
    }

    public int getDefence() {
        return defence;
    }
}
