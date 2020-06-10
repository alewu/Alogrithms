package org.ale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 轮询
 */
public class TestRoundRobin {
    /**
     * 定义map, key-ip,value-weight
     */
    static Map<String, Integer> ipMap = new HashMap<>();

    static {
        ipMap.put("192.168.13.1", 1);
        ipMap.put("192.168.13.2", 1);
        ipMap.put("192.168.13.3", 1);

    }


    public static void main(String[] args) {
        TestRoundRobin testRoundRobin = new TestRoundRobin();
        for (int i = 0; i < 10; i++) {
            String serverIp = testRoundRobin.roundRobin();
            System.out.println(serverIp);
        }

    }

    public static Integer pos = 0;

    public String roundRobin() {
        Map<String, Integer> ipServerMap = new ConcurrentHashMap<>(ipMap);

        //    2.取出来key,放到set中
        Set<String> ipSet = ipServerMap.keySet();

        //    3.set放到list，要循环list取出
        ArrayList<String> ipList = new ArrayList<>(ipSet);

        String serverName = null;

        //    4.定义一个循环的值，如果大于set就从0开始
        synchronized (pos) {
            if (pos >= ipSet.size()) {
                pos = 0;
            }
            serverName = ipList.get(pos);
            //轮询+1
            pos++;
        }
        return serverName;

    }
}
