package com.example.myrok.domain;

import jakarta.persistence.*;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_record_tag")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="UPDATE tb_record_tag SET is_deleted=true WHERE rt_id=?")
@Where(clause = "is_deleted = false")
public class RecordTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "r_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 영속성 설정, 안하면 sql 오류
    @JoinColumn(name = "t_id")
    private Tag tag;

    @Description("RecordTag 매핑 삭제")
    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = false;




}
