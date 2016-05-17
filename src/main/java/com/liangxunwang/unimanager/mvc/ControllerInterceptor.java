package com.liangxunwang.unimanager.mvc;

import com.liangxunwang.unimanager.util.ControllerConstants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 *
 */
public class ControllerInterceptor extends ControllerConstants implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        Object account = request.getSession().getAttribute(ACCOUNT_KEY);

        if(uri.contains(".json")){
                return true;
        }

        if(uri.matches("(^/$)|(^/index\\.do$)|(^/adminLogin\\.do$)|(^/logout\\.do$)" +
                "|(^/uploadImage\\.do$)" +
                "|(^/uploadUnCompressImage\\.do$)" +
                "|(^/recordList\\.do$)" +
                "|(^/goodsTypeList\\.do$)" +
                "|(^/zanRecord\\.do$)" +
                "|(^/checkCode\\.do$)" +
                "|(^/checkNickName\\.do$)" +
                "|(^/memberLogin\\.do$)" +
                "|(^/getRegisterCode\\.do$)"+
                "|(^/newsListBySchool\\.do$)" +
                "|(^/listVisitor\\.do$)" +
                "|(^/nearbyUniversity\\.do$)" +
                "|(^/getBigAdvert\\.do$)" +
                "|(^/getSmallAdvert\\.do$)" +
                "|(^/getCommentsByRecord\\.do$)" +
                "|(^/sendRecord\\.do$)" +
                "|(^/saveComment\\.do$)" +
                "|(^/listZan\\.do$)"+
                "|(^/listGoodsByType\\.do$)"+
                "|(^/addGoods\\.do$)"+
                "|(^/deleteGoodsById\\.do$)"+
                "|(^/appListNotice\\.do$)"+
                "|(^/viewNotice\\.do$)"+
                "|(^/appListPartTimeType\\.do$)"+
                "|(^/listPartTime\\.do$)"+
                "|(^/viewPartTime\\.do$)"+
                "|(^/savePartTime\\.do$)"+
                "|(^/deletePartTime\\.do$)"+
                "|(^/listRelate\\.do$)"+
                "|(^/getRecordById\\.do$)"+
                "|(^/findGoodsById\\.do$)"+
                "|(^/listGoodsComment\\.do$)"+
                "|(^/saveGoodsComment\\.do$)"+
                "|(^/getMemberInfoById\\.do$)"+
                "|(^/updatePushId\\.do$)"+
                "|(^/getCheckCode\\.do$)"+
                "|(^/resetPassword\\.do$)"+
                "|(^/modifyMember\\.do$)"+
                "|(^/report\\.do$)"+
                "|(^/listReport\\.do$)"+
                "|(^/listCloseMember\\.do$)"+
                "|(^/manageReport\\.do$)"+
                "|(^/saveManagerEmp\\.do$)"+
                "|(^/getMemberInfoById\\.do$)"+
                "|(^/listMemberInfo\\.do$)"+
                "|(^/getProvince\\.do$)"+
                "|(^/getCollege\\.do$)"+
                "|(^/resetPass\\.do$)"+
                "|(^/resetMobile\\.do$)"+
                "|(^/getGroupId\\.do$)"+
                "|(^/searchMember\\.do$)"+
                "|(^/listInviteMemberInfo\\.do$)"+
                "|(^/openManagerEmp\\.do$)"+
                "|(^/listNewsTypeApp\\.do$)"+
                "|(^/listNewsApp\\.do$)"+
                "|(^/viewNews\\.do$)"+
                "|(^/memberRegister\\.do$)"+
                "|(^/deleteRecordById\\.do$)"+
                "|(^/viewRecord\\.do$)"+
                "|(^/viewGoods\\.do$)"+
                "|(^/saveNewsApp\\.do$)"+
                "|(^/deleteNews\\.do$)"+
                "|(^/addUserToGroup\\.do$)"+
                "|(^/searchRecommend\\.do$)"+
                "|(^/findContractSchoolById\\.do$)"+
                "|(^/setSeller\\.do$)"+
                "|(^/deleteSeller\\.do$)"+
                "|(^/findSellerById\\.do$)"+
                "|(^/findSchoolList\\.do$)"+
                "|(^/deleteSellerGoodsById\\.do$)"+
                "|(^/updateSellerGoods\\.do$)"+
                "|(^/getAllContract\\.do$)"+ "|(^/getTheme\\.do$)"+
                        "|(^/addPkComment\\.do$)"+
                        "|(^/addPkZanApp\\.do$)"+
                        "|(^/listPkComment\\.do$)"+
                        "|(^/listPkZanApp\\.do$)"+
                        "|(^/savePrize\\.do$)"+
                        "|(^/deletePrize\\.do$)"+
                        "|(^/getChampion\\.do$)"+
                        "|(^/viewPriceApp\\.do$)"+
                        "|(^/deleteWorkById\\.do$)"+
                        "|(^/listThemeApp\\.do$)"+
                        "|(^/championSure\\.do$)"+
                        "|(^/findWorkById\\.do$)"+
                        "|(^/findSchoolListByEmp\\.do$)"+
                        "|(^/viewWork\\.do$)"+
                        "|(^/listMemberBySchool\\.do$)"+
                "|(^/updateGoodsPosition\\.do$)"+ "|(^/listWorkApp\\.do$)"+
                "|(^/getPromotion\\.do$)"+ "|(^/savePkWork\\.do$)"+
                "|(^/deletePromotion\\.do$)"
                +"|(^/saveGoodsFavour\\.do$)"
                +"|(^/listFavour\\.do$)"
                +"|(^/deleteFavour\\.do$)"
                +"|(^/saveShoppingAddress\\.do$)"
                +"|(^/listShoppingAddress\\.do$)"
                +"|(^/deleteShoppingAddress\\.do$)"
                +"|(^/updateShoppingAddress\\.do$)"
                +"|(^/getSingleAddressByEmpId\\.do$)"
                +"|(^/orderSave\\.do$)"
                +"|(^/orderUpdate\\.do$)"
                +"|(^/listOrders\\.do$)"
                +"|(^/getSingleAddressByAddressId\\.do$)"
                +"|(^/updateOrder\\.do$)"
                +"|(^/orderSaveSingle\\.do$)"
                +"|(^/orderUpdateSingle\\.do$)"
                +"|(^/appGetProvince\\.do$)"
                +"|(^/appGetCity\\.do$)"
                +"|(^/appGetArea\\.do$)"
                +"|(^/paopaogoods/listGoods\\.do$)"
                +"|(^/paopaogoods/findById\\.do$)"
                +"|(^/paopaogoods/detail\\.do$)"
                +"|(^/viewpager/appList\\.do$)"
                +"|(^/selectOrderNum\\.do$)"
                +"|(^/listOrdersMng\\.do$)"
                +"|(^/cancelOrderSave\\.do$)"
                +"|(^/paopaogoods/updatePosition\\.do$)"
                +"|(^/findOrderByOrderNo\\.do$)"
                +"|(^/paopaogoods/delete\\.do$)"
                +"|(^/viewNewsShare\\.do$)"
                +"|(^/listVideosApp\\.do$)"
                +"|(^/listVideosCommentApp\\.do$)"
                +"|(^/appVideosSaveComment\\.do$)"
                +"|(^/appVideosListZan\\.do$)"
                +"|(^/appVideoZanSave\\.do$)"
                +"|(^/viewVideos\\.do$)"
                +"|(^/getThemeApp\\.do$)"
        ) || account != null) {
            return true;
        }

        if("true".equals(request.getParameter("j"))) {
            PrintWriter out = response.getWriter();
            out.print(toJSONString(TIMEOUT));
            out.close();
        } else {
            request.getRequestDispatcher("/WEB-INF/session.jsp").forward(request, response);
        }
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
