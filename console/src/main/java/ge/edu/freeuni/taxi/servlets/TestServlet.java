package ge.edu.freeuni.taxi.servlets;

import ge.edu.freeuni.taxi.services.TestService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("testMessage", TestService.getTestMessage());
		RequestDispatcher dispatch = request.getRequestDispatcher("user-welcome.jsp");
		dispatch.forward(request, response);
	}
}
