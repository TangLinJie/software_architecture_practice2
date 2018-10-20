package DataBUS;

import DataDAO.HealthDataDAO;
import DataVO.DataVO;

import java.util.Vector;

public class DataBUS {
    Vector<DataVO> allData=null;
    float[] minData;
    float[] maxData;
    private void getPercentageGraphics()
    {
        HealthDataDAO dao=new HealthDataDAO();
        allData=dao.getAllData();
        minData=dao.getMin();
        maxData=dao.getMax();
    }
    public float[] getPercentageGraphicsByAge(int age,int spe)
    {
        if(allData==null)
            getPercentageGraphics();
        float[] result=new float[spe];
        int amount=allData.size();
        float delta;
        switch (age) {
            case 21:
            delta = (maxData[0]-minData[0])/spe;
            for(int i=0;i<amount;i++)
            {
                int index=(int)((allData.get(i).getPremiumAdultIdi21()-minData[0])/delta);
                if (index==spe)
                    index--;
                result[index]+=1;
            }
            break;
            case 27:
                delta = (maxData[1]-minData[1])/spe;
                for(int i=0;i<amount;i++)
                {
                    int index=(int)((allData.get(i).getPremiumAdultIdi27()-minData[1])/delta);
                    if (index==spe)
                        index--;
                    result[index]+=1;
                }
                break;
            case 30:
                delta = (maxData[2]-minData[2])/spe;
                for(int i=0;i<amount;i++)
                {
                    int index=(int)((allData.get(i).getPremiumAdultIdi30()-minData[2])/delta);
                    if (index==spe)
                        index--;
                    result[index]+=1;
                }
                break;
            case 40:
                delta = (maxData[3]-minData[3])/spe;
                for(int i=0;i<amount;i++)
                {
                    int index=(int)((allData.get(i).getPremiumAdultIdi40()-minData[3])/delta);
                    if (index==spe)
                        index--;
                    result[index]+=1;
                }
                break;
            case 50:
                delta = (maxData[4]-minData[4])/spe;
                for(int i=0;i<amount;i++)
                {
                    int index=(int)((allData.get(i).getPremiumAdultIdi50()-minData[4])/delta);
                    if (index==spe)
                        index--;
                    result[index]+=1;
                }
                break;
            case 60:
                delta = (maxData[5]-minData[5])/spe;
                for(int i=0;i<amount;i++)
                {
                    int index=(int)((allData.get(i).getPremiumAdultIdi60()-minData[5])/delta);
                    if (index==spe)
                        index--;
                    result[index]+=1;
                }
                break;
        }
        float sum=0;
        for(int i=0;i<spe;i++)
        {
            sum+=result[i];
        }
        for(int i=0;i<spe;i++)
        {
            result[i]/=sum;
        }
        return result;
    }

    public float[] getBrokenLineByAge(int age)
    {
        if(allData==null)
            getPercentageGraphics();
        int amount=allData.size();
        float[] values=new float[amount];
        switch (age) {
            case 21:
                for(int i=0;i<amount;i++)
                {
                    values[i]=allData.get(i).getPremiumAdultIdi21();
                }
                break;
            case 27:
                for(int i=0;i<amount;i++)
                {
                    values[i]=allData.get(i).getPremiumAdultIdi27();
                }
                break;
            case 30:
                for(int i=0;i<amount;i++)
                {
                    values[i]=allData.get(i).getPremiumAdultIdi30();
                }
                break;
            case 40:
                for(int i=0;i<amount;i++)
                {
                    values[i]=allData.get(i).getPremiumAdultIdi40();
                }
                break;
            case 50:
                for(int i=0;i<amount;i++)
                {
                    values[i]=allData.get(i).getPremiumAdultIdi50();
                }
                break;
            case 60:
                for(int i=0;i<amount;i++)
                {
                    values[i]=allData.get(i).getPremiumAdultIdi60();
                }
                break;
        }
        return values;
    }


    public float[] getIntervalByAge(int age,int spe)
    {
        if(allData==null)
            getPercentageGraphics();
        float[] result=new float[spe+1];
        float delta;
        switch (age) {
            case 21:
                delta = (maxData[0] - minData[0]) / spe;
                for (int i = 0; i <= spe; i++) {
                    result[i] = i * delta + minData[0];
                }
                break;
            case 27:
                delta = (maxData[1] - minData[1]) / spe;
                for (int i = 0; i <= spe; i++) {
                    result[i] = i * delta + minData[1];
                }
                break;
            case 30:
                delta = (maxData[2] - minData[2]) / spe;
                for (int i = 0; i <= spe; i++) {
                    result[i] = i * delta + minData[2];
                }
                break;

            case 40:
                delta = (maxData[3] - minData[3]) / spe;
                for (int i = 0; i <= spe; i++) {
                    result[i] = i * delta + minData[3];
                }
                break;
            case 50:
                delta = (maxData[4] - minData[4]) / spe;
                for (int i = 0; i <= spe; i++) {
                    result[i] = i * delta + minData[4];
                }
                break;
            case 60:
                delta = (maxData[5] - minData[5]) / spe;
                for (int i = 0; i <= spe; i++) {
                    result[i] = i * delta + minData[5];
                }
                break;
        }
        return result;
        }

    public float[] getMinData() {
        if (allData==null)
            getPercentageGraphics();
        return minData;
    }

    public float[] getMaxData() {
        if (allData==null)
            getPercentageGraphics();
        return maxData;
    }
}
