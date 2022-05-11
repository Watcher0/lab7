package ServerModule.util;

import ServerModule.commands.Command;
import common.exceptions.HistoryIsEmptyException;
import common.util.User;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final int COMMAND_HISTORY_SIZE = 14;
    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];

    private final List<Command> commands = new ArrayList<>();
    private final Command helpCommand;
    private final Command infoCommand;
    private final Command showCommand;
    private final Command addCommand;
    private final Command updateCommand;
    private final Command removeByIdCommand;
    private final Command clearCommand;
    private final Command executeScriptCommand;
    private final Command exitCommand;
    private final Command addIfMinCommand;
    private final Command removeGreaterCommand;
    private final Command historyCommand;
    private final Command printAscendingCommand;
    private final Command printDescendingCommand;
    private final Command printFieldDescendingMpaaRatingCommand;
    private final Command signUpCommand;
    private final Command signInCommand;
    private final Command logOutCommand;

    public CommandManager(Command helpCommand, Command infoCommand, Command showCommand, Command addCommand, Command updateCommand, Command removeByIdCommand, Command clearCommand, Command executeScriptCommand, Command exitCommand, Command addIfMinCommand, Command removeGreaterCommand, Command historyCommand, Command printAscendingCommand, Command printDescendingCommand, Command printFieldDescendingMpaaRatingCommand, Command signUpCommand, Command signInCommand, Command logOutCommand) {
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.showCommand = showCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.exitCommand = exitCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.removeGreaterCommand = removeGreaterCommand;
        this.historyCommand = historyCommand;
        this.printAscendingCommand = printAscendingCommand;
        this.printDescendingCommand = printDescendingCommand;
        this.printFieldDescendingMpaaRatingCommand = printFieldDescendingMpaaRatingCommand;
        this.signUpCommand = signUpCommand;
        this.signInCommand = signInCommand;
        this.logOutCommand = logOutCommand;

        commands.add(infoCommand);
        commands.add(showCommand);
        commands.add(addCommand);
        commands.add(updateCommand);
        commands.add(removeByIdCommand);
        commands.add(clearCommand);
        commands.add(exitCommand);
        commands.add(executeScriptCommand);
        commands.add(historyCommand);
        commands.add(addIfMinCommand);
        commands.add(removeGreaterCommand);
        commands.add(printAscendingCommand);
        commands.add(printDescendingCommand);
        commands.add(printFieldDescendingMpaaRatingCommand);
        commands.add(logOutCommand);
        commands.add(new Command("log_in", "авторизоваться") {
            @Override
            public boolean execute(String argument, Object objectArgument, User user) {
                return false;
            }
        });
    }

    /**
     * @return List of manager's com.serverModule.commands.
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Prints info about the all com.serverModule.commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean help(String argument, Object objectArgument, User user) {
        if (helpCommand.execute(argument, objectArgument, user)) {
            for (Command command : commands) {
                ResponseOutputer.appendTable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean info(String argument, Object objectArgument, User user) {
        return infoCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean show(String argument, Object objectArgument, User user) {
        return showCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean add(String argument, Object objectArgument, User user) {
        return addCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean update(String argument, Object objectArgument, User user) {
        return updateCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeById(String argument, Object objectArgument, User user) {
        return removeByIdCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean clear(String argument, Object objectArgument, User user) {
        return clearCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean exit(String argument, Object objectArgument, User user) {
        return exitCommand.execute(argument,objectArgument, user);
    }

    public boolean sign_up(String argument, Object objectArgument, User user) {
        return signUpCommand.execute(argument, objectArgument, user);
    }

    public boolean sign_in(String argument, Object objectArgument, User user) {
        return signInCommand.execute(argument, objectArgument, user);
    }

    public boolean log_out(String argument, Object objectArgument, User user) {
        return logOutCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean executeScript(String argument, Object objectArgument, User user) {
        return executeScriptCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean removeGreater(String argument, Object objectArgument, User user) {
        return removeGreaterCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean addIfMin(String argument, Object objectArgument, User user) {
        return addIfMinCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printAscending(String argument, Object objectArgument, User user){
        return printAscendingCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printDescending(String argument, Object objectArgument, User user){
        return printDescendingCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean printFieldDescendingMpaaRating(String argument, Object objectArgument, User user){
        return printFieldDescendingMpaaRatingCommand.execute(argument, objectArgument, user);
    }


    /**
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore, User user) {

        for (Command command : commands) {
            if (command.getName().split(" ")[0].equals(commandToStore)) {
                for (int i = COMMAND_HISTORY_SIZE - 1; i > 0; i--) {
                    commandHistory[i] = commandHistory[i - 1];
                }
                commandHistory[0] = commandToStore;
            }
        }
    }

    /**
     * Prints the history of used serverModule.commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean history(String argument, Object objectArgument, User user) {
        if (historyCommand.execute(argument, objectArgument, user)) {
            try {
                if (commandHistory.length == 0) throw new HistoryIsEmptyException();

                ResponseOutputer.append("Последние использованные команды:\n");
                for (int i=0; i<commandHistory.length; i++) {
                    if (commandHistory[i] != null) ResponseOutputer.append(" " + commandHistory[i] + "\n");
                }
                return true;
            } catch (HistoryIsEmptyException exception) {
                ResponseOutputer.append("Ни одной команды еще не было использовано!\n");
            }
        }
        return false;
    }


}
