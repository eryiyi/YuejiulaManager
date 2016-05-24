package com.liangxunwang.unimanager.service.account;

import com.google.gson.Gson;
import com.liangxunwang.unimanager.dao.*;
import com.liangxunwang.unimanager.model.*;
import com.liangxunwang.unimanager.mvc.vo.SellerGoodsVO;
import com.liangxunwang.unimanager.mvc.vo.SellerSchoolList;
import com.liangxunwang.unimanager.query.SellerGoodsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.DateUtil;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhl on 2015/3/25.
 */
@Service("mineSellerService")
public class MineSellerService implements ExecuteService{

    @Autowired
    @Qualifier("sellerGoodsDao")
    private SellerGoodsDao sellerGoodsDao;


    @Override
    public Object execute(Object object) throws ServiceException {
        String empid = (String) object;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("empId", empid);

        List<SellerGoodsVO> list = sellerGoodsDao.findSellerByIdAll(map);
        for (SellerGoodsVO vo : list) {
            if (vo.getEmpCover().startsWith("upload")) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }else {
                vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
            }
        }

        return list;
    }

}
