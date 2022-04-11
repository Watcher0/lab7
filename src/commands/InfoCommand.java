package commands;

import utils.Collection;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
 * Класс, описывающий команду info
 */
public class InfoCommand extends Command{
    /**
     * Конструктор
     */
    public InfoCommand(){
        parametersCount = 0;
    }

    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "info";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит информацию о коллекции";
    }
    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public void execute(String s, Scanner scanner){
        System.out.println("Тип - " + Collection.getType());
        System.out.println("Дата инициализации - " + Collection.getInitializationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("Количество элементов - " + Collection.getSize());
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
