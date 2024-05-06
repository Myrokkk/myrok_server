package com.example.myrok.controller;

import com.example.myrok.domain.Record;
import org.springframework.context.ApplicationEvent;

public class RecordSavedEvent {
    private Record record;

    public RecordSavedEvent(Record record) {
        this.record = record;
    }
    public Record getRecord() {
        return record;
    }
}
