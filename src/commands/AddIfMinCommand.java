package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;
import utils.UserInterface;

import java.util.Scanner;

public class AddIfMinCommand extends Command{

    public AddIfMinCommand(){
        parametersCount = 0;
        isElementRequired = true;
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getInfo() {
        return "добавляет новый элемент в коллекцию, если он меньше наименьшего элемента коллекции";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException {

        if (Collection.getSize() == 0){
            System.out.println("В коллекции нет элементов");
        }
        else {
            Movie movie;
            movie = UserInterface.getElement(scanner);
            if (movie == null) {
                System.out.println("Ввод элемента прекращен пользователем");
            } else {
                Movie minMovie = null;
                for (Movie elem : Collection.getMovieHashSet()) {
                    if (elem.compareTo(minMovie) < 0 | minMovie == null) {
                        minMovie = elem;
                    }
                }
                if (movie.compareTo(minMovie) < 0) {
                    Collection.add(movie);
                    System.out.println("Элемент добавлен в коллекцию");
                }
            }
        }
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
