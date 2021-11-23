package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.commands.factory.GatherCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.TreeMap;

public class Help extends Command {

    @Override
    public void go(GuildMessageReceivedEvent event, String[] commandAttributes) {
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
