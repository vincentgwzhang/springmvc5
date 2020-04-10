package org.personal.views;

import java.util.Date;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("helloView")
public class HelloView implements View
{
    @Override
    public String getContentType()
    {
        return MediaType.TEXT_HTML_VALUE;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.getWriter().print("<a href=\"http://localhost:22900/test\">Back to home</a>, Hello view, time: " + new Date());
    }
}
