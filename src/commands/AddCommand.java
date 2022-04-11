package commands;

import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;
import utils.UserInterface;

import javax.swing.undo.UndoableEditSupport;
import java.util.Scanner;

public class AddCommand extends Command{

    public AddCommand(){
        isElementRequired = true;
        parametersCount = 0;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getInfo() {
        return "добавляет новый элемент в коллекцию";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException {
        Movie movie = UserInterface.getElement(scanner);
        if (movie == null){
            System.out.println("Ввод элемента прекращен пользователем");
        }else
        if (Collection.add(movie)){
            System.out.println("Элемент добавлен в коллекцию");
        }
        else{
            System.out.println("Такой элемент уже существует в коллекции");
        }
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
