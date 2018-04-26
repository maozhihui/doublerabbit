package com.comba.server.extensions.core.filter;

import org.junit.Test;

import javax.script.SimpleBindings;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/1 10:59
 **/
public class NashornJsEvaluatorTest {

    @Test
    public void testJs() throws Exception{
        String filter = "\"switch\"==1";
        NashornJsEvaluator evaluator = new NashornJsEvaluator(filter);
        Map<String,Object> param = new HashMap<>();
        param.put("\"switch\"",1);
        boolean res = evaluator.execute(new SimpleBindings(param));
        System.out.println(res);
    }

    @Test
    public void testComplexExpress() throws Exception{
        String filter = "(A||B)&&C";
        NashornJsEvaluator evaluator = new NashornJsEvaluator(filter);
        Map<String,Object> param = new HashMap<>();
        param.put("A",false);
        param.put("B",true);
        param.put("C",true);
        boolean res = evaluator.execute(new SimpleBindings(param));
        System.out.println(res);
    }

    @Test
    public void testJsFloat() throws Exception{
        String filter = "name>1.0 && name<6.0";
        NashornJsEvaluator evaluator = new NashornJsEvaluator(filter);
        Map<String,Object> param = new HashMap<>();
        param.put("name",8.3);
        boolean res = evaluator.execute(new SimpleBindings(param));
        System.out.println(res);
    }

    @Test
    public void testJsTime() throws Exception{
        String filter = "Now>'2017-9-12 12:00:02'";
        NashornJsEvaluator evaluator = new NashornJsEvaluator(filter);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> param = new HashMap<>();
        //param.put(RuleVariable.NOW_VAR,dateFormat.format(new Date()));
        boolean res = evaluator.execute(new SimpleBindings(param));
        System.out.println(res);
    }

    @Test
    public void testJsTime02() throws Exception{
        String filter = "Now>1505188802000 && Now < 1513051202000";
        NashornJsEvaluator evaluator = new NashornJsEvaluator(filter);
        Map<String,Object> param = new HashMap<>();
        //param.put(RuleVariable.NOW_VAR,System.currentTimeMillis());
        boolean res = evaluator.execute(new SimpleBindings(param));
        System.out.println(res);
    }

    @Test
    public void testJsTime3() throws Exception{
        String filter = "'2017-9-12 12:00:02'<Now && '2017-12-12 12:00:02'>Now";
        String[] splitArray = filter.split("'");
        System.out.println("size = " + splitArray.length);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 1;i < splitArray.length;i+=2){
            long time = dateFormat.parse(splitArray[i]).getTime();
            splitArray[i] = String.valueOf(time);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splitArray.length; i++)
            sb.append(splitArray[i]);
        System.out.println("filter = " + sb.toString());
    }

    @Test
    public void testBitset(){
        BitSet bitSet = new BitSet();
        bitSet.set(1,true);
        bitSet.set(2,true);
        bitSet.set(3,true);
        bitSet.set(4,false);
        bitSet.set(5,true);
        bitSet.set(6,true);
        System.out.println("size="+bitSet.size());
        System.out.println(bitSet);
        System.out.println("true.size=" + bitSet.cardinality());
    }

}
