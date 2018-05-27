

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.io.*;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


/**
 * Servlet implementation class book
 */
@WebServlet("/book")
public class book extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public book() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String JDBC_Driver = "com.mysql.jdbc.driver";
		final String DB_URL = "jdbc:mysql://localhost:3306/ClassDB";

		final String User = "root";
		final String Pass = "root";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try
		{
				String nameI;
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(DB_URL, User, Pass);
				nameI = request.getParameter("insert");
				String nameU = request.getParameter("update");
				String nameD = request.getParameter("delete");
				String nameR = request.getParameter("read");
				if(nameR != null)
				{
					
				
						String bid = request.getParameter("bid");
						Statement stmt = con.createStatement();
						String sql;
						sql = "Select * from books where BookID = "+bid;
						ResultSet rs = stmt.executeQuery(sql);

						while(rs.next())
						{
							int bookid = rs.getInt("BookID");
							String bname = rs.getString("BookName");
							String Author = rs.getString("Author");

							out.println("BookID is: "+bookid);
							out.println("Book Name is: "+bname);
							out.println("Author is: "+Author);

						}
						rs.close();
						stmt.close();
				
				}
				else if(nameI != null)
				{
					String bid = request.getParameter("bid");
					String bname = request.getParameter("bname");
					String author = request.getParameter("auth");
					
					String sql;
					sql = "Insert into books values(?,?,?)";
					PreparedStatement stmt = con.prepareStatement(sql);
					stmt.setString(1,bid);
					stmt.setString(2,bname);
					stmt.setString(3,author);
					int i = stmt.executeUpdate();
					out.println(" "+i+"Updated Successfully");
					stmt.close();
				}
				else if(nameD != null)
				{
					String bid = request.getParameter("bid");
					String sql = "Delete from books where BookID = ?";
					PreparedStatement stmt = con.prepareStatement(sql);
					int biid = Integer.parseInt(bid);
					stmt.setString(1, bid);
					stmt.executeUpdate();
					int i = stmt.executeUpdate();
					out.println(" "+i+"Updated Successfully");
					stmt.close();
				}
				con.close();
			}
			
		catch(Exception e)
		{
			out.println(e);
		}
		finally
		{
			
		}
		
		

	}

}
