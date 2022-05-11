package common.util;

import common.data.Coordinates;
import common.data.MovieGenre;
import common.data.MpaaRating;
import common.data.Person;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class MovieLite implements Serializable {
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Long oscarsCount;
    private Integer goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;

    public MovieLite(String name, Coordinates coordinates, LocalDate creationDate, Long oscarsCount, Integer goldenPalmCount, MovieGenre genre, MpaaRating mpaaRating, Person operator) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
    }

    public MovieLite() {

    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getOscarsCount() {
        return oscarsCount;
    }

    public Integer getGoldenPalmCount() {
        return goldenPalmCount;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    public Person getOperator() {
        return operator;
    }
}
