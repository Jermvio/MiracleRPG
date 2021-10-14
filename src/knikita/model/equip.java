package knikita.model;

public class equip extends Model {
    public long user_id;

    public int armor_id;

    public int weapon_id;

    public int staff_id;

    public equip() {
    }

    public equip(long user_id, int armor_id, int weapon_id, int staff_id) {
        this.user_id = user_id;
        this.armor_id = armor_id;
        this.weapon_id = weapon_id;
        this.staff_id = staff_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setArmor_id(int armor_id) {
        this.armor_id = armor_id;
    }

    public void setWeapon_id(int weapon_id) {
        this.weapon_id = weapon_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public int getArmor_id() {
        return armor_id;
    }

    public int getWeapon_id() {
        return weapon_id;
    }

    public int getStaff_id() {
        return staff_id;
    }
}
