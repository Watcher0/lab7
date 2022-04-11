package utils;

import data.Coordinates;
import data.Location;
import data.MovieGenre;
import data.MpaaRating;
import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * Класс, устанавливающий значения полей объекта класса Movie
 */
public class FieldSetterClass {

    public static Integer getMovieId(String s) throws NumberOutOfBoundsException, UsedIdException {
        Parser.exceptionPointer = "id";
        ValidationClass.checkInt(s, false, true, 0);
        Integer id = Integer.valueOf(s);
        if (Collection.getUsedId().contains(id)){
            throw new UsedIdException();
        }
        return id;
    }

    /**
     * Метод, устанавливающий значение movieName
     * @param s необработанная строка
     * @return groupName
     */
    public static String getMovieName(String s){
        Parser.exceptionPointer = "name";
        ValidationClass.checkString(s, false);
        String name = new String(s);
        return name;
    }
    /**
     * Метод, устанавливающий значение groupCoordinates
     * @param s необработанная строка
     * @return groupCoordinates
     */
    public static Coordinates getMovieCoordinates(String s) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        Parser.exceptionPointer = "coordinates";
        ValidationClass.checkCoordinates(s, false);
        Coordinates coordinates = Coordinates.valueOf(s);
        if (s.equals("")){return null;}
        return coordinates;
    }
    /**
     * Метод, устанавливающий значение groupStudentsCount
     * @param s необработанная строка
     * @return groupStudentsCount
     */
    public static Long getMovieOscarsCount(String s) throws NumberOutOfBoundsException{
        Parser.exceptionPointer = "OscarsCount";
        ValidationClass.checkLong(s, false, true, 0L);
        Long studentsCount = Long.valueOf(s);
        if (s.equals("")){return null;}
        return studentsCount;
    }
    /**
     * Метод, устанавливающий значение groupExpelledStudents
     * @param s необработанная строка
     * @return groupExpelledStudents
     */
    public static Integer getMovieGoldenPalmCount(String s) throws NumberOutOfBoundsException{
        Parser.exceptionPointer = "goldenPalmCount";
        ValidationClass.checkInt(s, true, true, 0);
        if (s.equals("")){return null;}
        Integer expelledStudents = Integer.valueOf(s);
        return expelledStudents;
    }
    /**
     * Метод, устанавливающий значение groupShouldBeExpelled
     * @param s необработанная строка
     * @return groupShouldBeExpelled
     */
    public static MovieGenre getMovieGenre(String s){
        Parser.exceptionPointer = "genre";
        ValidationClass.checkGenre(s, false);
        if (s.equals("")){return null;}
        MovieGenre movieGenre = MovieGenre.valueOf(s);
        return movieGenre;
    }
    /**
     * Метод, устанавливающий значение groupSemesterEnum
     * @param s необработанная строка
     * @return groupSemesterEnum
     */
    public static MpaaRating getMovieMpaaRating(String s){
        Parser.exceptionPointer = "mpaaRating";
        ValidationClass.checkMpaaRating(s, true);
        if (s.equals("")){return null;}
        MpaaRating mpaaRating = MpaaRating.valueOf(s);
        return mpaaRating;
    }
    /**
     * Метод, устанавливающий значение adminName
     * @param s необработанная строка
     * @return adminName
     */
    public static String getOperatorName(String s){
        Parser.exceptionPointer = "movieOperator - name";
        ValidationClass.checkString(s, false);
        if (s.equals("")){return null;}
        return s;
    }
    /**
     * Метод, устанавливающий значение adminHeight
     * @param s необработанная строка
     * @return adminHeight
     */
    public static Long getOperatorHeight(String s) throws NumberOutOfBoundsException{
        Parser.exceptionPointer = "movieOperator - height";
        ValidationClass.checkLong(s, false, true, 0L);
        if (s.equals("")){return null;}
        Long height = Long.valueOf(s);
        return height;
    }
    /**
     * Метод, устанавливающий значение adminHairColor
     * @param s необработанная строка
     * @return adminHairColor
     */
    public static LocalDate getOperatorBirthday(String s){
        Parser.exceptionPointer = "movieOperator - birthday";
        ValidationClass.checkDate(s, true);
        if (s.equals("")){return null;}
        LocalDate birthday = LocalDate.parse(s, ISO_LOCAL_DATE);
        return birthday;
    }
    /**
     * Метод, устанавливающий значение adminNationality
     * @param s необработанная строка
     * @return adminNationality
     */
    public static String getOperatorPassportID(String s){
        Parser.exceptionPointer = "movieOperator - passportID";
        ValidationClass.checkString(s, false);
        if (s.equals("")){return null;}
        return s;
    }
    /**
     * Метод, устанавливающий значение adminLocation
     * @param s необработанная строка
     * @return adminLocation
     */
    public static Location getOperatorLocation(String s) throws WrongAmountOfCoordinatesException, NumberOutOfBoundsException {
        Parser.exceptionPointer = "movieOperator - location";
        ValidationClass.checkLocation(s, false);
        if (s.equals("")){return null;}
        Location location = Location.valueOf(s);
        return location;
    }
}
