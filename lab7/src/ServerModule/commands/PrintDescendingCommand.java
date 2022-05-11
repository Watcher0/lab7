package ServerModule.commands;

import ServerModule.util.ResponseOutputer;
import common.exceptions.NonAuthorizedUserException;
import common.exceptions.WrongAmountOfArgumentsException;
import ServerModule.util.CollectionManager;
import common.util.User;

/**
 * Command 'print_ascending'. Prints collection descending.
 */
public class PrintDescendingCommand extends Command {
    private CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending", "вывести элементы коллекции в порядке убывания");
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
            ResponseOutputer.append(collectionManager.descending());
            return true;
        } catch (WrongAmountOfArgumentsException exception) {
            ResponseOutputer.append("Использование: '" + getName() + "'\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }
}
