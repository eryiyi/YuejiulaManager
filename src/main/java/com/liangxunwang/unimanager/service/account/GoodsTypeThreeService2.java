package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.GoodsTypeDao;
import com.liangxunwang.unimanager.model.GoodsType;
import com.liangxunwang.unimanager.query.GoodsTypeThreeQuery;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2015/2/2.
 */
@Service("goodsTypeThreeService2")
public class GoodsTypeThreeService2 implements  ListService{

    @Autowired
    @Qualifier("goodsTypeDao")
    private GoodsTypeDao goodsTypeDao;

    @Override
    public Object list(Object object) throws ServiceException {
        //先查找商城分类
        GoodsTypeThreeQuery query = (GoodsTypeThreeQuery) object;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lx_goods_type_type", "0");
        map.put("isUse", "0");
        List<GoodsType> list1 = goodsTypeDao.list(map);

        return list1;
    }

}
