package com.circle.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.circle.pojo.dict.DictBean;
import com.circle.pojo.farm.Farm;
import com.circle.pojo.goodtype.GoodType;
import com.circle.pojo.goodtype.GoodTypeArg;
import com.circle.pojo.goodtype.GoodTypeAttr;
import com.circle.pojo.goodtype.GoodTypeAttrValues;
import com.circle.pojo.msg.MessageTypeBean;

public class SystemDict{
	/**
	 * 注册发送短信
	 */
	public static final String SEND_MSG_STATUS_REG = "1";
	/**
	 * 修改密码发送短信
	 */
	public static final String SEND_MSG_STATUS_UPPASS = "2";
	
	/**
	 * 圈子审核消息
	 */
	public static final String MESSAGE_CIRCLE ="2";
	/**
	 * 普通消息
	 */
	public static final String MESSAGE_NORMAL ="1";
	/**
	 * 用户默认头像位置
	 */
	public static final String DEFAULT_USER_HEAD_PATH = "/images/def_adv.png";
	/**
	 * 圈子允许 不审核 就加入
	 */
	public static final String CIRCLE_AUTO_JOIN="8001";
	
	/**
	 * 圈子需要审核 
	 */
	public static final String CIRCLE_JOIN="8002";
	
	/**
	 * 普通用户
	 */
	public static final String NORMAL_USER ="1001";
	
	/**
	 * 农场主
	 */
	public static final String FARMER ="1002";
	/**
	 * 审核中
	 */
	public static final String EXAMINEING ="0";
	
	/**
	 * 审核完成
	 */
	public static final String EXAMINE_OVER ="1";
	
	
	/**
	 * 用户类型
	 */
	public static final String USER_TYPE="USER_TYPE";
	
	/**
	 * 登录结果
	 */
	public static final String LOGIN_RESULT="LOGIN_RESULT";
	
	/**
	 * 订单状态
	 */
	public static final String ORDER_STATUS="ORDER_STATUS";
	
	/**
	 * 下单成功
	 */
	public static final String ORDER_STATUS_SUCCESS="3001";
	
	/**
	 * 交易中
	 */
	public static final String ORDER_STATUS_DEAL="3002";
	
	/**
	 * 已完成
	 */
	public static final String ORDER_STATUS_COMPLETE="3003";
	
	/**
	 * 作废
	 */
	public static final String ORDER_STATUS_CANCEL="3004";
	
	
	/**
	 * 支付状态
	 */
	public static final String PAY_STATUS="PAY_STATUS";
	
	/**
	 * 未付款
	 */
	public static final String PAY_STATUS_NO="0";
	
	/**
	 * 未付款
	 */
	public static final String PAY_STATUS_YES="1";
	
	/**
	 * 发货状态
	 */
	public static final String SHIPPING_STATUS="SHIPPING_STATUS";
	
	/**
	 * 未配货
	 */
	public static final String SHIPPING_STATUS_PREPARE="5001";
	
	/**
	 * 配货中
	 */
	public static final String SHIPPING_STATUS_PREPAREING="5002";
	
	/**
	 * 配货完成
	 */
	public static final String SHIPPING_STATUS_NO="5003";
	
	/**
	 * 已发货
	 */
	public static final String SHIPPING_STATUS_YES="5004";
	
	/**
	 * 已收货
	 */
	public static final String SHIPPING_STATUS_SIGN="5005";
	
	/**
	 * 支付类型
	 */
	public static final String PAY_TYPE="PAY_TYPE";
	
	/**
	 * 加入方式
	 */
	public static final String JOIN_TYPE="JOIN_TYPE";
	
	/**
	 * 圈子历史类型
	 */
	public static final String CIRCLE_HISTORY_TYPE="CIRCLE_HISTORY_TYPE";
	
	/**
	 * 圈子历史类型-圈子公告
	 */
	public static final String CIRCLE_HISTORY_TYPE_INTRO="9001";
	
	/**
	 * 发放货物时间和地点
	 */
	public static final String CIRCLE_HISTORY_TYPE_ISSUE="9002";
	
	
	/**
	 * 圈子状态
	 * CIRCLE_STATUS 
	 */
	public static final String CIRCLE_STATUS="11";
	
	/**
	 * 删除
	 * CIRCLE_STATUS_DELETE 
	 */
	public static final String CIRCLE_STATUS_DELETE="11001";
	
	/**
	 * 待审核
	 * CIRCLE_STATUS_AUDIT 
	 */
	public static final String CIRCLE_STATUS_AUDIT="11002";
	
	/**
	 * 审核通过
	 * CIRCLE_STATUS_AUDIT_PASS 
	 */
	public static final String CIRCLE_STATUS_AUDIT_PASS="11003";
	
	/**
	 * 审核不通过
	 * CIRCLE_STATUS_AUDIT_REFUSE 
	 */
	public static final String CIRCLE_STATUS_AUDIT_REFUSE="11004";
	
	/**
	 * 删除
	 * CIRCLE_HISTORY_STATUS_DELETE 
	 */
	public static final String CIRCLE_HISTORY_STATUS_DELETE="12001";
	
	/**
	 * 待审核
	 * CIRCLE_HISTORY_STATUS_AUDIT 
	 */
	public static final String CIRCLE_HISTORY_STATUS_AUDIT="12002";
	
