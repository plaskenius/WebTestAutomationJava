package com.project.project.components;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SearchInfoInLogs extends Utils{

    public void searchingInformationInLogs(String searchKeyword) throws IOException, ParseException {
//        String pathFile = '//segotx623//got623v002$//segot2ardhn010//wins//VLDGOT02_NODE01_WINS//serverlogs//SystemOut.log'
        String pathFile = "//segotx813.vcn.ds.project.net//wp1200_dsfs_1001_q1000m$//system//VLDGOT02_NODE01_system//logs//system_app.log";
        BufferedReader br = new BufferedReader(new FileReader(new File(pathFile)));
        String line;
        String messageXml = "";
        String messageStr = "";
        String xmlStr = "";
        int counterXML = 0;
        while ((line = br.readLine()) != null) {
            if(line.startsWith("2")){ //for wins '['
                String lineDateStr = line.substring(0, line.indexOf("[")); //dla wins ' CET]' i od '1'
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH); //dla wins 'M/d/yy H:mm:ss:SSS'
                Date lineDate = format1.parse(lineDateStr);
                Date startDateFormatted = format1.parse(Utils.startTimestamp);
                if(lineDate.before(startDateFormatted)){
//                    String afterReplaceText1 = line.replaceAll("[^\\x20-\\x7e]", "")
                    String afterReplaceText1 = line.replaceAll("", "");
                    messageStr += (afterReplaceText1+"\n");
                }
            }
        }
        assert messageStr.contains(searchKeyword);
    }
}
