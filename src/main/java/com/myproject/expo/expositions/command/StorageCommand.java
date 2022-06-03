package com.myproject.expo.expositions.command;

import com.myproject.expo.expositions.command.admin.*;
import com.myproject.expo.expositions.command.admin.AddExpoCommand;
import com.myproject.expo.expositions.command.admin.facade.FacadeCommand;
import com.myproject.expo.expositions.command.general.*;
import com.myproject.expo.expositions.command.user.BuyExpoCommand;
import com.myproject.expo.expositions.command.user.GetUserExposCommand;
import com.myproject.expo.expositions.command.user.TopUpBalanceCommand;
import com.myproject.expo.expositions.service.impl.ExpoServiceImpl;
import com.myproject.expo.expositions.service.impl.HallServiceImpl;
import com.myproject.expo.expositions.service.impl.ThemeServiceImpl;

import java.util.Arrays;

public enum StorageCommand {
    REGISTER("register", new RegisterCommand()),
    LOGIN("login", new LoginCommand()),
    LOGOUT("logout", new LogOutCommand()),
    VIEW_ALL_THEMES("viewAllThemes", new ViewAllThemesCommand()),
    ADD_NEW_THEME("addNewTheme", new FacadeCommand<>(new AddThemeCommand(), new ThemeServiceImpl())),
    UPDATE_THEME("updateTheme", new FacadeCommand<>(new UpdateThemeCommand(), new ThemeServiceImpl())),
    DELETE_THEME("deleteTheme", new FacadeCommand<>(new DeleteThemeCommand(), new ThemeServiceImpl())),
    VIEW_ALL_HALLS("viewAllHalls", new ViewAllHallsCommand()),
    ADD_NEW_HALL("addNewHall", new FacadeCommand<>(new AddHallCommand(), new HallServiceImpl())),
    UPDATE_HALL("updateHall", new FacadeCommand<>(new UpdateHallCommand(), new HallServiceImpl())),
    DELETE_HALL("deleteHall", new FacadeCommand<>(new DeleteHallCommand(), new HallServiceImpl())),
    VIEW_ALL_EXPOS("viewAllExpos", new ViewAllExposCommand()),
    SEE_ONE_EXPO("viewSelectedExpo", new ViewSelectedExpoCommand()),
    UPDATE_EXPO("updateExpo", new FacadeCommand<>(new UpdateExpoCommand(), new ExpoServiceImpl())),
    PREPARE_TO_ADD_EXPO("prepareToAddExpo", new PrepareToAddExpoCommand()),
    ADD_EXPO("addExpo", new AddExpoCommand()),
    TOP_UP_BALANCE("topUpBalance", new TopUpBalanceCommand()),
    BUY_EXPO("buyExpo", new BuyExpoCommand()),
    CHANGE_EMAIL("changeEmail", new ChangeEmailCommand()),
    CHANGE_PASS("changePass", new ChangePassCommand()),
    CANCELED_EXPOS("viewMyExpos", new GetUserExposCommand()),
    SEARCH_EXPO("searchExpo", new SearchExpoCommand()),
    CANCEL_EXPO("changeExpoStatus", new ChangeExpoStatusCommand()),
    ALL_USERS("viewAllUsers", new AllUsersCommand()),
    BLOCK_UNBLOCK_USER("blockUnblockUser", new BlockUnblockUserCommand());

    private final String action;
    private final Command command;

    StorageCommand(String action, Command command) {
        this.action = action;
        this.command = command;
    }

    public static Command getCommand(String act) {
        return Arrays.stream(StorageCommand.values())
                .filter(storageCommand -> storageCommand.getAction().equals(act))
                .findFirst()
                .map(StorageCommand::getCommand)
                .orElse(null);
    }

    public String getAction() {
        return action;
    }

    public Command getCommand() {
        return command;
    }
}
