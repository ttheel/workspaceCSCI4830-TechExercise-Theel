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
public class SimpleFormSearchHuntTracker extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearchHuntTracker() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String date = request.getParameter("date");
      String location = request.getParameter("location");
      String species = request.getParameter("species");
      String amount = request.getParameter("amount");
      String notes = request.getParameter("notes");
      search(date, location, species, amount, notes, response);
   }

   void search(String d, String l, String s, String a, String n, HttpServletResponse response) throws IOException {
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
         DBConnectionHuntTracker.getDBConnection(getServletContext());
         connection = DBConnectionHuntTracker.connection;

         if (d.isEmpty() && l.isEmpty() && s.isEmpty() && a.isEmpty() && n.isEmpty()) {
            String selectSQL = "SELECT * FROM MyTableHuntTracker";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MyTableHuntTracker WHERE MYUSER LIKE ? AND ? AND ? AND ? AND ?";
            String theDate = d + "%";
            String theLocation = l + "%";
            String theSpecies = s + "%";
            String theAmount = a + "%";
            String theNotes = n + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            if (!d.isEmpty()) {
            	preparedStatement.setString(1, theDate);
            }
            if (!l.isEmpty()) {
            	preparedStatement.setString(2, theLocation);
            }
            if (!s.isEmpty()) {
            	preparedStatement.setString(3, theSpecies);
            }
            if (!a.isEmpty()) {
            	preparedStatement.setString(4, theAmount);
            }
            if (!n.isEmpty()) {
            	preparedStatement.setString(5, theNotes);
            }
            
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            //int id = rs.getInt("id");
            String date = rs.getString("date").trim();
            String location = rs.getString("location").trim();
            String species = rs.getString("species").trim();
            String amount = rs.getString("amount").trim();
            String notes = rs.getString("notes").trim();

            if (d.isEmpty() || date.contains(d)){
               out.println("Date: " + date + ", ");
            }
            if (l.isEmpty() || location.contains(l)){
            	out.println("Location: " + location + ", ");
             }
            if (s.isEmpty() || species.contains(s)){
            	out.println("Species: " + species + ", ");
             }
            if (a.isEmpty() || amount.contains(a)){
            	out.println("Amount: " + amount + ", ");
             }
            if (n.isEmpty() || notes.contains(n)){
            	out.println("Notes: " + notes + "<br>");
             }
            
         }
         out.println("<a href=/TechExercise-Theel/simpleFormInsert.html>Insert Data</a> <br>");
         out.println("</body></html>");
         out.println("<a href=/TechExercise-Theel/simpleFormSearch.html>Search Data</a> <br>");
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
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
