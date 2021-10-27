package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import knikita.model.inventory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.xml.crypto.Data;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Chop extends Command {
    @Override
    public long getCoolDownTime() {
        return 1;
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.insertIntoTable(new inventory(event.getAuthor().getIdLong(), 2));
        itemsMap.put(2,2);

    }
}
