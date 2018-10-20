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

public class GetMaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try
        {
            DataBUS dataBUS=new DataBUS();
            float[] max=dataBUS.getMaxData();
            String[] labels={"Adult21" ,
                    "Adult27",
                    "Adult30",
                    "Adult40",
                    "Adult50",
                    "Adult60"};
            Map<String,Object> map=new HashMap<>();
            map.put("labels",labels);
            map.put("values",max);

            Gson gson=new Gson();

            resp.setContentType("text/html;charset=utf-8");
            PrintWriter printWriter=resp.getWriter();
            printWriter.print(gson.toJson(map));
            printWriter.close();
        }
        catch (Exception e)
        {
        }
    }
}
