package com.example.rptlvks.Repository;

import com.example.rptlvks.Entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByIdx(Long idx);
    public Page<Notice> findAllByOrderByIdxDesc(PageRequest pageRequest);
}