package knikita.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "items_table")
@Getter
@Setter
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "item_emoji_id")
    private long emoji_id;

    @ManyToMany(mappedBy = "inventory")
    private List<User> owners;
}
