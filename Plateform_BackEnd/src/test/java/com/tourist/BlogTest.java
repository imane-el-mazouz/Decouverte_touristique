package com.tourist;

import com.tourist.exception.TraditionNotFoundException;
import com.tourist.model.Blog;
import com.tourist.model.Tradition;
import com.tourist.repository.BlogRepository;
import com.tourist.repository.TraditionRepository;
import com.tourist.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BlogTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private TraditionRepository traditionRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBlogsByTraditionId() {
        Long traditionId = 1L;
        Blog blog = new Blog();
        when(blogRepository.findByTraditionId(traditionId)).thenReturn(Collections.singletonList(blog));

        List<Blog> blogs = blogService.getBlogsByTraditionId(traditionId);

        assertNotNull(blogs);
        assertEquals(1, blogs.size());
        assertEquals(blog, blogs.getFirst());
        verify(blogRepository, times(1)).findByTraditionId(traditionId);
    }

    @Test
    public void testAddBlogToTradition_Success() {
        Long traditionId = 1L;
        Tradition tradition = new Tradition();
        Blog blog = new Blog();

        when(traditionRepository.findById(traditionId)).thenReturn(Optional.of(tradition));
        when(blogRepository.save(blog)).thenReturn(blog);

        Blog savedBlog = blogService.addBlogToTradition(traditionId, blog);

        assertNotNull(savedBlog);
        assertEquals(tradition, savedBlog.getTradition());
        verify(traditionRepository, times(1)).findById(traditionId);
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    public void testAddBlogToTradition_TraditionNotFound() {
        Long traditionId = 1L;
        Blog blog = new Blog();

        when(traditionRepository.findById(traditionId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TraditionNotFoundException.class, () -> blogService.addBlogToTradition(traditionId, blog));

        assertEquals("Tradition not found with id: " + traditionId, exception.getMessage());
        verify(traditionRepository, times(1)).findById(traditionId);
        verify(blogRepository, never()).save(any(Blog.class));
    }
}
