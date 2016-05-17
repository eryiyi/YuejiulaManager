package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Advert;
import com.liangxunwang.unimanager.model.Goods;
import com.liangxunwang.unimanager.model.GoodsFavour;
import com.liangxunwang.unimanager.mvc.vo.GoodsFavourVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/10.
 */
@Repository("appGoodsFavourDao")
public interface AppGoodsFavourDao {
    /**
     * 保存商品收藏
     * @param goodsFavour
     */
    void save(GoodsFavour goodsFavour);
    /**
     * 查询某个商品是否已经收藏过
     * */
    GoodsFavour isMineFavour(Map<String, Object> map);

    /**
     * 查询收藏列表
     * */
    List<GoodsFavourVO> findList(Map<String, Object> map);

    /**
     * 根据收藏UUID删除收藏
     * */
    void deleteFavourById(String favourId);
 }
