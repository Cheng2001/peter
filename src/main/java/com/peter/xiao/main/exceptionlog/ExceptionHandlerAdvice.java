package com.peter.xiao.main.exceptionlog;

import com.peter.xiao.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/** @author Eweee 全局异常处理类 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Value("${err.file}")
  private String errFile;

  @Value("${err.head}")
  private String head;

  private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**
   * 拦截所有Exception
   *
   * @param exception 异常对象
   * @param response 响应信息
   * @param request 请求信息
   */
  @ExceptionHandler(Exception.class)
  public void exception(
      Exception exception, HttpServletResponse response, HttpServletRequest request) {
    try {
      File file = new File(head);
      if (!file.exists()){
        file.mkdir();
      }
      String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
      FileWriter fileWriter = new FileWriter(head+"\\"+date+errFile, true);
      fileWriter.write(
          "========================================================================================================================================\n");
      // 记录错误时间
      fileWriter.write(sdf.format(new Date()) + "\n");
      // 记录用户设备型号
      fileWriter.write(request.getHeader("User-Agent") + "\n");
      // 获取ip信息
      String ip = getRemoteAddress(request);
      String requestURL = request.getRequestURL().toString();
      String url =
          request.getQueryString() == null
              ? requestURL + ""
              : (requestURL + "?" + request.getQueryString());
      // 记录请求地址
      fileWriter.write(url + "\n");
      // 记录请求ip
      fileWriter.write(ip + "\n");
      Enumeration<String> paramNames = request.getParameterNames();
      // 循环记录所有请求参数
      while (paramNames.hasMoreElements()) {
        String paramName = (String) paramNames.nextElement();
        String[] paramValues = request.getParameterValues(paramName);
        if (paramValues.length == 1) {
          String paramValue = paramValues[0];
          if (paramValue.length() != 0) {
            // 记录请求参数
            fileWriter.write("param: " + paramName + " = " + paramValue + "\n");
          }
        }
      }
      PrintWriter printWriter = new PrintWriter(fileWriter);
      // 把异常信息记录到日志文件
      exception.printStackTrace(printWriter);
      // 关闭writer流
      fileWriter.write("\n");
      fileWriter.close();
      printWriter.close();
    } catch (IOException e) {
      logger.info("can't open the file "+errFile);
    }
    // 控制台打印异常信息
    exception.printStackTrace();
    // 使用OutputStream流向客户端输出错误信息
    try{
      OutputStream os = response.getOutputStream();
      // 通过设置响应头控制浏览器以UTF-8的编码显示数据。(不加这串代码，会显示乱码)
      response.setHeader("content-type","text/html;charset=UTF-8");
      ResultData<Object> resultData = new ResultData<>();
      String e = exception.toString();
      // 异常类型
      resultData.setData("异常类型"+(e.contains(":")?e.substring(0,e.indexOf(":")):e));
      resultData.setCode(500);
      resultData.setMsg("异常信息:"+exception.getMessage());
      String data = JsonUtil.toJson(resultData);
      // 将字符串转换成字节数组，指定以UTF-8编码进行转换
      byte[] dataByteArr = data.getBytes(StandardCharsets.UTF_8);
      // 使用OutputStream流向客户端输出字节数组
      os.write(dataByteArr);
      // 关闭输出流
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 获取ip地址
   *
   * @param request 请求信息
   * @return ip地址
   */
  private static String getRemoteAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
