package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;

import java.util.Scanner;

public class PrintFiendDescendingMpaaRatingCommand extends Command{

    public PrintFiendDescendingMpaaRatingCommand(){parametersCount = 0;}

    @Override
    public String getName() {
        return "print_field_descending_mpaa_rating";
    }

    @Override
    public String getInfo() {
        return "выводит значения поля mpaaRating всех элементов в порядке убывания";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        if (Collection.getSize() == 0){
            System.out.println("В коллекции нет элементов");
        }
        for (Movie movie:Collection.getSortedArray()){
            System.out.println("У элемента с id = " + movie.getId() + " mpaaRating = " + movie.getMpaaRating());
            System.out.println(separatorString);
        }
        CommandManger.history.addCommand(this);
    }
}
