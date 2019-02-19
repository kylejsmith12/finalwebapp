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
public class SimpleFormSearch extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearch() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#eeeeee\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM listOfRecipes";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM listOfRecipes WHERE RecipeName LIKE ?";
            String theUserName = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theUserName);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next() ) {
            int id = rs.getInt("id");
            String recipename = handleNull(rs.getString("RecipeName")).trim();
            String listofingredients = handleNull(rs.getString("listOfIngredients")).trim();
            String preptime = handleNull(rs.getString("PrepTime")).trim();
            String cookingtime = handleNull(rs.getString("CookingTime")).trim();
            String listofdirections = handleNull(rs.getString("ListOfDirections")).trim();
            String listofnutrition = handleNull( rs.getString("ListOfNutrition")).trim();
            String amountofingredients = handleNull(rs.getString("AmountOfIngredients")).trim();
            String numberofdifficulty = handleNull(rs.getString("NumberOfDifficulty")).trim();
            String saverecipe = handleNull(rs.getString("SaveRecipe")).trim();

            if (keyword.isEmpty() || recipename.contains(keyword)) {
               out.println("ID: " + id + ", ");
               out.println("Recipe Name: " + recipename + ", ");
               out.println("List of Ingredients: " + listofingredients + ", ");
               out.println("Preparation Time: " + preptime + ", ");
               out.println("Cooking Time: " + cookingtime + ", ");
               out.println("List of Directions: " + listofdirections + ", ");
               out.println("List of Nutrition: " + listofnutrition + ", ");
               out.println("Amount of Ingredients: " + amountofingredients + ", ");
               out.println("Number of Difficulty: " + numberofdifficulty + ", ");
               out.println("Save Recipe: " + saverecipe + "<br>");
            }
         }
         out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
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

   protected String handleNull(String column) {
	   String retString = column;
	   if(column == null) {
		   retString = "Null";
	   }
	   
	   return retString;
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
