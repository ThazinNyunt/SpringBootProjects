package com.innoveller.smsbroker.services.cache;

import com.innoveller.smsbroker.services.data.Ndc;
import com.innoveller.smsbroker.services.data.OperatorPrefix;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Component
public class PrefixCache {

    private final Map<Integer, Ndc> ndcMap = new HashMap<>();
    private final Map<Integer, List<OperatorPrefix>> operatorPrefixMap = new HashMap<>();
    private final List<Ndc> sortedNdcList = new ArrayList<>();

    public Map<Integer, Ndc> getNdcMap() {
        return ndcMap;
    }

    public Map<Integer, List<OperatorPrefix>> getOperatorPrefixMap() {
        return operatorPrefixMap;
    }

    public List<Ndc> getSortedNdcList() {
        return sortedNdcList;
    }

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream ndcInput = new ClassPathResource("ndc.json").getInputStream();
            List<Ndc> ndcList = mapper.readValue(ndcInput, new TypeReference<List<Ndc>>() {});
            for (Ndc n : ndcList) {
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

            InputStream opInput = new ClassPathResource("operator-prefix.json").getInputStream();
            List<OperatorPrefix> opList = mapper.readValue(opInput, new TypeReference<List<OperatorPrefix>>() {});

            for (OperatorPrefix op : opList) {
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
