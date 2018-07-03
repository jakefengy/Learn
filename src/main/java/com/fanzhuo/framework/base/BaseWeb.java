package com.fanzhuo.framework.base;


import com.fanzhuo.framework.ErrorCode;
import com.fanzhuo.framework.IConstants;
import com.fanzhuo.framework.base.json.JSONWrapperList;
import com.fanzhuo.framework.base.json.JSONWrapperObject;
import com.fanzhuo.framework.context.SpringContext;
import com.fanzhuo.framework.lang.errorcode.MyActionCodeException;
import com.fanzhuo.framework.util.StringUtil;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 控制器基类<br/>
 * 2018-7-2
 */
public class BaseWeb {

    private static final Logger log = LoggerFactory.getLogger(BaseWeb.class);

    private String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 初始化方法，每次请求都会被调用
     *
     * @param req
     * @param res
     */
    @ModelAttribute
    public void initEveryRequest(HttpServletRequest req, HttpServletResponse res) {
        log.debug("This method will be invoked on every request... ");
        // init Permissions

    }

    /**
     * get spring's bean <br>
     *
     * @param beanId
     * @return
     */
    protected Object getBean(String beanId) {
        return SpringContext.getBean(beanId);
    }

    /**
     * http session <br>
     *
     * @param req
     * @param key
     * @return
     */
    protected Object getSession(HttpServletRequest req, String key) {
        HttpSession session = req.getSession();
        return session.getAttribute(key);
    }

    protected void setSession(HttpServletRequest req, String key, Object object) {
        HttpSession session = req.getSession();
        session.setAttribute(key, object);
    }

    protected void removeSession(HttpServletRequest req, String key) {
        HttpSession session = req.getSession();
        session.removeAttribute(key);
    }

    //----parameters--------------------------------------------------------

    /**
     * 接收分页参数 pageSize，若为空则使用默认值IConstants.PAGE_SIZE
     */
    public int getPageSize(HttpServletRequest req) {
        String pageSize = req.getParameter("pageSize");
        if (StringUtils.isBlank(pageSize)) {
            return IConstants.PAGE_SIZE;
        }
        return Integer.valueOf(pageSize);
    }

    /**
     * 接收分页参数 pageNo，若为空则使用默认值IConstants.PAGE_NO
     */
    public int getPageNo(HttpServletRequest req) {
        String pageNo = req.getParameter("pageNo");
        if (StringUtils.isBlank(pageNo)) {
            return IConstants.PAGE_NO;
        }
        return Integer.valueOf(pageNo);
    }

    /**
     * 接收分页参数 pageBegin，若为空则使用默认值IConstants.PAGE_BEGIN
     */
    public int getPageBegin(HttpServletRequest req) {
        String pageBegin = req.getParameter("pageBegin");
        if (StringUtils.isBlank(pageBegin)) {
            return IConstants.PAGE_BEGIN;
        }
        return Integer.valueOf(pageBegin);
    }

    /**
     * 计算总页数
     */
    public int getPageTotal(int total, int pageSize) {
        if (total <= 0) {
            return 0;
        }
        if (pageSize <= 0) {
            pageSize = IConstants.PAGE_SIZE;
        }
        return new Double(Math.ceil(new Double(total) / pageSize)).intValue();
    }

