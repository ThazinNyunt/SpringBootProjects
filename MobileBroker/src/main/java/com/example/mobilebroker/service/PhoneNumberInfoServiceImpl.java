package com.example.mobilebroker.service;

import com.example.mobilebroker.cache.PrefixCache;
import com.example.mobilebroker.controller.api.dtos.PhoneNumberInfoResponse;
import com.example.mobilebroker.error.PhoneNumberInfoLookupError;
import com.example.mobilebroker.json.Ndc;
import com.example.mobilebroker.json.OperatorPrefix;
import com.example.mobilebroker.model.Operator;
import com.example.mobilebroker.repository.OperatorRepository;
import com.example.mobilebroker.service.dtos.PhoneNumberInfo;
import com.example.mobilebroker.util.PhoneNumberNormalizer;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PhoneNumberInfoServiceImpl implements PhoneNumberInfoService {

    private final PrefixCache prefixCache;
    private final OperatorRepository operatorRepository;

    public PhoneNumberInfoServiceImpl(PrefixCache prefixCache, OperatorRepository operatorRepository) {
        this.prefixCache = prefixCache;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public Either<PhoneNumberInfoLookupError, PhoneNumberInfo> findOperator(String phoneNumber) {
        String nsn = PhoneNumberNormalizer.normalizeToNsn(phoneNumber); // 9254252784
        Ndc matchedNdc = null;
        for(Ndc ndc : prefixCache.getSortedNdcList()) {
            if(nsn.startsWith(ndc.getNdc().toString())) {
                matchedNdc = ndc;
                break;
            }
        }
        if(matchedNdc == null) {
            return Either.left(new PhoneNumberInfoLookupError.NdcNotFound(phoneNumber));
        }

        String remaining = nsn.substring(matchedNdc.getNdc().toString().length()); // 254252784
        List<OperatorPrefix> operatorPrefixes = prefixCache.getOperatorPrefixMap().get(matchedNdc.getNdc()); // ndc=9 {}
        if(operatorPrefixes == null || operatorPrefixes.isEmpty()) {
            //return Either.left(new PhoneNumberInfoLookupError.OperatorNotFound(phoneNumber));
            return Either.right(new PhoneNumberInfoResponse(phoneNumber, "Operator Not Found", matchedNdc.getArea(), matchedNdc.getNumberType()));
        }

        for(int length = 5; length >=1 ; length--) {
            if(remaining.length() < length) {
                continue;
            }
            String prefix = remaining.substring(0, length); // 25425, 2542, 254, 25, 2
            for(OperatorPrefix p : operatorPrefixes) {
                String prefixStart = String.valueOf(p.getPrefixStart());
                String prefixEnd = String.valueOf(p.getPrefixEnd());
                if(prefix.compareTo(prefixStart) >= 0 && prefix.compareTo(prefixEnd) <= 0) {
                    Optional<Operator> operator = operatorRepository.findById(p.getOperatorId());
                    if(operator.isPresent()) {
                        return Either.right(new PhoneNumberInfoResponse(phoneNumber, operator.get().getOperatorId(), matchedNdc.getArea(), matchedNdc.getNumberType()));
                    }
                    return Either.left(new PhoneNumberInfoLookupError.OperatorNotFound(phoneNumber));
                }
            }
        }
        return Either.left(new PhoneNumberInfoLookupError.OperatorNotFound(phoneNumber));

    }







   /* @Override
    public Either<PhoneNumberInfoLookupError, PhoneNumberInfoResponse> findOperator(String phoneNumber) {

        String nsn = PhoneNumberNormalizer.normalizeToNsn(phoneNumber); // 9254252784

        Ndc matchedNdc = getMatchedNdc(nsn); // 9, Mobile

        if (matchedNdc == null) { // ndc = 0
            return Either.left(new PhoneNumberInfoLookupError.NdcNotFound(phoneNumber));
        }

        return Either.right(resolveOperator(phoneNumber, nsn, matchedNdc));
    }

    private PhoneNumberInfoResponse resolveOperator(String phoneNumber, String nsn, Ndc ndc) {
        String remaining = nsn.substring(ndc.getNdc().toString().length()); // 254252784

        if (remaining.startsWith("0")) { // 090
            return new PhoneNumberInfoResponse(phoneNumber,"Operator Not Found",ndc.getArea(),ndc.getNumberType());
        }

        List<OperatorPrefix> operatorPrefixes = prefixCache.getOperatorPrefixMap().get(ndc.getNdc()); // ndc=9 {}

        if(operatorPrefixes == null) {
            return new PhoneNumberInfoResponse(phoneNumber, "Operator Not Found", ndc.getArea(), ndc.getNumberType());
        }

        String operatorName = matchPrefix(remaining, operatorPrefixes);
        return new PhoneNumberInfoResponse(phoneNumber, operatorName, ndc.getArea(), ndc.getNumberType());
    }

    private String matchPrefix(String remaining, List<OperatorPrefix> prefixes) {
        for(int length = 5; length >= 1 ; length--) {
            if (remaining.length() < length) {  // 25425<5 , 2542<3
                continue;
            }
            int prefix = Integer.parseInt(remaining.substring(0, length)); // 25425 2542 254 25
            for (OperatorPrefix p : prefixes) {
                if (prefix >= p.getPrefixStart() && prefix <= p.getPrefixEnd()) {
                    Optional<Operator> operator = operatorRepository.findById(p.getOperatorId());
                    if(operator.isPresent()) {
                        return operator.get().getOperatorId();
                    }
                    return "Operator Not Found";
                }
            }
        }
        return "Operator Not Found";
    }

    private Ndc getMatchedNdc(String nsn) {
        for(Ndc ndc: prefixCache.getSortedNdcList()) {
            if(nsn.startsWith(ndc.getNdc().toString())) {
                return ndc;
            }
        }
        return null;
    }*/

//    @Override
//    public Optional<PhoneNumberInfoResponse> findOperator(String phoneNumber) {
//
//        String nsn = PhoneNumberNormalizer.normalizeToNsn(phoneNumber); // 9254252784
//
//        Ndc matchedNdc = getMatchedNdc(nsn); // 9, Mobile
//
//        if (matchedNdc == null) {
//            return Optional.empty();
//        }
//
//        String remaining = nsn.substring(matchedNdc.getNdc().toString().length()); // 254252784
//
//        if (remaining.startsWith("0")) {
//            return Optional.of(new PhoneNumberInfoResponse(phoneNumber,"Invalid Number",matchedNdc.getArea(),matchedNdc.getNumberType()
//            ));
//        }
//
//        List<OperatorPrefix> operatorPrefixes = prefixCache.getOperatorPrefixMap().get(matchedNdc.getNdc()); // ndc=9 {}
//
//        if(operatorPrefixes == null) {
//            return Optional.of(new PhoneNumberInfoResponse(phoneNumber, "Operator Not Found", matchedNdc.getArea(), matchedNdc.getNumberType()));
//        }
//
//        for(int length = 5; length >= 1 ; length--) {
//            if (remaining.length() < length) {  // 25425<5 , 2542<3
//                continue;
//            }
//            int prefix = Integer.parseInt(remaining.substring(0, length)); // 25425 2542 254 25
//            for (OperatorPrefix p : operatorPrefixes) {
//                if (prefix >= p.getPrefixStart() && prefix <= p.getPrefixEnd()) {
//                    Operator operator = operatorRepository.findById(p.getOperatorId()).orElse(null);
//                    String operatorName;
//                    if(operator != null) {
//                        operatorName = operator.getOperatorId();
//                    } else {
//                        operatorName = "Operator Not Found";
//                    }
//                    return Optional.of(new PhoneNumberInfoResponse(phoneNumber, operatorName, matchedNdc.getArea(), matchedNdc.getNumberType()));
//                }
//            }
//        }
//        return Optional.of(new PhoneNumberInfoResponse(phoneNumber, "Operator Not Found", matchedNdc.getArea(), matchedNdc.getNumberType()));
//
//    }
//
//    private Ndc getMatchedNdc(String nsn) {
//        List<Ndc> ndcList = prefixCache.getSortedNdcList();
//        for (Ndc ndc : ndcList) {
//            if (nsn.startsWith(ndc.getNdc().toString())) {
//                return ndc;
//            }
//        }
//        return null;
//    }
}


