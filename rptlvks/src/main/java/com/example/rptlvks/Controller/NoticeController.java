package com.example.rptlvks.Controller;

import com.example.rptlvks.Entity.Notice;
import com.example.rptlvks.Entity.Comment;
import com.example.rptlvks.Entity.User;
import com.example.rptlvks.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;



    @GetMapping("/goWrite")
    public String goWrite() {
        return "redirect:/writePage";
    }

    @GetMapping("/writePage")
    public String writePage() {
        return "writePage";
    }

    @PostMapping("/writeSend")
    public String writeSend(@ModelAttribute Notice notice, @AuthenticationPrincipal User user) {
        String id = user.getId();
        String name = user.getName();

        noticeService.noticewrite_setting(notice, id, name);
        return "redirect:/";
    }

    @GetMapping("/noticeIndexCheck")
    public String noticeIndexCheck(@RequestParam("checkIdx") Long checkIdx, Model model, @AuthenticationPrincipal User user) {
        Notice res = noticeService.notice_search(checkIdx);
        if(user == null) {
            model.addAttribute("userdata", "none");
        } else {
            model.addAttribute("userdata",user);
        }
        model.addAttribute("result",res);
        return "showNotice";
    }

    @PostMapping("/deleteNoticeIdxCheck")
    public String deleteNoticeIdxCheck(@RequestParam Long idx) {
        noticeService.noticeDelete(idx);
        return "redirect:/";
    }

    @PostMapping("/noticeEdit")
    public String noticeEdit(@RequestParam Long idx, Model model) {
        Notice notice = noticeService.notice_search(idx);
        model.addAttribute("notice", notice);
        return "editPage";
    }

    @PostMapping("/goEditNotice")
    public String goEditNotice(@ModelAttribute Notice notice) {
        noticeService.noticeUpdate(notice);
        return "redirect:/noticeIndexCheck?checkIdx=" + notice.getIdx();
    }

    @PostMapping("/replyAdd")
    public String replyAdd(Long nid, Long sort, Long margin, String comment, @AuthenticationPrincipal User writeUser) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Comment comm = Comment.builder()
                        .nid(nid)
                        .id(writeUser.getId())
                        .name(writeUser.getUsername())
                        .comment(comment)
                        .date(timestamp)
                        .sort(sort)
                        .margin(margin)
                        .build();

        noticeService.saveComment(comm);

        return "redirect:/noticeIndexCheck?checkIdx=" + nid;
    }
}