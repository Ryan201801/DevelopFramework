package com.ryan.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * JsonUtils is used to handle json object
 * <ul>
 * <li>Read JSON object from string
 * <li>Read JSON object from HTTP Connect Stream
 * <li>Write JSON object as string
 * </ul>
 * <p>
 * JsonUtils use Jackson ObjectMapper to serialize or deserialize JSON object;
 */
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Deserialize string text to Object which is user specify.
     * <p>
     * It will return JSON object properties map, the key is property name and value is JSON property value.
     * <p>
     * Do not use this method anymore, use {@link #readObject(String, Class)} to replace.
     * @param jsonString    JSON string
     * @return              <code>Map</code> if string can be deserialize to JSON object.
     * @exception RuntimeException if JSON string can not be parsed.
     */
    public static Map<String, Object> parseJson2Map(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize JSON object map to JSON string.
     * <p>
     * It will return JSON string which is the serialize result.
     * <p>
     * Do not use this method anymore, use {@link #writeObject(Object)} to replace.
     * @param jsonMap       JSON object map
     * @return              <code>String</code> if JSON object map can be serializable.
     * @exception           RuntimeException
     */
    public static String parseMap2Json(Map<String, Object> jsonMap) {
        try {
            return objectMapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get object from Http connection, the connection must contain a stream which can be read,
     * the method will read the stream and stream never use anymore after invoking.
     * <p>
     * It will return deserialized object.
     * <p>
     * Do not use this method anymore, use {@link #readObjectFromConnection(HttpURLConnection, Class)} to replace.
     *
     * @param connection    Http connection which including a readable stream
     * @param typeOfT       The type of deserialized object
     * @param <T>           This deserialized object type
     * @return              <code>Object</code> if http connection can be read.
     * @exception RuntimeException if the stream cannot be read or deserialize fail.
     * @see                 ConnectionUtils#readStringFromConnectionStream(HttpURLConnection)
     */
//    public static<T> T readObjectFromConnectionStream(HttpURLConnection connection, Type typeOfT) {
//        try {
//            String result = ConnectionUtils.readStringFromConnectionStream(connection);
//            return objectMapper.readValue(result, (TypeReference) typeOfT);
//        } catch (IOException e) {
//            imsLog.error(e);
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * Get object from Http connection, the connection must contain a stream which can be read,
     * the method will read the stream and stream never use anymore after invoking.
     * <p>
     * It will return deserialized object.
     *
     * @param connection    Http connection which including a readable stream
     * @param type          The type of deserialized object
     * @param <T>           This deserialized object type
     * @return              <code>Object</code> if http connection can be read.
     * @exception RuntimeException if the stream cannot be read or deserialize fail.
     * @see                 ConnectionUtils#readStringFromConnectionStream(HttpURLConnection)
     */
//    public static<T> T readObjectFromConnection(HttpURLConnection connection, Class<T> type) {
//        String result = null;
//        try {
//            result = ConnectionUtils.readStringFromConnectionStream(connection);
//            return readObject(result, type);
//        } catch (IOException e) {
//            imsLog.error(e);
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * Deserialize string text to Object which is user specify.
     * <p>
     * It will return deserialized object.
     * @param jsonString    JSON string
     * @param type          The type of deserialized object
     * @param <T>           This deserialized object type
     * @return              <code>Map</code> if string can be deserialize to JSON object.
     * @exception RuntimeException if the string cannot be read or deserialize fail.
     * @see                 ObjectMapper#readValue(String, Class)
     */
    public static<T> T readObject(String jsonString, Class<T> type) {
        try {
            return type.cast(objectMapper.readValue(jsonString, type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize string text to Object which is user specify.
     * <p>
     * It will return deserialized object.
     * @param jsonString    JSON string
     * @param type          The type of deserialized object
     * @param <T>           This deserialized object type
     * @return              <code>Map</code> if string can be deserialize to JSON object.
     * @exception RuntimeException if the string cannot be read or deserialize fail.
     * @see                 ObjectMapper#readValue(String, Class)
     */
    public static<T> T readObject(String jsonString, TypeReference type) {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize JSON object map to JSON string.
     * <p>
     * It will return JSON string which is the serialize result.
     * @param jsonObject       JSON object map
     * @return              <code>String</code> if JSON object map can be serializable.
     * @exception RuntimeException if the JSON object cannot be read or serialize fail.
     */
    public static String writeObject(Object jsonObject) {
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Serialize JSON object map to JSON string following XML annotation.
     * <p>
     * It will return JSON string which is the serialize result.
     * @param jsonObject       JSON object map
     * @return              <code>String</code> if JSON object map can be serializable.
     * @exception RuntimeException if the JSON object cannot be read or serialize fail.
     */
    public static String writeObjectWithAnnotation(Object jsonObject) {
        ObjectMapper objectMapper = new ObjectMapper();
        AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();
        objectMapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));
        try {
            return objectMapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
