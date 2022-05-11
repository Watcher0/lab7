package ServerModule.util;

import common.data.*;
import common.exceptions.DatabaseManagerException;
import common.util.MovieLite;
import common.util.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;

public class DatabaseCollectionManager {
    private final String CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseManager.MOVIE_TABLE +
            " (" + DatabaseManager.MOVIE_TABLE_ID_COLUMN + " SERIAL PRIMARY KEY, " +
            DatabaseManager.MOVIE_TABLE_NAME_COLUMN + " VARCHAR(200), " +
            DatabaseManager.MOVIE_TABLE_COORDINATES_ID_COLUMN + " INT REFERENCES " + DatabaseManager.COORDINATES_TABLE + "(" + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + "), " +
            DatabaseManager.MOVIE_TABLE_CREATION_DATE_COLUMN + " VARCHAR(200), " +
            DatabaseManager.MOVIE_TABLE_OSCARS_COUNT_COLUMN + " BIGINT, " +
            DatabaseManager.MOVIE_TABLE_GOLDEN_PALM_COUNT_COLUMN + " INT, " +
            DatabaseManager.MOVIE_TABLE_GENRE_COLUMN + " VARCHAR(200), " +
            DatabaseManager.MOVIE_TABLE_MPAA_RATING_COLUMN + " VARCHAR(200), " +
            DatabaseManager.MOVIE_TABLE_PERSON_ID_COLUMN + " INT REFERENCES " + DatabaseManager.PERSON_TABLE + "(" + DatabaseManager.PERSON_TABLE_ID_COLUMN + "), " +
            DatabaseManager.MOVIE_TABLE_USER_ID_COLUMN + " INT REFERENCES " + DatabaseManager.USER_TABLE + "(" + DatabaseManager.USER_TABLE_ID_COLUMN + "))" ;
    private final String SELECT_ALL_MOVIES = "SELECT * FROM " + DatabaseManager.MOVIE_TABLE;
    private final String SELECT_MOVIE_BY_ID = SELECT_ALL_MOVIES + " WHERE " +
            DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_MOVIE_BY_ID_AND_USER_ID = SELECT_MOVIE_BY_ID + " AND " +
            DatabaseManager.MOVIE_TABLE_USER_ID_COLUMN + " = ?";
    private final String INSERT_MOVIE = "INSERT INTO " +
            DatabaseManager.MOVIE_TABLE + " (" +
            DatabaseManager.MOVIE_TABLE_NAME_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_COORDINATES_ID_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_OSCARS_COUNT_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_GOLDEN_PALM_COUNT_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_GENRE_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_MPAA_RATING_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_PERSON_ID_COLUMN + ", " +
            DatabaseManager.MOVIE_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_MOVIE_BY_ID = "DELETE FROM " + DatabaseManager.MOVIE_TABLE +
            " WHERE " + DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_NAME_BY_ID = "UPDATE " + DatabaseManager.MOVIE_TABLE + " SET " +
            DatabaseManager.MOVIE_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_OSCARS_COUNT_BY_ID = "UPDATE " + DatabaseManager.MOVIE_TABLE + " SET " +
            DatabaseManager.MOVIE_TABLE_OSCARS_COUNT_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_GOLDEN_PALM_COUNT_BY_ID = "UPDATE " + DatabaseManager.MOVIE_TABLE + " SET " +
            DatabaseManager.MOVIE_TABLE_GOLDEN_PALM_COUNT_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_GENRE_BY_ID = "UPDATE " + DatabaseManager.MOVIE_TABLE + " SET " +
            DatabaseManager.MOVIE_TABLE_GENRE_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_MOVIE_MPAA_RATING_BY_ID = "UPDATE " + DatabaseManager.MOVIE_TABLE + " SET " +
            DatabaseManager.MOVIE_TABLE_MPAA_RATING_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.MOVIE_TABLE_ID_COLUMN + " = ?";

