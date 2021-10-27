package knikita.model;

public class users extends Model {
    public long user_id;

    public String clas;

    public int health;

    public int money;

    public int lvl;

    public String language;

    public users(long user_id) {
        super();
        this.user_id = user_id;
        this.clas = "non";
        this.health = 20;
        this.money = 0;
        this.lvl = 1;
        this.language = "ru";
    }

    public users() {
        super();
    }

    public users setUser_id(long user_id) {
        this.user_id = user_id;
        return this;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getClas() {
        return clas;
    }

    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
    }

    public int getLvl() {
        return lvl;
    }

    public String getLanguage() {
        return language;
    }
}
