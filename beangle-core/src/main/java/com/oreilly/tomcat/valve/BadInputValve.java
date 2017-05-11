// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   BadInputValve.java

package com.oreilly.tomcat.valve;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.valves.RequestFilterValve;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class BadInputValve extends RequestFilterValve
{

    public BadInputValve()
    {
        escapeQuotes = false;
        escapeAngleBrackets = false;
        escapeJavaScript = false;
        quotesHashMap = new HashMap();
        angleBracketsHashMap = new HashMap();
        javaScriptHashMap = new HashMap();
        parameterEscapes = new HashMap();
        quotesHashMap.put("\"", "&quot;");
        quotesHashMap.put("'", "&#39;");
        quotesHashMap.put("`", "&#96;");
        angleBracketsHashMap.put("<", "&lt;");
        angleBracketsHashMap.put(">", "&gt;");
        javaScriptHashMap.put("document(.*)\\.(.*)cookie", "document&#46;&#99;ookie");
        javaScriptHashMap.put("eval(\\s*)\\(", "eval&#40;");
        javaScriptHashMap.put("setTimeout(\\s*)\\(", "setTimeout$1&#40;");
        javaScriptHashMap.put("setInterval(\\s*)\\(", "setInterval$1&#40;");
        javaScriptHashMap.put("execScript(\\s*)\\(", "exexScript$1&#40;");
        javaScriptHashMap.put("(?i)javascript(?-i):", "javascript&#58;");
        log.info("BadInputValve instantiated.");
    }

    public boolean getEscapeQuotes()
    {
        return escapeQuotes;
    }

    public void setEscapeQuotes(boolean escapeQuotes)
    {
        this.escapeQuotes = escapeQuotes;
        if(escapeQuotes)
            parameterEscapes.putAll(quotesHashMap);
    }

    public boolean getEscapeAngleBrackets()
    {
        return escapeAngleBrackets;
    }

    public void setEscapeAngleBrackets(boolean escapeAngleBrackets)
    {
        this.escapeAngleBrackets = escapeAngleBrackets;
        if(escapeAngleBrackets)
            parameterEscapes.putAll(angleBracketsHashMap);
    }

    public boolean getEscapeJavaScript()
    {
        return escapeJavaScript;
    }

    public void setEscapeJavaScript(boolean escapeJavaScript)
    {
        this.escapeJavaScript = escapeJavaScript;
        if(escapeJavaScript)
            parameterEscapes.putAll(javaScriptHashMap);
    }

    public String getInfo()
    {
        return info;
    }

    public void invoke(Request request, Response response)
        throws IOException, ServletException
    {
        if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse))
        {
            getNext().invoke(request, response);
            return;
        }
        if(processAllowsAndDenies(request, response))
        {
            filterParameters(request);
            getNext().invoke(request, response);
        }
    }

    public boolean processAllowsAndDenies(Request request, Response response)
        throws IOException, ServletException
    {
        ParameterMap paramMap = (ParameterMap)request.getParameterMap();
        for(Iterator y = paramMap.keySet().iterator(); y.hasNext();)
        {
            String name = (String)y.next();
            String values[] = request.getParameterValues(name);
            if(!checkAllowsAndDenies(name, response))
                return false;
            if(values != null)
            {
                for(int i = 0; i < values.length; i++)
                {
                    String value = values[i];
                    if(!checkAllowsAndDenies(value, response))
                        return false;
                }

            }
        }

        return true;
    }

    public boolean checkAllowsAndDenies(String property, Response response)
        throws IOException, ServletException
    {
        if(denies.length == 0 && allows.length == 0)
            return true;
        for(int i = 0; i < denies.length; i++)
        {
            Matcher m = denies[i].matcher(property);
            if(m.find())
            {
                ServletResponse sres = response.getResponse();
                if(sres instanceof HttpServletResponse)
                {
                    HttpServletResponse hres = (HttpServletResponse)sres;
                    hres.sendError(403);
                    return false;
                }
            }
        }

        for(int i = 0; i < allows.length; i++)
        {
            Matcher m = allows[i].matcher(property);
            if(m.find())
                return true;
        }

        if(denies.length > 0 && allows.length == 0)
            return true;
        ServletResponse sres = response.getResponse();
        if(sres instanceof HttpServletResponse)
        {
            HttpServletResponse hres = (HttpServletResponse)sres;
            hres.sendError(403);
        }
        return false;
    }

    public void filterParameters(Request request)
    {
        ParameterMap paramMap = (ParameterMap)request.getParameterMap();
        paramMap.setLocked(false);
        for(Iterator escapesIterator = parameterEscapes.keySet().iterator(); escapesIterator.hasNext();)
        {
            String patternString = (String)escapesIterator.next();
            Pattern pattern = Pattern.compile(patternString);
            String paramNames[] = (String[])paramMap.keySet().toArray(STRING_ARRAY);
            for(int i = 0; i < paramNames.length; i++)
            {
                String name = paramNames[i];
                String values[] = request.getParameterValues(name);
                Matcher matcher = pattern.matcher(name);
                boolean nameMatch = matcher.find();
                if(nameMatch)
                {
                    String newName = matcher.replaceAll((String)parameterEscapes.get(patternString));
                    request.addParameter(newName, values);
                    paramMap.remove(name);
                    log.warn((new StringBuilder("Parameter name ")).append(name).append(" matched pattern \"").append(patternString).append("\".  Remote addr: ").append(request.getRemoteAddr()).toString());
                }
                if(values != null)
                {
                    for(int j = 0; j < values.length; j++)
                    {
                        String value = values[j];
                        matcher = pattern.matcher(value);
                        boolean valueMatch = matcher.find();
                        if(valueMatch)
                        {
                            String newValue = matcher.replaceAll((String)parameterEscapes.get(patternString));
                            values[j] = newValue;
                            log.warn((new StringBuilder("Parameter \"")).append(name).append("\"'s value \"").append(value).append("\" matched pattern \"").append(patternString).append("\".  Remote addr: ").append(request.getRemoteAddr()).toString());
                        }
                    }

                }
            }

        }

        paramMap.setLocked(true);
    }

    public String toString()
    {
        return "BadInputValve";
    }

    private static Log log = LogFactory.getLog("com/oreilly/tomcat/valve/BadInputValve");
    protected static String info = "com.oreilly.tomcat.valve.BadInputValve/2.0";
    private static final String STRING_ARRAY[] = new String[0];
    protected boolean escapeQuotes;
    protected boolean escapeAngleBrackets;
    protected boolean escapeJavaScript;
    protected HashMap quotesHashMap;
    protected HashMap angleBracketsHashMap;
    protected HashMap javaScriptHashMap;
    protected HashMap parameterEscapes;

}
