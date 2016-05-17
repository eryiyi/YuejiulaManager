package com.liangxunwang.unimanager.dao;

import com.liangxunwang.unimanager.model.Member;
import com.liangxunwang.unimanager.mvc.vo.MemberVO;
import com.liangxunwang.unimanager.query.MemberQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuzwei on 2015/1/29.
 */
@Repository("memberDao")
public interface MemberDao {
    /**
     * 根据手机号查找会员
     * @param mobile
     * @return
     */
    MemberVO findByMobile(String mobile);

    Member findByPhone(String mobile);

    Member findById(String id);

    Member findByCommentFplid(String fplId);

    MemberVO findInfoById(String empId);

    void save(Member member);

    /**
     * 根据查询条件查询  index:第几页 size：每页显示多少条数据
     * @param map
     * @return
     */
    List<MemberVO> list(Map<String,Object> map);

    /**
     * 根据条件查询数量
     * @param map
     * @return
     */
    long count(Map<String,Object> map);

    /**
     * 根据会员ID，去更改是否为管理员
     * @param empId
     * @param flag
     */
    void updateAdmin(@Param(value = "empId")String empId, @Param(value = "flag")String flag);

    /**
     * 根据会员ID设为商家
     * @param empId
     * @param flag
     */
    void changeBusiness(@Param(value = "empId")String empId, @Param(value = "flag")String flag);

    /**
     * 根据昵称查找会员
     * @param map
     * @return
     */
    Member findMemberByNickName(Map<String, Object> map);

    /**
     * 关禁闭
     * @param empId
     */
    void closeMember(String empId);

    /**
     * 解除禁闭
     * @param empId
     */
    void openMember(String empId);

    /**
     * 修改资料
     * @param member
     */
    void modifyMember(Member member);
    void modifyMemberNoCover(Member member);

    void updatePassword(@Param(value = "mobile")String mobile, @Param(value = "password") String password);

    List<Member> listMemberInfo(Map<String,Object> map);

    List<Member> listMemberInfoByUsername(Map<String,Object> map);

    /**
     * 根据ID更新pushId
     * @param id
     * @param pushId
     */
    void updatePushId(@Param(value = "id") String id, @Param(value = "pushId") String pushId, @Param(value = "type")String type);

    /**
     * 修改密码
     * @param empId
     * @param rePass
     */
    void resetPass(@Param(value = "empId") String empId, @Param(value = "rePass") String rePass);

    /**
     * 设置手机号
     * @param empId
     * @param reMobile
     */
    void resetMobile(@Param(value = "empId") String empId, @Param(value = "reMobile") String reMobile);

    List<Member> searchMember(String keyWords);

    List<Member> searchMemberByPage(Map<String,Object> map);

    /**
     * 根据用户ID批量解除禁闭
     * @param map
     */
    void systemOpenEmp(Map<String,Object> map);

    /**
     * 注册会员数量
     * @return
     */
    long memberCount();

    /**
     * 被关禁闭会员数量
     * @return
     */
    long closeMemberCount();

    /**
     * 更改环信是否在组
     * @param empId
     */
    void updateHx(String empId);

    /**
     * 根据会员ID设置为承包商
     * @param empId
     */
    void setContractUser(@Param(value = "empId")String empId, @Param(value = "typeId")String typeId);

    /**
     * 根据学校ID查询所有的会员信息
     * @param schoolId
     * @return
     */
    List<Member> listMemberBySchool(String schoolId);
}
