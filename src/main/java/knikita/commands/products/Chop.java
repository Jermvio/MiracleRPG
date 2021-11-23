package knikita.commands.products;

import knikita.commands.factory.GatherCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Chop extends GatherCommand {

    @Override
    public void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes) {
        obtainedItemsIds.add(1);
    }
}