    private final String CREATE_COORDINATES_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseManager.COORDINATES_TABLE +
             "(" + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " SERIAL PRIMARY KEY, " +
            DatabaseManager.COORDINATES_TABLE_X_COLUMN + " INT, " +
            DatabaseManager.COORDINATES_TABLE_Y_COLUMN + " double precision)";
    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseManager.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_ID = SELECT_ALL_COORDINATES + " WHERE " + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_COORDINATES_BY_ID = "DELETE FROM " + DatabaseManager.COORDINATES_TABLE +
            " WHERE " + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
            DatabaseManager.COORDINATES_TABLE + " (" +
            DatabaseManager.COORDINATES_TABLE_X_COLUMN + ", " +
            DatabaseManager.COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?)";
    private final String UPDATE_COORDINATES_BY_ID = "UPDATE " + DatabaseManager.COORDINATES_TABLE + " SET " +
            DatabaseManager.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseManager.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " = ?";

    private final String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseManager.PERSON_TABLE +
            "(" + DatabaseManager.PERSON_TABLE_ID_COLUMN + " SERIAL PRIMARY KEY, " +
            DatabaseManager.PERSON_TABLE_NAME_COLUMN + " VARCHAR(200), " +
            DatabaseManager.PERSON_TABLE_BIRTHDAY_COLUMN + " VARCHAR(200), " +
            DatabaseManager.PERSON_TABLE_HEIGHT_COLUMN + " BIGINT, " +
            DatabaseManager.PERSON_TABLE_PASSPORT_COLUMN + " VARCHAR(200), " +
            DatabaseManager.PERSON_TABLE_LOCATION_ID_COLUMN + " INT REFERENCES " + DatabaseManager.LOCATION_TABLE + "(" + DatabaseManager.LOCATION_TABLE_ID_COLUMN + "))";
    private final String SELECT_ALL_PERSONS = "SELECT * FROM " + DatabaseManager.PERSON_TABLE;
    private final String SELECT_PERSON_BY_ID = SELECT_ALL_PERSONS + " WHERE " + DatabaseManager.PERSON_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_PERSON_BY_ID = "DELETE FROM " + DatabaseManager.PERSON_TABLE +
            " WHERE " + DatabaseManager.PERSON_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_PERSON = "INSERT INTO " +
            DatabaseManager.PERSON_TABLE + " (" +
            DatabaseManager.PERSON_TABLE_NAME_COLUMN + ", " +
            DatabaseManager.PERSON_TABLE_BIRTHDAY_COLUMN + ", " +
            DatabaseManager.PERSON_TABLE_HEIGHT_COLUMN + ", " +
            DatabaseManager.PERSON_TABLE_PASSPORT_COLUMN + ", " +
            DatabaseManager.PERSON_TABLE_LOCATION_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_PERSON_BY_ID = "UPDATE " + DatabaseManager.PERSON_TABLE + " SET " +
            DatabaseManager.PERSON_TABLE_NAME_COLUMN + " = ?, " +
            DatabaseManager.PERSON_TABLE_BIRTHDAY_COLUMN + " = ?, " +
            DatabaseManager.PERSON_TABLE_HEIGHT_COLUMN + " = ?, " +
            DatabaseManager.PERSON_TABLE_PASSPORT_COLUMN + ", " +
            DatabaseManager.PERSON_TABLE_LOCATION_ID_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.PERSON_TABLE_ID_COLUMN + " = ?";

    private final String CREATE_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + DatabaseManager.LOCATION_TABLE +
            "(" + DatabaseManager.LOCATION_TABLE_ID_COLUMN + " SERIAL PRIMARY KEY, " +
            DatabaseManager.LOCATION_TABLE_X_COLUMN + " INT, " +
            DatabaseManager.LOCATION_TABLE_Y_COLUMN + " double precision, " +
            DatabaseManager.LOCATION_TABLE_NAME_COLUMN + " VARCHAR(200))";
    private final String SELECT_ALL_LOCATIONS = "SELECT * FROM " + DatabaseManager.LOCATION_TABLE;
    private final String SELECT_LOCATION_BY_ID = SELECT_ALL_LOCATIONS + " WHERE " + DatabaseManager.LOCATION_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_LOCATION_BY_ID = "DELETE FROM " + DatabaseManager.LOCATION_TABLE +
            " WHERE " + DatabaseManager.LOCATION_TABLE_ID_COLUMN + " = ?";
    String INSERT_LOCATION = "INSERT INTO " +
            DatabaseManager.LOCATION_TABLE + " (" +
            DatabaseManager.LOCATION_TABLE_X_COLUMN + ", " +
            DatabaseManager.LOCATION_TABLE_Y_COLUMN + ", " +
            DatabaseManager.LOCATION_TABLE_NAME_COLUMN + ") VALUES (?, ?, ?)";
    private final String UPDATE_LOCATION_BY_ID = "UPDATE " + DatabaseManager.LOCATION_TABLE + " SET " +
            DatabaseManager.LOCATION_TABLE_X_COLUMN + " = ?, " +
            DatabaseManager.LOCATION_TABLE_Y_COLUMN + " = ?, " +
            DatabaseManager.LOCATION_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.LOCATION_TABLE_ID_COLUMN + " = ?";

    private DatabaseManager databaseManager;
    private ServerModule.util.DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseManager databaseManager, ServerModule.util.DatabaseUserManager databaseUserManager) {
        this.databaseManager = databaseManager;
        this.databaseUserManager = databaseUserManager;
        createTables();
    }

    private void createTables() {
        PreparedStatement createMovie = null;
        PreparedStatement createCoordinates = null;
        PreparedStatement createPerson = null;
        PreparedStatement createLocation = null;
        try {
            createLocation = databaseManager.doPreparedStatement(CREATE_LOCATION_TABLE, false);
            createLocation.executeUpdate();

            createPerson = databaseManager.doPreparedStatement(CREATE_PERSON_TABLE, false);
            createPerson.executeUpdate();

            createCoordinates = databaseManager.doPreparedStatement(CREATE_COORDINATES_TABLE, false);
            createCoordinates.executeUpdate();

            createMovie = databaseManager.doPreparedStatement(CREATE_MOVIE_TABLE, false);
            createMovie.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseManager.closePreparedStatement(createLocation);
            databaseManager.closePreparedStatement(createPerson);
            databaseManager.closePreparedStatement(createCoordinates);
            databaseManager.closePreparedStatement(createMovie);
        }
    }

    private Movie returnMovie(ResultSet resultSet, int id) throws SQLException{
        String name = resultSet.getString(DatabaseManager.MOVIE_TABLE_NAME_COLUMN);
        Coordinates coordinates = getCoordinatesByID(resultSet.getInt(DatabaseManager.MOVIE_TABLE_COORDINATES_ID_COLUMN));
        LocalDate creationDate = LocalDate.parse(resultSet.getString(DatabaseManager.MOVIE_TABLE_CREATION_DATE_COLUMN));
        long oscarsCount = resultSet.getLong(DatabaseManager.MOVIE_TABLE_OSCARS_COUNT_COLUMN);
        int goldenPalmCount = resultSet.getInt(DatabaseManager.MOVIE_TABLE_GOLDEN_PALM_COUNT_COLUMN);
        MovieGenre genre = MovieGenre.valueOf(resultSet.getString(DatabaseManager.MOVIE_TABLE_GENRE_COLUMN));
        MpaaRating mpaaRating  = MpaaRating.valueOf(resultSet.getString(DatabaseManager.MOVIE_TABLE_MPAA_RATING_COLUMN));
        Person operator = getPersonByID(resultSet.getInt(DatabaseManager.MOVIE_TABLE_PERSON_ID_COLUMN));
        User owner = databaseUserManager.getUserById(resultSet.getInt(DatabaseManager.MOVIE_TABLE_USER_ID_COLUMN));
        return new Movie(id, name, coordinates, creationDate, oscarsCount, goldenPalmCount, genre, mpaaRating, operator, owner);
    }

    public HashSet<Movie> getCollection() {
        HashSet<Movie> movies = new HashSet<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_ALL_MOVIES, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(DatabaseManager.MOVIE_TABLE_ID_COLUMN);
                movies.add(returnMovie(resultSet, id));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return movies;
    }

    private Coordinates getCoordinatesByID(int id) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_COORDINATES_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getInt(DatabaseManager.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseManager.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_COORDINATES_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return coordinates;
    }

    private Person getPersonByID(int id) throws SQLException {
        Person person;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_PERSON_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                person = new Person(
                        resultSet.getString(DatabaseManager.PERSON_TABLE_NAME_COLUMN),
                        LocalDate.parse(resultSet.getString(DatabaseManager.PERSON_TABLE_BIRTHDAY_COLUMN)),
                        resultSet.getLong(DatabaseManager.PERSON_TABLE_HEIGHT_COLUMN),
                        resultSet.getString(DatabaseManager.PERSON_TABLE_PASSPORT_COLUMN),
                        getLocationByID(getLocationIdByPersonID(id))
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_PERSON_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return person;
    }

    private Location getLocationByID(int id) throws SQLException {
        Location location;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_LOCATION_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                location = new Location(
                        resultSet.getInt(DatabaseManager.LOCATION_TABLE_X_COLUMN),
                        resultSet.getFloat(DatabaseManager.LOCATION_TABLE_Y_COLUMN),
                        resultSet.getString(DatabaseManager.LOCATION_TABLE_NAME_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_LOCATION_BY_ID!");
            e.printStackTrace();
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return location;
    }

    private int getLocationIdByPersonID (int personID) throws SQLException {
        int locationID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_PERSON_BY_ID, false);
            preparedStatement.setInt(1, personID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                locationID = resultSet.getInt(DatabaseManager.PERSON_TABLE_LOCATION_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_PERSON_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return locationID;
    }

    private int getPersonIdByMovieID (int MovieID) throws SQLException {
        int personID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_MOVIE_BY_ID, false);
            preparedStatement.setInt(1, MovieID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personID = resultSet.getInt(DatabaseManager.MOVIE_TABLE_PERSON_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_MOVIE_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return personID;
    }

    private int getCoordinatesIdByMovieID(int MovieID) throws SQLException {
        int coordinatesID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_MOVIE_BY_ID, false);
            preparedStatement.setInt(1, MovieID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                coordinatesID = resultSet.getInt(DatabaseManager.MOVIE_TABLE_COORDINATES_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_MOVIE_BY_ID!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return coordinatesID;
    }

    public Movie insertMovie(MovieLite movieLite, User user) throws DatabaseManagerException {
        Movie movieToInsert;
        PreparedStatement insertMovie = null;
        PreparedStatement insertCoordinates = null;
        PreparedStatement insertPerson = null;
        PreparedStatement insertLocation = null;
        try {
            databaseManager.setCommit();
            databaseManager.setSavepoint();

            LocalDate localDate = LocalDate.now();

            insertLocation = databaseManager.doPreparedStatement(INSERT_LOCATION, true);
            insertLocation.setInt(1, movieLite.getOperator().getLocation().getX());
            insertLocation.setFloat(2, movieLite.getOperator().getLocation().getY());
            insertLocation.setString(3, movieLite.getOperator().getLocation().getName());
            if (insertLocation.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetLocation = insertLocation.getGeneratedKeys();
            int locationID;
            if (resultSetLocation.next()) locationID = resultSetLocation.getInt(1) ;
            else throw new SQLException();

            insertPerson = databaseManager.doPreparedStatement(INSERT_PERSON, true);
            insertPerson.setString(1, movieLite.getOperator().getName());
            insertPerson.setString(2, movieLite.getOperator().getBirthday().toString());
            insertPerson.setLong(3, movieLite.getOperator().getHeight());
            insertPerson.setString(4, movieLite.getOperator().getPassportID());
            insertPerson.setInt(5, locationID);
            if (insertPerson.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetPerson = insertPerson.getGeneratedKeys();
            int personID;
            if (resultSetPerson.next()) personID = resultSetPerson.getInt(1);
            else throw new SQLException();

            insertCoordinates = databaseManager.doPreparedStatement(INSERT_COORDINATES, true);
            insertCoordinates.setDouble(1, movieLite.getCoordinates().getX());
            insertCoordinates.setDouble(2, movieLite.getCoordinates().getY());
            if (insertCoordinates.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetCoordinates = insertCoordinates.getGeneratedKeys();
            int coordinatesID;
            if (resultSetCoordinates.next()) coordinatesID = resultSetCoordinates.getInt(1);
            else throw new SQLException();

            insertMovie = databaseManager.doPreparedStatement(INSERT_MOVIE, true);
            insertMovie.setString(1, movieLite.getName());
            insertMovie.setInt(2, coordinatesID);
            insertMovie.setString(3, localDate.toString());
            insertMovie.setLong(4, movieLite.getOscarsCount());
            insertMovie.setInt(5, movieLite.getGoldenPalmCount());
            insertMovie.setString(6, movieLite.getGenre().toString());
            insertMovie.setString(7, movieLite.getMpaaRating().toString());
            insertMovie.setInt(8, personID);
            insertMovie.setInt(9, databaseUserManager.getUserIdByUsername(user));
            if (insertMovie.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetMovie = insertMovie.getGeneratedKeys();
            int MovieID;
            if (resultSetMovie.next()) MovieID = resultSetMovie.getInt(1);
            else throw new SQLException();
            movieToInsert = new Movie(
                    MovieID,
                    movieLite.getName(),
                    movieLite.getCoordinates(),
                    localDate,
                    movieLite.getOscarsCount(),
                    movieLite.getGoldenPalmCount(),
                    movieLite.getGenre(),
                    movieLite.getMpaaRating(),
                    movieLite.getOperator(),
                    user
            );
            databaseManager.commit();
            return movieToInsert;
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при добавлении нового объекта в БД!");
            exception.printStackTrace();
            databaseManager.rollback();
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(insertPerson);
            databaseManager.closePreparedStatement(insertCoordinates);
            databaseManager.closePreparedStatement(insertMovie);
            databaseManager.closePreparedStatement(insertLocation);
            databaseManager.setAutoCommit();
        }
    }

    public void updateMovieByID(int MovieID, MovieLite movieLite) throws DatabaseManagerException {
        PreparedStatement updateMovieName = null;
        PreparedStatement updateMovieCoordinates = null;
        PreparedStatement updateMovieOscarsCount = null;
        PreparedStatement updateMovieGoldenPalmCount = null;
        PreparedStatement updateMovieGenre = null;
        PreparedStatement updateMovieMpaaRating = null;
        PreparedStatement updateMoviePerson = null;
        PreparedStatement updateMoviePersonLocation = null;
        try {
            databaseManager.setCommit();
            databaseManager.setSavepoint();

            updateMovieName = databaseManager.doPreparedStatement(UPDATE_MOVIE_NAME_BY_ID, false);
            updateMovieCoordinates = databaseManager.doPreparedStatement(UPDATE_COORDINATES_BY_ID, false);
            updateMovieOscarsCount = databaseManager.doPreparedStatement(UPDATE_MOVIE_OSCARS_COUNT_BY_ID, false);
            updateMovieGoldenPalmCount = databaseManager.doPreparedStatement(UPDATE_MOVIE_GOLDEN_PALM_COUNT_BY_ID, false);
            updateMovieGenre = databaseManager.doPreparedStatement(UPDATE_MOVIE_GENRE_BY_ID, false);
            updateMovieMpaaRating = databaseManager.doPreparedStatement(UPDATE_MOVIE_MPAA_RATING_BY_ID, false);
            updateMoviePerson = databaseManager.doPreparedStatement(UPDATE_PERSON_BY_ID, false);
            updateMoviePersonLocation = databaseManager.doPreparedStatement(UPDATE_LOCATION_BY_ID, false);

            if (movieLite.getName() != null) {
                updateMovieName.setString(1, movieLite.getName());
                updateMovieName.setInt(2, MovieID);
                if (updateMovieName.executeUpdate() == 0) throw new SQLException();
            }
            if (movieLite.getCoordinates() != null) {
                updateMovieCoordinates.setInt(1, movieLite.getCoordinates().getX());
                updateMovieCoordinates.setFloat(2, movieLite.getCoordinates().getY());
                updateMovieCoordinates.setInt(3, getCoordinatesIdByMovieID(MovieID));
                if (updateMovieCoordinates.executeUpdate() == 0) throw new SQLException();
            }
            if (movieLite.getOscarsCount() != -1) {
                updateMovieOscarsCount.setLong(1, movieLite.getOscarsCount());
                updateMovieOscarsCount.setInt(2, MovieID);
                if (updateMovieOscarsCount.executeUpdate() == 0) throw new SQLException();
            }
            if (movieLite.getGoldenPalmCount() != -1) {
                updateMovieGoldenPalmCount.setInt(1, movieLite.getGoldenPalmCount());
                updateMovieGoldenPalmCount.setInt(2, MovieID);
                if (updateMovieGoldenPalmCount.executeUpdate() == 0) throw new SQLException();
            }
            if (movieLite.getGenre() != null) {
                updateMovieGenre.setString(1, movieLite.getGenre().toString());
                updateMovieGenre.setInt(2, MovieID);
                if (updateMovieGenre.executeUpdate() == 0) throw new SQLException();
            }
            if (movieLite.getMpaaRating() != null) {
                updateMovieMpaaRating.setString(1, movieLite.getMpaaRating().toString());
                updateMovieMpaaRating.setInt(2, MovieID);
                if (updateMovieMpaaRating.executeUpdate() == 0) throw new SQLException();
            }
            if (movieLite.getOperator() != null && movieLite.getOperator().getLocation() != null) {
                updateMoviePersonLocation.setInt(1, movieLite.getOperator().getLocation().getX());
                updateMoviePersonLocation.setFloat(2, movieLite.getOperator().getLocation().getY());
                updateMoviePersonLocation.setString(3, movieLite.getOperator().getLocation().getName());
                updateMoviePersonLocation.setInt(4, getLocationIdByPersonID(getPersonIdByMovieID(MovieID)));
            }
            if (movieLite.getOperator() != null) {
                updateMoviePerson.setString(1, movieLite.getOperator().getName());
                updateMoviePerson.setString(2, movieLite.getOperator().getBirthday().toString());
                updateMoviePerson.setLong(3, movieLite.getOperator().getHeight());
                updateMoviePerson.setString(4, movieLite.getOperator().getPassportID());
                updateMoviePerson.setInt(5, getLocationIdByPersonID(getPersonIdByMovieID(MovieID)));
                updateMoviePerson.setInt(6, getPersonIdByMovieID(MovieID));
                if (updateMoviePerson.executeUpdate() == 0) throw new SQLException();
            }
            databaseManager.commit();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            databaseManager.rollback();
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(updateMovieName);
            databaseManager.closePreparedStatement(updateMovieCoordinates);
            databaseManager.closePreparedStatement(updateMovieOscarsCount);
            databaseManager.closePreparedStatement(updateMovieGoldenPalmCount);
            databaseManager.closePreparedStatement(updateMovieGenre);
            databaseManager.closePreparedStatement(updateMovieMpaaRating);
            databaseManager.closePreparedStatement(updateMoviePerson);
            databaseManager.closePreparedStatement(updateMoviePersonLocation);
            databaseManager.setAutoCommit();
        }
    }

    public boolean checkMovieByIdAndUserId(int MovieID, User user) throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_MOVIE_BY_ID_AND_USER_ID, false);
            preparedStatement.setInt(1, MovieID);
            preparedStatement.setInt(2, databaseUserManager.getUserIdByUsername(user));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_MOVIE_BY_ID_AND_USER_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
    }

    public void deleteMovieById(int MovieID) throws DatabaseManagerException {
        PreparedStatement deleteMovie = null;
        PreparedStatement deleteCoordinates = null;
        PreparedStatement deletePerson = null;
        PreparedStatement deleteLocation = null;
        try {
            int coordinatesID = getCoordinatesIdByMovieID(MovieID);
            int personID = getPersonIdByMovieID(MovieID);
            int locationID = getLocationIdByPersonID(personID);
            deleteMovie = databaseManager.doPreparedStatement(DELETE_MOVIE_BY_ID, false);
            deleteMovie.setInt(1, MovieID);
            if (deleteMovie.executeUpdate() == 0) throw new SQLException();
            deleteCoordinates = databaseManager.doPreparedStatement(DELETE_COORDINATES_BY_ID, false);
            deleteCoordinates.setInt(1, coordinatesID);
            if (deleteCoordinates.executeUpdate() == 0) throw new SQLException();
            deletePerson = databaseManager.doPreparedStatement(DELETE_PERSON_BY_ID, false);
            deletePerson.setInt(1, personID);
            if (deletePerson.executeUpdate() == 0) throw new SQLException();
            deleteLocation = databaseManager.doPreparedStatement(DELETE_LOCATION_BY_ID, false);
            deleteLocation.setInt(1, locationID);
            if (deleteLocation.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса DELETE_MOVIE_BY_ID!");
            exception.printStackTrace();
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(deleteMovie);
            databaseManager.closePreparedStatement(deleteCoordinates);
            databaseManager.closePreparedStatement(deletePerson);
            databaseManager.closePreparedStatement(deleteLocation);
        }
    }

    public void clearCollection() throws DatabaseManagerException{
        HashSet<Movie> Movies = getCollection();
        for (Movie Movie : Movies) {
            deleteMovieById(Movie.getId());
        }
    }
}
