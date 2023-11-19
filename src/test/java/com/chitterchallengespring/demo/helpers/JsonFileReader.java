package com.chitterchallengespring.demo.helpers;

import com.chitterchallengespring.demo.model.Peep;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {

    public static List<Peep> fileToObjectList() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Peep> peeps = objectMapper.readValue(JsonFileReader.class.getResourceAsStream("/data.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Peep.class));
            return peeps;
        }
        catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
