package io.task.template.serivces.offers;

import io.task.template.CommonTest;
import io.task.template.data.repositories.offers.OfferRepository;
import io.task.template.services.offers.OffersManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OffersManagementService.class})
public class OffersManagementServiceTest extends CommonTest {

    @MockBean
    private OfferRepository offerRepository;

    @Autowired
    private OffersManagementService offersManagementService;


    @Test
    void getItemsManagementTest() {
        final var offers = getTestOffers(2);
        Mockito.when(this.offerRepository.findAll())
                .thenReturn(offers);

        final var offerSources = this.offersManagementService.getManagementOffers();

        Assertions.assertEquals(offers, offerSources);
        verify(this.offerRepository, times(1))
                .findAll();
    }

    @Test
    void postItemsManagementTest() {

        final var offers = getTestOffers(2);
        final var id = UUID.randomUUID().toString();
        offers.get(0).setId(id);

        when(this.offerRepository.saveAll(any(List.class))).thenReturn(offers);

        this.offersManagementService.postOffers(offers);

        Assertions.assertEquals(offers.get(0).getId(), id);
        verify(this.offerRepository).saveAll(offers);
    }
}
