package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.history.ModelState;
import seedu.address.history.exceptions.HistoryException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes any command that is reversible, reverting the model to its previous state
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the most recent command.";
    public static final String MESSAGE_SUCCESS = "Command undone: %1$s";
    public static final String MESSAGE_NO_ROLLBACK = "There is no more history to roll back!";

    public UndoCommand() {
        setReversible(false);
    }

    /**
     * Undoes the last command and restores the previous state.
     *
     * @param model The model system for managing the address book
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ModelState prevModelState = model.getCurrentModelState();
        try {
            model.rollBackState();
        } catch (HistoryException e) {
            throw new CommandException(MESSAGE_NO_ROLLBACK);
        }

        ModelState currModelState = model.getCurrentModelState();
        model.restoreModelState(currModelState);

        return new CommandResult(String.format(MESSAGE_SUCCESS, prevModelState.getCommand().getCommandString()));
    }

    @Override
    public String getCommandString() {
        return COMMAND_WORD;
    }
}

