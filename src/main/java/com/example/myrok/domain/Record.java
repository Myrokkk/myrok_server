package com.example.myrok.domain;

import jakarta.persistence.*;
import jdk.jfr.Description;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_record")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@SQLDelete(sql="UPDATE tb_record SET is_deleted=true WHERE r_id=?")
@Where(clause = "is_deleted = false")
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long id;

    @NonNull
    @Column(name = "record_name")
    private String recordName;

    @NonNull
    @Column(name = "record_date")
    private LocalDate recordDate;

    @NonNull
    @Column(name = "record_writer_id")
    private Long recordWriterId;

    @NonNull
    @Column(name = "record_content", columnDefinition = "TEXT")
    private String recordContent;

    @Description("삭제한 record")
    @Column(name = "is_deleted")
    @Builder.Default
    private Boolean deleted = false;

    @Description("해당 프로젝트에 참여하는 멤버리스트")
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<Member> memberList;

    @Description("해당 프로젝트에 포함된 태그리스트")
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<Tag> tagList;

    @JsonBackReference
    @Description("어떤 프로젝트의 회의록인지")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Project project;


}
