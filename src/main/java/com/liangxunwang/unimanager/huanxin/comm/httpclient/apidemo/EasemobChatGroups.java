package com.liangxunwang.unimanager.huanxin.comm.httpclient.apidemo;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liangxunwang.unimanager.huanxin.comm.HTTPMethod;
import com.liangxunwang.unimanager.huanxin.comm.HxConstants;
import com.liangxunwang.unimanager.huanxin.comm.Roles;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.utils.HTTPClientUtils;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.vo.ClientSecretCredential;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.vo.Credential;
import com.liangxunwang.unimanager.huanxin.comm.httpclient.vo.EndPoints;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * REST API Demo : 群组管理 HttpClient4.3实现
 * 
 * Doc URL: http://www.easemob.com/docs/rest/groups/
 * 
 * @author Lynch 2014-09-15
 *
 */
public class EasemobChatGroups {

	private static Logger LOGGER = LoggerFactory.getLogger(EasemobChatGroups.class);

    // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(HxConstants.APP_CLIENT_ID,
            HxConstants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    public static void main(String[] args) {
		/** 获取APP中所有的群组ID 
		 * curl示例: 
		 * curl -X GET -i "https://a1.easemob.com/easemob-playground/test1/chatgroups" -H "Authorization: Bearer {token}"
		 */
		ObjectNode chatgroupidsNode = getAllChatgroupids();
//		System.out.println(chatgroupidsNode.toString());
		
//		/**
//		 * 获取一个或者多个群组的详情
//		 * curl示例
//		 * curl -X GET -i "https://a1.easemob.com/easemob-playground/test1/chatgroups/1414379474926191,1405735927133519"
//         * -H "Authorization: Bearer {token}"
//		 */
		String[] chatgroupIDs = {"1414379474926191", "1405735927133519"};
		ObjectNode groupDetailNode = getGroupDetailsByChatgroupid(chatgroupIDs);
		System.out.println(groupDetailNode.toString());
//
		/** 创建群组
		 * curl示例
		 * "groupname":"testrestgrp12", //群组名称，此属性为必须的
		 "desc":"server create group", //群组描述，此属性为必须的
		 "public":true, //是否是公开群，此属性为必须的
		 "maxusers":300, //群组成员最大数(包括群主)，值为数值类型,默认值200,此属性为可选的
		 "approval":true, //加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
		 "owner":"jma1", //群组的管理员，此属性为必须的
		 "members":["jma2","jma3"] //群组成员,此属性为可选的,但是如果加了此项,数组元素至少一个（注：群主jma1不需要写入到members里面）

		 * curl -X POST 'https://a1.easemob.com/easemob-playground/test1/chatgroups' -H 'Authorization: Bearer {token}'
         * -d '{"groupname":"测试群组","desc":"测试群组","public":true,"approval":true,"owner":"xiaojianguo001","maxusers":333,"members":["xiaojianguo002","xiaojianguo003"]}'
		 */
		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		dataObjectNode.put("groupname", "测试群组");
		dataObjectNode.put("desc", "测试群组");
		dataObjectNode.put("approval", true);
		dataObjectNode.put("public", true);
		dataObjectNode.put("maxusers", 2000);
		dataObjectNode.put("owner", "12345678910");
		ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
		arrayNode.add("13181038186");
//		arrayNode.add("xiaojianguo003");
		dataObjectNode.put("members", arrayNode);
		ObjectNode creatChatGroupNode = creatChatGroups(dataObjectNode);
//		System.out.println(creatChatGroupNode.toString());
//        System.out.println(creatChatGroupNode.get("data").get("groupid"));

//		/**
//		 * 删除群组
//		 * curl示例
//		 * curl -X DELETE 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519'
//         * -H 'Authorization: Bearer {token}'
//		 */
//		String toDelChatgroupid = "1405735927133519";
//		ObjectNode deleteChatGroupNode =  deleteChatGroups(toDelChatgroupid) ;
//		System.out.println(deleteChatGroupNode.toString());
//
		/**
		 * 获取群组中的所有成员
		 * curl示例
		 * curl -X GET 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519/users'
         * -H 'Authorization: Bearer {token}'
		 */
//		String chatgroupid = "1424921151301843";
//		ObjectNode getAllMemberssByGroupIdNode = getAllMemberssByGroupId(chatgroupid);
//		System.out.println(getAllMemberssByGroupIdNode.toString());
//
		/**
		 * 在群组中添加一个人
		 * curl示例
		 * curl -X POST 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519/users/xiaojianguo002'
         * -H 'Authorization: Bearer {token}'
		 */
//		String addToChatgroupid = "1424921462880987";
//		String toAddUsername = "142535946124915065286110";
//		ObjectNode addUserToGroupNode = addUserToGroup(addToChatgroupid, toAddUsername);
//		System.out.println(addUserToGroupNode.toString());

//		/**
//		 * 在群组中减少一个人
//		 * curl示例
//		 * curl -X DELETE 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519/users/xiaojianguo002'
//         * -H 'Authorization: Bearer {token}'
//		 */
//		String delFromChatgroupid = "1405735927133519";
//		String toRemoveUsername = "xiaojianguo002";
//		ObjectNode deleteUserFromGroupNode = deleteUserFromGroup(delFromChatgroupid, toRemoveUsername);
//		System.out.println(deleteUserFromGroupNode.asText());
//
//		/**
//		 * 获取一个用户参与的所有群组
//		 * curl示例
//		 * curl -X GET 'https://a1.easemob.com/easemob-playground/test1/users/xiaojianguo002/joined_chatgroups'
//         * -H 'Authorization: Bearer {token}'
//		 */
//		String username = "xiaojianguo002";
//		ObjectNode getJoinedChatgroupsForIMUserNode = getJoinedChatgroupsForIMUser(username);
//		System.out.println(getJoinedChatgroupsForIMUserNode.toString());
//
//		/**
//		 * 群组批量添加成员
//		 * curl示例
//		 * curl -X POST -i 'https://a1.easemob.com/easemob-playground/test1/chatgroups/1405735927133519/users' -H 'Authorization: Bearer {token}' -d '{"usernames":["xiaojianguo002","xiaojianguo003"]}'
//		 */
//		String toAddBacthChatgroupid = "1405735927133519";
//		ArrayNode usernames = JsonNodeFactory.instance.arrayNode();
//		usernames.add("xiaojianguo002");
//		usernames.add("xiaojianguo003");
//		ObjectNode usernamesNode = JsonNodeFactory.instance.objectNode();
//		usernamesNode.put("usernames", usernames);
//		ObjectNode addUserToGroupBatchNode = addUsersToGroupBatch(toAddBacthChatgroupid, usernamesNode);
//		System.out.println(addUserToGroupBatchNode.toString());
	}
	
