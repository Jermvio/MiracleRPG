package knikita.model;

public class commands_cooldowns extends Model {
    public long hero;

    public String command;

    public long cooldown_last;

    public commands_cooldowns() {
        super();
    }

    public commands_cooldowns(long hero, String command, long cooldown_last) {
        super();
        this.hero = hero;
        this.command = command;
        this.cooldown_last = cooldown_last;
    }

    public commands_cooldowns setHero(long hero) {
        this.hero = hero;
        return this;
    }

    public commands_cooldowns setCommand(String command) {
        this.command = command;
        return this;
    }

    public void setCooldown_last(long cooldown_last) {
        this.cooldown_last = cooldown_last;
    }

    public long getHero() {
        return hero;
    }

    public String getCommand() {
        return command;
    }

    public long getCooldown_last() {
        return cooldown_last;
    }
}
