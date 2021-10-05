package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class Bag extends Command {
    TreeMap<Integer, Integer> itemsCountMap;

    @Override
    public long getCoolDownTime() {
        return 0;
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        itemsCountMap = new TreeMap<>(); //TODO звучит здоров но непонятно
        ResultSet resultSet = dbHandler.getHeroItemsId(event.getAuthor());

        try {

            while (resultSet.next()) {
                int itemId = resultSet.getInt(2);
                if (itemsCountMap.containsKey(itemId)) {
                    itemsCountMap.put(itemId, itemsCountMap.get(itemId) + 1);
                } else {
                    itemsCountMap.put(itemId, 1);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void sendFinishMessageSuccess(GuildMessageReceivedEvent event, TreeMap<Integer, Integer> receivedItems) {
        DatabaseHandler dbHandler = new DatabaseHandler();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(95, 63, 0, 168));

        embedBuilder.setTitle("**Инвентарь**");

        Set<Integer> keySet = itemsCountMap.keySet();

        try {
            for (int itemId : keySet) {
                ResultSet itemResultSet = dbHandler.getItemById(itemId);

                itemResultSet.next();
                String itemName = itemResultSet.getString(2);
                long itemEmojiId = itemResultSet.getLong(6);

                embedBuilder.addField(Emoji.fromEmote(itemName, itemEmojiId, false).getAsMention() + " " +
                                itemName + ": " +
                                itemsCountMap.get(itemId),
                        "", false);
                embedBuilder.setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
