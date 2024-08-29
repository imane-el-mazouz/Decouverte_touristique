package com.tourist.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
    @RequestMapping("api/blog")
    @CrossOrigin(*)
    public class BlogController {

        @Autowired
        private BlogService blogService;

        @GetMapping("/tradition/{traditionId}")
        public ResponseEntity<List<Blog>> getBlogsByTraditionId(@PathVariable Long traditionId) {
            List<Blog> blogs = blogService.getBlogsByTraditionId(traditionId);
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        }

        @PostMapping("/add/{traditionId}")
        public ResponseEntity<Blog> addBlogToTradition(@PathVariable Long traditionId, @RequestBody Blog blog) {
            Blog newBlog = blogService.addBlogToTradition(traditionId, blog);
            return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
        }
    }

}
