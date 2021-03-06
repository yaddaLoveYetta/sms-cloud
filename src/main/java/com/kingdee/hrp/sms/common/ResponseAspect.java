package com.kingdee.hrp.sms.common;

import com.kingdee.hrp.sms.common.exception.BaseRuntimeException;
import com.kingdee.hrp.sms.common.pojo.NoJsonWarp;
import com.kingdee.hrp.sms.common.pojo.ResultWarp;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.ibatis.jdbc.Null;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * 负责将返回转换成统一消息格式
 * *序列化为的格式如下:
 * {
 * code: 200,
 * msg:msg,
 * data: data
 * }
 *
 * @author yadda<silenceisok@163.com>
 * @version 0.0.1
 * @date 2015-5-18
 */
@Aspect
@Component
@Order(value = 2)
public class ResponseAspect {

    @Resource
    private MappingJackson2HttpMessageConverter converter;

    private static final Logger logger = LoggerFactory.getLogger(ResponseAspect.class);

    /**
     * 这些返回类型的方法，返回值进行统一包装成value
     */
    private static Set<Class> TYPES = new HashSet<>(32);

    static {
        TYPES.add(Integer.class);
        TYPES.add(Double.class);
        TYPES.add(Float.class);
        TYPES.add(Long.class);
        TYPES.add(Short.class);
        TYPES.add(Byte.class);
        TYPES.add(Boolean.class);
        TYPES.add(Character.class);
        TYPES.add(String.class);
        TYPES.add(int.class);
        TYPES.add(double.class);
        TYPES.add(long.class);
        TYPES.add(short.class);
        TYPES.add(byte.class);
        TYPES.add(boolean.class);
        TYPES.add(char.class);
        TYPES.add(float.class);
    }

    /**
     * 拦截Controller中所有@ResponseBody
     */
    @Pointcut("execution(* com.kingdee.hrp.sms..*.*Controller..*(..)) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public void responseBodyPointCut() {
    }

    /**
     * Controller中所有@ResponseBody方法执行前的参数日志处理
     */
    @Before(value = "responseBodyPointCut()")
    public void doBeforeLog(JoinPoint pjp) throws Throwable {

        Signature signature = pjp.getSignature();

        if (signature instanceof MethodSignature) {
            logger.info("signature is MethodSignature");
        }

        String classType = pjp.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String clazzSimpleName = clazz.getSimpleName();

        MethodSignature methodSignature = (MethodSignature) signature;
        String methodName = pjp.getTarget().getClass()
                .getMethod(methodSignature.getName(), methodSignature.getParameterTypes()).getName();

        // 参数名列表
        String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);
        // 参数内容
        String logContent = buildLogContent(paramNames, pjp.getArgs());

