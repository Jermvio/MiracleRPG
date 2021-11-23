package knikita.commands.products;

import knikita.MiracleRpgMechanics;
import knikita.commands.factory.GatherCommand;
import knikita.dao.DatabaseHandler;
import knikita.entity.EnemyNPC;
import knikita.entity.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Hunt extends GatherCommand {

    User user;

    @Override
    public void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes) {
        obtainedItemsIds.add(2);

        DatabaseHandler handler = DatabaseHandler.getInstance();
        user = handler.get(User.class, event.getAuthor().getIdLong());
        EnemyNPC enemy = handler.get(EnemyNPC.class, 1);

        MiracleRpgMechanics.fight(user, enemy);
    }

    @Override
    public void sendFinishMessage(GuildMessageReceivedEvent event) {
        super.sendFinishMessage(event);
        event.getChannel().sendMessage("У тебя осталось " + user.getHealth() + "хп").queue(); //TODO localization
    }
}
