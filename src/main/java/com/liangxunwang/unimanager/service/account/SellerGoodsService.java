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
@Service("sellerGoodsService")
public class SellerGoodsService implements SaveService, DeleteService, ListService, UpdateService, ExecuteService, FindService{

    @Autowired
    @Qualifier("sellerGoodsDao")
    private SellerGoodsDao sellerGoodsDao;

    @Autowired
    @Qualifier("paopaoGoodsDao")
    private PaopaoGoodsDao paopaoGoodsDao;

    @Autowired
    @Qualifier("memberDao")
    private MemberDao memberDao;

    @Autowired
    @Qualifier("contractSchoolDao")
    private ContractSchoolDao schoolDao;

    @Autowired
    @Qualifier("partTimeDao")
    private PartTimeDao partTimeDao;

    @Autowired
    @Qualifier("adminDao")
    private AdminDao adminDao;

    /**
     * 设置商家为哪个学校的商家，批量保存
     * @param object
     * @return
     * @throws ServiceException
     */
    @Override
    public Object save(Object object) throws ServiceException {
        if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            String contractId = (String) params[0];//承包商的ID
            String empId = (String) params[1];//商家的会员ID
            String schools = (String) params[2];//承包商要给商家开通的学校
            String dates = (String) params[3];//每个学校的过期时间

            List<SellerGoods> checks = sellerGoodsDao.getSellerGoodsById(empId, contractId);
//            if (checks.size() > 0) {
//                throw new ServiceException("HAS_SET_SELLER");
//            }

            String[] schoolAry = schools.split("\\|");
            String[] dateAry = dates.split("\\|");

            List<String> schoolList = new ArrayList<String>();
            List<String> dateList = new ArrayList<String>();

            if (checks.size() >0) {
                String checkStr = new Gson().toJson(checks);
                for (int i = 0; i < schoolAry.length; i++) {
                    if (!checkStr.contains(schoolAry[i])){
                        schoolList.add(schoolAry[i]);
                        dateList.add(dateAry[i]);
                    }
                }
            }else {
                schoolList = Arrays.asList(schoolAry);
                dateList = Arrays.asList(dateAry);
            }
//            List<SellerGoods> list = new ArrayList<SellerGoods>();

            for (int i=0; i<schoolList.size(); i++){
                SellerGoods sellerGoods = new SellerGoods();
                sellerGoods.setId(UUIDFactory.random());
                sellerGoods.setContractId(contractId);
                sellerGoods.setEmpId(empId);
                sellerGoods.setSchoolId(schoolList.get(i));
                sellerGoods.setEndTime(DateUtil.getMs(dateList.get(i), "MM/dd/yyyy") + "");
                sellerGoods.setDateline(System.currentTimeMillis() + "");
//                list.add(sellerGoods);
                List<SellerGoods> list = new ArrayList<SellerGoods>();
                list.add(sellerGoods);
                sellerGoodsDao.saveList(list);
            }

//            if (list.size()>0) {
//                sellerGoodsDao.saveList(list);
                //将会员设置为商家
                memberDao.changeBusiness(empId, "2");

                //给用户插入一条后台登录账号数据
                Member member = memberDao.findById(empId);

                //检查一下后台登录账号有没有该会员的账号
                Admin check = adminDao.findByUsername(member.getEmpMobile());
                if (check == null) {//如果没有再去添加
                    Admin admin = new Admin();
                    admin.setId(UUIDFactory.random());
                    admin.setUsername(member.getEmpMobile());
                    admin.setPassword(member.getEmpPass());
                    admin.setType("3");//3是商家   2是承包商  1是管理员
                    admin.setEmpId(member.getEmpId());
                    admin.setIsUse("0");//可用状态
                    admin.setGoodsCount("3");//默认是三个商品
                    adminDao.add(admin);
                }
//            }
        }else {
            List<SellerGoods> list = (List<SellerGoods>) object;
            if (list.size() > 0) {
                String empId = list.get(0).getEmpId();
                String contractId = list.get(0).getContractId();
                List<SellerGoods> checks = sellerGoodsDao.getSellerGoodsById(empId, contractId);
                if (checks.size() > 0) {
                    throw new ServiceException("HAS_SET_SELLER");
                }

                for (SellerGoods sellerGoods : list) {
                    sellerGoods.setId(UUIDFactory.random());
                    sellerGoods.setEndTime(DateUtil.getMs(sellerGoods.getEndTime(), "yyyy-MM-dd") + "");
                    sellerGoods.setDateline(System.currentTimeMillis() + "");
                }
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("list", list);
                sellerGoodsDao.saveList(list);
                //将会员设置为商家
                memberDao.changeBusiness(list.get(0).getEmpId(), "2");
            }
        }
        return null;
    }

    @Override
    public Object delete(Object object) throws ServiceException {
        if (object instanceof String){
            String id = (String) object;
            sellerGoodsDao.deleteById(id);
        }else {
            Object[] params = (Object[]) object;
            String empId = (String) params[0];//商家会员ID
            String contractId = (String) params[1];//承包商ID
            List<SellerGoods> list = sellerGoodsDao.getSellerGoodsById(empId, contractId);
            int[] schoolIds = new int[list.size()];
            for (int i=0; i<list.size(); i++){
                schoolIds[i] = Integer.parseInt(list.get(i).getSchoolId());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("empId", empId);
            map.put("schoolIds", schoolIds);
            paopaoGoodsDao.deleteGoodsById(map);//删除该商家所在该承包商学校下的商品

            sellerGoodsDao.delete(empId, contractId);//删除承包商和商家的联系
            //如果不在是商家将其设为普通会员
            List<SellerGoods> sellerGoodses = sellerGoodsDao.findByEmpId(empId);
            if (sellerGoodses.size() == 0) {
                memberDao.changeBusiness(empId, "0");
                //删除其后台登录的登录账号
                adminDao.delete(empId);
            }

        }
        return null;
    }

    @Override
    public Object list(Object object) throws ServiceException {
        if (object instanceof SellerGoodsQuery) {
            SellerGoodsQuery query = (SellerGoodsQuery) object;
            String keyWords = query.getKeyWords();
            String empId = query.getEmpId();
            int index = (query.getIndex() - 1) * query.getSize();
            int size = query.getIndex() * query.getSize();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("index", index);
            map.put("size", size);
            map.put("empId", empId);
            if (!StringUtil.isNullOrEmpty(keyWords)) {
                if (StringUtil.isMobileNO(keyWords)) {
                    map.put("phone", keyWords);
                } else {
                    map.put("name", keyWords);
                }
            }

            List<SellerGoodsVO> list = sellerGoodsDao.findSellerById(map);
            for (SellerGoodsVO vo : list) {
                if (vo.getEmpCover().startsWith("upload")) {
                    vo.setEmpCover(Constants.URL + vo.getEmpCover());
                }else {
                    vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
                }
            }
            return list;
        }else if (object instanceof Object[]){
            Object[] params = (Object[]) object;
            String empId = (String) params[0];
            String contractId = (String) params[1];
            List<SellerSchoolList> list = sellerGoodsDao.findSellerSchoolList(empId, contractId);
            for (SellerSchoolList  se : list){
                se.setStartTime(DateUtil.getDate(se.getStartTime(), "yyyy-MM-dd"));
                se.setEndTime(DateUtil.getDate(se.getEndTime(), "yyyy-MM-dd"));
            }
            return list;
        }else {
            String empId = (String) object;
            List<SellerSchoolList> list = sellerGoodsDao.findSellerSchoolListById(empId);
            for (SellerSchoolList  se : list){
                se.setStartTime(DateUtil.getDate(se.getStartTime(), "yyyy-MM-dd"));
                se.setEndTime(DateUtil.getDate(se.getEndTime(), "yyyy-MM-dd"));
            }
            return list;
        }
    }

    @Override
    public Object update(Object object) {
        if (object instanceof  SellerGoods) {
            SellerGoods sellerGoods = (SellerGoods) object;
            String id = sellerGoods.getId();
            //校验商家是不是属于该承包商
            List<SellerGoods> goods = sellerGoodsDao.getSellerGoodsById(sellerGoods.getEmpId(), sellerGoods.getContractId());
            if (goods.size() == 0) {
                throw new ServiceException("ERROR_EXISTS");
            }
            String schoolId = sellerGoods.getSchoolId();

            ContractSchool school = schoolDao.findBySchoolId(schoolId);
            long endTime = DateUtil.getMs(sellerGoods.getEndTime(), "yyyy-MM-dd");
            //校验承包商的到期时间是否大于商家的到期时间
            if (Long.parseLong(school.getEndTime()) < endTime) {
                throw new ServiceException("ERROR_TIME");
            }
            sellerGoodsDao.updateEndTime(id, endTime + "");
        }else if (object instanceof SellerGoodsSetting){
            SellerGoodsSetting setting = (SellerGoodsSetting) object;
            //修改可发宝贝数量
            if (!StringUtil.isNullOrEmpty(setting.getCount())){
                adminDao.updateCount(setting.getEmpId(),setting.getCount());
            }//续费
            else if (!StringUtil.isNullOrEmpty(setting.getEndTime())){
                sellerGoodsDao.updateEndTime(setting.getId(), DateUtil.getMs(setting.getEndTime(), "MM/dd/yyyy")+"");
            }
        }
        else {
            //将商家设置为普通会员
            //1获得过期的会员
            List<SellerGoods> endSeller = sellerGoodsDao.getEndSeller(System.currentTimeMillis()+"");
            for (int i=0; i<endSeller.size(); i++){
                SellerGoods sellerGoods = endSeller.get(i);
                //查找该会员下还有没有其他的学校
                List<SellerGoods> list = sellerGoodsDao.getCheckSeller(sellerGoods.getEmpId(), System.currentTimeMillis()+"");
                //如果等于0说明该会员没有别的学校了
                if (list.size() == 0){
                //修改会员为普通会员
                    memberDao.changeBusiness(sellerGoods.getEmpId(), "0");
                    //删除该会员的后台登录账号
                    adminDao.delete(sellerGoods.getEmpId());
                }
                //删除商家和学校关联
                sellerGoodsDao.deleteById(sellerGoods.getId());
                //删除该商家发布的所有商品
                paopaoGoodsDao.deleteGoodsByEmp(sellerGoods.getEmpId(), sellerGoods.getSchoolId());
                //删除该商家下的所有兼职
                partTimeDao.deletePartTimeByEmp(sellerGoods.getEmpId(), sellerGoods.getSchoolId());
            }
        }
        return null;
    }

    @Override
    public Object execute(Object object) throws ServiceException {
        SellerGoodsQuery query = (SellerGoodsQuery) object;
        String keyWords = query.getKeyWords();
        String empId = query.getEmpId();
        int index = (query.getIndex() - 1) * query.getSize();
        int size = query.getIndex() * query.getSize();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("index", index);
        map.put("size", size);
        map.put("empId", empId);
        if (!StringUtil.isNullOrEmpty(keyWords)) {
            if (StringUtil.isMobileNO(keyWords)) {
                map.put("phone", keyWords);
            } else {
                map.put("name", keyWords);
            }
        }

        List<SellerGoodsVO> list = sellerGoodsDao.findSellerById(map);
        for (SellerGoodsVO vo : list) {
            if (vo.getEmpCover().startsWith("upload")) {
                vo.setEmpCover(Constants.URL + vo.getEmpCover());
            }else {
                vo.setEmpCover(Constants.QINIU_URL + vo.getEmpCover());
            }
        }

        long count = sellerGoodsDao.count(map);
        return new Object[]{list, count};
    }

    @Override
    public Object findById(Object object) throws ServiceException {
        if (object instanceof Object[]) {
            Object[] params = (Object[]) object;
            String empId = (String) params[0];
            String schoolId = (String) params[1];
            SellerGoodsVO vo = sellerGoodsDao.findByEmpContract(empId, schoolId);

            Admin admin = adminDao.findByEmpId(empId);
            return new Object[]{vo, admin.getGoodsCount()};
        }
        return null;
    }
}
