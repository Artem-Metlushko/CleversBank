package com.clever.bank.util;

public class UuidWrapperMock extends UuidWrapper {

    private String UUID_DEFAULT;

    public UuidWrapperMock(String UUID_DEFAULT) {
        this.UUID_DEFAULT = UUID_DEFAULT;
    }

    public UuidWrapperMock() {

    }

    public static UuidWrapperMock getInstance(String UUID_DEFAULT) {
        return new UuidWrapperMock(UUID_DEFAULT);
    }

    @Override
    public String randomUUID() {
        return UUID_DEFAULT;
    }



}