
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
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String RecipeName = request.getParameter("RecipeName");
      String listOfIngredients = request.getParameter("listOfIngredients");
      String PrepTime = request.getParameter("PrepTime");
      String CookingTime = request.getParameter("CookingTime");
      String ListOfDirections = request.getParameter("ListOfDirections");
      String ListOfNutrition = request.getParameter("ListOfNutrition");
      String AmountOfIngredients = request.getParameter("AmountOfIngredients");
      String NumberOfDifficulty = request.getParameter("NumberOfDifficulty");
      String SaveRecipe = request.getParameter("SaveRecipe");

      Connection connection = null;
      String insertSql = " INSERT INTO listOfRecipes (id, RecipeName, listOfIngredients, PrepTime, CookingTime, ListOfDirections, ListOfNutrition, AmountOfIngredients, NumberOfDifficulty, SaveRecipe) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnection.getDBConnection();
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, RecipeName);
         preparedStmt.setString(2, listOfIngredients);
         preparedStmt.setString(3, PrepTime);
         preparedStmt.setString(4, CookingTime);
         preparedStmt.setString(5, ListOfDirections);
         preparedStmt.setString(6, ListOfNutrition);
         preparedStmt.setString(7, AmountOfIngredients);
         preparedStmt.setString(8, NumberOfDifficulty);
         preparedStmt.setString(9, SaveRecipe);
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
            "<body bgcolor=\"#eeeeee\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Recipe Name</b>: " + RecipeName + "\n" + //
            "  <li><b>List Of Ingredients</b>: " + listOfIngredients + "\n" + //
            "  <li><b>Preparation Time</b>: " + PrepTime + "\n" + //
            "  <li><b>Cooking Time</b>: " + CookingTime + "\n" + //
            "  <li><b>List of Directions</b>: " + ListOfDirections + "\n" + //
            "  <li><b>List of Nutrition</b>: " + ListOfNutrition + "\n" + //
            "  <li><b>Amount of Ingredients</b>: " + AmountOfIngredients + "\n" + //
            "  <li><b>Number of Difficulty</b>: " + NumberOfDifficulty + "\n" + //
            "  <li><b>Save Recipe?</b>: " + SaveRecipe + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
