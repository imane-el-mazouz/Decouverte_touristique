package com.tourist;

import com.tourist.enums.Role;
import com.tourist.model.Admin;
import com.tourist.model.DashAdmin;
import com.tourist.repository.AdminRepository;
import com.tourist.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDashboard_ShouldReturnDashAdmin() {
        when(adminRepository.countTotalClients()).thenReturn(100L);
        when(adminRepository.countTotalEvents()).thenReturn(50L);
        when(adminRepository.countTotalExcursions()).thenReturn(25L);
        when(adminRepository.countTotalHotels()).thenReturn(10L);
        when(adminRepository.countTotalRoomsPerHotel()).thenReturn(200L);
        when(adminRepository.countTotalReservations()).thenReturn(300L);

        DashAdmin dashboard = adminService.getDashboard();

        assertNotNull(dashboard);
        assertEquals(100, dashboard.getTotalClients());
        assertEquals(50, dashboard.getTotalEvents());
        assertEquals(25, dashboard.getTotalExcursions());
        assertEquals(10, dashboard.getTotalHotels());
        assertEquals(200, dashboard.getTotalRoomsPerHotel());
        assertEquals(300, dashboard.getTotalReservations());
    }
    @Test
    void getAdminById() {
        Admin admin = new Admin();
        admin.setId(1L);
        when(adminRepository.findById(1L)).thenReturn(Optional.of(admin));

        Optional<Admin> foundAdmin = adminService.getAdminById(1L);

        assertTrue(foundAdmin.isPresent());
        assertEquals(1L, foundAdmin.get().getId());
    }


    @Test
    void getNoAdminById() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Admin> foundAdmin = adminService.getAdminById(1L);
        assertFalse(foundAdmin.isPresent());
    }

    @Test
    void updateAdmin_ShouldUpdateAdmin() {
        Admin existingAdmin = new Admin();
        existingAdmin.setId(1L);
        existingAdmin.setName("Old Name");
        when(adminRepository.findById(1L)).thenReturn(Optional.of(existingAdmin));

        Admin updatedAdmin = new Admin();
        updatedAdmin.setName("inas");
        updatedAdmin.setEmail("inas@gmail.com");
        updatedAdmin.setPassword("1234");
        updatedAdmin.setRole(Role.valueOf("Admin"));

        when(adminRepository.save(any(Admin.class))).thenReturn(updatedAdmin);

        Admin result = adminService.updateAdmin(1L, updatedAdmin);


        assertNotNull(result);
        assertEquals("inas", result.getName());
        assertEquals("inas@gmail.com", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertEquals("Admin", result.getRole());
    }

    @Test
    void updateAdmin_ShouldThrowExceptionWhenAdminNotFound() {
        when(adminRepository.findById(1L)).thenReturn(Optional.empty());

        Admin updatedAdmin = new Admin();
        updatedAdmin.setName("inas");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            adminService.updateAdmin(1L, updatedAdmin);
        });
        assertEquals("Admin not found", thrown.getMessage());
    }
}
