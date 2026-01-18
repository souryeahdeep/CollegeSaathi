package org.college.admin.service;

import org.college.admin.dto.AdminDTO;
import org.college.admin.entity.Admin;
import org.college.admin.repo.AdminRepo;
import org.college.admin.utils.AdminMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepo adminRepo;
    private final AdminMapper adminMapper;

    public AdminService(AdminRepo adminRepo, AdminMapper adminMapper) {
        this.adminRepo = adminRepo;
        this.adminMapper = adminMapper;
    }

    public boolean isAdminPresent(String id) {
       return adminRepo.existsById(id);
    }


    public List<Admin> fetchAllAdmin() {
        return adminRepo.findAll();
    }

    public String addAdmin(AdminDTO adminDTO) {
        Admin admin = adminMapper.adminDTOToAdmin(adminDTO);
        adminRepo.save(admin);
        return "Admin added successfully";
    }
}
