package io.task.template.api.v1.offers;


import io.task.template.api.mappers.offers.OfferManagementMapper;
import io.task.template.api.v1.AbstractController;
import io.task.template.api.v1.OffersManagementApi;
import io.task.template.api.v1.resources.DeletedOfferIdsResource;
import io.task.template.api.v1.resources.OfferIdsResource;
import io.task.template.api.v1.resources.OffersManagementOfferListResource;
import io.task.template.api.v1.resources.OffersManagementOffersResource;
import io.task.template.services.offers.OffersManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OffersManagementController extends AbstractController implements OffersManagementApi {

    @Autowired
    private OfferManagementMapper offerManagementMapper;

    @Autowired
    private OffersManagementService offersManagementService;


    @Override
    public ResponseEntity<DeletedOfferIdsResource> deleteOffers(OfferIdsResource offerIdsResource) {
        final var deletedOfferIds = offersManagementService.deleteOffers(offerIdsResource.getOfferIds());
        return ResponseEntity.ok().body(new DeletedOfferIdsResource().offerIds(deletedOfferIds));
    }

    @Override
    public ResponseEntity<OffersManagementOfferListResource> getManagementOffers() {
        final var offersResource = offerManagementMapper.mapOfferSources(offersManagementService.getManagementOffers());
        return ResponseEntity.ok().body(new OffersManagementOfferListResource().offers(offersResource));
    }

    @Override
    public ResponseEntity<OffersManagementOffersResource> postOffers(OffersManagementOffersResource offersManagementOffersResource) {
        final var offersResource = offersManagementService.postOffers(offerManagementMapper.mapOffers(offersManagementOffersResource.getOffers()));
        return ResponseEntity.ok().body(new OffersManagementOffersResource().offers(offerManagementMapper.mapOfferSources(offersResource)));
    }

}
