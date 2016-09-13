package notes.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( "/DeconnecterUtilisateur" )
public class DeconnecterUtilisateur extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String URL_REDIRECTION  = "/notes/Menu";

    public DeconnecterUtilisateur() {
        super();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect( URL_REDIRECTION );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect( URL_REDIRECTION );
    }
}
