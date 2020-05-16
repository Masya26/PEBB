package PEBB;

import java.io.IOException;
import java.util.Date;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main extends HttpServlet { //Класс для расчета расхода топлива и вывода результатов
	private static final long serialVersionUID = 1L;
	public static int k, index, pass;
	public static double lugg, dist;
	public String[] autoArr = {
		    "Hyundai Solaris",
		    "Renault Logan",
		    "Skoda Octavia",
		    "Kia Soul",
		    "Ford Focus",
		    "Lada Vesta"
	};
	public static double[][] data = {
		{6.4, 42.34},
		{6.6, 45.79},
		{5.2, 45.79},
		{8, 45.79},
		{5.5, 42.34},
		{7.2, 42.34}
	};
	public static double i1 = 0.0002, i2 = 0.0005;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        /*
         * Вызов метода проверки введеных данных
         * Если метод возвращает true, то выполняется расчет
         * Иначе пользователя перенаправляет на страницу ошибки
         */
        if(checkData(request.getParameter("passenger"), request.getParameter("luggage"), request.getParameter("distance"))){
	       		double Smin, Smax, MinSum, MaxSum = 0, k1, k2;
	       	 	Date date =new Date();
	       		pass = Integer.parseInt(request.getParameter("passenger"));
	    		lugg = Double.parseDouble(request.getParameter("luggage"));
	      		dist = Double.parseDouble(request.getParameter("distance"));
	       		k = Integer.parseInt(request.getParameter("season"));
	       		index = Integer.parseInt(request.getParameter("vehicle"));
	       			
	       		lugg = lugg < 5 ? 0 : lugg; //Игнорирование веса багажа, если он меньше 5 кг
	    		k1 = k == 0 ? 0 : 0.14; //Установка значения переменной в зависимости от выбранного пользователем сезона
	    		k2 = k == 0 ? 0 : 0.19; //Установка значения переменной в зависимости от выбранного пользователем сезона
	    		/*
	    		 * Расчет
	    		 */	
		    	Smin = 0.01 * data[index][0] * dist * (((pass + 1) * 62 + lugg) * i1 + k1 + 1);
		    	Smax = 0.01 * data[index][0] * dist * (((pass + 1) * 62 + lugg) * i2 + k2 + 1);
		   		
		    	MinSum = Smin * data[index][1];
		    	MaxSum = Smax * data[index][1];
		    		
		    	Smin = Math.round(Smin * 100.0) / 100.0;
		    	Smax = Math.round(Smax * 100.0) / 100.0;
		    		
		    	MinSum = Math.round(MinSum * 100.0) / 100.0;
		    	MaxSum = Math.round(MaxSum * 100.0) / 100.0;
		    	/*
	    		 * Вывод результатов расчета, написанный в виде HTML-кода, 
	    		 * в котором реализована возможность возврата на страницу калькулятора
	    		 */	
		    	writer.println("<html>" +
			        "				<head>" + 
			       	"					<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" + 
			     	"					<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">" + 
			      	"	  		  	    <title>Результат</title>" +
			       	"				</head>" +
			       	"				<body>" + 
			       	"   		    	<form class=\"w3-container w3-card-4\" action=\"MainView.html\">" +
			       	"     		     		<h2 class=\"w3-text-blue\">Для данной поездки Вам необходимо:</h2>" +
			       	"       		       	<ul name=result class=\"w3-ul\">" +
					"							<li name=gasoline class=\"w3-text-blue\">от " + Smin + " до " + Smax + " литров бензина</li>" +
					"							<li name=money class=\"w3-text-blue\">от " + MinSum + " до " + MaxSum + " рублей денег</li>" +
					"						</ul>" +
		        	"						<p><button class=\"w3-btn w3-blue\">Калькулятор</button></p>" + 
			       	"      			 	</form>" +
		        	"                   <label class=\"w3-text-blue\"><b>Разработан Бикбулатовым А., Буракановым А., Елисеевой В., Печенкиной А.</b></label>" +
		        	"                   <p><label class=\"w3-text-blue\"><b>"+date+"</b></label><p>" +
			       	"				</body>" +
			        "			</html>");		      				    			    				    	        	    	        	    	            	            	        	 		
        }
        else
           	response.sendRedirect("ErrorPage.html"); //Перенаправление на страницу ошибки
	
	}
	
	public static boolean checkData(String passenStr, String luggStr, String distStr){ //Метод проверки введенных данных
		int size = 0;
        String[] cc = {"1","2","3","4","5","6","7","8","9","0","."}; //Массив возможных символов в полях
        /*
         * Далее следует проверка на заполненность полей
         * Если хотя бы одно поле не заполнено, то возвращается false и дальше проверка не идет по причине того,
         * что иначе код попробует получить значение этих полей для проверки, а их не окажется и браузер объявит об ошибке 500.
         */
        if(passenStr.equals("") || luggStr.equals("") || distStr.equals(""))
			return false;
		else{
			//Проверка на "чистоту" поля "Number of passengers"
			for(int i = 0; i < passenStr.length(); i++) {
				/*
	    		 * В следующем цикле игнорируется последний элемент массива, 
	    		 * так как он является точкой, 
	    		 * а количество пассажиров должно быть целым числом.
	    		 */
	    		for (int j = 0; j < 10; j++) {
		    		if(cc[j].equals(passenStr.substring(i, i+1))) {
		    			size++;
		    			break;
		    		}
	    		}
	    	}
	    	if (size != passenStr.length() || ((Integer.parseInt(passenStr) < 0) || (Integer.parseInt(passenStr) > 4))) 
	    		return false; //Возврат false с дальнейшим выходом из метода
	    	
	    	//Проверка на "чистоту" поля "Luggage weight"	    	
	    	size = 0;
	    	for(int i = 0; i < luggStr.length(); i++) {
	    		for (int j = 0; j < 11; j++) {
		    		if(cc[j].equals(luggStr.substring(i, i+1))) {
		    			size++;
		    			break;
		    		}
	    		}
	    	}
	    	if (size != luggStr.length()) 
	    		return false; //Возврат false с дальнейшим выходом из метода
	
	    	//Проверка на "чистоту" поля "Distance"
	    	size = 0; 
	    	for(int i = 0; i < distStr.length(); i++) { 
	    		for (int j = 0; j < 11; j++) {
		    		if(cc[j].equals(distStr.substring(i, i+1))) { 
		    			size++; 
		    			break; 
		    		}
	    		}
	    	}
	    	if (size != distStr.length()) 
	    		return false; //Возврат false с дальнейшим выходом из метода
		}
		return true; //Если все проверки прошли упешно, то возвращается true
	}
}
