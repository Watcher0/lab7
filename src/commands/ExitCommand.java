package commands;

import java.util.Scanner;
/**
 * Класс, описывающий команду exit
 */
public class ExitCommand extends Command{
    /**
     * Конструктор
     */
    public ExitCommand(){
        parametersCount = 0;
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "exit";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override

    public String getInfo() {
        return "Прекращает выполнение программы";
    }
    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public void execute(String s, Scanner scanner){CommandManger.history.addCommand(this);}
}
