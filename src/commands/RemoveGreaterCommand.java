package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;
import utils.UserInterface;

import java.util.HashSet;
import java.util.Scanner;

public class RemoveGreaterCommand extends Command{

    public RemoveGreaterCommand(){
        parametersCount = 0;
        isElementRequired = true;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы больше заданного";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException {

        Movie movie;
        movie = UserInterface.getElement(scanner);
        if (movie == null){
            System.out.println("Ввод элемента прекращен пользователем");
        }
        else {
            int elemCount = 0;
            HashSet<Movie> copyHashSet = new HashSet<>();
            copyHashSet.addAll(Collection.getMovieHashSet());
            for (Movie elem : copyHashSet) {
                if (elem.compareTo(movie) > 0) {
                    Collection.remove(elem);
                    elemCount += 1;
                }
            }
            System.out.println("Из коллекции удалено " + elemCount + " элементов");
        }
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
