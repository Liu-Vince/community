package com.lwc.community.controller;

import com.lwc.community.entity.Comment;
import com.lwc.community.entity.DiscussPost;
import com.lwc.community.entity.Event;
import com.lwc.community.entity.Page;
import com.lwc.community.event.EventProducer;
import com.lwc.community.service.CommentService;
import com.lwc.community.service.DiscussPostService;
import com.lwc.community.util.CommunityConstant;
import com.lwc.community.util.HostHolder;
import com.lwc.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * @author 刘文长
 * @version 1.0
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/add/{discussPostId}", method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        commentService.addComment(comment);

        // 触发评论事件
        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId",discussPostId);
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }
        eventProducer.fireEvent(event);

        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            event = new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);
            eventProducer.fireEvent(event);
        }
        // 计算帖子的分数
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey, discussPostId);

        return "redirect:/discuss/detail/" + discussPostId;
    }


    @RequestMapping(path = "/myreply", method = RequestMethod.GET)
    public String getMyReplyPost(Model model, Page page) {
        int userId = hostHolder.getUser().getId();
        page.setRows(commentService.findCommentsRows(userId,1));
        page.setPath("/comment/myreply");

        List<Comment> list =
                commentService.findComments(userId,1,page.getOffset(), page.getLimit());
        List<Map<String, Object>> comments = new ArrayList<>();
        if (list != null) {
            for (Comment post : list) {
                Map<String, Object> map = new HashMap<>(16);
                String title = HtmlUtils.htmlUnescape(discussPostService.findDiscussPostById(post.getEntityId()).getTitle());
                String content = HtmlUtils.htmlUnescape(post.getContent());
                map.put("title",title);
                post.setContent(content);
                map.put("post", post);
                comments.add(map);
            }
        }
        model.addAttribute("commentCount", commentService.findCommentsRows(userId,1));
        model.addAttribute("comments", comments);



        return "site/my-reply";
    }
}
