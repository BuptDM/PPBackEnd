package com.pp.algorithm;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.stereotype.Component;
import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

import javax.servlet.http.HttpServletRequest;

@Component
@Data
public class KMeans {
    /*算法ID，对应数据库algorithm表中的id字段*/
    public static int KMeansID = 10001;
    /*聚类算法的分数评价*/
    float score;
    /*聚类算法的实例*/
    Instances data;
    /*weka聚类算法实例*/
    SimpleKMeans simpleKMeans;
    /*聚类算法的参数实例*/
    KMeansParam kMeansParam;
    /*聚类算法的参数类*/
    @Data
    class KMeansParam{
        int clustersNumber;
        boolean displayStdDevs;
        String distanceFunction;
        boolean dontReplaceMissingValues;
        int maxIterations;
        boolean preserveInstancesOrder;
        int seed;
    }
    /*构造函数，创建算法实例和参数实例*/
    public KMeans(){
        simpleKMeans = new SimpleKMeans();
        kMeansParam = new KMeansParam();
    }
    /*从http-request获取参数*/
    public void setParam(HttpServletRequest request) throws Exception {
        // 获取字符串参数
        kMeansParam.setClustersNumber(Integer.parseInt(request.getParameter("clustersNumber")));
        kMeansParam.setDisplayStdDevs(Boolean.parseBoolean(request.getParameter("displayStdDevs")));
        kMeansParam.setDistanceFunction(request.getParameter("distanceFunction"));
        kMeansParam.setSeed(Integer.parseInt(request.getParameter("seed")));
        kMeansParam.setDontReplaceMissingValues(Boolean.parseBoolean(request.getParameter("dontReplaceMissingValues")));
        kMeansParam.setPreserveInstancesOrder(Boolean.parseBoolean(request.getParameter("preserveInstancesOrder")));
        kMeansParam.setMaxIterations(Integer.parseInt(request.getParameter("maxIterations")));
        // 设置simpleKMeans参数
        simpleKMeans.setSeed(kMeansParam.seed);
        simpleKMeans.setDisplayStdDevs(kMeansParam.displayStdDevs);
        simpleKMeans.setDontReplaceMissingValues(kMeansParam.dontReplaceMissingValues);
        simpleKMeans.setMaxIterations(kMeansParam.maxIterations);
        simpleKMeans.setNumClusters(kMeansParam.clustersNumber);
        simpleKMeans.setPreserveInstancesOrder(kMeansParam.preserveInstancesOrder);
        if ("EuclideanDistance".equals(kMeansParam.distanceFunction)) {
            simpleKMeans.setDistanceFunction(new EuclideanDistance());
        } else {
            simpleKMeans.setDistanceFunction(new EuclideanDistance());
        }
    }
    /*运行算法*/
    public void run() throws Exception {
        simpleKMeans.buildClusterer(data);
        score = (float) simpleKMeans.getSquaredError();
    }
    /*获取算法运行结果*/
    public String getResult(){
        return simpleKMeans.preserveInstancesOrderTipText()+simpleKMeans.toString();
    }
    /*获取参数的序列化Json串*/
    public String getParamJsonStr(){
        return JSONObject.toJSONString(kMeansParam);
    }
    /*设置聚类所用的数据*/
    public void setData(Instances data){
        this.data = data;
    }
}