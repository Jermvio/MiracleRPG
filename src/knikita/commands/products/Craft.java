package knikita.commands.products;

import knikita.commands.factory.Command;
import knikita.dao.DatabaseHandler;
import knikita.model.allowed_recipes;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Craft extends Command {
    @Override
    public long getCoolDownTime() {
        return 0;
    }

    @Override
    public void commandBody(GuildMessageReceivedEvent event, String[] commandAttributes) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        if (commandAttributes.length == 1) {
            ResultSet resultSet = dbHandler.selectFromTable(new allowed_recipes().setUser_id(event.getAuthor().getIdLong()));
            try {
                if (resultSet.next()) {

                } else {

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
