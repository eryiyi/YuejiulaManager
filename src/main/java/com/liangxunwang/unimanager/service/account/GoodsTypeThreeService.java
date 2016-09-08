package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.GoodsTypeDao;
import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.query.GoodsTypeThreeQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/2.
 */
@Service("goodsTypeThreeService")
public class GoodsTypeThreeService implements SaveService, ListService, FindService , UpdateService, DeleteService{

    @Autowired
    @Qualifier("goodsTypeDao")
    private GoodsTypeDao goodsTypeDao;

    @Override
    public Object save(Object object) throws ServiceException {
        if (object instanceof Object[]){
            String str = "";
            Object[] params = (Object[]) object;
            GoodsType type = (GoodsType) params[0];
            String schools = (String) params[1];

            String[] schoolAry = schools.split("\\|");
            for (int i=0; i<schoolAry.length; i++){
                type.setTypeId(UUIDFactory.random());
                type.setSchool_id(schoolAry[i]);
                goodsTypeDao.save(type);
            }
            return str;
        }
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        //先查找商城分类
        GoodsTypeThreeQuery query = (GoodsTypeThreeQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lx_goods_type_type", "0");
        map.put("isUse", "0");
        List<GoodsType> list1 = goodsTypeDao.list(map);

        //再查找第三方平台  圈主添加的
        map.put("lx_goods_type_type", "1");
        map.put("school_id", query.getSchool_id());
        List<GoodsType> list2 = goodsTypeDao.list(map);
        if(list2 != null){
            for(GoodsType goodsType:list2){
                list1.add(goodsType);
            }
        }
        return list1;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        return goodsTypeDao.findById((String) object);
    }

    @Override
    public Object update(Object object) {
        GoodsType type = (GoodsType) object;
        goodsTypeDao.update(type);
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        goodsTypeDao.deleteById((String) object);
        return null;
    }
}
