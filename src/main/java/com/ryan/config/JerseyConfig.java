/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ryan.config;
import com.ryan.exception.mapper.AuthExceptionMapper;
import com.ryan.exception.mapper.InternalServerExceptionMapper;
import com.ryan.exception.mapper.ResourceNotFoundExceptionMapper;
import com.ryan.log.LoggingFilter;
import com.ryan.log.TraceIdContainerFilter;
import com.ryan.security.AuthClientHeaderFilter;
import com.ryan.security.CORSResponseFilter;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.ryan.controller.*;
import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerControllers();
        registerExceptionMapper();
        registerFilter();
        registerSwagger();
    }

    private void registerExceptionMapper() {
        register(JacksonJaxbJsonProvider.class);
    }

    private void registerControllers() {
        register(UserController.class);
    }

    private void registerFilter() {
        register(AuthExceptionMapper.class);
        register(ResourceNotFoundExceptionMapper.class);
        register(InternalServerExceptionMapper.class);
        register(AuthClientHeaderFilter.class);
        register(CORSResponseFilter.class);
        register(TraceIdContainerFilter.class);
        register(LoggingFilter.class);
    }


    private void registerSwagger() {
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        BeanConfig config = new BeanConfig();
        config.setConfigId("phoenix-backend-doc");
        config.setSchemes(new String[]{"http", "https"});
        config.setBasePath("/api/allianz/phoenix");
        config.setResourcePackage("com.ryan");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
