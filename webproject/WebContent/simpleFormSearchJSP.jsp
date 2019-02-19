<%@ page language="java" import="java.sql.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" >
<title>Simple DB Connection</title>
</head>
<body>
	<h1 align="center"> Database Result (JSP) </h1>
	
		
	<%!String keyword;%>
	<%keyword = request.getParameter("keyword");%>
	<%=runMySQL()%>

	<%!String runMySQL() throws SQLException {
		System.out.println("[DBG] User entered keyword: " + keyword);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return null;
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://ec2-3-16-149-195.us-east-2.compute.amazonaws.com:3306/recipes", "recipeuser", "password");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}

		PreparedStatement query = null;
		StringBuilder sb = new StringBuilder();

		try {
			connection.setAutoCommit(false);

			if (keyword.isEmpty()) {
				String selectSQL = "SELECT * FROM listOfRecipes";
				query = connection.prepareStatement(selectSQL);
			} else {
				String selectSQL = "SELECT * FROM listOfRecipes WHERE RecipeName LIKE ?";
				String theUserName = keyword + "%";
				query = connection.prepareStatement(selectSQL);
				query.setString(1, theUserName);
			}
			
			//String qSql = "SELECT * FROM myTable";
			//query = connection.prepareStatement(qSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String RecipeName = rs.getString("RecipeName").trim();
				String listOfIngredients = rs.getString("listOfIngredients").trim();
				String PrepTime = rs.getString("PrepTime").trim();
				String CookingTime = rs.getString("CookingTime").trim();
				String ListOfDirections = rs.getString("ListOfDirections").trim();
				String ListOfNutrition = rs.getString("ListOfNutrition").trim();
				String AmountOfIngredients = rs.getString("AmountOfIngredients").trim();
				String NumberOfDifficulty = rs.getString("NumberOfDifficulty").trim();
				String SaveRecipe = rs.getString("SaveRecipe").trim();

				// Display values to console.
				System.out.println("ID: " + id + ", ");
				System.out.println("Recipe Name: " + RecipeName + ", ");
				System.out.println("List Of Ingredients: " + listOfIngredients + ", ");
				System.out.println("Preparation Time: " + PrepTime + ", ");
				System.out.println("Cooking Time: " + CookingTime + ", ");
				System.out.println("List of Directions: " + ListOfDirections + ", ");
				System.out.println("List of Nutrition: " + ListOfNutrition + ", ");
				System.out.println("Amount of Ingredients: " + AmountOfIngredients + ", ");
				System.out.println("Number of Difficulty: " + NumberOfDifficulty + ", ");
				System.out.println("Save Recipe: " + SaveRecipe + "<br>");
				// Display values to webpage.
				sb.append("ID: " + id + ", ");
				sb.append("User: " + RecipeName + ", ");
				sb.append("Email: " + listOfIngredients + ", ");
				sb.append("User: " + PrepTime + ", ");
				sb.append("Email: " + CookingTime + ", ");
				sb.append("User: " + ListOfDirections + ", ");
				sb.append("Email: " + ListOfNutrition + ", ");
				sb.append("User: " + AmountOfIngredients + ", ");
				sb.append("Email: " + NumberOfDifficulty + ", ");
				sb.append("Phone: " + SaveRecipe + "<br>");
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}%>

	<a href=/webproject/simpleFormSearchJSP.html>Search Data</a> <br>
	<button type="button" name="back" onclick="history.back()">Back</button>
</body>
</html>