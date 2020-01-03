package com.nsc.dao;

import com.nsc.entity.Admin;

import java.util.List;

public interface AdminDao {
    Admin queryByName(String name);
    List<Admin> queryAll();
}
