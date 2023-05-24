package io.task.template.api.v1.offers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.task.template.CommonTest;
import io.task.template.api.v1.resources.OffersResource;
import io.task.template.services.offers.OffersService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OffersControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OffersService offersService;


    @Test
    void canGetInventory() throws Exception {

        final var offers = getTestOffers(2);

        Mockito.when(this.offersService.getOffers()).thenReturn(offers);

        /* Building the request */
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/offers")
                .accept(MediaType.APPLICATION_JSON);

        /* Performing the request */
        final var response = this.mockMvc.perform(requestBuilder).andReturn().getResponse().getContentAsString();
        final var result = objectMapper.readValue(response, OffersResource.class);
        Assertions.assertNotNull(result);
        final var offersResource = result.getOffers();
        assertEquals(2, offersResource.size());
        Assertions.assertEquals(offers.get(0).getName(), offersResource.get(0).getName());
        Assertions.assertEquals(offers.get(1).getItem().getId(), offersResource.get(1).getItemId());
    }


}
