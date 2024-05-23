package com.example.myrok.controller;

import com.example.myrok.domain.Record;
import com.example.myrok.dto.ClovaDto;
import com.example.myrok.dto.RecordDTO;
import com.example.myrok.dto.RecordResponseDTO;
import com.example.myrok.dto.RecordUpdateDTO;
import com.example.myrok.service.RecordService;
import com.example.myrok.service.openapi.RecordSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myrok")
public class RecordController {
    @Autowired
    private final RecordService recordService;
    private final RecordSummaryService recordSummaryService;

    // 회의록 작성 이동
    @GetMapping("/records")
    public void save(){}

    @PostMapping("/records")
    public ResponseEntity<Long> save( @RequestBody @Valid RecordDTO recordDTO){
        Record savedRecord=recordService.save(recordDTO);
        return new ResponseEntity<>(savedRecord.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/delete/{recordId}")
    public ResponseEntity<Record> delete(@PathVariable("recordId") Long id){
        recordService.deleteUpdate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/records/{recordId}")
    public ResponseEntity<Long> update( @PathVariable("recordId") Long recordId, @RequestBody @Valid RecordUpdateDTO recordUpdatedDTO){
        Record updatedRecord=recordService.update(recordId,recordUpdatedDTO);
        return new ResponseEntity<>(updatedRecord.getId(), HttpStatus.CREATED);
    }


    @Operation(
            summary = "회의록을 요약합니다.",
            description = "회의록을 요약합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "회의록이 요약 되었습니다."
    )
    @PostMapping("/record/summary")
    public ResponseEntity<Long> summary(@RequestBody ClovaDto.RecordRequestDto requestDto) {
        return new ResponseEntity<>(recordSummaryService.makeRecordSummary(requestDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "요약된 회의록을 가져옵니다.",
            description = "요약된 회의록을 가져옵니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "요약된 회의록을 가져왔습니다."
    )
    @GetMapping("/record/summary")
    public ResponseEntity<ClovaDto.ResponseDto> getSummary(Long recordId) {
        return new ResponseEntity<>(recordSummaryService.getSummaryRecord(recordId), HttpStatus.CREATED);
    }
}
