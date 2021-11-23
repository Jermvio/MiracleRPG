package knikita.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users_table")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_health")
    private int health;

    @Column(name = "user_damage")
    private int damage;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "sub_inventory_slots_table")
    private List<Item> inventory;


}
