package groupFinal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Download")
public class Download extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 
	    public Download()
	    {
	        super();
	    }


	 public String getFilePath(Integer id) throws ServletException {
		 
	    Connection c = null;
	    String filePath = "";
	    
	    try
	    {
	        String url = "jdbc:mysql://cs3.calstatela.edu:3307/cs3220stu06?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	        String username = "cs3220stu07";
	        String password = "";
	
	        c = DriverManager.getConnection(url, username, password);
	        Statement stmt = c.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM tablename WHERE id='" + id + "'");
	        
	        while (rs.next()) {
	        	filePath = rs.getString("filePath");
	        }
	    }
	    catch(SQLException e)
	    {
	        throw new ServletException(e);
	    }
	    finally
	    {
	        try
	        {
	            if(c != null) c.close();
	        }
	        catch(SQLException e)
	        {
	            throw new ServletException(e);
	        }
	    }
	    
	    if (filePath == "" || filePath.isEmpty()) {
	    	return null;
	    }
	
	    return filePath;
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        String path = getFilePath(id);

        FileInputStream in = new FileInputStream(path);
        OutputStream out = response.getOutputStream();

        byte buffer[] = new byte[2048];
        int bytesRead;
        while( (bytesRead = in.read( buffer )) > 0 )
            out.write( buffer, 0, bytesRead );

        in.close();

        response.sendRedirect("/databases/FileManager");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }

}

