package com.taole.toolkit.util;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

//import com.taole.framework.util.MessageUtils;

public class ReturnResult {

    private String code;

    private String desc;

    private Object object;

    public ReturnResult() {
        super();
    }

    public ReturnResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ReturnResult(String code, String desc, Object object) {
        this.code = code;
        this.desc = desc;
        this.object = object;
    }

    public ReturnResult(String[] code, Object object) {
        this.code = code[0];
        this.desc = code[1];
        this.object = object;
    }

    // jerry added
    public ReturnResult(String code, HttpServletResponse response) {
        this.code = code;
        this.desc = com.taole.framework.util.MessageUtils.getLocaleMessage("com.taole.prjms.ReturnCode.FAILURE");
        response.setHeader("code", this.code);
        response.setHeader("desc", this.desc);
    }

    public ReturnResult(String[] code) {
        this.code = code[0];
        this.desc = code[1];
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(String[] key) {
        this.code = key[0];
        this.desc = key[1];
    }

    public void setResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public JSONObject getJsonResult() {
        JSONObject resultjo = new JSONObject();
        try {
            resultjo.put("result_code", this.getCode());
            resultjo.put("result_desc", this.getDesc());
            if (this.getObject() != null) {
                resultjo.put("result_object", object);
            }
            // 为了兼容js的submit提交方式
            boolean success = false;
            if (this.getCode().equals("100")) {
                success = true;
            }
            resultjo.put("success", success);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultjo;
    }

    /**
     * EXt Form自动赋值json返回结构
     *
     * @return
     */
    public JSONObject getJsonResult(String args) {
        JSONObject resultjo = new JSONObject();
        try {
            resultjo.put("result_code", this.getCode());
            resultjo.put("result_desc", this.getDesc());
            if (this.getObject() != null) {
                resultjo.put(StringUtils.isNotBlank(args) ? args : "data", object);
            }
            // 为了兼容js的submit提交方式
            boolean success = false;
            if (this.getCode().equals("100")) {
                success = true;
            }
            resultjo.put("success", success);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultjo;
    }

    public JSONObject getJsonResult(String code, String desc) {
        JSONObject resultjo = new JSONObject();
        try {
            resultjo.put("result_code", code);
            resultjo.put("result_desc", desc);
            // 为了兼容js的submit提交方式
            boolean success = false;
            if (code.equals("100")) {
                success = true;
            }
            resultjo.put("success", success);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultjo;
    }
}
