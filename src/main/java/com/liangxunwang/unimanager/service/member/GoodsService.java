package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.ContractSchoolDao;
import com.liangxunwang.unimanager.dao.GoodsDao;
import com.liangxunwang.unimanager.dao.RelateDao;
import com.liangxunwang.unimanager.dao.SellerGoodsDao;
import com.liangxunwang.unimanager.model.ContractSchool;
import com.liangxunwang.unimanager.model.Goods;
import com.liangxunwang.unimanager.model.SellerGoods;
import com.liangxunwang.unimanager.mvc.vo.GoodsVO;
import com.liangxunwang.unimanager.query.GoodsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
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
 * Created by zhl on 2015/2/2.
 */
@Service("goodsService")
public class GoodsService implements SaveService, ListService, FindService , DeleteService, UpdateService{
    @Autowired
    @Qualifier("goodsDao")
    private GoodsDao goodsDao;

    @Autowired
    @Qualifier("sellerGoodsDao")
    private SellerGoodsDao sellerGoodsDao;

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao contractSchoolDao;

    @Autowired
    @Qualifier("relateDao")
    private RelateDao relateDao;

    @Override
    public Object save(Object object) throws ServiceException {
        Goods goods = (Goods) object;

        switch (Integer.parseInt(goods.getType())){
            case 0://普通会员
            case 1://管理员
                goods.setId(UUIDFactory.random());
                goods.setIsUse("0");
                goods.setIsDel("0");
                goods.setDateline(System.currentTimeMillis() + "");
                goods.setPosition(0);
                goodsDao.save(goods);
                break;
            case 2://商家
                List<SellerGoods> list = sellerGoodsDao.getSellerGoodsByEmpId(goods.getEmpId());
                for (int i=0; i<list.size(); i++) {
                    goods.setSchoolId(list.get(i).getSchoolId());
                    goods.setId(UUIDFactory.random());
                    goods.setIsUse("0");
                    goods.setIsDel("0");
                    goods.setDateline(System.currentTimeMillis() + "");
                    goods.setPosition(0);
                    goodsDao.save(goods);
                }
                break;
            case 3://圈主
                List<ContractSchool> contractSchools = contractSchoolDao.findContractSchoolByEmpId(goods.getEmpId());
                for (int i=0; i<contractSchools.size(); i++) {
                    goods.setSchoolId(contractSchools.get(i).getSchoolId());
                    goods.setId(UUIDFactory.random());
                    goods.setIsUse("0");
                    goods.setIsDel("0");
                    goods.setDateline(System.currentTimeMillis() + "");
                    goods.setPosition(0);
                    goodsDao.save(goods);
                }
                break;
        }

        return null;
    }
//    localhost:8080/llistGoodsByType.do?&empId=8a7c1c6aece54eaaa84e0bdbbe2e2e45&isMine=1
    @Override
    public Object list(Object object) throws ServiceException {
        GoodsQuery query = (GoodsQuery) object;
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        if (!StringUtil.isNullOrEmpty(query.getTypeId())) {
            map.put("typeId", query.getTypeId());
        }
        if (!StringUtil.isNullOrEmpty(query.getCont())) {
            map.put("cont", query.getCont());
        }
        //查询的是我的商品
        if (!StringUtil.isNullOrEmpty(query.getIsMine()) && query.getIsMine().equals("1")) {
            //查询会员下的所有商品
            if (!StringUtil.isNullOrEmpty(query.getEmpId())) {
                map.put("empId", query.getEmpId());
                map.put("schoolIds", "");
            }
        }
        int[] schoolIds = null;
        if (!StringUtil.isNullOrEmpty(query.getType()) && !query.getIsMine().equals("1")) {
            if (query.getType().equals("0")) {//查询会员圈子下的商品
                schoolIds = new int[]{Integer.parseInt(query.getSchoolId())};
            } else if (query.getType().equals("2")) {//商家
                List<SellerGoods> list = sellerGoodsDao.getSellerGoodsByEmpId(query.getEmpId());
                if (!StringUtil.isNullOrEmpty(query.getSchoolId())){
                    schoolIds = new int[list.size()+1];
                }else {
                    schoolIds = new int[list.size()];
                }
                for (int i = 0; i < list.size(); i++) {
                    schoolIds[i] = Integer.parseInt(list.get(i).getSchoolId().trim());
                }
                if (!StringUtil.isNullOrEmpty(query.getSchoolId())){
                    schoolIds[list.size()] = Integer.parseInt(query.getSchoolId());
                }
            } else if (query.getType().equals("3")) {//圈主
                List<ContractSchool> list = contractSchoolDao.findContractSchoolByEmpId(query.getEmpId());
                schoolIds = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    schoolIds[i] = Integer.parseInt(list.get(i).getSchoolId().trim());
                }
            }
            if (schoolIds != null && schoolIds.length != 0) {
                map.put("schoolIds", schoolIds);
            }
        }
        List<GoodsVO> list = goodsDao.findListByType(map);
        for (GoodsVO vo : list){
            if (!StringUtil.isNullOrEmpty(vo.getCover())){
                String[] pics = vo.getCover().split(",");
                StringBuffer buffer = new StringBuffer();
                for (int i=0; i<pics.length; i++){
                    buffer.append(Constants.URL+pics[i]);
                    if (i<pics.length-1){
                        buffer.append(",");
                    }
                }
                vo.setCover(buffer.toString());
            }
            if (!StringUtil.isNullOrEmpty(vo.getEmpCover())) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }
            vo.setDateline(RelativeDateFormat.format(Long.parseLong(vo.getDateline())));
        }
        return list;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        GoodsVO goodsVO = goodsDao.findByGoodsId((String) object);
        if (goodsVO != null) {
            if (goodsVO.getEmpCover().startsWith("upload")) {
                goodsVO.setEmpCover(Constants.URL + goodsVO.getEmpCover());
            }else {
                goodsVO.setEmpCover(Constants.QINIU_URL + goodsVO.getEmpCover());
            }
            goodsVO.setDateline(RelativeDateFormat.format(Long.parseLong(goodsVO.getDateline())));
            if (!StringUtil.isNullOrEmpty(goodsVO.getCover())) {
                String[] pics = goodsVO.getCover().split(",");
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < pics.length; i++) {
                    buffer.append(Constants.URL + pics[i]);
                    if (i < pics.length - 1) {
                        buffer.append(",");
                    }
                }
                goodsVO.setCover(buffer.toString());
            }
            goodsVO.setContent(goodsVO.getContent().replaceAll("(<img[^>]*style=\\\")", ""));
//            goodsVO.setContent(StringUtil.replaceHtmlTag(goodsVO.getContent(),  "style","img","width=\"","px;\""));
            return goodsVO;
        }else {
            return null;
        }
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        Object[] params = (Object[]) object;
        String goodsId = (String) params[0];
        String type = (String) params[1];
        if (type.equals("1")){//下架
            goodsDao.downGoods(goodsId);
        }else {//删除
            goodsDao.deleteGoods(goodsId);
            //删除该商品的与我相关
            relateDao.deleteByGoodsId(goodsId);
        }
        return null;
    }

    @Override
    public Object update(Object object) {
        Object[] params = (Object[]) object;
        String goodsId = (String) params[0];
        String position = (String) params[1];
        goodsDao.updatePosition(goodsId, position);
        return null;
    }
}
