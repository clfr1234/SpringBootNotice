package com.example.rptlvks.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@Table(name = "notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    private String id;
    @Column(name = "title")
    private String noticeTitle;
    private String content;
    private Timestamp date;
}

