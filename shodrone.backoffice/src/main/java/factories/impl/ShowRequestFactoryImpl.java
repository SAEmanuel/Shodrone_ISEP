package factories.impl;

import domain.entity.ShowRequest;
import eapli.framework.domain.model.DomainFactory;
import utils.AuthUtils;

import java.time.LocalDateTime;

public class ShowRequestFactoryImpl implements DomainFactory<ShowRequest> {

    private LocalDateTime requestSubmissionDate;
    private String responsibleCollaborator;

    @Override
    public ShowRequest build() {
        this.requestSubmissionDate = LocalDateTime.now();
        this.responsibleCollaborator = AuthUtils.getCurrentUserEmail();
        return null;
    }

}
