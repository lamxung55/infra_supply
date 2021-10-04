package com.mine.webservice;//package com.mine.webservice;
//
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.MatchMode;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//
//import com.google.gson.GsonBuilder;
//import com.mine.model.Word;
//import com.mine.persistence.WordServiceImpl;
//import ConditionQuery;
//import OrderBy;
//
//@WebServlet("/searchWord")
//public class SearchWord extends HttpServlet {
//
//	private static final long serialVersionUID = 1L;
//
//	WordServiceImpl wordService;
//
//	public SearchWord() {
//		wordService = new WordServiceImpl();
//	}
//
//	protected void processRequest(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		PrintWriter out = new PrintWriter(new OutputStreamWriter(
//				response.getOutputStream(), StandardCharsets.UTF_8), true);
//		String jResult = "";
//		try {
//			response.setContentType("text/plain;charset=UTF-8");
//			request.setCharacterEncoding("UTF-8");
//			Object attribute = request.getSession().getAttribute("login");
//			attribute = true;
//			if (attribute == null)
//				return;
//			boolean login = Boolean.parseBoolean(attribute.toString());
//			if (login) {
//				String name = request.getParameter("keyword");
//				String type = request.getParameter("type");
//				String detail = request.getParameter("detail");
//				String domain = request.getParameter("domain");
//				if (type != null && type.equals("word_only")) {
//					List<?> words = searchWordOnly(name, domain);
//					jResult = new GsonBuilder().create().toJson(words);
//					return;
//				}
//				// String[] ipa1 = new
//				// String[]{"Ã²a","Ã³a","á»�a","Ãµa","á»�a","Ã²e","Ã³e","á»�e","Ãµe","á»�e","Ã¹y","Ãºy","á»§y","Å©y",
//				// "á»¥y"};
//				// String[] ipa2 = new
//				// String[]{"oÃ ","oÃ¡","oÃ£","Ãµa","oáº¡","oÃ¨","oÃ©","oáº»","oáº½","oáº¹","uá»³","uÃ½","uá»·","uá»·",
//				// "uá»µ"};
//				// if(name!=null)
//				// for(int i=0; i< ipa2.length; i++)
//				// name = name.replace(ipa2[i],ipa1[i]);
//
//				Object pageNumber = request.getParameter("pageNumber");
//				if (pageNumber == null)
//					pageNumber = 1;
//				else
//					pageNumber = Integer.parseInt(pageNumber.toString());
//				Object pageSize = request.getParameter("pageSize");
//				if (pageSize == null)
//					pageSize = 10;
//				else
//					pageSize = Integer.parseInt(pageSize.toString());
//
//				OrderBy orderBy = new OrderBy();
//				orderBy.add(Order.asc("word"));
//				ConditionQuery query = new ConditionQuery();
//				List<Criterion> predicates = new ArrayList<Criterion>();
//				predicates.add(Restrictions.eq("word", name));
//				query.add(Restrictions.or(predicates
//						.toArray(new Criterion[predicates.size()])));
//				List<Word> words = wordService.findList(query, orderBy,
//						(int) -1, (int) -1);
//				List<Long> mark = new ArrayList<>();
//				for (Word word : words) {
//					mark.add(word.getId().longValue());
//				}
//				int lastSize = words.size();
//				for (int i = 0; i < lastSize; i++) {
//					Word word = words.get(i);
//					List<Word> lstWordInGroup = word.getGroups();
//					for (Word wordInGrp : lstWordInGroup) {
//						if (!mark.contains(wordInGrp.getId().longValue())) {
//							words.add(wordInGrp);
//							mark.add(wordInGrp.getId().longValue());
//						}
//					}
//				}
//				if (words.size() > 0) {
//					// words.get(0).getGroups();
//					words.get(0).getWordInTags();
//				}
//				// if (detail != null && "true".equalsIgnoreCase(detail)) {
//				// Word wordDetail = words.get(0);
//				// words.clear();
//				// words.add(wordDetail);
//				// }
//				jResult = new GsonBuilder()
//						.setDateFormat("dd/MM/yyyy hh:mm aaa").create()
//						.toJson(words);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			out.println(jResult);
//		}
//	}
//
//	public List<?> searchWordOnly(String key, String domain) {
//		List<?> words;
//		if (domain != null && domain.trim().length() > 0) {
//			words = wordService
//					.findList(
//							"select DISTINCT word from Word where domain.name = ? and lower(word) LIKE lower('%' || ? || '%' )",
//							1, 10, domain, key);
//		} else {
//			words = wordService
//					.findList(
//							"select DISTINCT word from Word where lower(word) LIKE lower('%' || ? || '%' )",
//							1, 10, key);
//		}
//		return words;
//	}
//
//	protected void doGet(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		processRequest(request, response);
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request,
//			HttpServletResponse response) throws ServletException, IOException {
//		processRequest(request, response);
//	}
//
//	public static void main(String[] args) {
//		List<Long> lst = new ArrayList<>();
//		lst.add(1L);
//		lst.add(3L);
//		lst.add(5L);
//		if (lst.contains(3L)) {
//			System.out.println("OK");
//		}
//	}
//
//}
