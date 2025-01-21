package com.drexelbuildingsupply.servlet;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;

@WebServlet("/drexel-login")
public class DrexelLoginServlet extends HttpServlet {

    private ScriptingContainer container;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize JRuby container
        container = new ScriptingContainer(LocalContextScope.SINGLETHREAD, LocalVariableBehavior.TRANSIENT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Path to your Ruby file in /webapp
        // If you put it in /webapp/ruby, adjust the path accordingly:
        String rubyFilePath = getServletContext().getRealPath("/drexel_login.rb");
        // or: getServletContext().getRealPath("/ruby/drexel_login.rb");

        // Execute the Ruby script
        // The script prints HTML to stdout; we’ll capture the output
        String scriptOutput;
        try {
            // The runScriptlet call returns the last expression, 
            // but 'puts' writes to stdout, so we temporarily redirect stdout
            container.setArgv(new String[]{});
            container.runScriptlet("require 'stringio'");
            container.runScriptlet("$stdout = StringIO.new");
            container.runScriptlet("load '" + rubyFilePath.replace("\\", "\\\\") + "'");
            scriptOutput = (String)container.runScriptlet("$stdout.string");
            // Reset stdout
            container.runScriptlet("$stdout = STDOUT");
        } catch (Exception e) {
            throw new ServletException("Error running drexel_login.rb", e);
        }

        // Write the Ruby-generated HTML to the response
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().write(scriptOutput);
    }

    @Override
    public void destroy() {
        if (container != null) {
            container.terminate();
        }
        super.destroy();
    }
}
