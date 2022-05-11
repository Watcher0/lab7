package common.data;

import common.util.User;

import java.io.Serializable;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class Movie implements Comparable<Movie>, Serializable {
    private Integer id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private Long oscarsCount;
    private Integer goldenPalmCount;
    private MovieGenre genre;
    private MpaaRating mpaaRating;
    private Person operator;
    private User owner;

    public Movie(Integer id, String name, Coordinates coordinates, Long oscarsCount, Integer goldenPalmCount, MovieGenre
                 genre, MpaaRating mpaaRating, Person operator, User owner){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
        creationDate = LocalDate.now();
        this.owner = owner;
    }

    public Movie(Integer id, String name, Coordinates coordinates, LocalDate creationDate, Long oscarsCount, Integer goldenPalmCount, MovieGenre
            genre, MpaaRating mpaaRating, Person operator, User owner){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.genre = genre;
        this.mpaaRating = mpaaRating;
        this.operator = operator;
        this.owner = owner;
    }


    public Integer getId() {
        return id;
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

    public User getOwner() {
        return owner;
    }

    @Override
    public int compareTo(Movie movie) {
        Integer notNullGoldenPalm = goldenPalmCount;
        if (goldenPalmCount == null){
            notNullGoldenPalm = 1;
        }
        Integer notNullGoldenPalmO = movie.goldenPalmCount;
        if (movie.goldenPalmCount == null){
            notNullGoldenPalmO = 1;
        }

        MpaaRating notNullMpaaRating = mpaaRating;
        if (mpaaRating == null){
            notNullMpaaRating = MpaaRating.G;
        }
        MpaaRating notNullMpaaRatingO = movie.mpaaRating;
        if (movie.mpaaRating == null){
            notNullMpaaRatingO = MpaaRating.G;
        }
        if (oscarsCount > movie.oscarsCount){
            return 1;
        }
        else if (oscarsCount < movie.oscarsCount){
            return -1;
        }
        else if (notNullGoldenPalm > notNullGoldenPalmO){
            return 1;
        }
        else if (notNullGoldenPalm < notNullGoldenPalmO){
            return -1;
        }
        else if (genre.compareTo(movie.genre) > 0){
            return 1;
        }
        else if (genre.compareTo(movie.genre) < 0){
            return -1;
        }
        else if (notNullMpaaRating.compareTo(notNullMpaaRatingO) > 0){
            return 1;
        }
        else if (notNullMpaaRating.compareTo(notNullMpaaRatingO) < 0){
            return -1;
        }
        else if (operator.compareTo(movie.operator) > 0){
            return 1;
        }
        else if (operator.compareTo(movie.operator) < 0){
            return -1;
        }
        else if (coordinates.compareTo(movie.coordinates) > 0){
            return 1;
        }
        else if (coordinates.compareTo(movie.coordinates) < 0){
            return -1;
        }
        else return 0;
    }

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "name = " + name + "\n" +
                "coordinates = " + coordinates + "\n" +
                "creationDate = " + creationDate.format(ISO_LOCAL_DATE) + "\n" +
                "oscarsCount = " + oscarsCount + "\n" +
                "goldenPalmCount = " + goldenPalmCount + "\n" +
                "genre = " + genre.toString() + "\n" +
                "mpaaRating = " + mpaaRating + "\n" +
                "operator: " + "\n" + operator.toString() +
                "owner = " + owner.getLogin();
    }
}
