package com.innoveller.smsbroker.services.cache;

import com.innoveller.smsbroker.services.dtos.NdcInfo;
import com.innoveller.smsbroker.services.dtos.OperatorInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Component
public class PrefixCache {

    private final Map<Integer, NdcInfo> ndcMap = new HashMap<>();
    private final Map<Integer, List<OperatorInfo>> operatorPrefixMap = new HashMap<>();
    private final List<NdcInfo> sortedNdcInfoList = new ArrayList<>();

    public Map<Integer, NdcInfo> getNdcMap() {
        return ndcMap;
    }

    public Map<Integer, List<OperatorInfo>> getOperatorPrefixMap() {
        return operatorPrefixMap;
    }

    public List<NdcInfo> getSortedNdcList() {
        return sortedNdcInfoList;
    }

    @PostConstruct
    public void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream ndcInput = new ClassPathResource("ndc.json").getInputStream();
            List<NdcInfo> NdcInfoList = mapper.readValue(ndcInput, new TypeReference<List<NdcInfo>>() {});
            for (NdcInfo n : NdcInfoList) {
                ndcMap.put(n.ndc(), n);
                sortedNdcInfoList.add(n);
            }

            sortedNdcInfoList.sort(new Comparator<NdcInfo>() {
                @Override
                public int compare(NdcInfo n1, NdcInfo n2) {
                    return Integer.compare(
                            n2.ndc().toString().length(),
                            n1.ndc().toString().length());
                }
            });

            InputStream opInput = new ClassPathResource("operator-prefix.json").getInputStream();
            List<OperatorInfo> opList = mapper.readValue(opInput, new TypeReference<List<OperatorInfo>>() {});

            for (OperatorInfo op : opList) {
                List<OperatorInfo> list = operatorPrefixMap.get(op.ndc());
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
