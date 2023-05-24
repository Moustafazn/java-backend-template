package io.task.template.api.v1.offers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.task.template.CommonTest;
import io.task.template.api.v1.resources.OfferIdsResource;
import io.task.template.api.v1.resources.OffersManagementOfferListResource;
import io.task.template.api.v1.resources.OffersManagementOffersResource;
import io.task.template.services.offers.OffersManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OffersManagementControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OffersManagementService offersManagementService;


    @Test
    void canGetManagementOffers() throws Exception {

        final var offers = getTestOffers(2);

        Mockito.when(this.offersManagementService.getManagementOffers()).thenReturn(offers);

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/offers-management")
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, OffersManagementOfferListResource.class);
        Assertions.assertNotNull(result);
        assertEquals(2, result.getOffers().size());
        Assertions.assertEquals(offers.get(0).getName(), result.getOffers().get(0).getName());
        Assertions.assertEquals(offers.get(1).getItem().getId(), result.getOffers().get(1).getItemId());
        Assertions.assertEquals(offers.get(1).getItem().getId(), result.getOffers().get(1).getItemId());

    }

    @Test
    void postOffersTest() throws Exception {

        final var offers = getTestOffers(2);
        offers.get(0).setId(String.valueOf(UUID.randomUUID()));

        final var OffersManagementOffers = getTestOffersManagementOfferResource(2);
        Mockito.when(this.offersManagementService.postOffers(isA(List.class))).thenReturn(offers);

        final RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/offers-management")
                .content(this.objectMapper.writeValueAsString(new OffersManagementOffersResource().offers(OffersManagementOffers)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        var result = objectMapper.readValue(response, OffersManagementOffersResource.class);

        Assertions.assertNotNull(result);
        assertEquals(2, result.getOffers().size());
        Assertions.assertEquals(offers.get(0).getName(), result.getOffers().get(0).getName());
        Assertions.assertEquals(offers.get(1).getDescription(), result.getOffers().get(1).getDescription());

    }

    @Test
    void deleteOffersTest() throws Exception {
        final var ids = List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.
                delete(String.format("/api/v1/offers-management"))
                .content(this.objectMapper.writeValueAsString(new OfferIdsResource().offerIds(ids)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(this.offersManagementService).deleteOffers(ids);
    }


}
