package controller.user;

import authz.User;

/**
 * Controller responsible for checking the current active status of a user.
 */
public class CheckUserStatusController {

    public boolean isUserActive(User user) {
        return user.isActive();
    }
}
