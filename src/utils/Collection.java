package utils;

import data.Movie;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


/**
 * Класс, описывающий коллекцию и определяющий взаимодействие с ней
 */
public class Collection {
    /**
     * Максимальное количество элементов, которое может хранить наша коллекция
     */
    public static final long MAX_SIZE = (long) Math.pow(10, 6);
    /**
     * Время инициализации коллекции
     */
    private static LocalDateTime initializationTime;
    /**
     * Текущий id коллекции
     */

    private static HashSet<Integer> usedId = new HashSet<>();

    public static Long currentId = 0L;
    /**
     * HashTable, где хранятся все элементы коллекции
     */
    private static HashSet<Movie> movieHashSet = new HashSet<>();

    /**
     * Метод, добавляющий новый элемент в коллекцию
     * @param  movie объект, который нужно добавить в коллекцию
     */
    public static boolean add(Movie movie){
        usedId.add(movie.getId());
        if (movieHashSet.contains(movie)){
            return false;
        }
        movieHashSet.add(movie);
        return true;
    }

    /**
     * Метод, удаляющий элемент из коллекции
     * @param movie объект, который нужно удалить из коллекции
     */
    public static boolean remove(Movie movie){
        usedId.remove(movie.getId());
        if (movieHashSet.contains(movie)) {
            movieHashSet.remove(movie);
            return true;
        }
        return false;
    }

    /**
     * Метод, удаляющий все элементы коллекции
     */
    public static boolean clear(){
        usedId.clear();
        if (movieHashSet.isEmpty()){
            return false;
        }
        movieHashSet.clear();
        return true;
    }

    /**
     * Метод, возвращающий HashTable со всеми элементами коллекции
     * @return movieHashSet
     */
    public static HashSet<Movie> getMovieHashSet() {
        return movieHashSet;
    }

    /**
     * Метод, возвращающий класс, реализуемой коллекции
     * @return type
     */
    public static Class getType(){
        return movieHashSet.getClass();
    }

    /**
     * Метод возвращающий время инициализации коллекции
     * @return initializationTime
     */
    public static LocalDateTime getInitializationTime(){
        return initializationTime;
    }

    /**
     * Метод, инициализирующий коллекцию
     */
    public static void initCollection(){
        initializationTime = LocalDateTime.now();
    }

    /**
     * Метод, возвращающий размер коллекции
     * @return size
     */
    public static int getSize(){return movieHashSet.size();}

    public static Movie[] getSortedArray(){
        Movie[] movies = movieHashSet.toArray(new Movie[0]);
        Arrays.sort(movies);
        return movies;
    }

    public static HashSet<Integer> getUsedId(){
        return usedId;
    }


}
