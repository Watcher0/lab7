package ServerModule.commands;

import ServerModule.util.DatabaseCollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.Movie;
import common.exceptions.*;
import ServerModule.util.CollectionManager;
import common.util.User;

/**
 * 'remove_by_id' command. Removes the element by its ID.
 */
public class RemoveByIdCommand extends Command {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
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
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Integer id = Integer.parseInt(argument);
            Movie movie = collectionManager.getById(id);
            if (movie == null) throw new MovieNotFoundException();
            if (!movie.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkMovieByIdAndUserId(movie.getId(), user)) throw new IllegalDatabaseEditException();
            databaseCollectionManager.deleteMovieById(movie.getId());
            collectionManager.removeFromCollection(movie);
            ResponseOutputer.append("Кино успешно удалено!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NumberFormatException exception) {
            ResponseOutputer.append("ID должен быть представлен числом!\n");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.append("Кино с таким ID в коллекции нет!\n");
        } catch (DatabaseManagerException e) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (IllegalDatabaseEditException exception) {
            ResponseOutputer.append("Произошло нелегальное изменение объекта в базе данных!\n");
            ResponseOutputer.append("Перезапустите клиент для избежания ошибок!\n");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.append("Принадлежащие другим пользователям объекты доступны только для чтения!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}