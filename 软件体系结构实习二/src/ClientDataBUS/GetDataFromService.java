package ClientDataBUS;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class GetDataFromService {
    public Vector<float[]> getPercentageGraphicsByAge(int age, int spe)
    {
        //String httpReq="http://47.93.251.190:8080/getPercentageGraphicsByAge";
        String httpReq="http://47.93.251.190:8080/saweb2/getPercentageGraphicsByAge?age="+age+"&spe="+spe;
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpGet get=new HttpGet(httpReq);

        try {
            HttpResponse response = (HttpResponse) httpClient.execute(get);
            if(response.getStatusLine().getStatusCode()==200)
            {
                HttpEntity entity=response.getEntity();
                String result=EntityUtils.toString(entity);
                Map<String,Object> map=parseJson(result,"getPercentageGraphicsByAge");
                Vector<float[]> vector=new Vector<>();
                vector.add((float[])map.get("result"));
                vector.add((float[])map.get("interval"));
                return vector;
            }
        }
        catch (Exception e)
        {
            System.out.println("执行httpget错误！");
        }
        return null;
    }
    //解析json
    private Map<String,Object> parseJson(String json,String op) throws Exception
    {
        Map<String,Object> result=new HashMap<>();
        //解析json
        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject=jsonParser.parse(json).getAsJsonObject();
        JsonArray jsonArray1=null;
        JsonArray jsonArray2=null;
        JsonArray jsonArray3=null;
        JsonArray jsonArray4=null;
        if(op.equals("getPercentageGraphicsByAge"))
        {
            jsonArray1=jsonObject.get("result").getAsJsonArray();
            jsonArray2=jsonObject.get("interval").getAsJsonArray();
        }
        else if(op.equals("getBrokenLineByAge"))
        {
            jsonArray1=jsonObject.get("result").getAsJsonArray();
        }
        else
        {
            jsonArray3=jsonObject.get("labels").getAsJsonArray();
            jsonArray4=jsonObject.get("values").getAsJsonArray();
        }

        if(jsonArray1!=null&&jsonArray2!=null) {
            float[] value1 = null;
            float[] value2 = null;
            value1 = new float[jsonArray1.size()];
            value2 = new float[jsonArray2.size()];

            int i = 0;
            for (Iterator iterator = jsonArray1.iterator(); iterator.hasNext(); ) {
                value1[i++] = Float.parseFloat( iterator.next().toString());
            }
            i = 0;
            for (Iterator iterator = jsonArray2.iterator(); iterator.hasNext(); ) {
                value2[i++] = Float.parseFloat(iterator.next().toString());
            }


            result.put("result", value1);
            result.put("interval", value2);
            return result;
        }
        else if(jsonArray3!=null&&jsonArray4!=null)
        {
            String[] v3=new String[jsonArray3.size()];
            float[] v4=new float[jsonArray4.size()];

            int i=0;
            for(Iterator iterator=jsonArray3.iterator();iterator.hasNext();)
            {
                v3[i++]=iterator.next().toString();
            }
            i=0;
            for(Iterator iterator=jsonArray4.iterator();iterator.hasNext();)
            {
                v4[i++]=Float.parseFloat(iterator.next().toString());
            }

            result.put("labels",v3);
            result.put("values",v4);
            return result;
        }
        else if(jsonArray1!=null)
        {
            float[] v1=new float[jsonArray1.size()];

            int i=0;
            for(Iterator iterator=jsonArray1.iterator();iterator.hasNext();)
            {
                v1[i++]=Float.parseFloat(iterator.next().toString());
            }

            result.put("result",v1);
            return result;
        }
        return null;
    }
    public float[] getBrokenLineByAge(int age)
    {
        String httppath="http://47.93.251.190:8080/saweb2/getBrokenLineByAge?age="+age;
        CloseableHttpClient client= HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(httppath);

        try
        {
            HttpResponse httpResponse=(HttpResponse)client.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200)
            {
                HttpEntity entity=httpResponse.getEntity();
                String json=EntityUtils.toString(entity);
                Map<String,Object> map=parseJson(json,"getBrokenLineByAge");
                return (float[])map.get("result");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public Map<String,Object> getMaxDistribute()
    {
        String httppath="http://47.93.251.190:8080/saweb2/getMax";
        CloseableHttpClient client=HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(httppath);

        try
        {
            HttpResponse httpResponse=(HttpResponse)client.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity);
                Map<String, Object> map = parseJson(json,"getMaxDistribute");

                return map;
            }
        }
        catch (Exception e)
        {

        }
        return null;
    }
}
