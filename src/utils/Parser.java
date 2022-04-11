package utils;

import data.*;
import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;

/**
 * Класс, описывающий парсер из CSV формата
 */
public class Parser {
    /**
     * Место, где произошла ошибка
     */
    static String exceptionPointer;
    /**
     * Количество объектов
     */
    static private int objectsCount;

    /**
     * Метод, создающий коллекцию на основе файла
     * @param file файл, из которого считываем данные
     */
    public static void setCollectionFromFile(File file) throws FileNotFoundException {
        Collection.initCollection();
        String[][] values = CSVReader.getValuesFromFile(file);
        HashSet<Movie> movieHashSet = new HashSet<>();
        objectsCount = CSVReader.getResultSize();
        for(int i = 0;i<objectsCount;i++){
            try {
                Integer id = FieldSetterClass.getMovieId(values[i][0]);
                String name = FieldSetterClass.getMovieName(values[i][1]);
                Coordinates coordinates = FieldSetterClass.getMovieCoordinates(values[i][2]);
                Long oscarsCount = FieldSetterClass.getMovieOscarsCount(values[i][3]);
                Integer goldenPalmCount = FieldSetterClass.getMovieGoldenPalmCount(values[i][4]);
                MovieGenre movieGenre = FieldSetterClass.getMovieGenre(values[i][5]);
                MpaaRating mpaaRating = FieldSetterClass.getMovieMpaaRating(values[i][6]);
                String operatorName = FieldSetterClass.getOperatorName(values[i][7]);
                LocalDate operatorBirthday = FieldSetterClass.getOperatorBirthday(values[i][8]);
                long operatorHeight = FieldSetterClass.getOperatorHeight(values[i][9]);
                String operatorPassportID = FieldSetterClass.getOperatorPassportID(values[i][10]);
                Location operatorLocation = FieldSetterClass.getOperatorLocation(values[i][11]);

                Person movieOperator= new Person(operatorName, operatorBirthday, operatorHeight, operatorPassportID, operatorLocation);
                Movie movie = new Movie(id, name, coordinates, oscarsCount, goldenPalmCount, movieGenre, mpaaRating, movieOperator);
                Collection.add(movie);
            }
            catch (IllegalArgumentException e){
                System.out.println("Некорректное значение в поле " + exceptionPointer + " в строке " + i);
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            }
            catch (NumberOutOfBoundsException f){
                System.out.println("Значение не входящее в указанные рамки в поле " + exceptionPointer + " в строке" + i);
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            }
            catch (WrongAmountOfCoordinatesException g){
                System.out.println("Неверное количество координат в поле " + exceptionPointer + " в строке " + i);
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            }
            catch (UsedIdException l){
                System.out.println("id из строки " + i + " уже используется");
                System.out.println("Объект из этой строки не будет добавлен в коллекцию");
            }
        }
    }

    /**
     * Метод, создающий файл на основе коллекции
     */
    public static boolean setFileFromCollection(){
        HashSet<Movie> movieHashSet = Collection.getMovieHashSet();
        try {
            FileWriter fileWriter = new FileWriter("output.csv", false);
            for (Movie movie : movieHashSet) {
                String s;

                String stringGoldenPalm;
                if (movie.getGoldenPalmCount() == null){
                    stringGoldenPalm = "";
                }
                else {
                    stringGoldenPalm = movie.getGoldenPalmCount().toString();
                }

                String stringMpaaRating;
                if (movie.getMpaaRating() == null){
                    stringMpaaRating = "";
                }
                else {
                    stringMpaaRating = movie.getMpaaRating().toString();
                }

                String stringBirthday;
                if (movie.getOperator().getBirthday() == null){
                    stringBirthday = "";
                }
                else{
                    stringBirthday = movie.getOperator().getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE);
                }
                s = movie.getId().toString() + "," + movie.getName() + "," + movie.getCoordinates().toString()
                       + "," + movie.getOscarsCount().toString() + "," + stringGoldenPalm +
                        "," + movie.getGenre() + "," +  stringMpaaRating + "," + movie.getOperator().getName() + "," + stringBirthday + "," +
                        movie.getOperator().getHeight() + "," + movie.getOperator().getPassportID() + "," + movie.getOperator().getLocation()+ "\n";
                fileWriter.write(s);

            }
            fileWriter.flush();
            return true;
        }
        catch (IOException e){
            if (e.getMessage().contains("(Отказано в доступе)")) System.out.println("Нет прав на запись в файл");
            else
            System.out.println("Файл, в который должна происходить запись данных, не существует");
            return false;
        }
    }

}
