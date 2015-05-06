package com.feng.jetty;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import com.feng.view.LoginServlet;
import com.feng.view.UserServlet;

/**
 * @see --------------------------------------------------------------------------------------------------------------
 * @see Jetty简介
 * @see Jetty是一个嵌入式的Web服务器
 * @see Jetty官网:http://www.eclipse.org/jetty/
 * @see Jetty下载:http://download.eclipse.org/jetty/
 * @see --------------------------------------------------------------------------------------------------------------
 * @see Jetty与Tomcat比较(取自网络)
 * @see Jetty的架构要比Tomcat简单一些,它是基于Handler来实现的,它可以通过Handler来进行扩展
 * @see Tomcat的架构是基于容器设计的,扩展Tomcat时需要了解Tomcat的整体设计结果,故不易扩展
 * @see --------------------------------------------------------------------------------------------------------------
 * @see Jetty使用
 * @see 0)版本选择:由于Jetty9需要JDK7的支持,所以我们这里使用jetty-distribution-8.1.10.v20130312.zip
 * @see 1)修改端口:修改\\JETTY_HOME\\etc\\jetty.xml第40行default="8080"即可
 * @see 2)非嵌入式的项目发布(有两种方式)
 * @see   非嵌入式的启动方式为命令行下执行该命令-->D:\Develop\jetty-distribution-8.1.10.v20130312>java -jar start.jar
 * @see   第一种:项目打成war包放到\\JETTY_HOME\\webapps\\下即可,访问地址为http://127.0.0.1:8080/warName(war名字大小写严格相同)
 * @see   第二种:类似于Tomcat的<Context path="" docBase="">的方式,即在\\JETTY_HOME\\contexts\\目录下新增一个名字随意的xml文件
 * @see         文件格式与该目录下的javadoc.xml相同,其中主要修改以下两处
 * @see         <Set name="contextPath">/testBbb</Set>
 * @see         <Set name="resourceBase">F:/Tool/Code/JavaSE/loginManager/WebRoot</Set>
 * @see 3)嵌入式的项目发布
 * @see   也就是把Jetty提供的jar加入到项目中(可以是Java项目或Web项目),然后编写通过一个main()启动Jetty,所用到的jar如下
 * @see   JETTY_HOME中的lib目录,和lib下的jsp目录,这俩目录中的jar加入到项目中即可(若不涉及jsp页面,就不需要jsp目录下的jar了)
 * @see   具体写法详见这里的startForServlet()和startForWebApp()方法
 * @see --------------------------------------------------------------------------------------------------------------
 * @create Jul 5, 2013 12:54:24 PM
 * @author 玄玉<http://blog.csdn.net/jadyer>
 */
public class MyServer {
    public static void main(String[] args) {
        Map<Class<? extends Servlet>, String> servletMap = new HashMap<Class<? extends Servlet>, String>();
        servletMap.put(UserServlet.class, "/user");
        servletMap.put(LoginServlet.class, "/login");
        //startForServlet(7089, "/testJettyDemo", servletMap);
        startForWebApp(9089, "/gbook", "E:/work/jetty-test/src/main/webapp");
    }
     
     
    /**
     * 针对Servlet
     * @see 通过servletMap参数可以使得该方法直接同时发布多个Servlet
     * @param port        访问服务器的端口
     * @param contextPath 访问服务器的地址
     * @param servletMap  发布的Servlet(这是一个Map,键为Servlet的class对象,值为映射url-pattern)
     */
    @SuppressWarnings("unused")
	private static void startForServlet(int port, String contextPath, Map<Class<? extends Servlet>, String> servletMap){
        //绑定端口
        Server server = new Server(port);
        //可以使用ServletContextHandler处理Servlet
        ServletContextHandler context = new ServletContextHandler();
        //添加Servlet并指定映射url-pattern
        for(Map.Entry<Class<? extends Servlet>, String> servletEntry : servletMap.entrySet()){
            context.addServlet(servletEntry.getKey(), servletEntry.getValue());
        }
        //此时访问路径就是http://127.0.0.1:port/contextPath/urlPattern
        context.setContextPath(contextPath);
        //绑定Handler
        server.setHandler(context);
        //启动服务
        try {
            server.start();
        } catch (Exception e) {
            System.out.println("启动Jetty时发生异常,堆栈轨迹如下");
            e.printStackTrace();
        }
        if(server.isStarted()){
            System.out.println("Servlet服务启动成功");
        }
    }
     
     
    /**
     * 针对一个Web应用
     * @see 注意resourceBase参数指向的应用所依赖的jar必须全部存放在其WebRoot\WEB-INF\lib目录中
     * @see 否则应用启动后,访问时会由于在lib中找不到jar而报告java.lang.ClassNotFoundException
     * @param port         访问服务器的端口
     * @param contextPath  访问服务器的地址
     * @param resourceBase Web应用的目录(需指向到WebRoot目录下)
     */
    private static void startForWebApp(int port, String contextPath, String resourceBase){
        Server server = new Server(port);
        //使用WebAppContext时就必须设置resourceBase
        WebAppContext context = new WebAppContext();
        //此时访问路径就是http://127.0.0.1:port/contextPath
        context.setContextPath(contextPath);
        context.setResourceBase(resourceBase);
        server.setHandler(context);
        try {
            server.start();
        } catch (Exception e) {
            System.out.println("启动Jetty时发生异常,堆栈轨迹如下");
            e.printStackTrace();
        }
        if(server.isStarted()){
            System.out.println("Web服务启动成功");
        }
    }
}