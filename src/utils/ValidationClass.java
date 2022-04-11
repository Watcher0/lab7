package utils;


import data.MovieGenre;
import data.MpaaRating;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;

import java.time.LocalDate;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * Класс, оценивающий корректность введенных данных
 */
public class ValidationClass {
    /**
     * Метод, проверяющий корректность Long значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     * @param leftRange Устанавливается ли левая граница
     * @param range значение границы
     */
    public static void checkLong(String s, Boolean nullable, Boolean leftRange, Long range) throws NumberFormatException, NumberOutOfBoundsException{
        if(s.equals("") && !nullable){
            throw new NullPointerException();
        }

        Long n = Long.valueOf(s);

        if((leftRange && n<=range) | (!(leftRange) && n>range)){
            throw new NumberOutOfBoundsException();
        }
    }

    /**
     * Метод, проверяющий корректность Double значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkFloat(String s, Boolean nullable, Boolean leftRange, Float range) throws NumberFormatException, NumberOutOfBoundsException{
        if(s.equals("") && !nullable){
            throw new NullPointerException();
        }

        Float n = Float.valueOf(s);

        if((leftRange && n<=range) | (!(leftRange) && n>range)){
            throw new NumberOutOfBoundsException();
        }
    }

    /**
     * Метод, проверяющий корректность Int значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     * @param leftRange Устанавливается ли левая граница
     * @param range значение границы
     */
    public static void checkInt(String s, Boolean nullable, Boolean leftRange, Integer range) throws NumberFormatException, NumberOutOfBoundsException{
        if(s.equals("") && !nullable){
            throw new NullPointerException();
        }
        else if (!s.equals("")) {
            Integer n = Integer.valueOf(s);
            if ((leftRange && n <= range) | (!(leftRange) && n > range)) {
                throw new NumberOutOfBoundsException();
            }
        }
    }

    /**
     * Метод, проверяющий корректность String значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkString(String s, Boolean nullable){
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
    }

    public static void checkString(String s, Boolean nullable, int length){
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
    }

    /**
     * Метод, проверяющий корректность Coordinates значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkCoordinates(String s, Boolean nullable) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        String[] sList = s.split(" ");
        String[] values = new String[realLength(sList)];
        int j = 0;
        for (int i =0;i<sList.length;i++){
            if (!sList[i].equals("")){
                values[j] = sList[i];
                j += 1;
            }
        }
        if(values.length == 2){
            checkInt(values[0], false, false, 892);
            checkFloat(values[1], false, true, -11f);
        }
        else throw new WrongAmountOfCoordinatesException();
    }

    private static int realLength(String[] sList){
        int ans = 0;
        for (int i = 0;i<sList.length;i++){
            if (!sList[i].equals("")) ans += 1;
        }
        return ans;
    }

    /**
     * Метод, проверяющий корректность Semester значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkGenre(String s, Boolean nullable) throws IllegalArgumentException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        MovieGenre movieGenre = MovieGenre.valueOf(s);
    }

    /**
     * Метод, проверяющий корректность Color значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkMpaaRating(String s, Boolean nullable) throws IllegalArgumentException{
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        else if (!s.equals("")) {
            MpaaRating mpaaRating = MpaaRating.valueOf(s);
        }
    }


    /**
     * Метод, проверяющий корректность Location значения
     * @param s пользовательский ввод
     * @param nullable пользовательский ввод
     */
    public static void checkLocation(String s, Boolean nullable) throws WrongAmountOfCoordinatesException, NumberOutOfBoundsException {
        if (s.equals("") && !nullable){
            throw new NullPointerException();
        }
        String[] values = s.split(" ");
        if(values.length == 3){
            checkInt(values[0], nullable, true, Integer.MIN_VALUE);
            checkFloat(values[1], nullable, true, Float.MIN_VALUE);
            checkString(values[2], nullable);
        }
        else if (values.length == 2){
            checkInt(values[0], nullable, true, Integer.MIN_VALUE);
            checkFloat(values[1], nullable, true, Float.MIN_VALUE);
        }
        else throw new WrongAmountOfCoordinatesException();
    }

    public static void checkDate(String s, Boolean nullable) throws IllegalArgumentException {
        if (s.equals("") && !nullable) {
            throw new NullPointerException();
        } else if (!s.equals("")) {
            LocalDate birthday = LocalDate.parse(s, ISO_LOCAL_DATE);
        }
    }




}
