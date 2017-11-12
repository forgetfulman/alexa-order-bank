import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgetfulman.alexa.orderbank.GOBOrder;
import com.forgetfulman.alexa.orderbank.SearchResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.forgetfulman.alexa.orderbank.OrderBankResponse.extractFrom;
import static org.junit.Assert.assertTrue;

public class OrderBankTest {

    private static final Logger log = LoggerFactory.getLogger(OrderBankTest.class);

    @Test
    public void convertElasticSearchResponseToAGOBOrder() {
        String esResponse = ExampleOrderBankResponse.orderDetails();

        SearchResponse processedResponse = new SearchResponse();

        try {
            processedResponse = new ObjectMapper().readValue(esResponse, SearchResponse.class);
        } catch (IOException ioe) {
            log.info("Unable to convert ES response to a GOB order!");
        }

        GOBOrder order;
        order = processedResponse.result.orders.get(0).order;

        assertTrue(order.catalog.equals("WSPAD-CD9-2016-LincolnMKXCHN"));
        assertTrue(order.brand.equals("L"));
        assertTrue(order.orderId.equals("1"));
    }

    @Test
    public void extractElementFromElasticSearchResponse() {
        String esResponse = ExampleOrderBankResponse.orderTotal();

        JsonNode indexSummary = extractFrom(esResponse, "indices", "order-alexa");
        String orderTotal = indexSummary.at("/primaries/docs/count").asText();

        assertTrue(orderTotal.equals("20001"));
    }

}
