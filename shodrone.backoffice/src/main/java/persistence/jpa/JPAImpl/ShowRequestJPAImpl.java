/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.jpa.JPAImpl;

import domain.entity.ShowRequest;
import persistence.jpa.JpaBaseRepository;
import persistence.interfaces.ShowRequestRepository;


public class ShowRequestJPAImpl extends JpaBaseRepository<ShowRequest, Integer> implements ShowRequestRepository<ShowRequest> {

    @Override
    public ShowRequest save(ShowRequest entity) {
        return null;
    }
}