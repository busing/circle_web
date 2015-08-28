package com.circle.service.goodtype;

import java.util.List;
import java.util.Map;

import com.circle.pojo.goodtype.GoodType;
import com.xwtec.xwserver.exception.SPTException;

public interface IGoodTypeService {
	
	/**
	 * 查询商品类型列表数据
	 * @return
	 * @throws SPTException
	 */
	public List<Map<String, Object>> queryGoodTypes() throws SPTException;

	/**保存商品类型信息
	 * @param goodType
	 * @param argStr
	 * @param attrStr
	 * @return
	 */
	public boolean save(GoodType goodType, String argStr, String attrStr);

	/**
	 * 查询商品类型
	 * @param id
	 * @return
	 */
	public GoodType getGoodType(String id) throws SPTException;
	
	/**
	 * 查询商品类型参数
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getGoodTypeArg(String type_id) throws SPTException;
	
	/**
	 * 查询商品类型属性
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getGoodTypeAttr(String type_id) throws SPTException;
	
}