	private static JsonNodeFactory factory = new JsonNodeFactory(false);
	private static final String APPKEY = HxConstants.APPKEY;
	
	/**
	 * 获取APP中所有的群组ID
	 * 
	 * @return
	 */
	public static ObjectNode getAllChatgroupids() {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		try {
			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.CHATGROUPS_URL, credential, null,
                    HTTPMethod.METHOD_GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 获取一个或者多个群组的详情
	 * 
	 * @return
	 */
	public static ObjectNode getGroupDetailsByChatgroupid(String[] chatgroupIDs) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		try {
			URL groupDetailsByChatgroupidUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/")
					+ "/chatgroups/" + chatgroupIDs.toString());
			objectNode = HTTPClientUtils.sendHTTPRequest(groupDetailsByChatgroupidUrl, credential, null,
					HTTPMethod.METHOD_GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 创建群组
	 * 
	 */
	public static ObjectNode creatChatGroups(ObjectNode dataObjectNode) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
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
			objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.CHATGROUPS_URL, credential, dataObjectNode,
					HTTPMethod.METHOD_POST);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 删除群组
	 * 
	 */
	public static ObjectNode deleteChatGroups(String chatgroupid) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		try {
			URL deleteChatGroupsUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/") + "/chatgroups/"
					+ chatgroupid);
			objectNode = HTTPClientUtils.sendHTTPRequest(deleteChatGroupsUrl, credential, null,
					HTTPMethod.METHOD_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 获取群组中的所有成员
	 * 
	 */
	public static ObjectNode getAllMemberssByGroupId(String chatgroupid) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		try {
			URL allMemberssByGroupIdUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/") + "/chatgroups/"
					+ chatgroupid + "/users");
			objectNode = HTTPClientUtils.sendHTTPRequest(allMemberssByGroupIdUrl, credential, null,
					HTTPMethod.METHOD_GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 在群组中添加一个人
	 * 
	 */
	public static ObjectNode addUserToGroup(String chatgroupid, String userprimarykey) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		try {
			URL allMemberssByGroupIdUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/") + "/chatgroups/"
					+ chatgroupid + "/users/" + userprimarykey);
			ObjectNode dataobjectNode = factory.objectNode();
			objectNode = HTTPClientUtils.sendHTTPRequest(allMemberssByGroupIdUrl, credential, dataobjectNode,
					HTTPMethod.METHOD_POST);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

	/**
	 * 在群组中减少一个人
	 * 
	 */
	public static ObjectNode deleteUserFromGroup(String chatgroupid, String userprimarykey) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}

		try {
			URL allMemberssByGroupIdUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/") + "/chatgroups/"
					+ chatgroupid + "/users/" + userprimarykey);
			objectNode = HTTPClientUtils.sendHTTPRequest(allMemberssByGroupIdUrl, credential, null,
					HTTPMethod.METHOD_DELETE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	/**
	 * 获取一个用户参与的所有群组
	 * 
	 * @param username
	 * @return
	 */
	private static ObjectNode getJoinedChatgroupsForIMUser(String username) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}
		if (StringUtils.isBlank(username.trim())) {
			LOGGER.error("Property that named username must be provided .");
			objectNode.put("message", "Property that named username must be provided .");
			return objectNode;
		}

		try {
			URL getJoinedChatgroupsForIMUserUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/")
					+ "/users/" + username + "/joined_chatgroups");
			objectNode = HTTPClientUtils.sendHTTPRequest(getJoinedChatgroupsForIMUserUrl, credential, null,
					HTTPMethod.METHOD_GET);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	/**
	 * 群组批量添加成员
	 * 
	 * @param toAddBacthChatgroupid
	 * @param usernames
	 * @return
	 */
	private static ObjectNode addUsersToGroupBatch(String toAddBacthChatgroupid, ObjectNode usernames) {
		ObjectNode objectNode = factory.objectNode();
		// check appKey format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
			LOGGER.error("Bad format of Appkey: " + APPKEY);
			objectNode.put("message", "Bad format of Appkey");
			return objectNode;
		}
		if (StringUtils.isBlank(toAddBacthChatgroupid.trim())) {
			LOGGER.error("Property that named toAddBacthChatgroupid must be provided .");
			objectNode.put("message", "Property that named toAddBacthChatgroupid must be provided .");
			return objectNode;
		}
		// check properties that must be provided
		if (null != usernames && !usernames.has("usernames")) {
			LOGGER.error("Property that named usernames must be provided .");
			objectNode.put("message", "Property that named usernames must be provided .");
			return objectNode;
		}

		try {
			URL getJoinedChatgroupsForIMUserUrl = HTTPClientUtils.getURL(HxConstants.APPKEY.replace("#", "/")
					+ "/chatgroups/" + toAddBacthChatgroupid + "/users");
			objectNode = HTTPClientUtils.sendHTTPRequest(getJoinedChatgroupsForIMUserUrl, credential, usernames,
					HTTPMethod.METHOD_POST);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}

}
