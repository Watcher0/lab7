package commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для взаимодействия с командами
 */

public class CommandManger {
    /**
     * Список всех команд
     */
    private static List<Command> commands = new ArrayList<>();
    public static CommandHistory history = new CommandHistory();


    /**
     * Метод, добавляющий команды в список
     */
    public static void setCommands(){
        commands.add(new HelpCommand());
        commands.add(new InfoCommand());
        commands.add(new ShowCommand());
        commands.add(new UpdateCommand());
        commands.add(new ClearCommand());
        commands.add(new SaveCommand());
        commands.add(new ExecuteScriptCommand());
        commands.add(new ExitCommand());
        commands.add(new AddIfMinCommand());
        commands.add(new HistoryCommand());
        commands.add(new PrintAscendingCommand());
        commands.add(new PrintDescendingCommand());
        commands.add(new PrintFiendDescendingMpaaRatingCommand());
        commands.add(new RemoveGreaterCommand());
        commands.add(new AddCommand());
        commands.add(new RemoveByIdCommand());
    }

    /**
     *Метод, возвращающий список всех команд
     * @return list of all commands
     */
    public static List<Command> getCommands(){
        return commands;
    }

}

class CommandHistory {

    private Command[] history = new Command[10];
    private int currentIndex = 0;
    private int size = 0;

    public void addCommand(Command command){
        history[currentIndex] = command;
        currentIndex = (currentIndex + 1) % 10;
        size = Math.min(size+1, 10);
    }

    public Command[] getHistory(){
        Command[] commands = new Command[size];
        int j = 0;
        int i = currentIndex-size;
        if (i < 0){
            i = 10 + i;
        }
        while (j < size){
            commands[j] = history[i];
            j += 1;
            i = (i + 1) % 10;
        }
        return commands;
    }

}
