package com.liangxunwang.unimanager.service.member;

import com.liangxunwang.unimanager.dao.AdvertDao;
import com.liangxunwang.unimanager.model.Advert;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.ServiceException;
import com.liangxunwang.unimanager.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by zhl on 2015/2/3.
 */
@Service("memberAdvertService")
public class MemberAdvertService implements ListService, FindService{

    @Autowired
    @Qualifier("advertDao")
    private AdvertDao advertDao;
    @Override
    public Object list(Object object) throws ServiceException {
        String schoolId = (String) object;
        Random random = new Random();

        List<Advert> list = advertDao.getSmall(schoolId);

        for (Advert advert : list){
            if (!advert.getAdPic().contains("http://")) {
                advert.setAdPic(Constants.URL + advert.getAdPic());
            }
        }
        if (list.size()>0){
            return list.get(random.nextInt(list.size()));
        }else {
            List<Advert> adverts = advertDao.getBig("4");
            for (Advert advert : adverts){
                advert.setAdPic(Constants.URL + advert.getAdPic());
            }
            if (adverts.size()>0) {
                return adverts.get(0);
            }else {
                return null;
            }
        }
    }

    /**
     * 查找大广告位
     * @param object
     * @return
     * @throws ServiceException
     */
    @Override
    public Object findById(Object object) throws ServiceException {
        List<Advert> adverts = advertDao.getBig("1");
        if (adverts.size()>0) {
            Advert advert = adverts.get(0);
            if(advert != null){
                if (advert.getAdPic().startsWith("upload")) {
                    advert.setAdPic(Constants.URL + advert.getAdPic());
                }else {
                    advert.setAdPic(Constants.QINIU_URL + advert.getAdPic());
                }
            }
            return advert;
        }else {
            List<Advert> list = advertDao.getBig("3");
            if(list != null && list.size()>0){
                Advert advert = list.get(0);
                if(advert != null){
                    if (advert.getAdPic().startsWith("upload")) {
                        advert.setAdPic(Constants.URL + advert.getAdPic());
                    }else {
                        advert.setAdPic(Constants.QINIU_URL + advert.getAdPic());
                    }
                }
                return advert;
            }else {
                return null;
            }
        }
    }
}
