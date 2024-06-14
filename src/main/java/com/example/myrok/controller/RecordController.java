package com.example.myrok.controller;

import com.example.myrok.domain.Record;
import com.example.myrok.dto.RecordDeleteDTO;
import com.example.myrok.dto.RecordResponseDTO;
import com.example.myrok.dto.pagination.PageRequestDto;
import com.example.myrok.dto.pagination.PageResponseDto;
import com.example.myrok.dto.recordtype.RecordDTO;
import com.example.myrok.dto.RecordUpdateDTO;
import com.example.myrok.service.RecordService;
import com.example.myrok.service.openAi.ChatCompletionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myrok")
public class RecordController {
    private final RecordService recordService;
    private final ChatCompletionService chatCompletionService;
    // 회의록 작성 이동
    @GetMapping("/records")
    public void save(){}


    @PostMapping("/records")
    public ResponseEntity<Long> save( @RequestBody @Valid RecordDTO recordDTO){
        Record savedRecord=recordService.save(recordDTO);
        return new ResponseEntity<>(savedRecord.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/delete/{recordId}")
    public ResponseEntity<Record> delete(@PathVariable("recordId") Long recordId, @RequestBody @Valid RecordDeleteDTO recordDeleteDTO){
        Long recordWriterId = recordDeleteDTO.recordWriterId();
        recordService.deleteUpdate(recordId,recordWriterId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "프로젝트 회의록 목록을 가져옵니다",
            description = "프로젝트 회의록 목록을 가져옵니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 회의록 목록을 가져왔습니다."
    )
    @GetMapping("/list")
    public ResponseEntity<List<com.example.myrok.dto.classtype.RecordDTO.RecordListObject>> getRecordList(Long projectId){
        return new ResponseEntity<>(recordService.getRecords(projectId), HttpStatus.OK);
    }
    @Operation(
            summary = "제목 혹은 태그명에 따른 회의록 목록을 가져옵니다.",
            description = "제목 혹은 태그명에 따른 회의록 목록을 가져옵니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "제목 혹은 태그명에 따른 회의록 목록을 가져옵니다."
    )
    @GetMapping("/{projectId}/list")
    public ResponseEntity<List<com.example.myrok.dto.classtype.RecordDTO.RecordListObject>> getRecordsByProjectNameOrTagName(@PathVariable Long projectId, @RequestParam(value = "recordName", required = false) String recordName, @RequestParam(value = "tagName", required = false) String tagName){
        return new ResponseEntity<>(recordService.getRecordsBySearch(recordName, tagName, projectId), HttpStatus.OK);
    }

    @Operation(
            summary = "프로젝트 회의록 목록을 페이징 처리하여 가져옵니다",
            description = "프로젝트 회의록 목록을 페이징 처리하여 가져옵니다"
    )
    @ApiResponse(
            responseCode = "200",
            description = "프로젝트 회의록 목록을 페이징 처리하여 가져옵니다."
    )
    @GetMapping("/list/pagination")
    public ResponseEntity<PageResponseDto<com.example.myrok.dto.classtype.RecordDTO.RecordListObject>> getRecordsPagination(PageRequestDto pageRequestDto, Long projectId){
        return new ResponseEntity<>(recordService.getRecords(pageRequestDto, projectId), HttpStatus.OK);
    }

    @Operation(
            summary = "제목 혹은 태그명에 따른 회의록 목록을 페이징 처리하여 가져옵니다.",
            description = "제목 혹은 태그명에 따른 회의록 목록을 페이징 처리하여 가져옵니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "제목 혹은 태그명에 따른 회의록 목록을 페이징 처리하여 가져옵니다."
    )
    @GetMapping("/{projectId}/list/pagination")
    public ResponseEntity<PageResponseDto<com.example.myrok.dto.classtype.RecordDTO.RecordListObject>> getRecordsPaginationByProjectNameOrTagName(PageRequestDto pageRequestDto, @PathVariable Long projectId, @RequestParam(value = "recordName", required = false) String recordName, @RequestParam(value = "tagName", required = false) String tagName){
        return new ResponseEntity<>(recordService.getRecordsBySearch(pageRequestDto, recordName, tagName, projectId), HttpStatus.OK);
    }

    @PatchMapping("/records/{recordId}")
    public ResponseEntity<Long> update( @PathVariable("recordId") Long recordId, @RequestBody @Valid RecordUpdateDTO recordUpdatedDTO){
        Record updatedRecord=recordService.update(recordId,recordUpdatedDTO);
        return new ResponseEntity<>(updatedRecord.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/records/{recordId}")
    public ResponseEntity<RecordResponseDTO> get(@PathVariable("recordId") Long recordId){
        RecordResponseDTO readRecord = recordService.read(recordId);
        return new ResponseEntity<>(readRecord, HttpStatus.OK);
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "q")String question){
        if(!StringUtils.hasLength(question)){
            throw new RuntimeException();
        }
        return chatCompletionService.chatCompletions(question);
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
    public ResponseEntity<com.example.myrok.dto.classtype.RecordDTO.ResponseDTO> getSummary(Long recordId) {
        return new ResponseEntity<>(recordService.getRecordSummary(recordId), HttpStatus.CREATED);
    }

}
