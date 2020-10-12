package com.peter.xiao.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author Eweee
 * <p>
 * axios获取post过来的数据
 */
public class PostParam {

  private String postParam = "";

  public String getPostParam() {
    return postParam;
  }

  /**
   * 获取响应体中的参数
   *
   * @param request 请求对象
   * @return 获取的参数
   */
  public PostParam(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = request.getReader();) {
      char[] buff = new char[1024];
      int len;
      while ((len = reader.read(buff)) != -1) {
        sb.append(buff, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    postParam = sb.toString();
  }

  /**
   * 将post的数据转换成map
   *
   * @return 封装成map后的数据
   */
  public Map<String, String> getToMap() {
    JsonUtil json = new JsonUtil(postParam);
    return json.getAsMap();
  }
}
