package knikita.dao;

public interface Const {
    public static final String HEROES_TABLE = "heros";

    public static final String HERO_ID = "hero_id";
    public static final String USER_ID = "user_id";
    public static final String HERO_CLASS = "class";
    public static final String HERO_HEALTH = "health";
    public static final String HERO_MONEY = "money";
    public static final String HERO_LVL = "lvl";

    public static final String INVENTORY_TABLE = "inventory";

    public static final String ITEM_ID = "item_id";

    public static final String ITEM_TABLE = "items";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_DURABILITY = "durability";
    public static final String ITEM_USABLE = "usable";
    public static final String ITEM_DAMAGE = "damage";

    public static final String COMMANDS_TABLE = "commands_cooldowns";

    public static final String COMMANDS_USER_ID = "hero";
    public static final String COMMAND_NAME = "command";
    public static final String COMMAND_COOLDOWN = "cooldown_last";

    public static final String EQUIP_TABLE = "equip";
    public static final String EQUIP_USER_ID = "user_id";
    public static final String EQUIP_ARMOR_ID = "armor_id";
    public static final String EQUIP_WEAPON_ID = "weapon_id";
    public static final String EQUIP_STAFF_ID = "staff_id";
}
