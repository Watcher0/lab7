package ServerModule.commands;

import ServerModule.util.DatabaseCollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import common.exceptions.*;
import ServerModule.util.CollectionManager;
import common.util.MovieLite;
import common.util.User;

import java.util.List;

/**
 * 'remove_greater' command. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends Command {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (!argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            MovieLite movie = (MovieLite) objectArgument;
            Movie newMovie = new Movie(
                    collectionManager.generateNextId(),
                    movie.getName(),
                    movie.getCoordinates(),
                    movie.getOscarsCount(),
                    movie.getGoldenPalmCount(),
                    movie.getGenre(),
                    movie.getMpaaRating(),
                    movie.getOperator(),
                    user
            );
            List<Movie> movies = collectionManager.getGreater(newMovie);
            int k = 0;
            for (Movie m : movies) {
                if (!m.getOwner().equals(user)) continue;
                if (!databaseCollectionManager.checkMovieByIdAndUserId(m.getId(), user)) throw new IllegalDatabaseEditException();
                databaseCollectionManager.deleteMovieById(m.getId());
                collectionManager.removeFromCollection(m);
                k ++ ;
            }
            ResponseOutputer.append("Удалено фильмов: " + k + "\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (DatabaseManagerException e) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (IllegalDatabaseEditException exception) {
            ResponseOutputer.append("Произошло нелегальное изменение объекта в базе данных!\n");
            ResponseOutputer.append("Перезапустите клиент для избежания ошибок!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}