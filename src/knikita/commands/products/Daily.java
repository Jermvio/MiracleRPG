package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import knikita.model.inventory;
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
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 1));
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 1));
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 1));
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 2));
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 2));
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 2));
    }
}
