package com.cs499.demo.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Soccerplayer.
 */
@Entity
@Table(name = "soccerplayer")
public class Soccerplayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "goal", nullable = false)
    private Integer goal;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @OneToOne
    @NotNull
    @JoinColumn(unique = true)
    private Country country;

    @OneToOne
    @NotNull
    @JoinColumn(unique = true)
    private Club club;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Soccerplayer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGoal() {
        return goal;
    }

    public Soccerplayer goal(Integer goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public String getPosition() {
        return position;
    }

    public Soccerplayer position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Country getCountry() {
        return country;
    }

    public Soccerplayer country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Club getClub() {
        return club;
    }

    public Soccerplayer club(Club club) {
        this.club = club;
        return this;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Soccerplayer soccerplayer = (Soccerplayer) o;
        if (soccerplayer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, soccerplayer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Soccerplayer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", goal='" + goal + "'" +
            ", position='" + position + "'" +
            '}';
    }
}
