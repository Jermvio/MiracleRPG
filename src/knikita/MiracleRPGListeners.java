package knikita;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class MiracleRPGListeners extends ListenerAdapter {

    static final public String prefix = ">"; // TODO убрать нахуй отсюда

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {


        DatabaseHandler dbHandler = new DatabaseHandler();

        String[] message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT).split("\\s+");
        if (!message[0].startsWith(prefix)) {
            return;
        }
        message[0] = message[0].substring(1);

        try {
            ResultSet resultSet = dbHandler.getHero(event.getAuthor());
            if (!resultSet.next()) {
                dbHandler.addHero(event.getAuthor());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Command.makeCommand(message[0]).go(event, message);
    }
}
