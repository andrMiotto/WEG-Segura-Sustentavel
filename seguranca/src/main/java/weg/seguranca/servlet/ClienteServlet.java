package weg.seguranca.servlet;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import weg.seguranca.dao.SalaDao;
import weg.seguranca.model.Sala;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClienteServlet extends HttpServlet {
    private SalaDao salaDao = new SalaDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Sala> salas = null;
        try {
            salas = SalaDao.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String json = new Gson().toJson(salas);
        resp.getWriter().write(json);
    }
}
