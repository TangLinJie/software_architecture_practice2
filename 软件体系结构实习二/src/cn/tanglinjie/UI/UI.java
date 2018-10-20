package cn.tanglinjie.UI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;

import ClientDataBUS.GetDataFromService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;

public class UI extends JFrame{
    public static void main(String[] args)
    {
        new UI();
    }
    //控件申明
    JPanel jPanel1;
    JLabel ageLabel;
    JComboBox ageComboBox;
    JPanel jPanel2;
    JLabel graphicsLabel;
    JComboBox graphicsComboBox;
    JButton affirm;
    //逻辑层对象申明
    GetDataFromService dataGet;
    public UI()
    {
        super("Java");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2,3,10,10));

        ageLabel=new JLabel("选择年龄");
        String[] str1={"21","27","30","40","50","60"};
        ageComboBox=new JComboBox(str1);

        graphicsLabel=new JLabel("选择统计图像的形式");
        String[] str2={"折线","饼状图","最大值分布柱状图"};
        graphicsComboBox=new JComboBox(str2);

        affirm=new JButton("确定");

        dataGet=new GetDataFromService();

        jPanel1=new JPanel();
        jPanel1.setLayout(new GridLayout(2,1));
        jPanel2=new JPanel();
        jPanel2.setLayout(new GridLayout(2,1));

        eventRigister();//事件注册
        jPanel1.add(ageLabel);
        jPanel1.add(ageComboBox);
        jPanel2.add(graphicsLabel);
        jPanel2.add(graphicsComboBox);
        this.add(jPanel1);
        this.add(jPanel2);
        this.add(affirm);
        this.setBounds(50, 50, 1000, 1000);

        this.setVisible(true);
    }
    public void eventRigister()
    {
        affirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(graphicsComboBox.getSelectedIndex()==1)
                    showPieChart();
                else if(graphicsComboBox.getSelectedIndex()==0)
                    showBrokenLine();
                else if(graphicsComboBox.getSelectedIndex()==2)
                    showHistogramGraphics();

            }
        });
    }
    public void showPieChart()
    {
        int age=Integer.parseInt((String)ageComboBox.getSelectedItem());
        Vector<float[]> result=dataGet.getPercentageGraphicsByAge(age,4);   //默认划分为4块
        if(result==null) {
            System.out.println("result=null");
            return;
        }
        float[] values=result.get(0);
        float[] divide=result.get(1);
        this.add(new PieChart("Premium Adult individual age "+age+" statistics graphics",values,divide).getChartPanel());
        ((JPanel)this.getContentPane()).updateUI();
    }
    public void showBrokenLine()
    {
        int age=Integer.parseInt((String)ageComboBox.getSelectedItem());
        float[] values=dataGet.getBrokenLineByAge(age);
        if(values==null)
        {
            System.out.println("brokenline is null!");
            return;
        }
        this.add(new SeriesChart("Premium Adult individual age "+age+" statistics Broken Line",values).getChartPanel());
        ((JPanel)this.getContentPane()).updateUI();
    }
    public void showHistogramGraphics()
    {
        Map<String,Object> result=dataGet.getMaxDistribute();
        String[] labels=(String[])result.get("labels");
        float[] values=(float[])result.get("values");

        if(labels==null||values==null)
        {
            System.out.println("showHistagramGraphics is null!");
            return;
        }
        this.add(new HistogramGraphics(labels,values).getChartPanel());
        ((JPanel)this.getContentPane()).updateUI();
    }

}
class PieChart
{
    ChartPanel frame1;
    public PieChart(String title,float[] perc,float[] divide){
        DefaultPieDataset data = getDataSet(perc,divide);
        JFreeChart chart = ChartFactory.createPieChart3D(title,data,true,false,false);
        //设置百分比
        PiePlot pieplot = (PiePlot) chart.getPlot();
        DecimalFormat df = new DecimalFormat("0.00%");//获得一个DecimalFormat对象，主要是设置小数问题
        NumberFormat nf = NumberFormat.getNumberInstance();//获得一个NumberFormat对象
        StandardPieSectionLabelGenerator sp1 = new StandardPieSectionLabelGenerator("{0}  {2}", nf, df);//获得StandardPieSectionLabelGenerator对象
        pieplot.setLabelGenerator(sp1);//设置饼图显示百分比

        //没有数据的时候显示的内容
        pieplot.setNoDataMessage("无数据显示");
        pieplot.setCircular(false);
        pieplot.setLabelGap(0.02D);

        pieplot.setIgnoreNullValues(true);//设置不显示空值
        pieplot.setIgnoreZeroValues(true);//设置不显示负值
        frame1=new ChartPanel (chart,true);
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象
        piePlot.setLabelFont(new Font("宋体",Font.BOLD,10));//解决乱码
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,10));
    }
    private static DefaultPieDataset getDataSet(float[] perc,float[] divide) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(int i=0;i<perc.length;i++) {
            dataset.setValue(divide[i]+"<price<"+divide[i+1], perc[i]);
        }
        return dataset;
    }
    public ChartPanel getChartPanel() {
        return frame1;
    }
}
class SeriesChart {

    ChartPanel frame1;
    public SeriesChart(String title,float[] values){
        XYDataset xydataset = createDataset(values,title);
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title,"different row", "价格",xydataset, true, true, true);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        frame1=new ChartPanel(jfreechart,true);
        ValueAxis rangeAxis=xyplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体

    }
    private static XYDataset createDataset(float[] values,String title) {  //这个数据集有点多，但都不难理解
        XYSeries xyseries=new XYSeries(title);
        for(int i=0;i<values.length;i++)
        {
            xyseries.add(i/10.0,values[i]);
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        xyseriescollection.addSeries(xyseries);
        return xyseriescollection;
    }
    public ChartPanel getChartPanel(){
        return frame1;
    }
}
class HistogramGraphics{

    //用于获取数据
    private CategoryDataset getDataset(String[] labels,float[] values){

        DefaultCategoryDataset dataset=new DefaultCategoryDataset();//创建数据集对象
        for(int i=0;i<labels.length;i++) {
            dataset.addValue(values[i], "max value", labels[i]);//数据值，X轴，Y轴
        }
        return dataset;
    }

    private ChartPanel frame1;
    public HistogramGraphics(String[] labels,float[] values){
        CategoryDataset dataset = getDataset(labels,values);
        JFreeChart chart=ChartFactory.createBarChart3D(//工厂模式
                "最大值统计柱状图", //图形的标题
                "年龄", //目录轴的显示标签(X轴)
                "价格",  //数据轴的显示标签(Y轴)
                dataset, //数据集
                PlotOrientation.VERTICAL, //垂直显示图形
                true,  //是否生成图样
                false, //是否生成提示工具
                false);//是否生成URL链接
        CategoryPlot plot=chart.getCategoryPlot();//获取图形区域对象
        //------------------------------------------获取X轴
        CategoryAxis domainAxis=plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));//设置X轴的标题的字体
        domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));//设置X轴坐标上的字体
        //-----------------------------------------获取Y轴
        ValueAxis rangeAxis=plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));//设置Y轴坐标上的标题字体
        //设置图样的文字样式
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD ,15));
        //设置图形的标题
        chart.getTitle().setFont(new Font("宋体",Font.BOLD ,20));

        frame1 =new ChartPanel(chart,true);//将已经画好的图形报表存放到面板中
    }

    //构建一个方法，用于获取存放了图形的面板(封装：隐藏具体实现)
    public ChartPanel getChartPanel(){
        return frame1;
    }
}