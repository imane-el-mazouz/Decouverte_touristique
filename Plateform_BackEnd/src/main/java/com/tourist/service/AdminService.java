package com.tourist.service;

import com.tourist.model.Admin;
import com.tourist.model.DashAdmin;
import com.tourist.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public DashAdmin getDashboard() {
        DashAdmin dashboard = new DashAdmin();
        dashboard.setTotalClients(adminRepository.countTotalClients());
        dashboard.setTotalEvents(adminRepository.countTotalEvents());
        dashboard.setTotalExcursions(adminRepository.countTotalExcursions());
        dashboard.setTotalHotels(adminRepository.countTotalHotels());
        dashboard.setTotalRoomsPerHotel(adminRepository.countTotalRoomsPerHotel());
        dashboard.setTotalReservations(adminRepository.countTotalReservations());
        return dashboard;
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin updateAdmin(Long id, Admin updatedAdmin) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setName(updatedAdmin.getName());
                    admin.setEmail(updatedAdmin.getEmail());
                    admin.setPassword(updatedAdmin.getPassword());
                    admin.setRole(updatedAdmin.getRole());
                    return adminRepository.save(admin);
                })
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }


}
