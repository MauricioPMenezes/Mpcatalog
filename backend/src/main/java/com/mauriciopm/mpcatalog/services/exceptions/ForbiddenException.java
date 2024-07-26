package com.mauriciopm.mpcatalog.services.exceptions;


public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg){
        super(msg);
    }
}

