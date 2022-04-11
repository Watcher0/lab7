package commands;

import utils.Parser;

import java.util.Scanner;
/**
 * Класс, описывающий команду save
 */
public class SaveCommand extends Command{
    /**
     * Конструктор
     */
    public SaveCommand(){
        parametersCount = 0;
    }
    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "save";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "Сохраняет коллекцию в файл";
    }
    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public void execute(String s, Scanner scanner){
        if(Parser.setFileFromCollection()) System.out.println("Коллекция сохранена в файл output.csv");
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
