package com.ying.weixin_order.utils;

import com.ying.weixin_order.VO.ResultVO;

/**
 * @Author sunmengying
 * @Date 2019/1/9 10:31
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setData(object);
        resultVO.setMessage("成功");
        return  resultVO;
    }

    public static  ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
