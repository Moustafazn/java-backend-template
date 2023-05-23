package io.task.template.api.mappers.offers;

import io.task.template.api.v1.resources.OfferResource;
import io.task.template.data.entities.offers.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(source = "item.id", target = "itemId")
    OfferResource map(Offer offer);

    List<OfferResource> map(List<Offer> offers);
}
