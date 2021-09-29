import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearchTheel extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearchTheel() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      String keyword2 = request.getParameter("keyword2");
      search(keyword, keyword2, response);
   }

   void search(String keyword, String keyword2, HttpServletResponse response) throws IOException {
      
	  String theEmail;
      String thePhone;
	  response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionTheel.getDBConnection(getServletContext());
         connection = DBConnectionTheel.connection;

         if (keyword.isEmpty() && keyword2.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableTheel0927";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else if (!keyword.isEmpty() && keyword2.isEmpty()){
        	String selectSQL = "SELECT * FROM MyTableTheel0927 WHERE EMAIL LIKE ?";
            theEmail = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theEmail);
         } else if (keyword.isEmpty() && !keyword2.isEmpty()){
        	String selectSQL = "SELECT * FROM MyTableTheel0927 WHERE PHONE LIKE ?";
            thePhone = keyword2 + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, thePhone);
         } else {
        	 String selectSQL = "SELECT * FROM MyTableTheel0927 WHERE EMAIL LIKE ? AND PHONE LIKE ?";
        	 theEmail = keyword + "%";
        	 thePhone = keyword2 + "%";
             preparedStatement = connection.prepareStatement(selectSQL);
             preparedStatement.setString(1, theEmail);
             preparedStatement.setString(2, thePhone);
         }
         
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            int id = rs.getInt("id");
            String userName = rs.getString("myuser").trim();
            String email = rs.getString("email").trim();
            String phone = rs.getString("phone").trim();

            if (keyword.isEmpty() || email.contains(keyword) || phone.contains(keyword2)) {
               out.println("ID: " + id + ", ");
               out.println("User: " + userName + ", ");
               out.println("Email: " + email + ", ");
               out.println("Phone: " + phone + "<br>");
            }
         }
         out.println("<a href=/webproject-ex3-0927-Theel/simpleFormSearchTheel.html>Search Data</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null) {
               preparedStatement.close();
            }
         } catch (SQLException se2) {
         }
         try {
            if (connection != null) {
               connection.close();
            }
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
