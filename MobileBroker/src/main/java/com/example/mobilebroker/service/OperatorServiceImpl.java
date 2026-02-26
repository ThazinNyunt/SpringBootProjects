package com.example.mobilebroker.service;

import com.example.mobilebroker.cache.PrefixCache;
import com.example.mobilebroker.dto.OperatorResponse;
import com.example.mobilebroker.exception.InvalidPhoneNumberException;
import com.example.mobilebroker.json.Ndc;
import com.example.mobilebroker.json.OperatorPrefix;
import com.example.mobilebroker.model.Operator;
import com.example.mobilebroker.repository.OperatorRepository;
import com.example.mobilebroker.util.PhoneNumberNormalizer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperatorServiceImpl implements OperatorService{

    private final PrefixCache prefixCache;
    private final OperatorRepository operatorRepository;

    public OperatorServiceImpl(PrefixCache prefixCache, OperatorRepository operatorRepository) {
        this.prefixCache = prefixCache;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public OperatorResponse findOperator(String phoneNumber) {
        String normalized = PhoneNumberNormalizer.normalize(phoneNumber); //95254252784
        if(!normalized.startsWith("95")) {
            throw new InvalidPhoneNumberException("Invalid Myanmar Phone Number");
        }
        String nsn = normalized.substring(2); // 9254252784
        if (nsn.length() < 7 || nsn.length() > 10) {
            throw new InvalidPhoneNumberException("Invalid Phone Number");
        }
        Ndc matchedNdc = getMatchedNdc(nsn); // 9, Mobile

        String remaining = nsn.substring(matchedNdc.getNdc().toString().length()); // 254252784

        List<OperatorPrefix> operatorPrefixes = prefixCache.getOperatorPrefixMap().get(matchedNdc.getNdc()); // ndc=9 {}

        if(operatorPrefixes == null) {
            return new OperatorResponse(phoneNumber, "Operator Not Found", matchedNdc.getArea(), matchedNdc.getNumberType());
        }

        for(int length = 5; length >= 1 ; length--) {
            if (remaining.length() < length) {  // 25425<5 , 2542<3
                continue;
            }
            int prefix = Integer.parseInt(remaining.substring(0, length)); // 2542 254 25 2
            for (OperatorPrefix p : operatorPrefixes) {
                if (prefix >= p.getPrefixStart() && prefix <= p.getPrefixEnd()) {
                    Operator operator = operatorRepository.findById(p.getOperatorId()).orElse(null);
                    String operatorName;
                    if(operator != null) {
                        operatorName = operator.getOperatorId();
                    } else {
                        operatorName = "Operator Not Found";
                    }
                    return new OperatorResponse(phoneNumber, operatorName, matchedNdc.getArea(), matchedNdc.getNumberType());
                }
            }
        }
        return new OperatorResponse(phoneNumber,"Operator Not Found" , matchedNdc.getArea(), matchedNdc.getNumberType());

    }

    private Ndc getMatchedNdc(String nsn) {
        List<Ndc> ndcList = prefixCache.getSortedNdcList();
        for (Ndc ndc : ndcList) {
            if (nsn.startsWith(ndc.getNdc().toString())) {
                return ndc;
            }
        }
        throw new InvalidPhoneNumberException("NDC not found");
    }
}


