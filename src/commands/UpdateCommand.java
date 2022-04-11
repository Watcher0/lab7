package commands;


import data.Movie;
import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;
import utils.Collection;
import utils.UserInterface;
import utils.ValidationClass;

import java.util.Scanner;
/**
 * Класс, описывающий команду update
 */
public class UpdateCommand extends Command{
    /**
     * Конструктор
     */
    public UpdateCommand(){
        isElementRequired = true;
        parametersCount = 1;
        setParameters("id");
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "update";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Обновляет значение коллекции с заданным id";
    }
    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException {
        Movie movie = null;
        Movie changedElem = null;
        s = s.split(" ")[1];
        ValidationClass.checkInt(s, false, true, 0);
        Integer intId = Integer.valueOf(s);

        for (Movie elem : Collection.getMovieHashSet()) {
            if (elem.getId().equals(intId)) {
                changedElem = elem;
                break;
            }
        }
        if (changedElem == null) {
            System.out.println("Не найден элемент с заданным id");
        } else {
            Collection.remove(changedElem);
            movie = UserInterface.getElement(scanner);

            if (movie == null) {
                System.out.println("Ввод элемента прекращен пользователем");
                Collection.add(changedElem);
            } else {
                Collection.add(movie);
                System.out.println("Элемент с id " + movie.getId() + " был обновлен");
                }
                System.out.println(separatorString);
                CommandManger.history.addCommand(this);
            }
        }
}
