package io.task.template.data.repositories.offers;

import io.task.template.data.entities.offers.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
}
