package knikita.commands.products;

import knikita.commands.factory.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.TreeMap;

public class Buy extends Command {
    @Override
    public long getCoolDownTime() {
        return 0;
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes) {
        // - Забрать монеты
        // - Дать предмет если монеты есть
        // - Не дать если монет нету

    }

    @Override
    public void sendFinishMessageSuccess(GuildMessageReceivedEvent event, TreeMap<Integer, Integer> receivedItems) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.lightGray);

        embedBuilder.setTitle("**💠 Commands 💠**");
        embedBuilder.addField("**For exact info use: >help [command/item]**", "Add > before every command", false);
        embedBuilder.addField("**💰 To get some coins**", "``fish``, ``hunt``", false);
        embedBuilder.addField("**ℹ Information**", "``bag``", false);
        embedBuilder.setFooter("Direct commands soon");

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
