package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Fish extends Command {
    @Override
    public long getCoolDownTime() {
        return 1; //TODO Поставить нормальное кд
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.addItem(event.getAuthor(), 1);
        itemsMap.put(1, 1);
    }
}
