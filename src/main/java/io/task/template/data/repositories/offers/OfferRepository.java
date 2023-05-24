package io.task.template.data.repositories.offers;

import io.task.template.data.entities.offers.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    void deleteByIdIn(List<String> ids);
}
