package com.liangxunwang.unimanager.service.account;

import com.liangxunwang.unimanager.dao.CollegeDao;
import com.liangxunwang.unimanager.dao.ContractSchoolDao;
import com.liangxunwang.unimanager.dao.PaopaoGoodsDao;
import com.liangxunwang.unimanager.dao.SellerGoodsDao;
import com.liangxunwang.unimanager.model.ContractSchool;
import com.liangxunwang.unimanager.model.PaopaoGoods;
import com.liangxunwang.unimanager.model.SellerGoods;
import com.liangxunwang.unimanager.mvc.vo.PaopaoGoodsVO;
import com.liangxunwang.unimanager.query.PaopaoGoodsQuery;
import com.liangxunwang.unimanager.service.*;
import com.liangxunwang.unimanager.util.Constants;
import com.liangxunwang.unimanager.util.RelativeDateFormat;
import com.liangxunwang.unimanager.util.StringUtil;
import com.liangxunwang.unimanager.util.UUIDFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuzh on 2015/8/16.
 */
@Service("paopaoGoodsService")
public class PaopaoGoodsService implements ListService, SaveService, DeleteService, FindService, UpdateService {


    @Autowired
    @Qualifier("paopaoGoodsDao")
    private PaopaoGoodsDao paopaoGoodsDao;

    @Autowired
    @Qualifier("sellerGoodsDao")
    private SellerGoodsDao sellerGoodsDao;

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao contractSchoolDao;

    @Autowired
    @Qualifier("collegeDao")
    private CollegeDao collegeDao;

