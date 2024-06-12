package com.example.rptlvks.Repository;

import com.example.rptlvks.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByNidOrderBySortAsc(Long idx);

    @Modifying
    @Query("UPDATE Comment c SET c.sort = c.sort + 1 WHERE c.sort > :sort")
    Long commentUpdate(Long sort);

    @Query("SELECT MAX(c.sort) FROM Comment c WHERE nid = :nid")
    Optional<Comment> findByNid(Long nid);
}
