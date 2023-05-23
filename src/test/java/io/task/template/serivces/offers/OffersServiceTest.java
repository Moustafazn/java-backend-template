package io.task.template.serivces.offers;

import io.task.template.CommonTest;
import io.task.template.data.repositories.offers.OfferRepository;
import io.task.template.services.offers.OffersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {OffersService.class})
public class OffersServiceTest extends CommonTest {

    @MockBean
    private OfferRepository offerRepository;

    @Autowired
    private OffersService offersService;


    @Test
    void getLinesTest() {
        final var offers = getTestOffers(2);
        Mockito.when(this.offerRepository.findAll())
                .thenReturn(offers);

        final var offersResponse = this.offersService.getOffers();

        Assertions.assertEquals(offers, offersResponse);
        verify(this.offerRepository, times(1))
                .findAll();
    }

}
