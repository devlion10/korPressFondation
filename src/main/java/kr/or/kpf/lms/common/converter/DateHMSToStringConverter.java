package kr.or.kpf.lms.common.converter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Converter
public class DateHMSToStringConverter implements AttributeConverter<String, Date> {

    private static final Logger logger = LoggerFactory.getLogger(DateHMSToStringConverter.class);
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    /** String to Date */
    @Override
    public Date convertToDatabaseColumn(String attribute) {
        try {
            if(!StringUtils.isEmpty(attribute)) return format.parse(attribute);
            else return null;
        } catch (Exception e) {
            return new Date();
        }
    }
    /** Date to String */
    @Override
    public String convertToEntityAttribute(Date dbData) {
        try {
            if(dbData != null) return format.format(dbData);
            else return null;
        } catch (Exception e) {
            return format.format(new Date());
        }
    }
}
