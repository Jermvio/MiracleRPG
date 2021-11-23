package knikita;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import knikita.entity.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Locale;

public class MiracleRPGListeners extends ListenerAdapter {

    static final public String prefix = ">"; // TODO убрать отсюда

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {


        DatabaseHandler dbHandler = DatabaseHandler.getInstance();

        String[] message = event.getMessage().getContentRaw().toLowerCase(Locale.ROOT).split("\\s+");
        if (!message[0].startsWith(prefix)) {
            return;
        }
        message[0] = message[0].substring(1);

        //TODO придумать аналог
        User user = dbHandler.get(User.class, event.getAuthor().getIdLong());
        if (user == null) {
            user = new User();
            user.setId(event.getAuthor().getIdLong());
            user.setHealth(20);
            user.setDamage(3);
            dbHandler.save(user);
        }

        Command.makeCommand(message[0]).go(event, message);
    }
}
