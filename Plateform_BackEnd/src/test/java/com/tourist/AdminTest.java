//package com.tourist;
//
//import com.tourist.enums.Role;
//import com.tourist.model.Admin;
//import com.tourist.model.DashAdmin;
//import com.tourist.repository.AdminRepository;
//import com.tourist.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class AdminTest {
//
//    @Mock
//    private AdminRepository adminRepository;
//
//    @InjectMocks
//    private AdminService adminService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getNoAdminById() {
//        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
//        Optional<Admin> foundAdmin = adminService.getAdminById(1L);
//        assertFalse(foundAdmin.isPresent());
//    }
//
//    @Test
//    void updateAdmin_ShouldThrowExceptionWhenAdminNotFound() {
//        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
//
//        Admin updatedAdmin = new Admin();
//        updatedAdmin.setName("inas");
//
//        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
//            adminService.updateAdmin(1L, updatedAdmin);
//        });
//        assertEquals("Admin not found", thrown.getMessage());
//    }
//}
