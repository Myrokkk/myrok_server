package com.example.myrok.event;

import com.example.myrok.domain.Record;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RecordSavedEvent {
    private Record record;

    public RecordSavedEvent(Record record) {
        this.record = record;
    }
}
