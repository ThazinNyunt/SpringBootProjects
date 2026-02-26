package com.example.mobilebroker.cache;

import com.example.mobilebroker.json.Ndc;
import com.example.mobilebroker.json.OperatorPrefix;
import com.example.mobilebroker.json.PrefixData;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Component
public class PrefixCache {

    @Getter
    private final Map<Integer, Ndc> ndcMap = new HashMap<>();
    @Getter
    private final Map<Integer, List<OperatorPrefix>> operatorPrefixMap = new HashMap<>();
    @Getter
    private final List<Ndc> sortedNdcList = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new ClassPathResource("prefix-data.json").getInputStream();
            PrefixData data = mapper.readValue(input, PrefixData.class);
            for (Ndc n : data.getNdc()) {
                ndcMap.put(n.getNdc(), n);
                sortedNdcList.add(n);
            }

            sortedNdcList.sort(new Comparator<Ndc>() {
                @Override
                public int compare(Ndc n1, Ndc n2) {
                    return Integer.compare(n2.getNdc().toString().length(),
                            n1.getNdc().toString().length());
                }
            });

            for (OperatorPrefix op : data.getOperatorPrefix()) {
                List<OperatorPrefix> list = operatorPrefixMap.get(op.getNdc());
                if(list == null) {
                    list = new ArrayList<>();
                    operatorPrefixMap.put(op.getNdc(), list);
                }
                list.add(op);
            }
            System.out.println("JSON Prefix cache loaded successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prefix-data.json");
        }


    }


}
