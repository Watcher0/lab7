package commands;

import java.util.Scanner;
/**
 * Класс, описывающий команду help
 */
public class HelpCommand extends Command{
    /**
     * Конструктор
     */
    public HelpCommand(){
        parametersCount = 0;
    }

    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "help";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Выводит справку по доступным командам";
    }
    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public void execute(String s, Scanner scanner){
        for(Command command:CommandManger.getCommands()){
            if (command.isElementRequired){
            System.out.println(command.getName() + " " + command.getParameters() +  " {element} - " + command.getInfo());
            }
            else System.out.println(command.getName() + " " + command.getParameters() +  " - " + command.getInfo());
        }
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
