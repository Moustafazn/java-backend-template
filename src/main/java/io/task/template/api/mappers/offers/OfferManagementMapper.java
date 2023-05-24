package io.task.template.api.mappers.offers;

import io.task.template.api.v1.resources.OffersManagementOfferResource;
import io.task.template.data.entities.offers.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferManagementMapper {

    @Mapping(source = "item.id", target = "itemId")
    OffersManagementOfferResource map(Offer offer);

    @Mapping(source = "itemId", target = "item.id")
    Offer map(OffersManagementOfferResource offerResource);

    List<OffersManagementOfferResource> mapOfferSources(List<Offer> offers);

    List<Offer> mapOffers(List<OffersManagementOfferResource> offersSource);


}
