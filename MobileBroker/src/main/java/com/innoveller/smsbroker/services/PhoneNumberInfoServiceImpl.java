package com.innoveller.smsbroker.services;

import com.innoveller.smsbroker.services.cache.PrefixCache;
import com.innoveller.smsbroker.exceptions.PhoneNumberInfoLookupError;
import com.innoveller.smsbroker.services.data.PhonePrefix;
import com.innoveller.smsbroker.services.data.OperatorPrefix;
import com.innoveller.smsbroker.entities.Operator;
import com.innoveller.smsbroker.repositories.OperatorRepository;
import com.innoveller.smsbroker.services.dtos.NdcInfo;
import com.innoveller.smsbroker.services.dtos.PhoneNumberInfo;
import com.innoveller.smsbroker.utils.PhoneNumberNormalizer;
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

        Either<PhoneNumberInfoLookupError, String> nsnResult = PhoneNumberNormalizer.getNationalSignificantNumber(phoneNumber);
        if(nsnResult.isLeft()) {
            return Either.left(nsnResult.getLeft());
        }
        String nsn = nsnResult.get();

        PhonePrefix matchedPhonePrefix = null;
        for(PhonePrefix phonePrefix : prefixCache.getSortedNdcList()) {
            if(nsn.startsWith(phonePrefix.ndc().toString())) {
                matchedPhonePrefix = phonePrefix;
                break;
            }
        }
        if(matchedPhonePrefix == null) {
            return Either.left(new PhoneNumberInfoLookupError.NdcNotFound(phoneNumber));
        }

        String remaining = nsn.substring(matchedPhonePrefix.ndc().toString().length()); // 254252784
        List<OperatorPrefix> operatorPrefixes = prefixCache.getOperatorPrefixMap().get(matchedPhonePrefix.ndc()); // ndc=9 {}
        NdcInfo ndcInfo = new NdcInfo(
                matchedPhonePrefix.ndc(),
                matchedPhonePrefix.serviceArea(),
                matchedPhonePrefix.numberType()
        );
        if(operatorPrefixes == null || operatorPrefixes.isEmpty()) {
            return Either.right(new PhoneNumberInfo("Geographic numbers", ndcInfo));
        }

        for(int length = 5; length >=1 ; length--) {
            if(remaining.length() < length) {
                continue;
            }
            String prefix = remaining.substring(0, length); // 25425, 2542, 254, 25, 2
            for(OperatorPrefix p : operatorPrefixes) {
                String prefixStart = String.valueOf(p.prefixStart());
                String prefixEnd = String.valueOf(p.prefixEnd());
                if(prefix.compareTo(prefixStart) >= 0 && prefix.compareTo(prefixEnd) <= 0) {
                    Optional<Operator> operator = operatorRepository.findById(p.operator());
                    if(operator.isPresent()) {
                        return Either.right(new PhoneNumberInfo(operator.get().getOperatorId(), ndcInfo));
                    }
                }
            }
        }
        return Either.left(new PhoneNumberInfoLookupError.OperatorNotFound(phoneNumber));

    }
}


