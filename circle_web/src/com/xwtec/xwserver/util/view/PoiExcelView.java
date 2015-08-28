package com.xwtec.xwserver.util.view;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.xwtec.xwserver.constant.ConstantBusiKeys.PoiExcelKey;
import com.xwtec.xwserver.util.ExcelUtil;

/**
 * 
 * AbstractExcelView的实现类  . <br>
 * 实现方法buildExcelDocument 以做EXCEL下载
 * <p>
 * Copyright: Copyright (c) 2013-11-21 下午4:10:08
 * <p>
 * Company: 欣网视讯
 * <p>
 * @author caozhenxing@mail.xwtec.cn
 * @version 1.0.0
 */
public class PoiExcelView extends AbstractExcelView {
	
	private static final Log logger = LogFactory.getLog(PoiExcelView.class) ;
	
	/**
	 * EXCEL名称
	 */
	private String excelName ;
	
	/**
	 * excel 的 sheet 名称
	 */
	private String sheetName ;
	
	/**
	 * 需要导出的数据list  该LIST内第一个MAP的 KEY值设为表头
	 */
	List<Map<String, Object>> list ;

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		if(excelName != null && !"".equals(excelName))
			this.excelName = excelName;
		else
			this.excelName = PoiExcelKey.POI_EXCELNAME ;
	}
	
	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		if(sheetName != null && !"".equals(sheetName))
			this.sheetName = sheetName;
		else
			this.sheetName = PoiExcelKey.POI_SHEETNAME ;
	}
	

	public List<Map<String, Object>> getList() {
		return list;
	}
	
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	
	/**
	 * 全属性构造器
	 * @param excelName
	 * @param sheetName
	 * @param list
	 */
	public PoiExcelView(String excelName, String sheetName,
			List<Map<String, Object>> list) {
		super();
		this.setExcelName(excelName) ;
		this.setSheetName(sheetName) ;
		this.list = list;
	}

	/**
	 * 默认构造器
	 */
	public PoiExcelView() {
		super();
		this.excelName = PoiExcelKey.POI_EXCELNAME ;
		this.sheetName = PoiExcelKey.POI_SHEETNAME ;
	}

	protected void buildExcelDocument(Map<String, Object> paramMap,
			HSSFWorkbook workBook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("[PoiExcelView.buildExcelDocument:msg] start...") ;

		response.setContentType("APPLICATION/OCTET-STREAM");  
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));  
		
		/**
		 * 获取输出流
		 */
		OutputStream outPutStream = response.getOutputStream();  
		
		/**
		 * 调用ExcelUtil.assembleExcel组装EXCEL
		 */
		Workbook workbook = ExcelUtil.assembleExcel(list, excelName,sheetName) ;
		/**
		 * 写出EXCEL
		 */
		workbook.write(outPutStream);
		
		/**
		 * 强制输出输出流中的数据
		 */
		outPutStream.flush() ;
		/**
		 * 关闭输出流
		 */
		outPutStream.close() ;
		logger.debug("[PoiExcelView.buildExcelDocument:msg] end...") ;
	}

}
