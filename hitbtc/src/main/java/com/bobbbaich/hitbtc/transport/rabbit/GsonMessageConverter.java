package com.bobbbaich.hitbtc.transport.rabbit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.bobbbaich.hitbtc.config.UtilConfig.DATE_FORMAT;

@Slf4j
@Component
public class GsonMessageConverter extends AbstractJsonMessageConverter {

    private Gson gson = new GsonBuilder().create();

    private ObjectMapper jsonObjectMapper;
    private Jackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();

    /**
     * Construct with an internal {@link ObjectMapper} instance and trusted packed to all ({@code *}).
     *
     * @since 1.6.11
     */
    public GsonMessageConverter() {
        this("*");
    }

    /**
     * Construct with an internal {@link ObjectMapper} instance.
     * The {@link DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES} is set to false on
     * the {@link ObjectMapper}.
     *
     * @param trustedPackages the trusted Java packages for deserialization
     * @see DefaultJackson2JavaTypeMapper#setTrustedPackages(String...)
     */
    public GsonMessageConverter(String... trustedPackages) {
        this(new ObjectMapper(), trustedPackages);
        this.jsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        this.jsonObjectMapper.setDateFormat(dateFormat);
    }

    /**
     * Construct with the provided {@link ObjectMapper} instance.
     *
     * @param jsonObjectMapper the {@link ObjectMapper} to use.
     * @param trustedPackages  the trusted Java packages for deserialization
     * @see DefaultJackson2JavaTypeMapper#setTrustedPackages(String...)
     * @since 1.7.2
     */
    public GsonMessageConverter(ObjectMapper jsonObjectMapper, String... trustedPackages) {
        Assert.notNull(jsonObjectMapper, "'jsonObjectMapper' must not be null");
        this.jsonObjectMapper = jsonObjectMapper;
        ((DefaultJackson2JavaTypeMapper) this.javaTypeMapper).setTrustedPackages(trustedPackages);
    }

    @Override
    protected Message createMessage(Object objectToConvert, MessageProperties messageProperties) throws MessageConversionException {
        byte[] bytes;
        try {
            String jsonString = this.gson.toJson(objectToConvert);
            bytes = jsonString.getBytes(getDefaultCharset());
        } catch (IOException e) {
            throw new MessageConversionException("Failed to convert Message content", e);
        }
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(getDefaultCharset());
        messageProperties.setContentLength(bytes.length);

        if (getClassMapper() == null) {
            getJavaTypeMapper().fromJavaType(this.jsonObjectMapper.constructType(objectToConvert.getClass()),
                    messageProperties);
        } else {
            getClassMapper().fromClass(objectToConvert.getClass(),
                    messageProperties);
        }

        return new Message(bytes, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            if (contentType != null && contentType.contains("json")) {
                String encoding = properties.getContentEncoding();
                if (encoding == null) {
                    encoding = getDefaultCharset();
                }
                try {
                    if (getClassMapper() == null) {
                        JavaType targetJavaType = getJavaTypeMapper().toJavaType(message.getMessageProperties());
                        content = convertBytesToObject(message.getBody(), encoding, targetJavaType);
                    } else {
                        Class<?> targetClass = getClassMapper().toClass(message.getMessageProperties());
                        content = convertBytesToObject(message.getBody(), encoding, targetClass);
                    }
                } catch (IOException e) {
                    throw new MessageConversionException("Failed to convert Message content", e);
                }
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Could not convert incoming message with content-type [" + contentType + "]");
                }
            }
        }
        if (content == null) {
            content = message.getBody();
        }
        return content;
    }

    private Object convertBytesToObject(byte[] body, String encoding, JavaType targetJavaType) throws IOException {
        String contentAsString = new String(body, encoding);
        return this.jsonObjectMapper.readValue(contentAsString, targetJavaType);
    }

    private Object convertBytesToObject(byte[] body, String encoding, Class<?> targetClass) throws IOException {
        String contentAsString = new String(body, encoding);
        return this.jsonObjectMapper.readValue(contentAsString, this.jsonObjectMapper.constructType(targetClass));
    }

    private Jackson2JavaTypeMapper getJavaTypeMapper() {
        return this.javaTypeMapper;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
