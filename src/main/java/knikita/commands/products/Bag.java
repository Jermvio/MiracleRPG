package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import knikita.entity.Item;
import knikita.entity.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.List;

public class Bag extends Command {

    @Override
    public void go(GuildMessageReceivedEvent event, String[] commandAttributes) {
        DatabaseHandler handler = DatabaseHandler.getInstance();

        User user =  handler.get(User.class, event.getAuthor().getIdLong());
        List<Item> inventory = user.getInventory();
        HashMap<Item, Integer> sortedInventory = new HashMap<>();
        for (Item item : inventory) {
            if (sortedInventory.containsKey(item)) {
                sortedInventory.put(item, sortedInventory.get(item)+1);
            } else  {
                sortedInventory.put(item, 1);
            }
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl());
        embedBuilder.setTitle("**Bag**");

        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : sortedInventory.keySet()) {
            stringBuilder.append(Emoji.fromEmote(item.getName(), item.getEmoji_id(), false).getAsMention());
            stringBuilder.append(item.getName());
            stringBuilder.append(" x");
            stringBuilder.append(sortedInventory.get(item));
            stringBuilder.append("\n");
        }

        embedBuilder.addField("**Inventory**", stringBuilder.toString(), false);

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

    }
}
