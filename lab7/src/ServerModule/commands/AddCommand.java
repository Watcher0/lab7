package ServerModule.commands;

import ServerModule.util.DatabaseCollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import common.exceptions.DatabaseManagerException;
import common.exceptions.NonAuthorizedUserException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.MovieLite;
import common.util.User;

/**
 * 'add' command. Adds a new element to the collection.
 */
public class AddCommand extends Command{
    private final CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public AddCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
            collectionManager.addToCollection(databaseCollectionManager.insertMovie(movie, user));
            ResponseOutputer.append("Кино успешно добавлено в коллекцию!\n");
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