	/**
	 * 审核通过
	 * CIRCLE_HISTORY_STATUS_AUDIT_PASS 
	 */
	public static final String CIRCLE_HISTORY_STATUS_AUDIT_PASS="12003";
	
	/**
	 * 审核不通过
	 * CIRCLE_HISTORY_STATUS_AUDIT_REFUSE 
	 */
	public static final String CIRCLE_HISTORY_STATUS_AUDIT_REFUSE="12004";
	
	/**
	 * 手机消息类型
	 */
	public static final String MSG_TYPE="MSG_TYPE";
	
	/**
	 * 消息类型1
	 */
	public static final String MSG_TYPE_ONE = "1";

	/**
	 * 消息类型2
	 */
	public static final String MSG_TYPE_TWO = "2";
	
	
	/**
	 * 配送方式
	 * SHIPPING_TYPE 
	 */
	public static final String SHIPPING_TYPE="SHIPPING_TYPE";
	
	/**
	 * 农场主自提
	 * SHIPPING_TYPE_ONE 
	 */
	public static final String SHIPPING_TYPE_ONE="13001";
	
	
	/**
	 * 佣金类型
	 * COMMISSION_TYPE 
	 */
	public static final String COMMISSION_TYPE="COMMISSION_TYPE";
	
	/**
	 * 邀请注册佣金
	 * COMMISSION_TYPE 
	 */
	public static final String COMMISSION_TYPE_INVITE="14001";
	
	/**
	 * 下单提成
	 * COMMISSION_TYPE 
	 */
	public static final String COMMISSION_TYPE_ORDER="14002";
	
	/**
	 * 农场主售卖
	 * COMMISSION_TYPE 
	 */
	public static final String COMMISSION_TYPE_SELLER="14003";
	
	
	
	/**
	 * 单位
	 */
	public static final String UNIT="UNIT";
	
	/**
	 * 字典父级map
	 */
	public static Map<Integer, DictBean> PARENT_DICT_MAP=new HashMap<Integer, DictBean>();
	
	
	/**
	 * 字典map
	 */
	public static Map<String, List<DictBean>> DICT_MAP=new HashMap<String, List<DictBean>>();
	
	/**
	 * 消息类型map
	 */
	public static Map<String, MessageTypeBean> MESSAGE_TYPE_MAP=new HashMap<String, MessageTypeBean>();
	
	/**
	 * 商品类型
	 */
	public static List<GoodType> GOOD_TYPE=null;
	
	/**
	 * 农场信息
	 */
	public static List<Farm> FARM=null;
	
	/**
	 * 商品类型参数map
	 */
	public static Map<String, List<GoodTypeArg>> GOOD_TYPE_ARG_MAP=null;
	
	/**
	 * 商品类型属性map
	 */
	public static Map<String, List<GoodTypeAttr>> GOOD_TYPE_ATTR_MAP=null;
	
	/**
	 * 获取字典List
	 * @param type_code
	 * @return
	 */
	public static List<DictBean> getDictList(String type_code)
	{
		return DICT_MAP.get(type_code)==null?new ArrayList<DictBean>():DICT_MAP.get(type_code);
	}
	
	/**
	 * 获取商品属性list
	 * @param type_code
	 * @return
	 */
	public static List<GoodTypeAttr> getGoodTypeAttrList(String type_id)
	{
		return GOOD_TYPE_ATTR_MAP.get(type_id)==null?new ArrayList<GoodTypeAttr>():GOOD_TYPE_ATTR_MAP.get(type_id);
	}
	
	/**
	 * 获取商品属性list
	 * @param type_code
	 * @return
	 */
	public static List<GoodTypeArg> getGoodTypeArgList(String type_id)
	{
		return GOOD_TYPE_ARG_MAP.get(type_id)==null?new ArrayList<GoodTypeArg>():GOOD_TYPE_ARG_MAP.get(type_id);
	}
	
	/**
	 * 获取商品某个属性的后候选值
	 * @param type_code
	 * @return
	 */
	public static List<GoodTypeAttrValues> getGoodTypeAttrValueList(String type_id, String attr_id)
	{
		List<GoodTypeAttr> goodTypeList= GOOD_TYPE_ATTR_MAP.get(type_id)==null?new ArrayList<GoodTypeAttr>():GOOD_TYPE_ATTR_MAP.get(type_id);
		for (GoodTypeAttr goodTypeAttr : goodTypeList) {
			if((goodTypeAttr.getId()+"").equals(attr_id))
			{
				return goodTypeAttr.getAttr_value_list();
			}
		}
		return null;
	}
	
	
	/**
	 * 获取单个字段对象
	 * @param type_code
	 * @param type_id
	 * @return
	 */
	public static DictBean getDict(String type_code,String type_id)
	{
		List<DictBean> dictList=getDictList(type_code);
		if(dictList.size()>0)
		{
			for (DictBean dictBean : dictList) 
			{
				if(String.valueOf(dictBean.getType_id()).equals(type_id))
				{
					return dictBean;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 获取商品类型
	 * @param type_id
	 * @return
	 */
	public static GoodType getGoodType(String type_id)
	{
		for (GoodType type : GOOD_TYPE) {
			if(String.valueOf(type.getId()).equals(type_id))
			{
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 获取农场信息
	 * @param type_id
	 * @return
	 */
	public static Farm getFarm(String farm_id)
	{
		for (Farm f : FARM) {
			if(String.valueOf(f.getId()).equals(farm_id))
			{
				return f;
			}
		}
		return null;
	}
	
}
