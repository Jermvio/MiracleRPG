package knikita.commands.factory;

import knikita.dao.DatabaseHandler;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public abstract class Command {
    public static Command makeCommand(String commandName) {
        commandName = commandName.substring(0, 1).toUpperCase(Locale.ROOT) + commandName.substring(1).toLowerCase(Locale.ROOT);

        Command command;
        try {
            command = (Command) Class.forName("knikita.commands.products." + commandName).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;//TODO описать ошибку
        }
        return command;
    }

    protected TreeMap<Integer, Integer> itemsMap;

    public abstract long getCoolDownTime();

    public abstract void commandBody(GuildMessageReceivedEvent event);

    public void go(GuildMessageReceivedEvent event) {
        itemsMap = new TreeMap<>();

        if (isCoolDownPast(event)) {
            commandBody(event);
            setOnCoolDown(event);
            sendFinishMessageSuccess(event, itemsMap);
        } else {
            sendFinishMessageFail(event);
        }
    }

    public void setOnCoolDown(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        long lastCD = dbHandler.getCommandLastUse(event.getAuthor(), this.getClass().getName());
        if (lastCD != -1) {
            dbHandler.updateCommandCoolDown(event.getAuthor(), this.getClass().getName(),
                    System.currentTimeMillis() + this.getCoolDownTime());
        } else {
            dbHandler.setCommandCoolDown(event.getAuthor(), this.getClass().getName(),
                    System.currentTimeMillis() + this.getCoolDownTime());
        }
    }

    public boolean isCoolDownPast(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        long coolDownLasts = dbHandler.getCommandLastUse(event.getAuthor(), this.getClass().getName());
        return System.currentTimeMillis() >= coolDownLasts;
    }

    public void sendFinishMessageSuccess(GuildMessageReceivedEvent event, TreeMap<Integer, Integer> receivedItems) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet heroResultSet = dbHandler.getHero(event.getAuthor());
        Set<Integer> keySet = receivedItems.keySet();

        try {
            heroResultSet.next();
            File file = new File("src/resources/knikita/text/" + heroResultSet.getString(6)  +
                    "/commands/" + this.getClass().getSimpleName() + "/onComplete" + new Random().nextInt(3));
            BufferedReader reader = new BufferedReader(new FileReader(file));

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(reader.readLine()).append(" ");

            for (int itemId : keySet) {
                ResultSet itemResultSet = dbHandler.getItemById(itemId);
                itemResultSet.next();
                stringBuilder.append(Emoji.fromEmote(itemResultSet.getString(2), itemResultSet.getLong(6), false).getAsMention());
                stringBuilder.append(itemResultSet.getString(2));
                stringBuilder.append(" x");
                stringBuilder.append(receivedItems.get(itemId));
                stringBuilder.append(" ");
            }
            event.getChannel().sendMessage(stringBuilder.toString()).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendFinishMessageFail(GuildMessageReceivedEvent event) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        long coolDownLasts = dbHandler.getCommandLastUse(event.getAuthor(), this.getClass().getName());
        event.getChannel().sendMessage("Please wait " +
                ((coolDownLasts - System.currentTimeMillis()) / 1000) +
                " seconds").queue();//TODO локализация
    }
}
