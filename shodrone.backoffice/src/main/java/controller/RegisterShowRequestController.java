package controller;

import persistence.inmemory.Repositories;
import persistence.interfaces.ShowRequestRepository;
import persistence.jpa.JPAImpl.ShowRequestJPAImpl;

public class RegisterShowRequestController {

    private ShowRequestRepository inMemoryShowRequestRepository;
    private ShowRequestJPAImpl jpaShowRequestRepository;

    public RegisterShowRequestController() {
        getInMemoryShowRequestRepository();
        getJpaShowRequestRepository();
    }

    private ShowRequestRepository getInMemoryShowRequestRepository() {
        if (inMemoryShowRequestRepository == null) {
            inMemoryShowRequestRepository = Repositories.getInstance().getShowRequestRepository();
        }
        return inMemoryShowRequestRepository;
    }
    private ShowRequestJPAImpl getJpaShowRequestRepository() {
        if (jpaShowRequestRepository == null) {
            jpaShowRequestRepository = new ShowRequestJPAImpl();
        }
        return jpaShowRequestRepository;
    }


    public void registerShowRequest(){

    }

}
