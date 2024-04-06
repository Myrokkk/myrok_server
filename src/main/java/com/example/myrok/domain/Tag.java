package com.example.myrok.domain;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "tb_tag")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_tag SET is_deleted=true WHERE t_id=?")
@Where(clause = "is_deleted = false")
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

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    public Tag(String tagName, int count, boolean deleted) {
        this.tagName = tagName;
        this.count=count;
        this.deleted=deleted;
    }

    public void incrementCount() {
        this.count += 1;
    }

    public void decrementCount(){ this.count -= 1; }

    public void delete(){ this.deleted=true; }

}
