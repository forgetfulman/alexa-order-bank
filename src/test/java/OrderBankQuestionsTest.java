import com.forgetfulman.alexa.orderbank.ElasticSearch;
import com.forgetfulman.alexa.orderbankquestions.OrderBankAnswers;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderBankQuestionsTest {

    @Test
    public void loadPropertiesFile() {
        Properties theOrderBank = new OrderBankAnswers().theOrderBank();
        assertTrue(theOrderBank.getProperty("elasticSearchUrl").equals("http://orderbanktesturl/"));
        assertTrue(theOrderBank.getProperty("orderIndex").equals("order-test"));
        assertTrue(theOrderBank.getProperty("featureIndex").equals("i18n-test"));
    }

    @Test
    public void summariseAnOrderDetails() {
        ElasticSearch mockElasticSearchInstance = mock(ElasticSearch.class);
        when(mockElasticSearchInstance.call("http://orderbanktesturl/order-test/_search?q=orderId:1"))
                .thenReturn(ExampleOrderBankResponse.orderDetails());
        when(mockElasticSearchInstance.call("http://orderbanktesturl/i18n-test/_search?q=_id:WSPAD-CD9-2016-LincolnMKXCHN"))
                .thenReturn(ExampleOrderBankResponse.catalogDescriptions());

        OrderBankAnswers orderBankAnswers = new OrderBankAnswers(mockElasticSearchInstance);

        String orderDetails = orderBankAnswers.whatAreTheDetailsOfOrder("1");

        assertTrue(orderDetails.equals("Order 1 is for a Lincoln in Black Velvet Metallic paint,"
                + " with a 6-Speed SelectShift® Automatic Transmission"
                + " and a 2.0L EcoBoost® Engine"));
    }
}
