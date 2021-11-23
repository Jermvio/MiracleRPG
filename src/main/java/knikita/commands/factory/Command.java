package knikita.commands.factory;

import knikita.commands.products.Help;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Locale;

public abstract class Command {
    public static Command makeCommand(String commandName) {
        commandName = commandName.substring(0, 1).toUpperCase(Locale.ROOT) + commandName.substring(1).toLowerCase(Locale.ROOT);

        Command command;
        try {
            command = (Command) Class.forName("knikita.commands.products." + commandName).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return new Help();//TODO описать ошибку
        }
        return command;
    }

    public abstract void go(GuildMessageReceivedEvent event, String[] commandAttributes);
}