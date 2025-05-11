package controller;

import ui.common.PersistenceSelectorUI;

public class StartupMessageBackofficeController {
    private static final int OPTION_FOR_IN_MEMORY = 1;
    private PersistenceSelectorUI persistenceSelectorUI;

    public StartupMessageBackofficeController() {
        persistenceSelectorUI = getPersistenceSelectorUI();
    }

    private PersistenceSelectorUI getPersistenceSelectorUI() {
        if(persistenceSelectorUI == null){
             persistenceSelectorUI = new PersistenceSelectorUI();
         }
         return persistenceSelectorUI;
    }

    public boolean displayPersistenceOptions() {
        int option = persistenceSelectorUI.selectionUI();
        return option == OPTION_FOR_IN_MEMORY;
    }

}
