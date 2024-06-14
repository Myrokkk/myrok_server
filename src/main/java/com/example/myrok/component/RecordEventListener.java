package com.example.myrok.component;


import com.example.myrok.domain.Record;
import com.example.myrok.dto.classtype.event.RecordSavedEvent;
import com.example.myrok.repository.RecordRepository;
import com.example.myrok.service.openAi.ChatCompletionService;
import com.zaxxer.hikari.util.IsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Component
public class RecordEventListener {

    private final ChatCompletionService chatCompletionService;
    private final RecordRepository recordRepository;

    @Autowired
    public RecordEventListener(ChatCompletionService chatCompletionService, RecordRepository recordRepository) {
        this.chatCompletionService = chatCompletionService;
        this.recordRepository = recordRepository;
    }

    @Async
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 1600) //여쭤보기 -> transaction 부모 트랜잭션과 별개로 작동해야한다 -> 새로운 트랜잭션 만들고
    //끝날때까지 기다리는게 아니라, 독립적으로 트랜잭션이 실행되는 대신, 트랜잭션이 실행하면, 이 테이블은 lock상태가 되어야하며 읽기만 가능해야한다
    public void handleRecordSavedEvent(RecordSavedEvent event) {
        // RecordSavedEvent를 수신하면 makeRecordSummary 메서드를 호출하여 두 번째 작업 수행
        Record record = event.getRecord();
        String recordContent = record.getRecordContent();
        String summary = chatCompletionService.chatCompletions(recordContent);
        if(!StringUtils.hasLength(summary)){
            throw new RuntimeException();
        }
        record.updateSummary(summary);
        recordRepository.save(record);
        // 그러나 이 경우 비동기 메서드에서 발생한 예외는 부모 트랜잭션을 롤백시키지 않습니다. 따라서 비동기 메서드 내부에서 예외 처리를 제대로 수행하고 적절한 롤백 메커니즘을 구현해야 합니다.
        //비동기 메서드는 동기 메서드와 달리 별도의 스레드에서 실행되므로 시스템의 성능 및 확장성을 고려해야 합니다. 비동기 처리가 많이 사용될 경우 스레드 풀의 크기를 적절하게 조정하고, 시스템 리소스 사용을 최적화해야 합니다.
    }
}
