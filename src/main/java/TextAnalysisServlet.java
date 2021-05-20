import com.google.gson.Gson;
import io.swagger.client.model.ErrMessage;
import io.swagger.client.model.ResultVal;
import io.swagger.client.model.TextLine;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "TextAnalysisServlet")
public class TextAnalysisServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

      // get uri
      String urlPath = request.getPathInfo();

      // if uri is empty or only has '/', then it is an invalid uri without parameters
      if (urlPath == null || urlPath.isEmpty() || urlPath.split("/").length<=1) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("missing parameters");
        return;
      }

      // split url
      String[] urlParts = urlPath.split("/");

      StringBuilder jb = new StringBuilder();
      String line = null;

      try {
        // read request body line by line
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);

        // convert the request body to TextLine
        TextLine text = new Gson().fromJson(jb.toString(), TextLine.class);

        response.setContentType("application/json");

        // if the function is wordcount, then count the number of words and send back
        if (urlParts[1].equals("wordcount")) {
          response.setStatus(HttpServletResponse.SC_OK);
          ResultVal resultVal = new ResultVal();
          int count = text.getMessage().split(" ").length;
          resultVal.setMessage(count);
          response.getWriter().write(new Gson().toJson(resultVal));
        // other functions
        } else {
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          ErrMessage errMessage = new ErrMessage();
          errMessage.setMessage("function need to be added");
          response.getWriter().write(new Gson().toJson(errMessage));
        }
        // if there is any exception, return an error message
      } catch (Exception e) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ErrMessage errMessage = new ErrMessage();
        errMessage.setMessage("invalid body");
        System.out.println(e.getMessage());
        response.getWriter().write(new Gson().toJson(errMessage));
      }
  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

  }
}
