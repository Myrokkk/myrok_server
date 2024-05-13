package com.example.myrok.domain;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_tag")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Description("삭제한 tag")
    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = false;

    private int count;


    public Tag(String tagName, int count, boolean deleted) {
        this.tagName = tagName;
        this.count=count;
        this.deleted=deleted;
    }

    public void incrementCount() {
        this.count += 1;
    }

    public void decrementCount(){
        if (this.count <= 0) {
            throw new IllegalArgumentException("태그를 삭제할 수 없습니다.");
        }
        this.count -= 1;
    }

    public void delete(){ this.deleted=true; }

}