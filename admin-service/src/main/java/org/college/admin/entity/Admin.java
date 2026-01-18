package org.college.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "admins")
@Getter
@Setter
@ToString
@Entity
public class Admin {
    @Id
    @Column(name = "admin_id",unique = true,nullable = false)
    public String id;

    @Column(name="admin_name",nullable = false)
    public String name;
}
