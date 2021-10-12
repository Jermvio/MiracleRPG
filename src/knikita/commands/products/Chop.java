package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Chop extends Command {
    @Override
    public long getCoolDownTime() {
        return TimeUnit.MINUTES.toMillis(20);
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.addItem(event.getAuthor(), 2);
        itemsMap.put(2,2);
    }
}
