package com.mauriciopm.mpcatalog.controllers.customerror;

public class FieldMessage {

    private String fieldName;
    private String fieldMessage;

    public FieldMessage(String fieldName, String fieldMessage) {
        this.fieldName = fieldName;
        this.fieldMessage = fieldMessage;
    }

    public String getFieldMessage() {
        return fieldMessage;
    }

    public String getFieldName() {
        return fieldName;
    }
}
