package knikita.model;

public class inventory extends Model {
    public long user_id;

    public int item_id;

    public inventory() {
    }

    public inventory(long user_id, int item_id) {
        this.user_id = user_id;
        this.item_id = item_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public int getItem_id() {
        return item_id;
    }
}
