package knikita;

import knikita.dao.DatabaseHandler;
import knikita.entity.EnemyNPC;
import knikita.entity.User;

public class MiracleRpgMechanics {

    public static void fight(User userFighter, EnemyNPC enemy) {

        int userDamage = userFighter.getDamage();
        int enemyDamage = enemy.getDamage();

        int userHealth = userFighter.getHealth();
        int enemyHealth = enemy.getHealth();

        while (userHealth > 0 && enemyHealth > 0) {
            enemyHealth -= userDamage;
            userHealth -= enemyDamage;
        }

        DatabaseHandler handler = DatabaseHandler.getInstance();
        userFighter.setHealth(userHealth);
        handler.update(userFighter);
    }
}
