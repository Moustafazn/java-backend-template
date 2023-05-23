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
public class OffersService {

    @Autowired
    private OfferRepository offerRepository;

    @Transactional(readOnly = true)
    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }
}
