package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.compressor.core;



import java.util.List;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.compressor.callback.BatchCompressCallback;
import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.compressor.callback.CompressCallback;

/**
 * Created by mayikang on 2018/9/12.
 */

/**
 * 压缩功能代理接口
 */
public interface IEasyCompressor {

    /******单个压缩******/
    void compress(String filePath, CompressCallback callback);

    /******批量压缩*****/
    void batchCompress(List<String> filePaths, BatchCompressCallback callback);

}
