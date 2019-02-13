package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.models.net;


import android.util.Log;


import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.application.IPSCApplication;


/**
 * Created by wuqaing on 18/12/14.
 */

/**
 * 自定义基础响应转换类，处理响应头相关操作
 */
public class NormalResponseParser implements ResponseParser {
    private String TAG = "@link NormalResponseParser";

    @Override
    public void checkResponse(UriRequest request) throws Throwable {

        if (request != null) {
        }
    }

    /**
     * 对响应结果做类型转换，比如转 json
     * 或者对特定返回结果进行过滤
     *
     * @param resultType
     * @param resultClass
     * @param result
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {

        if (resultClass == List.class) {
            // 这里只是个示例, 不做json转换.
            List<NormalResponse> list = new ArrayList<NormalResponse>();
            NormalResponse response = new NormalResponse();
            response.setResult(result);
            list.add(response);
            return list;
            // fastJson 解析:
            // return JSON.parseArray(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
        } else {
            // 这里只是个示例, 不做json转换.
            NormalResponse response = new NormalResponse();
            response.setResult(result);
            return response;
            // fastjson 解析:
            // return JSON.parseObject(result, resultClass);
        }

    }
}
