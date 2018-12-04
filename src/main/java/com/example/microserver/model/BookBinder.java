package com.example.microserver.model;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.example.microserver.ObjectMapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(ObjectMapperFactory.class).to(ObjectMapper.class).in(Singleton.class);
    }
}