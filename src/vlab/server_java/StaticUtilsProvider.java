package vlab.server_java;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;

// По хорошему надо превратить в Bean, но я не хочу ковыряться в xml-конфигах спринга
public class StaticUtilsProvider {

    private static final ObjectMapper _mapper = new ObjectMapper();
    private static final Random _random = new Random();

    static {
        _mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    }
    private StaticUtilsProvider() {

    }

    public static ObjectMapper getMapperInstance() {
        return _mapper;
    }

    public static Random getRandomInstance() {
        return _random;
    }

}
