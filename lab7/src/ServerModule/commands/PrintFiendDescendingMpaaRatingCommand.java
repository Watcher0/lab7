package ServerModule.commands;

import ServerModule.util.ResponseOutputer;
import common.exceptions.NonAuthorizedUserException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.User;


/**
 * Command 'print_field_descending_mpaa_rating'. Prints collection descending mpaa rating.
 */
public class PrintFiendDescendingMpaaRatingCommand extends Command {
    private CollectionManager collectionManager;

    public PrintFiendDescendingMpaaRatingCommand(CollectionManager collectionManager) {
        super("print_field_descending_mpaa_rating", "вывести значения поля mpaaRating всех элементов в порядке убывания");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command execute status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfArgumentsException();
            ResponseOutputer.append(collectionManager.descendingMpaaRating());
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}
