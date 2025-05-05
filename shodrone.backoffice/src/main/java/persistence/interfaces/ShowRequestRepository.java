/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence.interfaces;


import domain.entity.Costumer;
import domain.entity.ShowRequest;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author eapli
 */
public interface ShowRequestRepository {


    public Optional<ShowRequest> saveInStore(ShowRequest entity);
    public List<ShowRequest> getAll();
    public Optional<ShowRequest> findById(Object id);
    public Optional<List<ShowRequest>> findByCostumer(Costumer costumer);
    public Optional<ShowRequest> updateShowRequest(ShowRequest entity);
}