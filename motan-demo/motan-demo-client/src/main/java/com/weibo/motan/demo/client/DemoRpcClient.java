/*
 *  Copyright 2009-2016 Weibo, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.weibo.motan.demo.client;

import com.weibo.motan.demo.service.MotanDemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoRpcClient {

    public static void main(String[] args) throws InterruptedException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:motan_demo_client.xml"});
        long start = System.currentTimeMillis();
        MotanDemoService service = (MotanDemoService) ctx.getBean("motanDemoReferer");
        for (int i = 0; i < 1; i++) {
            System.out.println(service.hello("motan" + i));
            //Thread.sleep(100);
        }
        long end = System.currentTimeMillis()-start;
        System.err.println("使用时间：" + end);
        System.out.println("motan demo is finish.");
        System.exit(0);
    }

}
