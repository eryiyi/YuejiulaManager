package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.PKTheme;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 15-4-3.
 */
@Repository("themeDao")
public interface ThemeDao {
    void saveTheme(PKTheme theme);

    /**
     * ��ҳ�鿴��������
     * @return
     */
    List<PKTheme> list(Map<String,Object> map);

    long count(Map<String,Object> map);

    PKTheme findTheme();

    PKTheme findByNumber(String number);

    /**
     * ���ҹ��ڵ�PK����
     * @param nowTime
     * @return
     */
    PKTheme findEndTheme(String nowTime);

    /**
     * ����ID��������
     * @param themeId
     * @return
     */
    PKTheme findByThemeId(String themeId);

    /**
     * ��������IDȥ����״̬
     * @param themeId
     */
    void updateStatus(@Param(value = "themeId")String themeId, @Param(value = "status")String status);

    /**
     * �����
     */
    void startTheme(String nowTime);

    /**
     * ��������IDɾ������
     * @param id
     */
    void deleteById(String id);


}
