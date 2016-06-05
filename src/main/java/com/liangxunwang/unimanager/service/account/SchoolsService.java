package com.liangxunwang.unimanager.service.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.apidemo.EasemobChatGroups;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.mvc.vo.SchoolVO;
import com.liangxunwang.unimanager.mvc.vo.VideosVO;
import com.liangxunwang.unimanager.query.CollegeQuery;
import com.liangxunwang.unimanager.query.VideosQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/25.
 */
@Service("schoolsService")
public class SchoolsService implements ListService ,UpdateService,FindService,SaveService,DeleteService{
    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;
    @Override
    public Object list(Object object) throws ServiceException {
        CollegeQuery query = (CollegeQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size =  query.getSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        String provinceID = query.getProvinceId();
        String keyWords = query.getKeyWords();
        if (!StringUtil.isNullOrEmpty(provinceID)) {
            map.put("provinceId", provinceID);
        }
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            map.put("keyWords", keyWords);
        }
        List<SchoolVO> list = collegeDao.lists(map);
        long count = collegeDao.count(map);
        return new Object[]{list, count};
    }

    @Override
    public Object update(Object object) {
        Object[] params = (Object[]) object;
        String coid = (String) params[0];
        String groupId = (String) params[1];
        collegeDao.updateGroupId(coid, groupId);
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String coid = (String) params[0];
        College college=collegeDao.getGroupId(coid);
        return college.getGroupId();
    }

    @Override
    public Object save(Object object) throws ServiceException {
        College college = (College) object;
        college.setCoid(String.valueOf(StringUtil.getFiveInt()));//随机生成一个5位数
        college.setGroupId(UUIDFactory.random());
        collegeDao.save(college);
//        ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
//        dataObjectNode.put("groupname", college.getName());
//        dataObjectNode.put("desc", college.getName());
//        dataObjectNode.put("approval", true);
//        dataObjectNode.put("public", true);
//        dataObjectNode.put("maxusers", 2000);
//        dataObjectNode.put("owner", "12345678910");//12345678910 超级管理员 密码 0123456
//        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
//        dataObjectNode.put("members", arrayNode);
//        ObjectNode creatChatGroupNode = EasemobChatGroups.creatChatGroups(dataObjectNode);
//        //处理数据
//        ObjectNode objectNode = (ObjectNode) creatChatGroupNode.get("data");//解析数据
//        JsonNode groupid = objectNode.get("groupid");
//        //获得环信的groupid，更新数据库
//        collegeDao.updateGroupId(college.getCoid(), groupid.textValue());
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        String coid = (String) object;
        collegeDao.deleteById(coid);
        return null;
    }
}
