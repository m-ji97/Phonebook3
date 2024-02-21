package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;


@WebServlet("/pbc")
public class PhonbookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;

	//생성자-기본 생성자 사용

	//메소드-gs

	//메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PhonbookController.goGet()");

		String action = request.getParameter("action");
		System.out.println(action);

		if("wform".equals(action)) {
			System.out.println("wform:등록폼");

			//jsp한테 html그리기 응답해라 ==>포워드
			/*RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);*/
			
			WebUtil.forward(request, response, "/WEB-INF/writeForm.jsp");			
			/*
			WebUtil.forward("/writeForm.jsp",request,response);
			redirec("/phonebook3/pbc?action=list",request,response);
			*/

		}else if("insert".equals(action)){
			System.out.println("insert:등록");

			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");


			/* vo로 묶기 */
			PersonVo personvo = new PersonVo(name, hp, company);
			System.out.println(personvo.toString());


			// db관련 업무
			PhoneDao phoneDao = new PhoneDao();

			// db에 저장
			phoneDao.personInsert(personvo);

			/*// 리다이렉트
			http://localhost:8080/phonebook3/pbc?action=list 엔터 효과를 낸다.*/

			//response.sendRedirect("/phonebook3/pbc?action=list");
			
			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
			
			/*redirect는 완성된 내용을 다시 보여주는 것, 수정 하기 편함*/

			// db에서 전체 데이터 가져오기
			/*List<PersonVo> personList = phoneDao.personSelect();

			request.setAttribute("personList", personList);

			//포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);  forward는 내부에서 이루어지는 것*/

		}else if("delete".equals(action)) {
			System.out.println("delete:삭제");
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);

			// db사용
			PhoneDao phoneDao = new PhoneDao();

			// 삭제
			phoneDao.personDelete(no);

			//리다이렉트
			/*
			response.sendRedirect("http://localhost:8080/phonebook3/pbc?action=list");
			*/
			//webUtil webutil = new webUtil();
			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");

		}else if("eform".equals(action)){
			System.out.println("eform:수정폼");

			WebUtil.forward(request, response, "/WEB-INF/editForm.jsp");
		
		}else if("edit".equals(action)){
			System.out.println("edit:수정");
			
			//수정내용 확인하기 -- 공부!
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");


			/* vo로 묶기 */
			PersonVo personvo = new PersonVo(name, hp, company);
			System.out.println(personvo.toString());


			// db관련 업무
			PhoneDao phoneDao = new PhoneDao();

			// db에 저장
			phoneDao.personInsert(personvo);

			/*// 리다이렉트
			http://localhost:8080/phonebook3/pbc?action=list 엔터 효과를 낸다.*/

			//response.sendRedirect("/phonebook3/pbc?action=list");
			
			WebUtil.redirect(request, response, "/phonebook3/pbc?action=list");
		
		}else {
			
			System.out.println("list:리스트");

			// db사용
			PhoneDao phonedao = new PhoneDao();

			// 리스트 가져오기
			List<PersonVo> personList = phonedao.personSelect();

			// 데이터담기 포워드
			request.setAttribute("personList", personList);
			
			/*
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/
			
			//webUtil webutil = new webUtil();
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

