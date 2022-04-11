package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;

import java.util.Scanner;

public class PrintDescendingCommand extends Command{

    public PrintDescendingCommand(){parametersCount = 0;}

    @Override
    public String getName() {
        return "print_descending";
    }

    @Override
    public String getInfo() {
        return "выводит элементы коллекции в порядке убывания";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        if (Collection.getSize() == 0){
            System.out.println("В коллекции нет элементов");
        }
        Movie[] movies = Collection.getSortedArray();
        for (int i = movies.length-1; i >= 0;i--){
            System.out.println(movies[i].toString());
            System.out.println(separatorString);
        }
        CommandManger.history.addCommand(this);
    }
}
