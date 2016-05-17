package com.liangxunwang.unimanager.mvc.admin;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.huanxin.comm.HTTPMethod;
import com.liangxunwang.unimanager.huanxin.comm.HxConstants;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.ChatUtils;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.HTTPClientUtils;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.MyToken;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.vo.EndPoints;
import com.liangxunwang.unimanager.model.College;
import com.liangxunwang.unimanager.model.tip.DataTip;
import com.liangxunwang.unimanager.service.FindService;
import com.liangxunwang.unimanager.service.ListService;
import com.liangxunwang.unimanager.service.UpdateService;
import com.liangxunwang.unimanager.util.ControllerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 刘先强 on 2015/2/26.
 */
@Controller
public class CollegeController extends ControllerConstants {
    private static Logger LOGGER = LoggerFactory.getLogger(ChatUtils.class);
    private static JsonNodeFactory factory = new JsonNodeFactory(false);

    @Autowired
    @Qualifier("collegeService")
    private UpdateService updateCollegeService;

    @Autowired
    @Qualifier("collegeService")
    private ListService listCollegeService;

    @Autowired
    @Qualifier("collegeService")
    private FindService findService;

    @RequestMapping("/updateCollege")
    @ResponseBody
    public String updateCollege(){
        List<College> list = (List<College>) listCollegeService.list(null);
        for(College university:list){
            ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
            dataObjectNode.put("groupname", university.getName());
            dataObjectNode.put("desc", university.getName());
            dataObjectNode.put("approval", false);
            dataObjectNode.put("public", false);
            dataObjectNode.put("maxusers", 200000);
            dataObjectNode.put("owner", "12345678910");
            ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
//        arrayNode.add("13012727843");
//		arrayNode.add("xiaojianguo003");
            dataObjectNode.put("members", arrayNode);
            ObjectNode creatChatGroupNode = creatChatGroups(dataObjectNode);

            Object[] params = new Object[]{university.getCoid(), creatChatGroupNode.get("data").get("groupid").textValue() };
            updateCollegeService.update(params);
//            System.out.println(creatChatGroupNode.toString());
//            System.out.println();

        }
        return toJSONString(SUCCESS);
    }

    @RequestMapping("/getGroupId")
    @ResponseBody
    public String getGroupId(String coid){
        Object[] params = new Object[]{coid };
        String college= (String) findService.findById(params);
        DataTip tip = new DataTip();
        tip.setData(college);
        return toJSONString(tip);
    }

    public static ObjectNode creatChatGroups(ObjectNode dataObjectNode) {
        ObjectNode objectNode = factory.objectNode();
        // check appKey format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", HxConstants.APPKEY)){
            LOGGER.error("Bad format of Appkey: " + HxConstants.APPKEY);
            objectNode.put("message", "Bad format of Appkey");
            return objectNode;
        }

        // check properties that must be provided
        if (!dataObjectNode.has("groupname")) {
            LOGGER.error("Property that named groupname must be provided .");
            objectNode.put("message", "Property that named groupname must be provided .");
            return objectNode;
        }
        if (!dataObjectNode.has("desc")) {
            LOGGER.error("Property that named desc must be provided .");
            objectNode.put("message", "Property that named desc must be provided .");
            return objectNode;
        }
        if (!dataObjectNode.has("public")) {
            LOGGER.error("Property that named public must be provided .");
            objectNode.put("message", "Property that named public must be provided .");
            return objectNode;
        }
        if (!dataObjectNode.has("approval")) {
            LOGGER.error("Property that named approval must be provided .");
            objectNode.put("message", "Property that named approval must be provided .");
            return objectNode;
        }
        if (!dataObjectNode.has("owner")) {
            LOGGER.error("Property that named owner must be provided .");
            objectNode.put("message", "Property that named owner must be provided .");
            return objectNode;
        }
        if (!dataObjectNode.has("members") || !dataObjectNode.path("members").isArray()) {
            LOGGER.error("Property that named members must be provided .");
            objectNode.put("message", "Property that named members must be provided .");
            return objectNode;
        }

        try {
            objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.CHATGROUPS_URL, MyToken.getCredential(), dataObjectNode,
                    HTTPMethod.METHOD_POST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }
}
