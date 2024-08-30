package com.tourist.controller;

import com.tourist.model.Blog;
import com.tourist.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog")
@CrossOrigin("*")
public class BlogController {
    private final BlogService blogService ;

    public BlogController (BlogService blogService) {
        this.blogService= blogService;
    }

    @GetMapping("/tradition/{traditionId}")
    @PreAuthorize("hasRole('Admin') or hasRole('Client')")
    public ResponseEntity<List<Blog>> getBlogsByTraditionId(@PathVariable Long traditionId) {
        List<Blog> blogs = blogService.getBlogsByTraditionId(traditionId);
        return ResponseEntity.ok(blogs);
    }

    @PostMapping("/add/{traditionId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Blog> addBlogToTradition(@PathVariable Long traditionId, @RequestBody Blog blog) {
        Blog newBlog = blogService.addBlogToTradition(traditionId, blog);
        return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
    }


}
