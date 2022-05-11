package ClientModule.util;

import common.data.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInBoundsException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Asks a user a movie's value.
 */
public class Iteractor {
    private final int MAX_PASSPORT_ID_LENGTH = 40;
    private final int MIN_OSCARS_COUNT = 0;
    private final int MIN_GOLDEN_PALM_COUNT = 0;
    private final int MIN_HEIGHT = 0;
    private final int MAX_X = 892;
    private final int MIN_Y = -11;

    private Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");

    private Scanner userScanner;
    private boolean fileMode;

    public Iteractor(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     *
     * @param userScanner Scanner to set.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets studyGroup asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets studyGroup asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }


    /**
     * Asks a user the Movie's name.
     *
     * @param inputTitle title of input.
     * @param minLength  min length of string
     * @param maxLength  max length of string
     * @return name
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName(String inputTitle, int minLength, int maxLength) throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Console.println(inputTitle);
                Console.print(">");
                name = userScanner.nextLine().trim();
                if (fileMode) Console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                if (name.length() <= minLength) throw new NotInBoundsException();
                if (name.length() >= maxLength) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror(String.format("Длина строки не входит в диапазон (%d; %d)", minLength, maxLength));
            }
        }
        return name;
    }

    /**
     * Asks a user the Movie's X coordinate.
     *
     * @return movie's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public int askX() throws IncorrectInputInScriptException {
        String strX = "";
        int x;
        while (true) {
            try {
                Console.println("Введите координату X:");
                Console.print(">");
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Integer.parseInt(strX);
                if (x > MAX_X) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strX).matches())
                    Console.printerror("Координата X должна быть в диапазоне (" + (Integer.MIN_VALUE)
                            + ";" + MAX_X + ")!");
                else
                    Console.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть меньше " + MAX_X);
            }
        }
        return x;
    }

    /**
     * Asks a user the movie's Y coordinate.
     *
     * @return Movie's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public float askY() throws IncorrectInputInScriptException {
        String strY = "";
        float y;
        while (true) {
            try {
                Console.println("Введите координату Y:");
                Console.print(">");
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Float.parseFloat(strY);
                if (y < MIN_Y) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strY).matches())
                    Console.printerror("Координата Y должна быть в диапазоне (" + Float.MIN_VALUE
                            + ";" + Float.MAX_VALUE + ")!");
                else
                    Console.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_Y);
            }
        }
        return y;
    }

    /**
     * Asks a user the movie's coordinates.
     *
     * @return Movie's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        int x = askX();
        float y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the movie's oscars count
     *
     * @return Movie's oscars count
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Long askOscarsCount() throws IncorrectInputInScriptException {
        String strOscarsCount = "";
        Long oscarsCount;
        while (true) {
            try {
                Console.println("Введите количество оскаров:");
                Console.print(">");
                strOscarsCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strOscarsCount);
                oscarsCount = Long.parseLong(strOscarsCount);
                if (oscarsCount <= MIN_OSCARS_COUNT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strOscarsCount).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_OSCARS_COUNT + ";" + Long.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество оскаров должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_OSCARS_COUNT);
            }
        }
        return oscarsCount;
    }

    /**
     * Asks a user the movie's golden Palms count
     *
     * @return Movie's golden Palms count
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Integer askGoldenPalmsCount() throws IncorrectInputInScriptException {
        String strGoldenPalmsCount = "";
        Integer goldenPalmsCount;
        while (true) {
            try {
                Console.println("Введите количество золотых пальм:");
                Console.print(">");
                strGoldenPalmsCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strGoldenPalmsCount);
                goldenPalmsCount = Integer.parseInt(strGoldenPalmsCount);
                if (goldenPalmsCount <= MIN_GOLDEN_PALM_COUNT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Число не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                if (patternNumber.matcher(strGoldenPalmsCount).matches())
                    Console.printerror("Число должно быть в диапазоне (" + MIN_GOLDEN_PALM_COUNT + ";" + Integer.MAX_VALUE + ")!");
                else
                    Console.printerror("Количество золотых пальм должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_GOLDEN_PALM_COUNT);
            }
        }
        return goldenPalmsCount;
    }

    /**
     * Asks a user the movie's genre
     *
     * @return Movie's genre
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public MovieGenre askMovieGenre() throws IncorrectInputInScriptException {
        String strMovieGenre;
        MovieGenre movieGenre;
        while (true) {
            try {
                Console.println("Список жанров - " + MovieGenre.nameList());
                Console.println("Введите жанр:");
                Console.print(">");
                strMovieGenre = userScanner.nextLine().trim();
                if (fileMode) Console.println(strMovieGenre);
                movieGenre = MovieGenre.valueOf(strMovieGenre.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Жанр не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Жанра нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return movieGenre;
    }

    /**
     * Asks a user the movie's MPAA rating
     *
     * @return Movie's MPAA rating
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public MpaaRating askMpaaRating() throws IncorrectInputInScriptException {
        String strMpaaRating;
        MpaaRating mpaaRating;
        while (true) {
            try {
                Console.println("Список возможных MPAA рейтингов - " + MpaaRating.nameList());
                Console.println("Введите MPAA рейтинг:");
                Console.print(">");
                strMpaaRating = userScanner.nextLine().trim();
                if (fileMode) Console.println(strMpaaRating);
                mpaaRating = MpaaRating.valueOf(strMpaaRating.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("MPAA рейтинг не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("MPAA рейтинга нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return mpaaRating;
    }


    /**
     * Asks a user the Movie's operator
     *
     * @return Person [Operator]
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Person askOperator() throws IncorrectInputInScriptException {
        String name = askOperatorName();
        LocalDate birthday = askBirthday();
        long height = askHeight();
        String passportID = askPassportID();
        Location location = askLocation();
        return new Person(name, birthday, height, passportID, location);
    }

    /**
     * Asks a user the operator's name
     *
     * @return Person's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askOperatorName() throws IncorrectInputInScriptException {
        return askName("Введите имя оператора:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the location's name
     *
     * @return Location's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askLocationName() throws IncorrectInputInScriptException {
        return askName("Введите имя локации:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the movie's name
     *
     * @return Movie's name
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askMovieName() throws IncorrectInputInScriptException {
        return askName("Введите имя кино:", 0, Integer.MAX_VALUE);
    }

    /**
     * Asks a user the operator's passport ID
     *
     * @return Operator's passportID
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public String askPassportID() throws IncorrectInputInScriptException {
        return askName("Введите ID паспорта:", 0, MAX_PASSPORT_ID_LENGTH);
    }

     /**
     * Asks a user the operator's location
     *
     * @return Operator's location
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public Location askLocation() throws IncorrectInputInScriptException {
        Integer x = askX();
        Float y = askY();
        String name = askLocationName();
        return new Location(x, y, name);
    }

    /**
     * Asks a user the operator's birthday
     *
     * @return Operator's birthday
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public LocalDate askBirthday() throws IncorrectInputInScriptException {
        String strBirthday;
        LocalDate birthday;
        while (true) {
            try {
                Console.println("Введите дату рождения оператора:");
                Console.print(">");
                strBirthday = userScanner.nextLine().trim();
                if (fileMode) Console.println(strBirthday);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
                birthday = LocalDate.parse(strBirthday, formatter);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Дата рождения оператора не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (DateTimeParseException ex) {
                Console.printerror("Неправильный формат даты!");
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return birthday;
    }

    /**
     * Asks a user the operator's height
     *
     * @return Operator's hairColor
     * @throws IncorrectInputInScriptException if script is running and something goes wrong.
     */
    public long askHeight() throws IncorrectInputInScriptException {
        String strHeight;
        long height;
        while (true) {
            try {
                Console.println("Введите рост оператора:");
                Console.print(">");
                strHeight = userScanner.nextLine().trim();
                if (fileMode) Console.println(strHeight);
                height = Long.parseLong(strHeight);
                if (height <= MIN_HEIGHT) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("рост оператора не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } catch (NotInBoundsException e) {
                Console.printerror("Число должно быть больше " + MIN_HEIGHT);
            }
        }
        return height;
    }

    /**
     * Asks a user a question.
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(">");
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInBoundsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInBoundsException exception) {
                Console.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return answer.equals("+");
    }
}
