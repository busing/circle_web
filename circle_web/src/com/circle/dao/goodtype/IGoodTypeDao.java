package com.circle.dao.goodtype;

import java.util.List;
import java.util.Map;

import com.circle.pojo.goodtype.GoodType;
import com.xwtec.xwserver.exception.SPTException;

public interface IGoodTypeDao {

	/**
	 * 查询商品类型
	 * @return
	 * @throws SPTException
	 */
	public List<Map<String, Object>> queryGoodTypes() throws SPTException;

	/**
	 * 保存商品类型
	 * @param goodType
	 * @return
	 * @throws SPTException
	 */
	public boolean save(GoodType goodType)  throws SPTException;
	
	
	/**
	 * 更新商品类型
	 * @param goodType
	 * @return
	 * @throws SPTException
	 */
	public boolean update(GoodType goodType) throws SPTException;

	/**
	 * 查询商品类型
	 * @param id
	 * @return
	 */
	public GoodType getGoodType(String id)  throws SPTException;

	/**
	 * 删除类型对应的参数配置
	 * @param id
	 */
	public void deleteArg(int typeId)  throws SPTException;

	/**
	 * 删除类型对应的属性配置
	 * @param id
	 */
	public void deleteAttr(int typeId)  throws SPTException;

	/**
	 * 保存类型对应的参数
	 * @param string
	 * @param string2
	 * @param id
	 */
	public void saveArg(String name, String english_name, int typeId)  throws SPTException;

	/**
	 * 保存类型对应的属性
	 * @param string
	 * @param string2
	 * @param id
	 */
	public void saveAttr(String name, String english_name, String attrValues, int typeId)  throws SPTException;
	

	/**
	 * 查询商品类型参数配置列表
	 * @param type_id
	 * @return
	 */
	public List<Map<String, Object>> queryGoodTypeArgList(String type_id)  throws SPTException ;

	/**
	 * 查询商品类型属性配置列表
	 * @param type_id
	 * @return
	 */
	public List<Map<String, Object>> queryGoodTypeAttrList(String type_id)  throws SPTException ;
}
