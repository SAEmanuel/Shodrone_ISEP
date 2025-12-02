package ui.users;

import controller.user.FilterUsersController;
import controller.user.UserStatusController;
import controller.user.CheckUserStatusController;
import utils.Utils;
import domain.entity.User;


import java.util.List;



public class ManageUserUI implements Runnable {

    private final FilterUsersController filterCtrl = new FilterUsersController();
    private final UserStatusController statusCtrl = new UserStatusController();
    private final CheckUserStatusController checkCtrl = new CheckUserStatusController();

    @Override
    public void run() {

        List<String> filterOptions = List.of("Active users", "Inactive users", "All users");
        Utils.printBoxTitle("User Filter");
        int filterIndex = Utils.showAndSelectIndex(filterOptions, "");
        if (filterIndex == -1) return;

        List<User> filteredUsers = filterCtrl.getUsersByFilter(filterIndex);
        if (filteredUsers.isEmpty()) {
            Utils.printFailMessage("No users match the selected filter.");
            return;
        }

        List<String> displayList = filteredUsers.stream()
                .map(user -> user.getId().toString())
                .toList();

        Utils.printBoxTitle("Select User");
        int selectedIndex = Utils.showAndSelectIndex(displayList, "");
        if (selectedIndex == -1) return;

        User selected = filteredUsers.get(selectedIndex);

        boolean isActive = checkCtrl.isUserActive(selected);
        Utils.printSubTitleNoColon("User: " + selected.getId());
        Utils.printSubTitleNoColon("Current state: " + (isActive ? "ACTIVE" : "INACTIVE"));

        List<String> actions = List.of("Change Status");
        Utils.printBoxTitle("Select Option");
        int actionIndex = Utils.showAndSelectIndex(actions, "");
        if (actionIndex == -1) return;

        boolean success = statusCtrl.changeUserStatus(selected, !selected.isActive());

        if (success) {
            Utils.printSuccessMessage("✅ User status changed to " + (selected.isActive() ? "ACTIVE" : "INACTIVE") + ".");
        } else {
            Utils.printAlterMessage("ℹ️  User is already " + (actionIndex == 0 ? "active." : "inactive."));
        }
    }
}
