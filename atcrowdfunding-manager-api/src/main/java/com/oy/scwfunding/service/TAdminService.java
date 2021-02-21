package com.oy.scwfunding.service;
import com.github.pagehelper.PageInfo;
import com.oy.scwfunding.bean.TAdmin;

import java.util.List;
import java.util.Map;

/**
 * @Author OY
 * @Date 2021/1/25
 */
public interface TAdminService {
    TAdmin getTAdminByLogin(Map<String, Object> paramMap);

    PageInfo<TAdmin> listAdminPage(Map<String, Object> paramMap);

    void saveTAdmin(TAdmin admin);

    TAdmin getTAdminById(Integer id);

    void updateTAdmin(TAdmin admin);

    void deleteTAdmin(Integer id);

    void deleteBatch(List<Integer> idList);
}
