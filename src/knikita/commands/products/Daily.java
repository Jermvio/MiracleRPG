package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class Daily extends Command {
    @Override
    public long getCoolDownTime() {
        return TimeUnit.DAYS.toMillis(1);
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.addItem(event.getAuthor(), 1);
        dbHandler.addItem(event.getAuthor(), 1);
        dbHandler.addItem(event.getAuthor(), 1);

        dbHandler.addItem(event.getAuthor(), 2);
        dbHandler.addItem(event.getAuthor(), 2);
        dbHandler.addItem(event.getAuthor(), 2);
    }
}
