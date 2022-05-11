package ServerModule.util;

import common.util.Request;
import common.util.Response;
import common.util.ResponseCode;
import common.util.User;

public class RequestManager {
    private CommandManager commandManager;

    public RequestManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response manage(Request request) {
        User hashUser;
        if (request.getUser() == null) {
            hashUser = null;
        } else {
            hashUser = new User(
                    request.getUser().getLogin(), DataHasher.hash(request.getUser().getPassword()));
            commandManager.addToHistory(request.getCommandName(), request.getUser());
        }
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getArgument(), request.getObjectArgument(), hashUser);
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    private synchronized ResponseCode executeCommand(String command, String argument, Object objectArgument, User user) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "remove_greater":
                if (!commandManager.removeGreater(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "history":
                if (!commandManager.history(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "add_if_min":
                if (!commandManager.addIfMin(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "print_ascending":
                if (!commandManager.printAscending(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "print_descending":
                if (!commandManager.printDescending(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "print_field_descending_mpaa_rating":
                if (!commandManager.printFieldDescendingMpaaRating(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "sign_up":
                if (!commandManager.sign_up(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "sign_in":
                if (!commandManager.sign_in(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            case "log_out":
                if (!commandManager.log_out(argument, objectArgument, user)) return ResponseCode.ERROR;
                break;
            default:
                ResponseOutputer.append("Команда '" + command + "' не найдена. Наберите 'help' для справки.\n");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}
