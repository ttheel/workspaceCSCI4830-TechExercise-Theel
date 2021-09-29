import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletTheel")
public class MyServletTheel extends HttpServlet {
	static String url = "jdbc:mysql://ec2-52-14-114-155.us-east-2.compute.amazonaws.com:3306/myDB?useSSL=false";
	static String user = "newmysqlremoteuser";
	static String password = "mypassword";
   private static final long serialVersionUID = 1L;

   public MyServletTheel() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.getWriter().append("Hello World!!");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
