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
import java.util.*;

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
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(95, 63, 0, 168));

        try {
            String inventoryString = getInventoryString();
            embedBuilder.addField("**Инвентарь**", inventoryString, false);

            String equipString = getEquipString(event);
            embedBuilder.addField("**Снаряжение**", equipString, false);

            embedBuilder.setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }

    private String getEquipString(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        StringBuilder string = new StringBuilder();
        ResultSet equipResultSet = dbHandler.getEquip(event.getAuthor());

        try {
            equipResultSet.next();
            ResultSet itemResultSet = dbHandler.getItemById(equipResultSet.getInt(3));
            itemResultSet.next();
            String armor_name = itemResultSet.getString(2);
            Emoji armor_emoji = Emoji.fromEmote(armor_name, itemResultSet.getLong(6), false);

            itemResultSet = dbHandler.getItemById(equipResultSet.getInt(4));
            itemResultSet.next();
            String weapon_name = itemResultSet.getString(2);
            Emoji weapon_emoji = Emoji.fromEmote(armor_name, itemResultSet.getLong(6), false);

            itemResultSet = dbHandler.getItemById(equipResultSet.getInt(5));
            itemResultSet.next();
            String staff_name = itemResultSet.getString(2);
            Emoji staff_emoji = Emoji.fromEmote(armor_name, itemResultSet.getLong(6), false);

            string.append(armor_emoji.getAsMention()).append(armor_name).append("\n").append(weapon_emoji.getAsMention()).append(weapon_name).append("\n").append(staff_emoji.getAsMention()).append(staff_name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return string.toString();
    }

    private String getInventoryString() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        StringBuilder string = new StringBuilder();
        Set<Integer> keySet = itemsCountMap.keySet();

        for (int itemId : keySet) {
            ResultSet itemResultSet = dbHandler.getItemById(itemId);

            itemResultSet.next();
            String itemName = itemResultSet.getString(2);
            long itemEmojiId = itemResultSet.getLong(6);

            string.append(Emoji.fromEmote(itemName, itemEmojiId, false).getAsMention())
                    .append(" ").append(itemName)
                    .append(": ").append(itemsCountMap.get(itemId))
                    .append(" \n");
        }

        return string.toString();
    }
}
