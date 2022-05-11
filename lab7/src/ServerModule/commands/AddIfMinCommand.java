package ServerModule.commands;

import ServerModule.util.DatabaseCollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import ServerModule.util.CollectionManager;
import common.exceptions.DatabaseManagerException;
import common.exceptions.NonAuthorizedUserException;
import common.exceptions.WrongAmountOfArgumentsException;
import common.util.MovieLite;
import common.util.User;

/**
 * 'add_if_min' command. Adds a new element to the collection if it's min.
 */
public class AddIfMinCommand extends Command{
    private final CollectionManager collectionManager;
    private final DatabaseCollectionManager databaseCollectionManager;

    public AddIfMinCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!argument.isEmpty()|| objectArgument == null) throw new WrongAmountOfArgumentsException();
            MovieLite movie = (MovieLite) objectArgument;
            Movie minMovie = collectionManager.getMin();
            if (minMovie.getOscarsCount() >= movie.getOscarsCount()) {
                collectionManager.addToCollection(databaseCollectionManager.insertMovie(movie, user));
                ResponseOutputer.append("Кино успешно добавлено в коллекцию!\n");
            } else {
                ResponseOutputer.append("Кино не добавлено в коллекцию!\n");
            }
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (DatabaseManagerException e) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}
