package com.ryan.config;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.ReaderListener;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import io.swagger.models.auth.BasicAuthDefinition;

import java.util.Arrays;


@SwaggerDefinition
public class SwaggerAuthListener implements ReaderListener {

    @Override
    public void beforeScan(Reader reader, Swagger swagger) {

    }

    @Override
    public void afterScan(Reader reader, Swagger swagger) {
        Info info = new Info();
        info.description("description");
        info.setVersion("v3.0");
        Contact contact = new Contact();
        contact.setName("Direct&Digital");
        info.setContact(contact);
        swagger.info(info);
        swagger.schemes(Arrays.asList(Scheme.forValue("http"), Scheme.forValue("https")));
        BasicAuthDefinition basicAuthDefinition = new BasicAuthDefinition();
        swagger.addSecurityDefinition("basic", basicAuthDefinition);
    }
}
