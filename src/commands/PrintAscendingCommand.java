package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;

import javax.management.modelmbean.ModelMBean;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.util.Scanner;

public class PrintAscendingCommand extends Command {

    public PrintAscendingCommand(){parametersCount = 0;}

    @Override
    public String getName() {
        return "print_ascending";
    }

    @Override
    public String getInfo() {
        return "выводит элементы в порядке возрастания";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        if (Collection.getSize() == 0){
            System.out.println("В коллекции нет элементов");
        }
        for (Movie movie:Collection.getSortedArray()){
            System.out.println(movie.toString());
            System.out.println(separatorString);
        }
        CommandManger.history.addCommand(this);
    }
}
