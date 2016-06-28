package com.sf.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Created by 01110635 on 2016/5/13.
 */
public class HiveUDF extends UDF {
    public int evaluate(String str){
        return str.length();
    }
}
