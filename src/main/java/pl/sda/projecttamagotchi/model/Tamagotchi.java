package pl.sda.projecttamagotchi.model;

import com.vaadin.flow.component.html.Image;
import pl.sda.projecttamagotchi.gui.TamagotchiGui;
import pl.sda.projecttamagotchi.repo.TamagotchiRepo;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;

@Entity
public class Tamagotchi {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String name;
    private LocalTime age;
    private int health;
    private long leaves;
    private long gold;
    private long water;
    private long fertilize;
    @OneToOne(mappedBy = "tamagotchi")
    private AppUser user;


    public Tamagotchi() {
    }

    public Tamagotchi(String name, LocalTime age, int health, long leaves, long gold, AppUser user) {
        this.name = name;
        this.age = age;
        this.health = health;
        this.leaves = leaves;
        this.gold = gold;
        this.user = user;
    }

    public Tamagotchi(String name, LocalTime age, int health, long leaves, long gold) {
        this.name = name;
        this.age = age;
        this.health = health;
        this.leaves = leaves;
        this.gold = gold;
    }

    public Tamagotchi(String name, LocalTime age, int health, long leaves, long gold, long water, long fertilize) {
        this.name = name;
        this.age = age;
        this.health = health;
        this.leaves = leaves;
        this.gold = gold;
        this.water = water;
        this.fertilize = fertilize;
    }


    public long getWater() {
        return water;
    }
    public void setWater(long water) {
        this.water = water;
    }

    public long getFertilize() {
        return fertilize;
    }
    public void setFertilize(long fertilize) {
        this.fertilize = fertilize;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getAge() {
        return age;
    }
    public void setAge(LocalTime age) {
        this.age = age;
    }

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public long getLeaves() {
        return leaves;
    }
    public void setLeaves(long leaves) {
        this.leaves = leaves;
    }

    public long getGold() {
        return gold;
    }
    public void setGold(long gold) {
        this.gold = gold;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
    public String getUser() {
        return user.getUsername();
    }


    public void subtractHealth() {
        user.getTamagotchi().setHealth(user.getTamagotchi().getHealth() +1);
    }

    public void addLeaves(){
        user.getTamagotchi().setLeaves(user.getTamagotchi().getLeaves() +1);
    }
}
