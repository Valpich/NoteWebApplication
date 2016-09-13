package notes.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( "/Menu" )
public class Menu extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String VUE_PRINCIPALE   = "/WEB-INF/index.jsp";

    public Menu() {
        super();
    }
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_PRINCIPALE ).forward( request, response );

    }

}
