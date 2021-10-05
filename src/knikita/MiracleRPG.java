package knikita;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class MiracleRPG {
    private static JDA jda;

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault("ODc0MjI2MTc4MDAzNzIyMjkw.YRD4kw.pjKqzTDwjM2oX3uaYkn5qrqzxQ0").build();
        jda.addEventListener(new MiracleRPGListeners());

        jda.getPresence().setActivity(Activity.watching(">help"));
        
    }
}
