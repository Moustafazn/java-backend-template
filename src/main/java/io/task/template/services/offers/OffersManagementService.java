package io.task.template.services.offers;


import io.task.template.data.entities.offers.Offer;
import io.task.template.data.repositories.offers.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OffersManagementService {

    @Autowired
    private OfferRepository offerRepository;


    @Transactional
    public List<String> deleteOffers(final List<String> offerIds) {
        offerRepository.deleteByIdIn(offerIds);
        return offerIds;
    }

    @Transactional(readOnly = true)
    public List<Offer> getManagementOffers() {
        return offerRepository.findAll();
    }

    @Transactional
    public List<Offer> postOffers(final List<Offer> offers) {
        return offerRepository.saveAll(offers);
    }


}
