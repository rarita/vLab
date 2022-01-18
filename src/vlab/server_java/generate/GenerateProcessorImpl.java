package vlab.server_java.generate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.generate.GenerateProcessor;
import vlab.server_java.StaticUtilsProvider;
import vlab.server_java.model.VariantRequest;
import vlab.server_java.model.VariantResponse;

/**
 * Simple GenerateProcessor implementation. Supposed to be changed as needed to
 * provide necessary Generate method support.
 */
public class GenerateProcessorImpl implements GenerateProcessor {

    private static final int DEFAULT_QUANTITY = 5;

    @Override
    public GeneratingResult generate(String condition) {

        //do Generate logic here
        final ObjectMapper mapper = StaticUtilsProvider.getMapperInstance();

        int q;
        try {
            final VariantRequest vReq = mapper.readValue(condition, VariantRequest.class);
            q = DEFAULT_QUANTITY + vReq.getComplexity() * 2;
        } catch (JsonProcessingException e) {
            q = DEFAULT_QUANTITY;
        }

        final VariantResponse varRsp = VariantResponse.random(q);

        String text = "text: " + condition;
        String code = null;
        try {
            code = mapper.writeValueAsString(varRsp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new GeneratingResult(text, code, null);

    }
}
