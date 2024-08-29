package com.tourist.service;

import com.tourist.exception.TraditionNotFoundException;
import com.tourist.model.Blog;
import com.tourist.model.Tradition;
import com.tourist.repository.BlogRepository;
import com.tourist.repository.TraditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final TraditionRepository traditionRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, TraditionRepository traditionRepository) {
        this.blogRepository = blogRepository;
        this.traditionRepository = traditionRepository;
    }

    public List<Blog> getBlogsByTraditionId(Long traditionId) {
        return blogRepository.findByTraditionId(traditionId);
    }

    public Blog addBlogToTradition(Long traditionId, Blog blog) {
        Tradition tradition = traditionRepository.findById(traditionId)
                .orElseThrow(() -> new TraditionNotFoundException(traditionId));
        blog.setTradition(tradition);
        return blogRepository.save(blog);
    }
}

