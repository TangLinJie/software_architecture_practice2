package Servlet;

import DataBUS.DataBUS;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GetPercentageGraphicsByAgeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try
        {
            int age=Integer.parseInt(req.getParameter("age"));
            int spe=Integer.parseInt(req.getParameter("spe"));
            DataBUS dataBUS=new DataBUS();
            float[] result=dataBUS.getPercentageGraphicsByAge(age,spe);
            float[] interval=dataBUS.getIntervalByAge(age,spe);
            Map<String,Object> map=new HashMap<>();
            map.put("result",result);
            map.put("interval",interval);
            Gson gson=new Gson();
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter out=resp.getWriter();
            out.print(gson.toJson(map));
            out.close();
        }
        catch (Exception e)
        {
        }
    }
}