    @Override
    public Object delete(Object object) throws ServiceException {
        if (object instanceof String){
            String id = (String) object;
            paopaoGoodsDao.deleteById(id);
        }
        return null;
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        if (object instanceof String){
            String id = (String) object;
            return paopaoGoodsDao.findById(id);
        }else if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            String id = (String) params[0];
            PaopaoGoodsVO vo =  paopaoGoodsDao.findGoodsVO(id);
            //处理图片URL链接
//            StringBuffer buffer = new StringBuffer();
            List<String> listPics = new ArrayList<String>();
            String[] pics = new String[]{};
            if(vo!=null && vo.getCover()!= null){
                pics = vo.getCover().split(",");
            }
            for (int i=0; i<pics.length; i++){
                if (pics[i].startsWith("upload")) {
                    listPics.add(Constants.URL + pics[i]);
//                    buffer.append(Constants.URL + pics[i]);
//                    if (i < pics.length - 1) {
//                        buffer.append(",");
//                    }
                }else {
                    listPics.add(Constants.QINIU_URL + pics[i]);
//                    buffer.append(Constants.QINIU_URL + pics[i]);
//                    if (i < pics.length - 1) {
//                        buffer.append(",");
//                    }
                }
                if (!StringUtil.isNullOrEmpty(vo.getVideourl())) {
                    if (vo.getEmpCover().startsWith("upload")) {
                        vo.setVideourl(Constants.URL + vo.getVideourl());
                    }else {
                        vo.setVideourl(Constants.QINIU_URL + vo.getVideourl());
                    }
                }

            }
//            vo.setCover(buffer.toString());

            vo.setUpTime(RelativeDateFormat.format(Long.parseLong(vo.getUpTime())));
            vo.setCont(vo.getCont().replaceAll("\\swidth=.*?;\"", ""));
            return new Object[]{vo, listPics};
        }
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        if (object instanceof PaopaoGoodsQuery){
            PaopaoGoodsQuery query = (PaopaoGoodsQuery) object;
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
            if (!StringUtil.isNullOrEmpty(query.getEmpId())) {
                map.put("empId", query.getEmpId());
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
            if (!StringUtil.isNullOrEmpty(query.getType()) && !"1".equals(query.getIsMine())) {
                if (query.getType().equals("0")) {//正常状态，查询该学校下的宝贝
                    if (!StringUtil.isNullOrEmpty(query.getSchoolId())) {
                        schoolIds = new int[]{Integer.parseInt(query.getSchoolId())};
                    }
                }else if (query.getType().equals("2")) {//商家，查询商家的宝贝
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
                } else if (query.getType().equals("3")) {//承包商
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
            List<PaopaoGoodsVO> list = paopaoGoodsDao.listGoods(map);
            for (PaopaoGoodsVO vo : list){
                //处理图片URL链接
                StringBuffer buffer = new StringBuffer();
                String[] pics = new String[]{};
                if(vo!=null && vo.getCover()!= null){
                    pics = vo.getCover().split(",");
                }
                for (int i=0; i<pics.length; i++){
                    if (pics[i].startsWith("upload")) {
                        buffer.append(Constants.URL + pics[i]);
                        if (i < pics.length - 1) {
                            buffer.append(",");
                        }
                    }else {
                        buffer.append(Constants.QINIU_URL + pics[i]);
                        if (i < pics.length - 1) {
                            buffer.append(",");
                        }
                    }
                }
                vo.setCover(buffer.toString());

                if (!StringUtil.isNullOrEmpty(vo.getEmpCover())) {
                    if (vo.getEmpCover().startsWith("upload")) {
                        vo.setEmpCover(Constants.URL + vo.getEmpCover());
                    }else {
                        vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
                    }
                }
                if (!StringUtil.isNullOrEmpty(vo.getVideourl())) {
                    if (vo.getEmpCover().startsWith("upload")) {
                        vo.setVideourl(Constants.URL + vo.getVideourl());
                    }else {
                        vo.setVideourl(Constants.QINIU_URL + vo.getVideourl());
                    }
                }
                vo.setUpTime(RelativeDateFormat.format(Long.parseLong(vo.getUpTime())));
            }
            return list;
        }else if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            PaopaoGoodsQuery query = (PaopaoGoodsQuery) params[0];
            String empId = (String) params[1];

            int index = (query.getIndex() - 1) * query.getSize();
            int size = query.getIndex() * query.getSize();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("index", index);
            map.put("size", size);
            map.put("empId", empId);
            if(!StringUtil.isNullOrEmpty(query.getManager_id())){
                map.put("manager_id", query.getManager_id());
            }
            List<PaopaoGoodsVO> list = paopaoGoodsDao.listGoods(map);
            for(PaopaoGoodsVO vo:list){
                //处理图片URL链接
                StringBuffer buffer = new StringBuffer();
                String[] pics = new String[]{};
                if(vo!=null && vo.getCover()!= null){
                    pics = vo.getCover().split(",");
                }
                for (int i=0; i<pics.length; i++){
                    if (pics[i].startsWith("upload")) {
                        buffer.append(Constants.URL + pics[i]);
                        if (i < pics.length - 1) {
                            buffer.append(",");
                        }
                    }else {
                        buffer.append(Constants.QINIU_URL + pics[i]);
                        if (i < pics.length - 1) {
                            buffer.append(",");
                        }
                    }
                }
                vo.setCover(buffer.toString());
                if (!StringUtil.isNullOrEmpty(vo.getVideourl())) {
                    if (vo.getEmpCover().startsWith("upload")) {
                        vo.setVideourl(Constants.URL + vo.getVideourl());
                    }else {
                        vo.setVideourl(Constants.QINIU_URL + vo.getVideourl());
                    }
                }
            }
            long count = paopaoGoodsDao.count(map);
            return new Object[]{list, count};
        }
        return null;
    }

    @Override
    public Object save(Object object) throws ServiceException {
        if (object instanceof Object[]){
            String str = "";
            Object[] params = (Object[]) object;
            PaopaoGoods goods = (PaopaoGoods) params[0];
            String schools = (String) params[1];
            String count = (String) params[2];

            String[] schoolAry = schools.split("\\|");
            for (int i=0; i<schoolAry.length; i++){
//                List<PaopaoGoods> list = paopaoGoodsDao.listByEmpSchool(goods.getEmpId(), schoolAry[i]);
//                if (list.size()< Integer.parseInt(count)) {//如果小于限制数量让发布
                    goods.setId(UUIDFactory.random());
                    goods.setIsUse("0");
                    goods.setIsDel("0");
                    goods.setUpTime(System.currentTimeMillis() + "");
                    goods.setSchoolId(schoolAry[i]);
                    paopaoGoodsDao.save(goods);
//                }else {
//                    College college = collegeDao.getGroupId(schoolAry[i]);
//                    str+= college.getName()+"  ";
//                }
            }
            return str;
        }
        return null;
    }

    @Override
    public Object update(Object object) {
        if (object instanceof PaopaoGoods){
            PaopaoGoods goods = (PaopaoGoods) object;
            paopaoGoodsDao.update(goods);
        }
        return null;
    }
}
