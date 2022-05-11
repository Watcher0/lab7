package ClientModule.util;

import common.data.Coordinates;
import common.data.MovieGenre;
import common.data.MpaaRating;
import common.data.Person;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.ScriptRecursionException;
import common.exceptions.WrongAmountOfParametersException;
import common.util.MovieLite;
import common.util.Request;
import common.util.ResponseCode;
import common.util.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Console {
    private Scanner userScanner;
    private Stack<File> scriptFileNames = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();
    private AuthManager authManager;

    public Console(Scanner userScanner, AuthManager authManager) {
        this.userScanner = userScanner;
        this.authManager = authManager;
    }

    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }

    /**
     * Mode for catching ServerModule.commands from user input.
     */
    public Request interactiveMode(ResponseCode serverResponseCode, User user) throws IncorrectInputInScriptException {
        String userInput = "";
        String[] userCommand = {"", ""};
        ProcessCode processCode = null;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.SERVER_EXIT || serverResponseCode == ResponseCode.ERROR)) {
                        throw new IncorrectInputInScriptException();
                    }
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        System.out.println("Возвращаюсь из скрипта '" + scriptFileNames.pop().getName() + "'!");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            System.out.print("$ ");
                            System.out.println(userInput);
                        }
                    } else {
                        System.out.print("$ ");
                        if (userScanner.hasNext()) {
                            userInput = userScanner.nextLine();
                        } else {
                            System.out.println("Клиент завершен!");
                            System.exit(0);
                        }
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    System.out.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                }
                processCode = checkCommand(userCommand[0], userCommand[1]);
            } while (processCode == ProcessCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                switch (processCode) {
                    case OBJECT:
                        MovieLite movieToInsert = generateMovieToInsert();
                        return new Request(userCommand[0], userCommand[1], movieToInsert, user);
                    case UPDATE_OBJECT:
                        MovieLite movieToUpdate = generateMovieToUpdate();
                        return new Request(userCommand[0], userCommand[1], movieToUpdate, user);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptFileNames.isEmpty() && scriptFileNames.search(scriptFile) != -1) {
                            throw new ScriptRecursionException();
                        }
                        scannerStack.push(userScanner);
                        scriptFileNames.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        System.out.println("Выполняю скрипт '" + scriptFile.getName() + "'!");
                        break;
                    case LOG_IN:
                        return authManager.handle();
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                System.out.println("Скрипты не могут вызываться рекурсивно!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            System.out.println("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
        }
        return new Request(userCommand[0], userCommand[1], null, user);
    }

    /**
     * Launches the command.
     * @param command Command to launch.
     * @return Exit code.
     */
    private ProcessCode checkCommand(String command, String argument) {
        try {
            switch (command) {
                case "":
                    return ProcessCode.ERROR;
                case "help":
                case "info":
                case "show":
                case "clear":
                case "history":
                case "print_ascending":
                case "print_descending":
                case "print_field_descending_mpaa_rating":
                case "exit":
                case "log_out":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OK;
                case "log_in":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.LOG_IN;
                case "add":
                case "remove_greater":
                case "add_if_min":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OBJECT;
                case "remove_by_id":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OK;
                case "update":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.UPDATE_OBJECT;
                case "execute_script":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.SCRIPT;
                case "save":
                    System.out.println("Эта команда недоступна клиентам!");
                    return ProcessCode.ERROR;
                default:
                    System.out.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessCode.ERROR;
            }
        } catch (WrongAmountOfParametersException e) {
            System.out.println("Проверьте правильность ввода аргументов!");
        }
        return ProcessCode.OK;
    }

    private MovieLite generateMovieToInsert() throws IncorrectInputInScriptException {
        Iteractor builder = new Iteractor(userScanner);
        if (fileMode()) {
            builder.setFileMode();
        } else {
            builder.setUserMode();
        }
        return new MovieLite(
                builder.askMovieName(),
                builder.askCoordinates(),
                LocalDate.now(),
                builder.askOscarsCount(),
                builder.askGoldenPalmsCount(),
                builder.askMovieGenre(),
                builder.askMpaaRating(),
                builder.askOperator()
        );
    }

    private MovieLite generateMovieToUpdate() throws IncorrectInputInScriptException{
        Iteractor builder = new Iteractor(userScanner);
        if (fileMode()) {
            builder.setFileMode();
        } else {
            builder.setUserMode();
        }
        String name = builder.askQuestion("Хотите изменить имя фильма?") ?
                builder.askMovieName() : null;
        Coordinates coordinates = builder.askQuestion("Хотите изменить координаты фильма?") ?
                builder.askCoordinates() : null;
        Long oscarCount = builder.askQuestion("Хотите изменить количество оскаров?") ?
                builder.askOscarsCount() : -1;
        Integer goldenPalmCount  = builder.askQuestion("Хотите изменить количество золотых пальм?") ?
                builder.askGoldenPalmsCount() : -1;
        MovieGenre movieGenre  = builder.askQuestion("Хотите изменить жанр фильма?") ?
                builder.askMovieGenre() : null;
        MpaaRating mpaaRating = builder.askQuestion("Хотите изменить рейтинг фильма?") ?
                builder.askMpaaRating() : null;
        Person operator  = builder.askQuestion("Хотите изменить оператора?") ?
                builder.askOperator() : null;
        return new MovieLite(
                name,
                coordinates,
                LocalDate.now(),
                oscarCount,
                goldenPalmCount,
                movieGenre,
                mpaaRating,
                operator
        );
    }


    /**
     * Prints toOut.toString() to Console
     * @param toOut Object to print
     */
    public static void print(Object toOut) {
        System.out.print(toOut);
    }

    /**
     * Prints toOut.toString() + \n to Console
     * @param toOut Object to print
     */
    public static void println(Object toOut) {
        System.out.println(toOut);
    }

    /**
     * Prints error: toOut.toString() to Console
     * @param toOut Error to print
     */
    public static void printerror(Object toOut) {
        System.out.println("error: " + toOut);
    }

}
