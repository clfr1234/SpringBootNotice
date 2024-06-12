package com.example.rptlvks.Service;

import com.example.rptlvks.Entity.Notice;
import com.example.rptlvks.Entity.Comment;
import com.example.rptlvks.Repository.NoticeRepository;
import com.example.rptlvks.Repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private CommentRepository commentRepository;


    public Notice noticewrite_setting(Notice notice, String id, String name) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        notice.setDate(timestamp);
        notice.setId(id);
        notice.setName(name);

        System.out.println(notice.getId());
        System.out.println(notice.getName());
        System.out.println(notice.getDate());

        return noticeRepository.save(notice);
    }

    public Notice notice_search(Long idx) {
        Optional<Notice> noticeOptional = noticeRepository.findByIdx(idx);

        if(noticeOptional.isPresent()) {
            Notice resNotice = noticeOptional.get();

            Notice authNotice = Notice.builder()
                    .idx(resNotice.getIdx())
                    .name(resNotice.getName())
                    .id(resNotice.getId())
                    .noticeTitle(resNotice.getNoticeTitle())
                    .content(resNotice.getContent())
                    .date(resNotice.getDate())
                    .build();
            return authNotice;
        }
        return null;
    }

    public String noticeDelete(Long idx) {
        noticeRepository.deleteById(idx);
        return "success";
    }

    public void noticeUpdate(Notice notice) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);

        Notice data = notice_search(notice.getIdx());
        notice.setName(data.getName());
        notice.setId(data.getId());
        notice.setDate(timestamp);

        noticeRepository.save(notice);
    }

    public Page<Notice> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        Page<Notice> notice = noticeRepository.findAllByOrderByIdxDesc(PageRequest.of(page, pageLimit));
        return notice;
    }

    public Comment commentSearch(Long idx){
        Comment commentResult = commentRepository.findByNidOrderBySortAsc(idx);
        return commentResult;
    }

    public void saveComment(Comment comment) {
        if(comment.getSort() == 0) {
            Optional<Comment> checkSort = commentRepository.findByNid(comment.getNid());
            if (checkSort.isPresent()) {
                comment.setSort(checkSort.get().getSort() + 1);
            }
        }
        commentRepository.commentUpdate(comment.getSort());
        commentRepository.save(comment);
    }
}