        logger.info("调用方法：" + clazzName + "." + methodName + " 参数：" + logContent);

    }

    @Around(value = "responseBodyPointCut()")
    public void formatResult2JSON(ProceedingJoinPoint pjp) throws Throwable {

        Object ret = pjp.proceed(pjp.getArgs());

        Method currentMethod = getCurrentMethod(pjp);
        currentMethod.getAnnotatedReturnType().getType();

        // ret统一格式化成json,如果接口返回基本类型或者不能序列化成json类型eg:String,int,boolean...用data做key格式化成json形式
        ret = warpResult(currentMethod, ret);

        // 将业务返回值包装成固定的返回类型格式ResultWarp
        ResultWarp result = new ResultWarp(ret);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        // test
        response.addHeader("Access-Control-Allow-Origin", "*");

        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        converter.write(result, MediaType.APPLICATION_JSON, outputMessage);
        shutdownResponse(response);
    }

    /**
     * 获取当前执行的方法
     *
     * @param pjp ProceedingJoinPoint
     * @return Method
     * @throws NoSuchMethodException 没有该方法
     */
    private Method getCurrentMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        Signature sig = pjp.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = pjp.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        return currentMethod;
    }

    @AfterThrowing(pointcut = "responseBodyPointCut()", throwing = "throwable")
    public void handleForException(JoinPoint jp, Throwable throwable) throws Throwable {

        ResultWarp result = new ResultWarp();

        result.setCode(-1);
        result.setMsg(throwable.getMessage() == null ? printStackTraceToString(throwable) : throwable.getMessage());

        if (throwable instanceof BaseRuntimeException) {

            BaseRuntimeException baseRuntimeException = (BaseRuntimeException) throwable;

            result.setCode(baseRuntimeException.getErrCode());

        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        logger.error(jp.getSignature().getName() + "-throwable!", throwable);
        converter.write(result, MediaType.APPLICATION_JSON, outputMessage);
        shutdownResponse(response);
    }

    /**
     * 在已返回json的情况下，将Response关掉，不然可能会在这里输出json后还会再次输出一些内容
     */
    private void shutdownResponse(HttpServletResponse response) throws IOException {
        response.getOutputStream().close();
    }

    /**
     * 对result进行包装，将不能格式化成json{}|[]的对象包装成NoJsonWarp对象
     *
     * @param method 当前调用的方法
     * @param result 方法返回结果
     * @return Object
     * @date 2017-09-06 11:55:39 星期三
     */
    private Object warpResult(Method method, Object result) {

        if (method.getReturnType() == Void.TYPE) {
            // void类型方法
            result = "";
        } else if (TYPES.contains(method.getReturnType())) {
            result = new NoJsonWarp(result);
        }

/*        if (result == null || Integer.class.equals(result.getClass()) || Long.class.equals(result.getClass()) ||
                Float.class.equals(result.getClass()) || Double.class.equals(result.getClass())
                || Date.class.equals(result.getClass()) || Character.class.equals(result.getClass()) ||
                Byte.class.equals(result.getClass())
                || Short.class.equals(result.getClass()) || String.class.equals(result.getClass()) ||
                Boolean.class.equals(result.getClass())) {

            result = new NoJsonWarp(result);
        }*/

        return result;
    }

    private static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

    /**
     * 日志内容
     *
     * @param paramNames
     * @param args
     * @return
     */
    private static String buildLogContent(String[] paramNames, Object[] args) {

        StringBuilder sb = new StringBuilder();
        //boolean clazzFlag = true;

        for (int k = 0; k < args.length; k++) {

            Object arg = args[k];

            sb.append(paramNames[k]);

            if (null == arg) {
                sb.append("=null").append("; ");
                continue;
            }
            // 获取对象类型
            Class<?> argClass = arg.getClass();
            String typeName = arg.getClass().getTypeName();

            if (TYPES.contains(typeName)) {
                sb.append("=").append(arg).append("; ");
                continue;
            }

            if (argClass.isAssignableFrom(HttpServletRequest.class) ||
                    argClass.isAssignableFrom(HttpServletResponse.class)) {
                sb.append("=").append(arg).append("; ");
                continue;
            }
            // eg Map，List javabean override Object.toString()
            sb.append("=").append(arg.toString()).append("; ");

/*            if (clazzFlag) {
                sb.append(getFieldsValue(arg));
            }*/
        }

        return sb.toString();
    }

    /**
     * 得到方法参数的名称
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @return
     * @throws NotFoundException
     */
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException {

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        if (attr == null) {
            // exception
        }

        String[] paramNames = new String[cm.getParameterTypes().length];

        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;

        // https://blog.csdn.net/tabactivity/article/details/80770076
        // 读取方法变量名需要依赖字节码中的本地变量表，这个写法假设方法参数在本地变量表中一定是保存在最前面的，而且有序。
        // 这个假设很不靠谱的假设：
/*        for (int i = 0; i < paramNames.length; i++) {
            //paramNames即参数名
            paramNames[i] = attr.variableName(i + pos);
        }*/

        TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();

        for (int i = 0; i < attr.tableLength(); i++) {
            sortMap.put(attr.index(i), attr.variableName(i));
        }

        paramNames = Arrays.copyOfRange(sortMap.values().toArray(new String[0]), pos, paramNames.length + pos);


        return paramNames;
    }

    /**
     * 得到Object类型参数的值
     *
     * @param obj
     */
    public static String getFieldsValue(Object obj) {

        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();

        if (TYPES.contains(typeName)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (Field f : fields) {

            f.setAccessible(true);

            try {
                for (Class aClass : TYPES) {
                    if (f.getType() == aClass) {
                        sb.append(f.getName() + " = " + f.get(obj) + "; ");
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
