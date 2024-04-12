package kr.or.kpf.lms.common.converter;

import org.apache.commons.codec.binary.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if(attribute != null && attribute){ return "Y"; }
        else { return "N"; }
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        if(dbData != null && StringUtils.equals(dbData, "Y")){ return true; }
        else { return false; }
    }
}
