package org.college.admin.utils;


import org.college.admin.dto.AdminDTO;
import org.college.admin.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    Admin adminToAdminDTO(Admin admin);

    Admin adminDTOToAdmin(AdminDTO adminDTO);
}
