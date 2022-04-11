package commands;

import utils.UserInterface;

import java.util.Scanner;

/**
 * Класс, описывающий команду execute_script
 */
public class ExecuteScriptCommand extends Command{
    /**
     * constructor
     */
    public ExecuteScriptCommand(){
        parametersCount = 1;
        setParameters("file_name");
    }

    /**
     * Метод, возвращающий имя команды
     * @return name of the command
     */
    @Override
    public String getName() {
        return "execute_script";
    }
    /**
     * Метод, возвращающий информацию о команде
     * @return information about the command
     */
    @Override
    public String getInfo() {
        return "исполняет скрипт из файла";
    }
    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public void execute(String s, Scanner scanner){
        s = s.split(" ")[1];
        UserInterface.scriptMode(s, scanner);
        System.out.println("Выполнение скрипта закончено");
        System.out.println(separatorString);
        CommandManger.history.addCommand(this);
    }
}
