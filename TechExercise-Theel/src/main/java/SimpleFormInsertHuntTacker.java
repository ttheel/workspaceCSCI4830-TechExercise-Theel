
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsertHuntTacker extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsertHuntTacker() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //String userName = request.getParameter("userName");
      String date = request.getParameter("date");
      String location = request.getParameter("location");
      String species = request.getParameter("species");
      String amount = request.getParameter("amount");
      String notes = request.getParameter("notes");

      Connection connection = null;
      String insertSql = " INSERT INTO MyTableHuntTracker (id, DATE, LOCATION, SPECIES, AMOUNT, NOTES) values (default, ?, ?, ?, ?, ?)";

      try {
         DBConnectionHuntTracker.getDBConnection(getServletContext());
         connection = DBConnectionHuntTracker.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, date);
         preparedStmt.setString(2, location);
         preparedStmt.setString(3, species);
         preparedStmt.setString(4, amount);
         preparedStmt.setString(5, notes);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Date</b>: " + date + "\n" + //
            "  <li><b>Location</b>: " + location + "\n" + //
            "  <li><b>Species</b>: " + species + "\n" + //
            "  <li><b>Amount</b>: " + amount + "\n" + //
            "  <li><b>Notes</b>: " + notes + "\n" + //

            "</ul>\n");

      out.println("<a href=/TechExercise-Theel/simpleFormInsert.html>Insert Data</a> <br>");
      out.println("</body></html>");
      out.println("<a href=/TechExercise-Theel/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
