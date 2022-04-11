package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;
import utils.ValidationClass;

import java.util.Objects;
import java.util.Scanner;

public class RemoveByIdCommand extends Command{

    public RemoveByIdCommand(){
        isElementRequired = false;
        parametersCount = 1;
        setParameters("id");
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getInfo() {
        return "удаляет элемент из коллекции по его id";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        s = s.split(" ")[1];
        ValidationClass.checkInt(s, false, true, 0);
        Integer intId = Integer.valueOf(s);
        Movie reqElement = null;
        for (Movie movie: Collection.getMovieHashSet()){
            if (Objects.equals(movie.getId(), intId)){
                reqElement = movie;
                break;
            }
        }
        if (reqElement == null){
            System.out.println("Элемента с таким id нет в коллекции");
        }
        else {
            Collection.remove(reqElement);
            System.out.println("Элемент с id = " + intId + " удален");
        }
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
