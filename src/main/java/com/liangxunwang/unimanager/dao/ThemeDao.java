package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PKTheme;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 15-4-3.
 */
@Repository("themeDao")
public interface ThemeDao {
    void saveTheme(PKTheme theme);

    /**
     * 分页查看所有主题
     * @return
     */
    List<PKTheme> list(Map<String,Object> map);

    long count(Map<String,Object> map);

    PKTheme findTheme();

    PKTheme findByNumber(String number);

    /**
     * 查找过期的PK主题
     * @param nowTime
     * @return
     */
    PKTheme findEndTheme(String nowTime);

    /**
     * 根据ID查找主题
     * @param themeId
     * @return
     */
    PKTheme findByThemeId(String themeId);

    /**
     * 根据主题ID去更改状态
     * @param themeId
     */
    void updateStatus(@Param(value = "themeId")String themeId, @Param(value = "status")String status);

    /**
     * 开启活动
     */
    void startTheme(String nowTime);

    /**
     * 根据主题ID删除主题
     * @param id
     */
    void deleteById(String id);


}
