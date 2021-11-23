package knikita.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "enemy_table")
@Getter
@Setter
public class EnemyNPC {

    @Id
    @Column(name = "enemy_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "enemy_health")
    private int health;

    @Column(name = "enemy_damage")
    private int damage;
}
