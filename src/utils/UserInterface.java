package utils;

import commands.Command;
import commands.CommandManger;
import data.*;
import exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Класс, который реализует пользовательский интерфейс
 */
public class UserInterface {
    /**
     * Режим чтения скрипта
     */
    private static boolean isScriptMode = false;
    /**
     * Символ, появляющийся, когда от пользователя ожидается введение команды
     */
    private static final String waitForCommand = "$";

    /**
     * Метод, реализующий интерактивный режим
     */
    public static void interactiveMode() {
        CommandManger.setCommands();
        String input = "";
        Command command = null;
        while (!input.equals("exit")) {
            try {
                Scanner console = new Scanner(System.in);
                System.out.print(waitForCommand);
                input = console.nextLine();
                command = getCommandFromInput(input);
                if(command.getParametersCount()+1 > input.split(" ").length){
                    throw new WrongNumberOfParametersException();
                }
                command.execute(input, console);
            }
            catch (WrongAmountOfCoordinatesException e){
                System.out.println("Неверное количество координат");
            }
            catch (WrongNumberOfParametersException e){
                System.out.println("Неверное количество параметров у команды, должно быть - " + command.getParametersCount() + " ( " + command.getParameters()+")");
            }
            catch (CommandNotFoundException e){
                System.out.println("Такой команды нет");
            }
            catch (NumberOutOfBoundsException e){
                System.out.println("Параметр не вписывается в установленные рамки");
            }
            catch (IllegalArgumentException e){
                System.out.println("Не верный формат параметра, должно быть " + command.getParameters());
            }
            catch (UsedIdException e){
                System.out.println("происходит добавление уже существующего id");
            }

        }
    }

    /**
     * Метод, возвращающий команду
     * @param input строка, введенная пользователем
     * @return command
     */
    public static Command getCommandFromInput(String input) throws CommandNotFoundException {
        String[] values = input.split(" ");
        List<Command> commands = CommandManger.getCommands();
        for (Command command:commands) {
            if(command.getName().equals(values[0])){
                return command;
            }
        }
        throw new CommandNotFoundException();
    }

    /**
     * Метод, реализующий режим чтения скрипта
     * @param path путь к файлу
     * @param scanner обертка для потока ввода
     */
    public static void scriptMode(String path, Scanner scanner){
        int line = 0;
        isScriptMode = true;
        Scanner reader;
        try {
            File file = new File(path);
            boolean examination = isRecursive(file);
            if (examination) {
                System.out.println("При исполнении скрипта возможно переполнение стека из-за рекурсивных вызовов");
                System.out.println("Скрипт исполнен не будет");
            }
            if (!examination) {
                reader = new Scanner(file);
                while (reader.hasNext()) {
                    String input = reader.nextLine();
                    line += 1;
                    Command command = getCommandFromInput(input);
                    if (command.getParametersCount() + 1 != input.split(" ").length) {
                        throw new WrongNumberOfParametersException();
                    }
                    System.out.println("Команда " + line + ": ");
                    command.execute(input, reader);

                }
            }

        }
        catch (WrongAmountOfCoordinatesException e){
            System.out.println("Неверное количество координат в строке " + line);
        }
        catch (FileNotFoundException e){
            System.out.println("Файл не найден");
        }
        catch (WrongNumberOfParametersException e){
            System.out.println("Неверное количество параметров в строке " + line);
        }
        catch (NumberOutOfBoundsException e){
            System.out.println("Параметр, не вписывающийся в установленные рамки, в строке " + line);
        }
        catch (CommandNotFoundException e){
            System.out.println("Такой команды не существует в строке " + line);
        }
        catch (IllegalArgumentException e){
            System.out.println("Неверные формат параметра у одной из команд в строке " + line);
        }
        catch (NoSuchElementException e){
            System.out.println("Неверное количество значений полей элемента при описании команды " + line);
        }
        catch (StackOverflowError e){
            System.out.println("При выполнении скрипта, произошли рекурсивные вызовы и случилось переполнение стека");
        }
        catch (UsedIdException e){
            System.out.println("В строке " + line + " происходит добавление уже существующего id");
        }

        finally {
            isScriptMode = false;
        }

    }

