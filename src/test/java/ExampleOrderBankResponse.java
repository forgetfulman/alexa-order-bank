import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class ExampleOrderBankResponse {

    static String orderDetails() {
        return loadFile("example_order_detail_response.json");
    }

    static String orderTotal() {
        return loadFile("example_order_total_response.json");
    }

    static String catalogDescriptions() {
        return loadFile("example_catalog_descriptions_response.json");
    }

    static String loadFile(String fileName) {
        Logger log = LoggerFactory.getLogger(ExampleOrderBankResponse.class);

        try {
            fileName = IOUtils.toString(
                    ExampleOrderBankResponse.class.getResourceAsStream(fileName),"UTF-8"
            );
        } catch (IOException ioe) {
            log.info("Unable to load file!");
        }
        return fileName;
    }
}
