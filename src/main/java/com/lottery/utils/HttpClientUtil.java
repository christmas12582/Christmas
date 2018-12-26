package com.lottery.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;


public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String ContentEncoding = "UTF-8";
    private static final int SocketTimeout = 5000;

    public static String doGet(String url)   {
        logger.info("==================================doGet=={}",url);
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(SocketTimeout);
        GetMethod getMethod = new GetMethod(url);
        getMethod.addRequestHeader("Content-Type","application/json");

        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SocketTimeout);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            logger.info("==================================doGet=={}",statusCode);

            if (statusCode != HttpStatus.SC_OK&&statusCode != HttpStatus.SC_CREATED&&statusCode != HttpStatus.SC_NO_CONTENT) {
                logger.error("请求出错: "+ getMethod.getStatusLine());
                return response;
            }

            byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
            response = new String(responseBody, ContentEncoding);
            logger.info("----------response:" + response);
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            logger.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            getMethod.releaseConnection();
        }
        return response;
    }

    public static String doPut(String uri,HashMap<String,Object> paramsmap){

        String response = "";
        String params= JsonUtils.toJson(paramsmap);
        logger.info("==================================doPut=={},{}",uri,params);

        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding);
        PutMethod putMethod = new PutMethod(uri);
        putMethod.addRequestHeader( "Content-Type","application/json" );

        putMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding );
        putMethod.setRequestBody(params);
        try{
            int statusCode = httpClient.executeMethod( putMethod );
            logger.info("==================================doPut=={}",statusCode);
            if (statusCode != HttpStatus.SC_OK&&statusCode != HttpStatus.SC_CREATED&&statusCode != HttpStatus.SC_NO_CONTENT) {
                logger.error("Method failed: "+putMethod.getStatusLine() );
                return response;
            }
            byte[] responseBody = putMethod.getResponseBody();
            response = new String(responseBody,ContentEncoding);
            logger.info("----------response:" + response);
        }catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            logger.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            putMethod.releaseConnection();
        }
        return response;
    }

    public static String doPost(String url,HashMap<String,Object> paramsmap){
        String response = "";
        String params= JsonUtils.toJson(paramsmap);
        logger.info("==================================doPost=={},{}",url,params);
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding);
        PostMethod postMethod= new PostMethod(url);
        postMethod.addRequestHeader("Content-Type", "application/json" );

        postMethod.setRequestBody(params);

        try {

            int statusCode = httpClient.executeMethod(postMethod);
            logger.info("==================================doPost=={}",statusCode);
            if (statusCode != HttpStatus.SC_OK&&statusCode != HttpStatus.SC_CREATED&&statusCode != HttpStatus.SC_NO_CONTENT) {
                logger.error("Method failed : "+postMethod.getStatusLine() );
                return response;
            }
            byte[] responseBody=postMethod.getResponseBody();
            response = new String(responseBody,ContentEncoding);
            logger.info("----------response:" + response);
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            logger.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            postMethod.releaseConnection();
        }
        return response;
    }




    public static String doPostwithxml(String url,String params){
        String response = "";
        logger.info("==================================doPost=={},{}",url,params);
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding);
        PostMethod postMethod= new PostMethod(url);
//        postMethod.addRequestHeader("Content-Type", "application/json" );

        postMethod.setRequestBody(params);

        try {

            int statusCode = httpClient.executeMethod(postMethod);
            logger.info("==================================doPost=={}",statusCode);
            if (statusCode != HttpStatus.SC_OK&&statusCode != HttpStatus.SC_CREATED&&statusCode != HttpStatus.SC_NO_CONTENT) {
                logger.error("Method failed : "+postMethod.getStatusLine() );
                return response;
            }
            byte[] responseBody=postMethod.getResponseBody();
            response = new String(responseBody,ContentEncoding);
            logger.info("----------response:" + response);
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            logger.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            postMethod.releaseConnection();
        }
        return response;
    }

    public static String doDelete(String url)  {
        logger.info("=================================doDelete=={}",url);

        String response= "";
        HttpClient httpClient= new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding);
        DeleteMethod deletemethod = new DeleteMethod(url);
        deletemethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        deletemethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SocketTimeout);
        deletemethod.addRequestHeader("Content-Type", "application/json" );

        try{

            int statusCode = httpClient.executeMethod(deletemethod);
            logger.info("==================================doDelete=={}",statusCode);
            if (statusCode != HttpStatus.SC_OK&&statusCode != HttpStatus.SC_CREATED&&statusCode != HttpStatus.SC_NO_CONTENT) {
                logger.error("Method failed: " + deletemethod.getStatusLine());
                return response;
            }
            response= new String(deletemethod.getResponseBody(),ContentEncoding);
            logger.info("----------response:" + response);
        }catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            logger.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            logger.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            deletemethod.releaseConnection();
        }
        return response;
    }




}
