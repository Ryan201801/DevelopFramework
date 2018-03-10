package com.ryan.util;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

public class JaxbUtils {

    private JaxbUtils() {
    }

    public static <T> T unmarshal(String content, Class<T> outputType, URL schemaURL) {
        StringReader reader = new StringReader(content);
        return unmarshal(reader, outputType, schemaURL);
    }

    public static <T> T unmarshal(URL url, Class<T> outputType, URL schemaURL) {
        try {
            return unmarshal(new InputStreamReader(url.openStream()), outputType, schemaURL);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(Reader reader, Class<T> outputType, URL schemaURL) {
        try {
            JAXBContext context = JAXBContext.newInstance(outputType);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            if (schemaURL != null) {
                SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = sf.newSchema(schemaURL);
                unmarshaller.setSchema(schema);
            }

            return (T) unmarshaller.unmarshal(reader);
        } catch (Exception e) {

            return null;
        }
    }

    public static <T> String marshal(T t, Class<T> inputType) {
        try {
            JAXBContext context = JAXBContext.newInstance(inputType);
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(t, writer);
            return writer.toString();
        } catch (Exception e) {

            return null;
        }
    }

    public static <T> String marshalWithoutHeader(T t, Class<T> inputType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(inputType);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(t, writer);
        return writer.toString();
    }

}
