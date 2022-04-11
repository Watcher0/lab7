package commands;

import exceptions.NumberOutOfBoundsException;
import exceptions.WrongAmountOfCoordinatesException;

import java.util.Scanner;

public class HistoryCommand extends Command{
    public HistoryCommand(){parametersCount = 0;}

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getInfo() {
        return "выводит последние 10 команд";
    }

    @Override
    public void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException {
        Command[] history = CommandManger.history.getHistory();
        if (history.length == 0) {
            System.out.println("В истории пока нет команд");
        } else {
            System.out.println("Последние 10 команд");
            for (int i = 0; i < history.length; i++) {
                System.out.println(history[i].getName());
            }
            System.out.println(separatorString);
            CommandManger.history.addCommand(this);
        }
    }
}
