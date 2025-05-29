package controller.user;

import domain.entity.User;

/**
 * Controller responsible for checking the current active status of a user.
 */
public class CheckUserStatusController {

    public boolean isUserActive(User user) {
        return user.isActive();
    }
}
