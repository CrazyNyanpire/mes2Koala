package org.seu.acetec.mes2Koala.facade.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.stereotype.Component;

/**
 * @author harlow
 * @version v1.0
 * @description SpringMVC对时间的序列化支持
 * @date： 2015-3-31
 */
@Component
public class JsonDateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = dateFormat.format(date);

        gen.writeString(formattedDate);
    }//java.sql.Timestamp

}