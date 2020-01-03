package com.nsc.service;

import com.nsc.entity.Admin;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AdminService {
    void  poiExcel(String downloadName, HttpServletResponse response);
    Map<String,Object> login(String loginName, String password);
}
