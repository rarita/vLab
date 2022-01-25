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
    private static final String DEFAULT_TEXT = "Вам дано тренировочное множество модели, заданное таблицей справа. \n" +
            "Необходимо верно расставить классификаторы точек тренировочного множества на координатной оси, что позволит " +
            "Вам определить наиболее вероятный классификатор объекта, который необходимо оценить.\n После классифицирования " +
            "точек на координатной оси, определите класс объекта по его K-ближайшим соседям.";

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

        String text = DEFAULT_TEXT;
        String code = null;
        try {
            code = mapper.writeValueAsString(varRsp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new GeneratingResult(text, code, null);

    }
}
