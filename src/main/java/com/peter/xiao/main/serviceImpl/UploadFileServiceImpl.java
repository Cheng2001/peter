package com.peter.xiao.main.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peter.xiao.main.constant.CosParams;
import com.peter.xiao.main.entity.UploadFile;
import com.peter.xiao.main.mapper.UploadFileMapper;
import com.peter.xiao.main.service.IUploadFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Eweee
 * @since 2020-09-08
 */
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile> implements IUploadFileService {

  /**
   * 文件操作mapper
   */
  @Resource
  UploadFileMapper fileMapper;

  @Override
  public Map<?, ?> getTmpToken() {
    // 配置map
    TreeMap<String, Object> config = new TreeMap<>();
    try {
      // 替换为您的 SecretId
      config.put("SecretId", CosParams.SECRET_ID);
      // 替换为您的 SecretKey
      config.put("SecretKey", CosParams.SECRET_KEY);

      // 临时密钥有效时长，单位是秒，默认1800秒，目前主账号最长2小时（即7200秒），子账号最长36小时（即129600秒）
      config.put("durationSeconds", 7200);

      // 换成您的 bucket
      config.put("bucket", CosParams.BUCKET);
      // 换成 bucket 所在地区
      config.put("region", CosParams.REGION);

      // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子：a.jpg 或者 a/* 或者 * 。
      // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
      config.put("allowPrefix", "*");

      // 密钥的权限列表。简单上传、表单上传和分片上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
      String[] allowActions = new String[]{
              // 简单上传
              "name/cos:PutObject",
              // 表单上传、小程序上传
              "name/cos:PostObject",
              // 分片上传
              "name/cos:InitiateMultipartUpload",
              "name/cos:ListMultipartUploads",
              "name/cos:ListParts",
              "name/cos:UploadPart",
              "name/cos:CompleteMultipartUpload"
      };
      config.put("allowActions", allowActions);
      // 成功返回密钥
      return CosStsClient.getCredential(config).toMap();
    } catch (Exception e) {
      //失败抛出异常
      throw new IllegalArgumentException("no valid secret !");
    }
  }

  @Override
  public String getDownUrl(String key) {
    // 初始化永久密钥信息
    // 密钥id
    String secretId = CosParams.SECRET_ID;
    // 密钥key
    String secretKey = CosParams.SECRET_KEY;
    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    Region region = new Region(CosParams.REGION);
    ClientConfig clientConfig = new ClientConfig(region);
    // 生成cos客户端
    COSClient cosClient = new COSClient(cred, clientConfig);
    // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
    String bucketName = CosParams.BUCKET;
    GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
    // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
    // 这里设置签名在半个小时后过期
    Date expirationDate = new Date(System.currentTimeMillis() + 30L * 60L * 1000L);
    req.setExpiration(expirationDate);
    // 获得URL对象
    URL url = cosClient.generatePresignedUrl(req);
    String path = url.toString();
    cosClient.shutdown();
    return path;
  }

  @Override
  public List<UploadFile> getFiles(Integer userId, int page, int limit) {
    return fileMapper.files(userId, page*limit, limit);
  }

  @Override
  public int getCountByUser(int userId) {
    QueryWrapper<UploadFile> wrapper = new QueryWrapper<>();
    // 定义条件
    wrapper.eq("upload_user", userId);
    return this.count(wrapper);
  }
}
