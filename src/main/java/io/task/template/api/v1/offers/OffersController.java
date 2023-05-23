package io.task.template.api.v1.offers;


import io.task.template.api.mappers.offers.OfferMapper;
import io.task.template.api.v1.AbstractController;
import io.task.template.api.v1.OffersApi;
import io.task.template.api.v1.resources.OffersResource;
import io.task.template.services.offers.OffersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OffersController extends AbstractController implements OffersApi {

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private OffersService offersService;


    @Override
    public ResponseEntity<OffersResource> getOffers() {
        final var offersResource = new OffersResource().offers(offerMapper.map(offersService.getOffers()));
        return ResponseEntity.ok().body(offersResource);
    }

}
