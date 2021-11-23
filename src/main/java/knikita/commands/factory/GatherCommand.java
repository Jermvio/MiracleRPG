package knikita.commands.factory;

import knikita.dao.DatabaseHandler;
import knikita.entity.Item;
import knikita.entity.User;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GatherCommand extends Command {

    protected List<Integer> obtainedItemsIds = new ArrayList<>();

    @Override
    public void go(GuildMessageReceivedEvent event, String[] commandAttributes) {
        commandBody(event, commandAttributes);
        sendFinishMessage(event);
        giveAllItemsToPlayer(event);
    }

    public abstract void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes);

    public void sendFinishMessage(GuildMessageReceivedEvent event) {
        int rand = (int)(Math.random() * 3);

        StringBuilder stringBuilder = new StringBuilder();

        try {

            File file = new File("src/main/java/resources/knikita/text/ru/commands/" +
                    this.getClass().getSimpleName() + "/onComplete/" + rand);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            stringBuilder.append(bufferedReader.readLine());
            stringBuilder.append(" ");

        } catch (IOException e) {
            e.printStackTrace();
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        for (int itemId : obtainedItemsIds) {
            Item item = handler.get(Item.class, itemId);
            stringBuilder.append(Emoji.fromEmote(item.getName(), item.getEmoji_id(), false).getAsMention());
            stringBuilder.append(item.getName());
            stringBuilder.append(" ");
        }
        event.getChannel().sendMessage(stringBuilder.toString()).queue();
    }

    private void giveAllItemsToPlayer(GuildMessageReceivedEvent event) {
        DatabaseHandler handler = DatabaseHandler.getInstance();

        User user = handler.get(User.class, event.getAuthor().getIdLong());
        for (int itemId : obtainedItemsIds) {
            user.getInventory().add(handler.get(Item.class, itemId));
        }

        handler.update(user);
    }

}