    private static boolean isRecursive(File file){
        int line = 0;
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                String input = reader.nextLine();
                line += 1;
                Command command = getCommandFromInput(input);
                if (input.equals("execute_script "+file.getPath())){
                    return true;
                }
                if (command.getParametersCount()+1 != input.split(" ").length) {
                    throw new WrongNumberOfParametersException();
                }
            }
        }catch (Exception e){

        }
        return false;
    }

    public static Movie getElement(Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException {
        if (isScriptMode()){
            return getElementFromScript(scanner);
        }
        else {
            return getElementFromUser(scanner);
        }
    }

    /**
     * Метод, возвращающий объект класса StudyGroup
     * @param scanner обертка для потока ввода
     * @return studyGroup
     */
    private static Movie getElementFromUser(Scanner scanner){

            String idS; Integer id = null;
            do {
                try {
                    System.out.println("Введите id фильма");
                    idS = scanner.nextLine();
                    if(checkIfExit(idS)){return null;}
                    id = FieldSetterClass.getMovieId(idS);
                }
                catch (NumberOutOfBoundsException e){
                    System.out.println("Значение не входит в установленные рамки");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (UsedIdException e){
                    System.out.println("Этот id уже используется");
                }
            }while (id == null);

            String nameS; String name = null;
            do {
                try {
                    System.out.println("Введите название фильма");
                    nameS = scanner.nextLine();
                    if(checkIfExit(nameS)){return null;}
                    name = FieldSetterClass.getMovieName(nameS);
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }

            } while (name == null);

            String coordinatesS; Coordinates coordinates  = null;
            do {
                try {
                    System.out.println("Введите координаты фильма");
                    coordinatesS = scanner.nextLine();
                    if(checkIfExit(coordinatesS)){return null;}
                    coordinates = FieldSetterClass.getMovieCoordinates(coordinatesS);
                } catch (NumberOutOfBoundsException e){
                    System.out.println("Значение не входит в установленные рамки");
                }
                catch (WrongAmountOfCoordinatesException e){
                    System.out.println("Неверное количество координат");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            } while(coordinates == null);

            String oscarsCountS; Long oscarsCount = null;
            do {
                try {
                    System.out.println("Введите количество оскаров");
                    oscarsCountS = scanner.nextLine();
                    if(checkIfExit(oscarsCountS)){return null;}
                    oscarsCount = FieldSetterClass.getMovieOscarsCount(oscarsCountS);
                }
                catch (NumberOutOfBoundsException e){
                    System.out.println("Значение не входит в установленные рамки");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            }while (oscarsCount == null);

            String goldenPalmCountS; Integer goldenPalmCount = -1;
            do {
                try {
                    System.out.println("Введите количество золотых пальмовых ветвей");
                    goldenPalmCountS = scanner.nextLine();
                    if(checkIfExit(goldenPalmCountS)){return null;}
                    goldenPalmCount = FieldSetterClass.getMovieGoldenPalmCount(goldenPalmCountS);
                    if (goldenPalmCountS.equals("")){
                        break;
                    }
                }
                catch (NumberOutOfBoundsException e){
                    System.out.println("Значение не входит в установленные рамки");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            }while (goldenPalmCount == null);



            String genreS; MovieGenre genre = null;
            do {
                try {
                    System.out.println("Введите жанр фильма");
                    System.out.println("Доступные варианты: " + Arrays.toString(MovieGenre.values()));
                    genreS = scanner.nextLine();
                    if(checkIfExit(genreS)){return null;}
                    genre = FieldSetterClass.getMovieGenre(genreS);
                }catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            }while (genre == null);

            String mpaaRatingS; MpaaRating mpaaRating = null;
            do {
                try {
                    System.out.println("Введите mpaa рейтинг фильма");
                    System.out.println("Доступные варианты: " + Arrays.toString(MpaaRating.values()));
                    mpaaRatingS = scanner.nextLine();
                    if(checkIfExit(mpaaRatingS)){return null;}
                    mpaaRating = FieldSetterClass.getMovieMpaaRating(mpaaRatingS);
                    if (mpaaRatingS.equals("")){
                        break;
                    }
                }catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            }while (mpaaRating == null);

            String operatorNameS; String operatorName = null;
            do {
                try{
                System.out.println("Введите имя оператора");
                operatorNameS = scanner.nextLine();
                if(checkIfExit(operatorNameS)){return null;}
                operatorName = FieldSetterClass.getOperatorName(operatorNameS);
                }
                catch (NullPointerException e){
                    System.out.println("Значение не может быть null");
                }
            }while (operatorName == null);

            String birthdayS; LocalDate birthday = null;
            do {
                try{
                    System.out.println("Введите день рождения оператора");
                    birthdayS = scanner.nextLine();
                    if (checkIfExit(birthdayS)){return null;}
                    birthday = FieldSetterClass.getOperatorBirthday(birthdayS);
                    if (birthdayS.equals("")){
                        break;
                    }
                }
                catch (IllegalArgumentException | DateTimeParseException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }

            } while (birthday == null);

            String operatorHeightS; long operatorHeight = 0;
            do {
                try {
                    System.out.println("Введите рост оператора");
                    operatorHeightS = scanner.nextLine();
                    if(checkIfExit(operatorHeightS)){return null;}
                    operatorHeight = FieldSetterClass.getOperatorHeight(operatorHeightS);
                }catch (NumberOutOfBoundsException e){
                    System.out.println("Значение не входит в установленные рамки");
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            }while (operatorHeight == 0);

            String passportIDS; String passportID = null;
            do {
                try {
                    System.out.println("Введите номер паспорта оператора");
                    passportIDS = scanner.nextLine();
                    if(checkIfExit(passportIDS)){return null;}
                    passportID = FieldSetterClass.getOperatorPassportID(passportIDS);
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
            }while(passportID == null);

            String locationS; Location location = null;
            do {
                try {
                    System.out.println("Введите локацию оператора");
                    locationS = scanner.nextLine();
                    if(checkIfExit(locationS)){return null;}
                    location = FieldSetterClass.getOperatorLocation(locationS);
                }
                catch (IllegalArgumentException e){
                    System.out.println("Значение неверного формата");
                }
                catch (NullPointerException e){
                    System.out.println("Это поле не может быть null");
                }
                catch (WrongAmountOfCoordinatesException e){
                    System.out.println("Неверное количество координат");
                }
                catch (NumberOutOfBoundsException e){
                    System.out.println("Значение не входит в установленные рамки");
                }

            }while (location == null);


            Person operator = new Person(operatorName, birthday, operatorHeight, passportID, location);
            Movie movie = new Movie(id, name, coordinates, oscarsCount, goldenPalmCount, genre, mpaaRating, operator);
            return movie;
        }

        private static boolean checkIfExit(String s){
            if (s.equals("exit")){
                System.out.println("Вы хотите прекратить ввод? (y/n)");
                String answer = new Scanner(System.in).nextLine();
                if (answer.equals("y")){
                    return true;
                }
            }
            return false;
        }


    /**
     * Метод, возвращающий объект класса StudyGroup из скрипта
     * @param scanner обертка для потока ввода
     * @return studyGroup
     */
        private static Movie getElementFromScript(Scanner scanner) throws NumberOutOfBoundsException, WrongAmountOfCoordinatesException, UsedIdException {

        String idS = scanner.nextLine();
        Integer id = FieldSetterClass.getMovieId(idS);

        String nameS = scanner.nextLine();
        String name = FieldSetterClass.getMovieName(nameS);

        String coordinatesS = scanner.nextLine();
        Coordinates coordinates = FieldSetterClass.getMovieCoordinates(coordinatesS);

        String oscarsCountS = scanner.nextLine();
        Long oscarsCount = FieldSetterClass.getMovieOscarsCount(oscarsCountS);

        String goldenPalmCountS = scanner.nextLine();
        Integer goldenPalmCount = FieldSetterClass.getMovieGoldenPalmCount(goldenPalmCountS);

        String genreS = scanner.nextLine();
        MovieGenre genre = FieldSetterClass.getMovieGenre(genreS);

        String mpaaRatingS = scanner.nextLine();
        MpaaRating mpaaRating = FieldSetterClass.getMovieMpaaRating(mpaaRatingS);

        String operatorNameS = scanner.nextLine();
        String operatorName = FieldSetterClass.getOperatorName(operatorNameS);

        String birthdayS = scanner.nextLine();
        LocalDate birthday = FieldSetterClass.getOperatorBirthday(birthdayS);

        String operatorHeightS = scanner.nextLine();
        long operatorHeight = FieldSetterClass.getOperatorHeight(operatorHeightS);

        String passportIDS = scanner.nextLine();
        String passportID = FieldSetterClass.getOperatorPassportID(passportIDS);

        String locationS = scanner.nextLine();
        Location location = FieldSetterClass.getOperatorLocation(locationS);

        Person operator = new Person(operatorName, birthday, operatorHeight, passportID, location);
        Movie movie = new Movie(id, name, coordinates, oscarsCount, goldenPalmCount, genre, mpaaRating, operator);

        return movie;
    }

    /**
     * Метод, проверяющий, включен ли скриптовый режим
     * @return isScriptMode
     */
    private static boolean isScriptMode(){
        return isScriptMode;
    }

}
