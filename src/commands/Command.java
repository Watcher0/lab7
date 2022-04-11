package commands;

import exceptions.NumberOutOfBoundsException;
import exceptions.UsedIdException;
import exceptions.WrongAmountOfCoordinatesException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Абстрактный класс, описывающий поведение команд
 */
public abstract class Command {

    boolean isElementRequired;

    List<String> parameters = new ArrayList<>();
    /**
     * Строка для разделения выводов
     */
    public final String separatorString = "-------------------------";
    /**
     * Количество параметров
     */
    protected int parametersCount;

    /**
     * Метод возвращающий количество параметров
     * @return parametersCount
     */
    public int getParametersCount() {
        return parametersCount;
    }

    /**
     * Метод, возвращающий имя
     * @return name
     */
    public abstract String getName();

    /**
     * Метод, возвращающий информацию
     * @return info
     */
    public abstract String getInfo();

    /**
     * Метод, исполняющий команду
     * @param s string for extract parameters from
     * @param scanner object that we use to read information from user input
     *                or script
     */
    public abstract void execute(String s, Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException;

    void setParameters(String... param){
        parameters.addAll(Arrays.asList(param));
    }
    public String getParameters(){
        String conc = "";
        for (String s:parameters){
            conc = conc + s + " ";
        }
        return conc;
    }
}
