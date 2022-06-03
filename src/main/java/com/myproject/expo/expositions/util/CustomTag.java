package com.myproject.expo.expositions.util;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * The Class represents created custom tag. Prints the author of this project
 */
public class CustomTag extends SimpleTagSupport {

    @Override
    public void doTag() throws  IOException {
        JspWriter output = getJspContext().getOut();
        output.print("by Alexandra Bushmelova");
    }
}
