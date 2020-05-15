package PEBB;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Check extends HttpServlet { //����� ��� �������� �������� ������ � ������ 
	private static final long serialVersionUID = 1L;
	public static String login, password;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        //��������� �������� ������ � ������ � ����� HTML-��������
        login = request.getParameter("login");
        password = request.getParameter("password");
        String appPath = request.getServletContext().getRealPath("");
        /*
         * ����� ������ �������� �������� ������ � ������
         * ���� ����� ���������� true, �� ������������ �������������� �� �������� ������������
         * ����� ������������ ��������� ������
         */
        if(checkLogAndPass(appPath)) 
        	response.sendRedirect("MainView.html"); //��������������� �� �������� ������������
        else
        	/*
    		 * ����� ������, ���������� � ���� HTML-����, 
    		 * � ������� ����������� ����������� �������� �� �������� �����������
    		 * ��� ��������� ������� �����
    		 */
        	writer.println("<html>" +
        			"			<head>" + 
        			"				<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" + 
        			"				<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">" + 
        			"               <title>Incorrect login or passwrod</title>" +
        			"			</head>" +
		        	"			<body>" + 
		        	"       		<form class=\"w3-container w3-card-4\" action=\"Authorization.html\">" +
		        	"                   <h2 class=\"w3-text-teal\">Login or password is incorrect</h2>" +
		        	"					<p><button class=\"w3-btn w3-teal\">Try again</button></p>" + 
		        	"       		</form>" +
		        	"			</body>" +
		        	"		</html>");
	}
	
	public static boolean checkLogAndPass(String appPath) throws IOException {//����� �������� ��������� ������ � ������

		FileReader read = new FileReader(appPath+"WEB-INF\\classes\\data.txt"); 
	    Scanner scan = new Scanner(read);
	    /*
	     * ��������� ���� ��������� ������� �������� ������ � ������ � ��������� �����
	     * ���� �������� �������, �� ����� ���������� �������� true 
	     */
	    while(scan.hasNextLine()) {
	    	if(login.equals(scan.nextLine()) && password.equals(scan.nextLine())) {
	    		return true;
	    	}
	    }
	    
	    read.close();
		return false; //����������� �������� false, ���� �������� ������ �������� 
	}
}