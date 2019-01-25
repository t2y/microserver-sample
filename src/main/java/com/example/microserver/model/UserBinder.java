package com.example.microserver.model;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.example.microserver.ObjectMapperFactory;
import com.example.microserver.RootResourceConfig;
import com.example.microserver.filter.AuthFilter;
import com.example.microserver.filter.CORSFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(ObjectMapperFactory.class).to(ObjectMapper.class).in(Singleton.class);
        bind(RootResourceConfig.class).to(RootResourceConfig.class);
        bind(AuthFilter.class).to(AuthFilter.class);
        bind(CORSFilter.class).to(CORSFilter.class);
    }
}
