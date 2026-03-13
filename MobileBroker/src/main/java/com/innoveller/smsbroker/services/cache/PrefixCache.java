package com.innoveller.smsbroker.services.cache;

import com.innoveller.smsbroker.services.data.PhonePrefix;
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

    private final Map<Integer, PhonePrefix> ndcMap = new HashMap<>();
    private final Map<Integer, List<OperatorPrefix>> operatorPrefixMap = new HashMap<>();
    private final List<PhonePrefix> sortedPhonePrefixList = new ArrayList<>();

    public Map<Integer, PhonePrefix> getNdcMap() {
        return ndcMap;
    }

    public Map<Integer, List<OperatorPrefix>> getOperatorPrefixMap() {
        return operatorPrefixMap;
    }

    public List<PhonePrefix> getSortedNdcList() {
        return sortedPhonePrefixList;
    }

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream ndcInput = new ClassPathResource("ndc.json").getInputStream();
            List<PhonePrefix> phonePrefixList = mapper.readValue(ndcInput, new TypeReference<List<PhonePrefix>>() {});
            for (PhonePrefix n : phonePrefixList) {
                ndcMap.put(n.ndc(), n);
                sortedPhonePrefixList.add(n);
            }

            sortedPhonePrefixList.sort(new Comparator<PhonePrefix>() {
                @Override
                public int compare(PhonePrefix n1, PhonePrefix n2) {
                    return Integer.compare(
                            n2.ndc().toString().length(),
                            n1.ndc().toString().length());
                }
            });

            InputStream opInput = new ClassPathResource("operator-prefix.json").getInputStream();
            List<OperatorPrefix> opList = mapper.readValue(opInput, new TypeReference<List<OperatorPrefix>>() {});

            for (OperatorPrefix op : opList) {
                List<OperatorPrefix> list = operatorPrefixMap.get(op.ndc());
                if(list == null) {
                    list = new ArrayList<>();
                    operatorPrefixMap.put(op.ndc(), list);
                }
                list.add(op);
            }
            System.out.println("JSON Prefix cache loaded successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prefix-data.json");
        }


    }


}
