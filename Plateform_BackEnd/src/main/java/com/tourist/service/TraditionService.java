package com.tourist.service;

import com.tourist.dto.BlogDTO;
import com.tourist.dto.TraditionDTO;
import com.tourist.model.Blog;
import com.tourist.model.Tradition;
import com.tourist.repository.BlogRepository;
import com.tourist.repository.TraditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraditionService {
    private final TraditionRepository traditionRepository;
    private final BlogRepository blogRepository;
    public TraditionService (TraditionRepository traditionRepository , BlogRepository blogRepository){
        this.traditionRepository = traditionRepository;
        this.blogRepository = blogRepository;
    }

    public TraditionDTO createTraditionWithBlogs(TraditionDTO traditionDTO) {
        if (traditionDTO.getBlogs() == null) {
            traditionDTO.setBlogs(new ArrayList<>());
        }
        Tradition tradition = new Tradition();
        tradition.setCity(traditionDTO.getCity());
        tradition.setDescription(traditionDTO.getDescription());
        Tradition savedTradition = traditionRepository.save(tradition);
        for (Blog blogDTO : traditionDTO.getBlogs()) {
            Blog blog = new Blog();
            blog.setName(blogDTO.getName());
            blog.setDescription(blogDTO.getDescription());
            blog.setTradition(savedTradition);

            blogRepository.save(blog);
        }
        traditionDTO.setId(savedTradition.getId());
        return traditionDTO;
    }
    public List<Tradition> listTraditions() {
        return traditionRepository.findAll();
    }
}
