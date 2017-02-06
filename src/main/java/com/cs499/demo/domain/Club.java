package com.cs499.demo.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Club.
 */
@Entity
@Table(name = "club")
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "coach", nullable = false)
    private String coach;

    @NotNull
    @Column(name = "stadium", nullable = false)
    private String stadium;

    @NotNull
    @Column(name = "league", nullable = false)
    private String league;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Club name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public Club coach(String coach) {
        this.coach = coach;
        return this;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getStadium() {
        return stadium;
    }

    public Club stadium(String stadium) {
        this.stadium = stadium;
        return this;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getLeague() {
        return league;
    }

    public Club league(String league) {
        this.league = league;
        return this;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Club club = (Club) o;
        if (club.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, club.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Club{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", coach='" + coach + "'" +
            ", stadium='" + stadium + "'" +
            ", league='" + league + "'" +
            '}';
    }
}
