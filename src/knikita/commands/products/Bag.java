package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import knikita.model.equip;
import knikita.model.inventory;
import knikita.model.items;
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
    public void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        itemsCountMap = new TreeMap<>(); //TODO звучит здоров но непонятно

        inventory inv = new inventory();
        inv.setUser_id(event.getAuthor().getIdLong());
        ResultSet resultSet = dbHandler.selectFromTable(inv);

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

        equip eq = new equip();
        eq.setUser_id(event.getAuthor().getIdLong());
        ResultSet equipResultSet = dbHandler.selectFromTable(eq);

        try { //TODO Дикая херня
            equipResultSet.next();
            items item = new items();

            item.setItem_id(equipResultSet.getInt(3));
            ResultSet itemResultSet = dbHandler.selectFromTable(item);
            itemResultSet.next();
            String armor_name = itemResultSet.getString(2);
            Emoji armor_emoji = Emoji.fromEmote(armor_name, itemResultSet.getLong(6), false);

            item.setItem_id(equipResultSet.getInt(4));
            itemResultSet = dbHandler.selectFromTable(item);
            itemResultSet.next();
            String weapon_name = itemResultSet.getString(2);
            Emoji weapon_emoji = Emoji.fromEmote(armor_name, itemResultSet.getLong(6), false);

            item.setItem_id(equipResultSet.getInt(5));
            itemResultSet = dbHandler.selectFromTable(item);
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
            items item = new items();
            item.setItem_id(itemId);
            ResultSet itemResultSet = dbHandler.selectFromTable(item);

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
