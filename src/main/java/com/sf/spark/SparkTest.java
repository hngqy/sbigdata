package com.sf.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by 01110635 on 2016/4/27.
 */
public class SparkTest {
    public static void main(String[] args){
     /*   if(args.length != 2 ){
            System.err.println("args length is not 2");
        }*/
       /* args[0]="/user/hive/warehouse/test.db/zack_test";
        args[1]="/user/hive/warehouse/test.db/zt";*/
        runJob(args);
    }

    public static void runJob(String[] args){
        SparkConf conf = new SparkConf();
        conf.setAppName("javaCount").setMaster("local[4]");
//        conf.setAppName("test").setMaster("spark://10.202.34.200:7076");
//        conf.setJars(new String[]{"E:\\code\\simon\\sbigdata\\out\\artifacts\\sbigdata_jar"});
 /*       Configuration config = new Configuration();
        config.addResource("/etc/hadoop/conf");*/

        JavaSparkContext jsc = new JavaSparkContext(conf);


      /*  String inputpath = args[0];
        String output=args[1];
        jsc.textFile(inputpath).flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String line) throws Exception {
                return  Arrays.asList(line.split(" "));
            }
        }).mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s,1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1+v2;
            }
        }).saveAsTextFile(output);
        //.saveAsHadoopFile(outpath, String.class,Integer.class,TextInputFormat.class,);*/
        jsc.stop();
    }
}
