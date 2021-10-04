package com.mine.util;

import com.google.gson.GsonBuilder;
import com.mine.exception.AppException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.primefaces.event.FileUploadEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public abstract class Importer<T extends Serializable> {
	protected Class<T> domainClass = getDomainClass();
	private Map<Integer,String> indexMapFieldClass = getIndexMapFieldClass();
	private String dateFormat = "MM/dd/yyyy";
	protected abstract Class<T> getDomainClass();
	/**
	 * 
	 * @return Map<Integer,String>
	 * <br>Start column = 0
	 * @author huynx6
	 * 
	 */
	protected abstract Map<Integer,String> getIndexMapFieldClass();
	protected abstract String getDateFormat();
	private Map<Integer,String> mapHeader = getMapHeader();
	public Importer() {
		super();
		if (getDateFormat()!=null)
			dateFormat = getDateFormat();
	}
	@SuppressWarnings("unchecked")
	public List<T> getDatas(FileUploadEvent event, int sheetNumber, List<String>ignoreFields){
		try {
			InputStream inputStream = event.getFile().getInputstream();
			//Get the workbook instance for XLS/xlsx file 
			Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(inputStream);
				if (workbook==null)
					throw new NullPointerException();
			} catch (InvalidFormatException e2) {
				e2.printStackTrace();
				throw new AppException("File import pháº£i lÃ  Excel 97-2012 (xls, xlsx)!");
			}
		
			List<T> list = new ArrayList<T>();
			Sheet sheet = workbook.getSheetAt(sheetNumber);
			List<String> headers = new ArrayList<>();
			boolean isReadHeader = false;
			Field[] fieldlist = new Field[0] ;
			String sLine="";
			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator
					.hasNext();) {
				Row row = (Row) rowIterator.next();
				//read header row
				sLine="";
				if (!isReadHeader){
					for (Iterator<Cell> cellIterator = row.iterator(); cellIterator
							.hasNext();) {
						Cell cell = (Cell) cellIterator.next();
						int i=0;
						switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								String header = cell.getStringCellValue();
								header = Pattern.compile("\\s?").matcher(header).replaceAll("");
								header = Pattern.compile("-?").matcher(header).replaceAll("");
								header = Pattern.compile("_?").matcher(header).replaceAll("");
								headers.add(i++,header);
									break;
							default:
								break;
						}
						
					}
					int countField=0;
					try {
						Class<?> cls = domainClass;
						fieldlist = cls.getDeclaredFields();
						countField = fieldlist.length-ignoreFields.size();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					if(headers.size() != countField)
						throw new AppException("Sá»‘ trÆ°á»�ng trong file khÃ¡c sá»‘ cá»™t trong báº£ng");
					isReadHeader=true;
				}else{//Read data
					Class<?> obj = domainClass;
					int index=1;
				    Object objInstance = obj.newInstance();
					for (Iterator<Cell> cellIterator = row.iterator(); cellIterator
							.hasNext();) {
						Cell cell = (Cell) cellIterator.next();
						Object cellValue = null;
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_BLANK:
							cellValue = new String();
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							cellValue = cell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							cell.setCellType(Cell.CELL_TYPE_STRING);
						case Cell.CELL_TYPE_STRING:
						default:
							cellValue = cell.getStringCellValue();
							break;
						}
						sLine +=cellValue.toString();
						for (Field field : fieldlist) {
							if (headers.get(headers.size()-index).equalsIgnoreCase(field.getName()) 
									&& !ignoreFields.contains(field.getName())){
								try {
									if (field.getType().getName().contains("java.lang.Long")) {
										PropertyUtils.setSimpleProperty(objInstance, field.getName(), Long.parseLong(cellValue.toString()));
									} else if (field.getType().getName().contains("java.lang.Integer"))  {
										PropertyUtils.setSimpleProperty(objInstance, field.getName(), Integer.parseInt(cellValue.toString()));
									} else if (field.getType().getName().contains("java.lang.String"))  {
										PropertyUtils.setSimpleProperty(objInstance, field.getName(), cellValue);
									} else if (field.getType().getName().contains("Date"))  {
										
									}
									
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								}
								index++;
								break;
							}
						}
						
					}
					if(sLine.trim().length()==0)
						break;
					list.add((T) objInstance);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<T> getDatas(FileUploadEvent event, Integer sheetNumber, String sline, String sheetName){
		try {
			return getDatas(event.getFile().getInputstream(), sheetNumber, sline, sheetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<T> getDatas(InputStream inputStream, Integer sheetNumber,String sline, String sheetName){
		
		try {
			//Get the workbook instance for XLS/xlsx file 
			Workbook workbook = null;
			try {
				workbook = WorkbookFactory.create(inputStream);
				if (workbook==null)
					throw new NullPointerException();
			} catch (InvalidFormatException e2) {
				e2.printStackTrace();
				throw new AppException("File import pháº£i lÃ  Excel 97-2012 (xls, xlsx)!");
			}
			String[] mulLine = sline.split(",",-1);
			 
			List<T> list = new LinkedList<T>();
			Sheet sheet = null;
			if (sheetNumber!=null)
				sheet = workbook.getSheetAt(sheetNumber);
			else if (sheetName!=null)
				sheet = workbook.getSheet(sheetName);
			List<Field> fields = new ArrayList<>();
			try {
				Class<?> cls = domainClass;
				fields = Arrays.asList(cls.getDeclaredFields());
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			int line=1;
			String sLine="";
			boolean isFindHeader =false;
			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator
					.hasNext();) {
				Row row = (Row) rowIterator.next();
				boolean cont = false;
				sLine="";
				try {
					if (Pattern.compile("\""+line+"\"").matcher(new GsonBuilder().create().toJson(mulLine)).find())
						cont = true;
					else {
						for (String _sline : mulLine) {
							String[] strip = _sline.split("-",-1);
							if (strip.length ==1) {
								if(line == Integer.parseInt(strip[0])){
									cont=true;
									break;
								}
							}else if (strip.length ==2) {
								if(strip[1].length()==0){
									if(line >= Integer.parseInt(strip[0])){
										cont=true;
										break;
									}
								}else if(line >= Integer.parseInt(strip[0]) && line <= Integer.parseInt(strip[1]) ){
										cont=true;
										break;
								}
								
							}
							
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				line++;
				if(!cont)
					continue;
				if(mapHeader!=null){
					
					if(!isFindHeader){
						boolean isCont =false;
						for(int i=row.getFirstCellNum();i<row.getLastCellNum();i++){
							if(row.getCell(i).getCellType()== Cell.CELL_TYPE_STRING && mapHeader.get(1).equalsIgnoreCase(row.getCell(i).getStringCellValue())){
								isFindHeader=true;
								isCont = true;
								if(i>0){
									Map<Integer, String>_indexMapFieldClass = new HashMap<Integer, String>();
									for (Iterator<Integer> iterator = indexMapFieldClass.keySet().iterator(); iterator.hasNext();) {
										Integer col = (Integer) iterator.next();
										_indexMapFieldClass.put(i+col, indexMapFieldClass.get(col));
									}
									indexMapFieldClass.clear();
									indexMapFieldClass.putAll(_indexMapFieldClass);
								}
								break;
							}
						}
						if(isCont)
							continue;
					}
					if(!isFindHeader)
						continue;
					
					
				}
				{//Read data
					Class<?> obj = domainClass;
					
				    Object objInstance = obj.newInstance();
				    for (Iterator<Integer> columnIndex = indexMapFieldClass.keySet().iterator(); columnIndex
				    		.hasNext();) {
				    	Integer nColumn = columnIndex.next();
				    	Cell cell ;
				    	if( row.getCell(nColumn) == null ){
	                        cell = row.createCell(nColumn);
	                    } else {
	                        cell = row.getCell(nColumn);
	                    }
				    	if(fields.toString().contains(domainClass.getName()+"."+indexMapFieldClass.get(nColumn))){
				    		Object cellValue = null;
				    		switch (cell.getCellType()) {
				    		case Cell.CELL_TYPE_BLANK:
				    			cellValue = new String();
				    			break;
				    		case Cell.CELL_TYPE_BOOLEAN:
				    			cellValue = cell.getBooleanCellValue();
				    			break;
				    		case Cell.CELL_TYPE_NUMERIC:
				    			cell.setCellType(Cell.CELL_TYPE_STRING);
				    		case Cell.CELL_TYPE_STRING:
				    		default:
				    			cellValue = cell.getStringCellValue();
				    			break;
				    		}
				    		sLine +=cellValue.toString();
				    		try {
				    			//Field field = fields.get(index);
				    			for (Field field : fields) {
									if(field.getName().equalsIgnoreCase(indexMapFieldClass.get(nColumn))){
										if (field .getType().getName().contains("java.lang.Long")) {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), Long.parseLong(cellValue.toString()));
										} else if (field.getType().getName().contains("java.lang.Integer"))  {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), Integer.parseInt(cellValue.toString()));
										} else if (field.getType().getName().contains("java.lang.String"))  {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), cellValue);
										} else if (field.getType().getName().contains("Date"))  {
											DateFormat formatter = new SimpleDateFormat(dateFormat);
											PropertyUtils.setSimpleProperty(objInstance, field.getName(),formatter.parse(cellValue.toString()) );
										} else if (field.getType().getName().contains("java.lang.Object"))  {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), cellValue);
										}
										break;
									}
				    			}

				    		} catch (IllegalArgumentException e) {
//				    			e.printStackTrace();
				    		}
				    	}
				    }
				    if(sLine.trim().length()==0)
				    	break;
					list.add((T) objInstance);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<T> getDatas2(Workbook workbook , Integer sheetNumber, String sline, String sheetName){
		
		try {
			//Get the workbook instance for XLS/xlsx file 
			if (workbook==null)
				throw new NullPointerException();
			String[] mulLine = sline.split(",",-1);
			 
			List<T> list = new ArrayList<T>();
			Sheet sheet = null;
			if (sheetNumber!=null)
				sheet = workbook.getSheetAt(sheetNumber);
			else if (sheetName!=null)
				sheet = workbook.getSheet(sheetName);
			List<Field> fields = new ArrayList<>();
			try {
				Class<?> cls = domainClass;
				fields = Arrays.asList(cls.getDeclaredFields());
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			int line=1;
			String sLine="";
			
			// Doc ten ngon ngu dang import
			sheet.getRow(4).getCell(5).getStringCellValue();
			
			
			for (Iterator<Row> rowIterator = sheet.iterator(); rowIterator
					.hasNext();) {
//				Word myWord = new Word();
				Row row = (Row) rowIterator.next();
				
				
				
				
				
				
				
				boolean cont = false;
				sLine="";
				try {
					if (Pattern.compile("\""+line+"\"").matcher(new GsonBuilder().create().toJson(mulLine)).find())
						cont = true;
					else {
						for (String _sline : mulLine) {
							String[] strip = _sline.split("-",-1);
							if (strip.length ==1) {
								if(line == Integer.parseInt(strip[0])){
									cont=true;
									break;
								}
							}else if (strip.length ==2) {
								if(strip[1].length()==0){
									if(line >= Integer.parseInt(strip[0])){
										cont=true;
										break;
									}
								}else if(line >= Integer.parseInt(strip[0]) && line <= Integer.parseInt(strip[1]) ){
										cont=true;
										break;
								}
								
							}
							
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				line++;
				if(!cont)
					continue;
				{//Read data
					Class<?> obj = domainClass;
					
				    Object objInstance = obj.newInstance();
				    for (Iterator<Integer> columnIndex = indexMapFieldClass.keySet().iterator(); columnIndex
				    		.hasNext();) {
				    	Integer nColumn = columnIndex.next();
				    	Cell cell ;
				    	if( row.getCell(nColumn) == null ){
	                        cell = row.createCell(nColumn);
	                    } else {
	                        cell = row.getCell(nColumn);
	                    }
				    	if(fields.toString().contains(domainClass.getName()+"."+indexMapFieldClass.get(nColumn))){
				    		Object cellValue = null;
				    		switch (cell.getCellType()) {
				    		case Cell.CELL_TYPE_BLANK:
				    			cellValue = new String();
				    			break;
				    		case Cell.CELL_TYPE_BOOLEAN:
				    			cellValue = cell.getBooleanCellValue();
				    			break;
				    		case Cell.CELL_TYPE_NUMERIC:
				    			cell.setCellType(Cell.CELL_TYPE_STRING);
				    		case Cell.CELL_TYPE_STRING:
				    		default:
				    			cellValue = cell.getStringCellValue();
				    			break;
				    		}
				    		sLine +=cellValue.toString();
				    		try {
				    			//Field field = fields.get(index);
				    			for (Field field : fields) {
									if(field.getName().equalsIgnoreCase(indexMapFieldClass.get(nColumn))){
										if (field .getType().getName().contains("java.lang.Long")) {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), Long.parseLong(cellValue.toString()));
										} else if (field.getType().getName().contains("java.lang.Integer"))  {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), Integer.parseInt(cellValue.toString()));
										} else if (field.getType().getName().contains("java.lang.String"))  {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), cellValue);
										} else if (field.getType().getName().contains("Date"))  {
											DateFormat formatter = new SimpleDateFormat(dateFormat);
											PropertyUtils.setSimpleProperty(objInstance, field.getName(),formatter.parse(cellValue.toString()) );
										} else if (field.getType().getName().contains("java.lang.Object"))  {
											PropertyUtils.setSimpleProperty(objInstance, field.getName(), cellValue);
										}
										break;
									}
				    			}

				    		} catch (IllegalArgumentException e) {
//				    			e.printStackTrace();
				    		}
				    	}
				    }
				    if(sLine.length()==0)
				    	break;
					list.add((T) objInstance);
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public void setIndexMapFieldClass(Map<Integer,String> indexMapFieldClass) {
		this.indexMapFieldClass = indexMapFieldClass;
	}
	public Map<Integer,String> getMapHeader() {
		return mapHeader;
	}
	public void setMapHeader(Map<Integer,String> mapHeader) {
		this.mapHeader = mapHeader;
	}
}
