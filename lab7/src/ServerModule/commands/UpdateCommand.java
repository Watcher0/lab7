package ServerModule.commands;


import ServerModule.util.DatabaseCollectionManager;
import ServerModule.util.ResponseOutputer;
import common.data.*;
import common.exceptions.*;
import ServerModule.util.CollectionManager;
import common.util.MovieLite;
import common.util.User;

import java.time.LocalDate;

/**
 * 'update' command. Updates the information about selected studyGroup.
 */
public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;
    private final DatabaseCollectionManager databaseCollectionManager;

    public UpdateCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
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

            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfArgumentsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            int id = Integer.parseInt(argument);
            Movie movie = collectionManager.getById(id);
            if (movie == null) throw new MovieNotFoundException();
            if (!movie.getOwner().equals(user)) throw new PermissionDeniedException();
            if (!databaseCollectionManager.checkMovieByIdAndUserId(movie.getId(), user)) throw new IllegalDatabaseEditException();
            MovieLite movieLite = (MovieLite) objectArgument;

            databaseCollectionManager.updateMovieByID(id, movieLite);

            String name = movieLite.getName() == null ? movie.getName() : movieLite.getName();
            Coordinates coordinates = movieLite.getCoordinates() == null ? movie.getCoordinates() : movieLite.getCoordinates();
            LocalDate creationDate = movie.getCreationDate();
            Long oscarsCount = movieLite.getOscarsCount() == -1 ? movie.getOscarsCount() : movieLite.getOscarsCount();
            Integer goldenPalmCount = movieLite.getGoldenPalmCount() == -1 ? movie.getGoldenPalmCount() : movieLite.getGoldenPalmCount();
            MovieGenre genre = movieLite.getGenre() == null ? movie.getGenre() : movieLite.getGenre();
            MpaaRating mpaaRating = movieLite.getMpaaRating() == null ? movie.getMpaaRating() : movieLite.getMpaaRating();
            Person operator = movieLite.getOperator() == null ? movie.getOperator() : movieLite.getOperator();

            collectionManager.removeFromCollection(movie);

            collectionManager.addToCollection(new Movie(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    oscarsCount,
                    goldenPalmCount,
                    genre,
                    mpaaRating,
                    operator,
                    user
            ));
            ResponseOutputer.append("Кино успешно изменено!\n");
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NumberFormatException exception) {
            ResponseOutputer.append("ID должен быть представлен числом!\n");
        } catch (MovieNotFoundException exception) {
            ResponseOutputer.append("Кино с таким ID в коллекции нет!\n");
        } catch (PermissionDeniedException e) {
            ResponseOutputer.append("Принадлежащие другим пользователям объекты доступны только для чтения!\n");
        } catch (DatabaseManagerException e) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (IllegalDatabaseEditException e) {
            ResponseOutputer.append("Произошло нелегальное изменение объекта в базе данных!\n");
            ResponseOutputer.append("Перезапустите клиент для избежания ошибок!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}
