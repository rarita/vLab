package vlab.server_java.check;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rlcp.check.ConditionForChecking;
import rlcp.generate.GeneratingResult;
import rlcp.server.processor.check.CheckProcessor;
import rlcp.server.processor.check.PreCheckProcessor;
import rlcp.server.processor.check.PreCheckProcessor.PreCheckResult;
import rlcp.server.processor.check.PreCheckResultAwareCheckProcessor;
import vlab.server_java.StaticUtilsProvider;
import vlab.server_java.common.Classifier;
import vlab.server_java.model.DataPoint;
import vlab.server_java.model.VariantResponse;
import vlab.server_java.model.VariantSolution;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple CheckProcessor implementation. Supposed to be changed as needed to provide
 * necessary Check method support.
 */
public class CheckProcessorImpl implements PreCheckResultAwareCheckProcessor<String> {

    private static final ObjectMapper mapper = StaticUtilsProvider.getMapperInstance();

    private class DistPair {

        private double dist;
        private Classifier cls;

        public DistPair(double dist, Classifier cls) {
            this.dist = dist;
            this.cls = cls;
        }
    }

    @Override
    public CheckingSingleConditionResult checkSingleCondition(ConditionForChecking condition,
                                                              String instructions,
                                                              GeneratingResult generatingResult) throws Exception {

        try {
            // do check logic here
            final VariantResponse variant = mapper.readValue(generatingResult.getCode(), VariantResponse.class);
            final VariantSolution solution = mapper.readValue(instructions, VariantSolution.class);

            float pointsF = 0.f;
            String comment = "";

            // правильность расставления точек по коорд. плоскости (70% оценки)
            long correctDataPoints = solution.getDataPoints()
                    .stream()
                    .filter(it -> variant.getDataPoints().contains(it))
                    .count();

            if (correctDataPoints != solution.getDataPoints().size()) {
                comment += "Были допущены ошибки в расстановке элементов по координатной плоскости. ";
            }

            pointsF += ((float) correctDataPoints / solution.getDataPoints().size()) * 0.7;

            // правильность выданного классификатора (вне зависимости от правильности точек)
            final List<DistPair> dists = new ArrayList<>();
            final DataPoint pt = variant.getPointToClassify();
            for (DataPoint dp : solution.getDataPoints()) {
                double dist = Math.sqrt(Math.pow((dp.getX() - pt.getX()), 2) + Math.pow((dp.getY() - pt.getY()), 2));
                dists.add(new DistPair(dist, dp.getCls()));
            }
            dists.sort(Comparator.comparingDouble(o -> o.dist));

            final Map<Classifier, Integer> hist = new TreeMap<>();
            Arrays.stream(Classifier.values()).forEach(classifier -> hist.put(classifier, 0));
            for (int idx = 0; idx < variant.getK(); idx++) {
                final Classifier _cls = dists.get(idx).cls;
                if (_cls == null) {
                    return new CheckingSingleConditionResult(
                            new BigDecimal(0),
                            "На плоскости расставлены не все точки."
                    );
                }
                hist.put(_cls, hist.get(_cls) + 1);
            }

            final int topClass = hist.entrySet().stream()
                    .max(Comparator.comparingInt(Map.Entry::getValue))
                    .map(Map.Entry::getValue)
                    .orElseThrow(() -> new RuntimeException("Invalid variant data."));

            final List<Classifier> correctClassifiers = hist.entrySet().stream()
                    .filter(kv -> kv.getValue() == topClass)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (correctClassifiers.contains(solution.getCls())) {
                pointsF += 0.3;
                comment += "Классификатор определен корректно. ";
            }


            if (pointsF > 0.995) { // щедрая погреха float32_t
                comment = "Решение полностью верное! Вы восхитительны!";
            }

            return new CheckingSingleConditionResult(
                    new BigDecimal(pointsF).setScale(2, BigDecimal.ROUND_HALF_EVEN),
                    comment
            );
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }

    }

    @Override
    public void setPreCheckResult(PreCheckResult<String> preCheckResult) {}
}