    /**
     * 判断是否是ajax请求
     */
    public boolean isAjaxRequest(HttpServletRequest req) {
        // ajax request, not standard request header
        // XMLHttpRequest
        String header = req.getHeader("X-Requested-With");
        if (StringUtils.isNotBlank(header) && header.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    //----validate--------------------------------------------------------
    public boolean hasErrors(BindingResult validResult) {
        if (validResult.hasErrors()) {
            List<FieldError> errList = validResult.getFieldErrors();
            for (FieldError err : errList) {
//				log.debug("{}", err);
                log.debug("{}, {}, {}, {}, {}, {}", err.getObjectName(), err.getField(), err.getCode(), err.getDefaultMessage(), err.getRejectedValue(), err.getCodes());
            }
            return true;
        }
        return false;
    }

    //----response message--------------------------------------------------------

    /**
     * 返回JSON消息，返回单条记录时使用
     * <p>
     * e.g. <br/> {"status":200, "message":"操作成功！", "data":{"id":"1", "name":"系统管理员"}} <br/>
     */
    protected void writeJSONData(HttpServletResponse res,
                                 int status) {
        this.writeJSONData(res, null, status, "");
    }

    protected void writeJSONData(HttpServletResponse res,
                                 int status, String message) {
        this.writeJSONData(res, null, status, message);
    }

    protected void writeJSONData(HttpServletResponse res,
                                 Object data, int status) {
        this.writeJSONData(res, data, status, "");
    }

    protected void writeJSONData(HttpServletResponse res,
                                 Object data, int status, String message) {
        // e.g.
        // {"status":200, "message":"操作成功！", "data":{"id":"1", "name":"系统管理员"}}
        JSONWrapperObject wrapper = new JSONWrapperObject(this.getDateFormat(), data, status, message);
        log.debug(wrapper.toStringPretty());
        this.writeMessage(res, wrapper.toString());
    }

    /**
     * 返回JSON消息，返回多条记录时使用
     * <p>
     * e.g. <br/> {"status":200, "message":"操作成功！", "total":10, "list":[{"id":"1", "name":"系统管理员"},{"id":"2", "name":"测试员"}]} <br/>
     */
    protected void writeJSONList(HttpServletResponse res,
                                 int status) {
        this.writeJSONList(res, null, status, "");
    }

    protected void writeJSONList(HttpServletResponse res,
                                 int status, String message) {
        this.writeJSONList(res, null, status, message);
    }

    protected <E> void writeJSONList(HttpServletResponse res,
                                     List<E> list, int status) {
        this.writeJSONList(res, list, status, "");
    }

    protected <E> void writeJSONList(HttpServletResponse res,
                                     List<E> list, int status, String message) {
        int total = (list != null ? list.size() : 0);
        this.writeJSONList(res, list, total, status, message);
    }

    protected <E> void writeJSONList(HttpServletResponse res,
                                     List<E> list, int total, int status) {
        this.writeJSONList(res, list, total, status, "");
    }

    protected <E> void writeJSONList(HttpServletResponse res,
                                     List<E> list, int total, int status, String message) {
        // e.g.
        // {"status":200, "message":"操作成功！", "total":10, "list":[{"id":"1", "name":"系统管理员"},{"id":"2", "name":"测试员"}]}
        JSONWrapperList<E> wrapper = new JSONWrapperList<E>(this.getDateFormat(), list, total, status, message);
        log.debug(wrapper.toStringPretty());
        this.writeMessage(res, wrapper.toString());
    }

    /**
     * 返回JSON消息
     *
     * @param res
     * @param status {@code} com.fanzhuo.framework.base.json.JSONWrapper.SC_OK
     * @return e.g. <br/> {"status":200, "message":""} <br/>
     * @throws IOException
     */
    protected void writeJSONSimple(HttpServletResponse res, int status) {
        this.writeJSONSimple(res, status, "");
    }

    /**
     * 返回JSON消息
     *
     * @param res
     * @param status  {@code} com.fanzhuo.framework.base.json.JSONWrapper.SC_OK
     * @param message
     * @return e.g. <br/> {"status":200, "message":"操作成功！"} <br/> {"status":0, "message":"操作失败！"} <br/>
     * @throws IOException
     */
    protected void writeJSONSimple(HttpServletResponse res,
                                   int status, String message) {
        // 字符转义
        message = StringUtil.escapeJSONCharacter(StringUtils.trimToEmpty(message));
        // 响应内容
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":").append(status).append(", \"message\":\"").append(message).append("\"}");
        log.debug(sb.toString());
        this.writeMessage(res, sb.toString());
    }

    /**
     * 返回自定义格式消息 <br/>
     *
     * @param res
     * @param str
     */
    protected void writeMessage(HttpServletResponse res, String str) {
        PrintWriter out = null;
        try {
//			res.setCharacterEncoding("UTF-8");// see web.xml's EncodeFilter
            out = res.getWriter();
            out.write(str);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (out != null) IOUtils.closeQuietly(out);
        }
    }

    //----export excel--------------------------------------------------------

    /**
     * 将导出数据填充到excel模板中，并向客户端输出，使用默认文件名
     *
     * @param req
     * @param res
     * @param template
     * @param bean
     * @throws IOException
     * @throws InvalidFormatException
     * @throws ParsePropertyException
     * @throws MyActionCodeException
     */
    public void exportExcel(HttpServletRequest req, HttpServletResponse res,
                            String template, Map<String, Object> bean)
            throws IOException, ParsePropertyException, InvalidFormatException, MyActionCodeException {
        this.exportExcel(req, res, template, bean, null);
    }

    /**
     * 将导出数据填充到excel模板中，并向客户端输出，使用自定义文件名
     *
     * @param req
     * @param res
     * @param template     模板文件路径(相对路径)
     * @param bean         data bean
     * @param downloadName
     * @throws IOException
     * @throws ParsePropertyException
     * @throws InvalidFormatException
     * @throws MyActionCodeException
     */
    public void exportExcel(HttpServletRequest req, HttpServletResponse res,
                            String template, Map<String, Object> bean, String downloadName)
            throws IOException, ParsePropertyException, InvalidFormatException, MyActionCodeException {
        InputStream in = null;
        OutputStream out = null;
        try {
            String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS") + ".xls";
            // browser type
            if (StringUtils.isNotBlank(downloadName)) {
                fileName = this.encodeFileName(req, downloadName);
            }
            res.reset();
            res.setCharacterEncoding(IConstants.RESP_CHARACTER_ENCODE);
            res.setContentType(IConstants.RESP_CONTENT_TYPE_EXCEL);
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            // e.g. template="/template/xls_sys_user.xls";
            URL url = BaseWeb.class.getClassLoader().getResource(template);
            if (url == null || url.getPath() == null) {
                throw new MyActionCodeException(ErrorCode.err_excel_not_exists);
            }
            in = new FileInputStream(url.getPath());

            // generating excel files using XLS templates with data
            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook = transformer.transformXLS(in, bean);
            // output
            out = res.getOutputStream();
            workbook.write(out);

        } finally {
            if (out != null) IOUtils.closeQuietly(out);
            if (in != null) IOUtils.closeQuietly(in);
        }
    }

    /**
     * 根据不同浏览器编码文件名(中文)
     *
     * @param req
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public String encodeFileName(HttpServletRequest req, String fileName) throws UnsupportedEncodingException {
        String userAgent = req.getHeader("USER-AGENT");
        log.debug("USER-AGENT: {}", userAgent);
        if (userAgent != null && userAgent.indexOf("MSIE") >= 0) {
            // ie
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            // firefox ...
            fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        }
        return fileName;
    }
}

