import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "nServlet")
public class nServlet extends HttpServlet {

  protected void doPost(HttpServletRequest request,
      HttpServletResponse res)
      throws ServletException, IOException {
    System.out.println("44");
      res.setContentType("text/plain");
      String urlPath = request.getPathInfo();
      // check we have a URL!
      if (urlPath == null || urlPath.isEmpty()) {
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        res.getWriter().write("missing paramterers");
        System.out.println("33");
        return;
      }

      String[] urlParts = urlPath.split("/");
      // and now validate url path and return the response status code
      // (and maybe also some value if input is valid)

      if (!isUrlValid(urlParts)) {
        System.out.println("11");
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        System.out.println("22");
        res.setStatus(HttpServletResponse.SC_OK);
        // do any sophisticated processing with urlParts which contains all the url params
        // TODO: process url params in `urlParts`
        //BufferedReader buffer = req.getReader();
        //String fileAsText = buffer.lines().collect(Collectors.joining());
        //res.getWriter().write(fileAsText);
        res.getWriter().write("It works!!");
      }
  }

  protected void doGet(HttpServletRequest req,
      HttpServletResponse res)
      throws ServletException, IOException {
    System.out.println("bbb");
      res.setContentType("text/plain");
      String urlPath = req.getPathInfo();

      // check we have a URL!
      if (urlPath == null || urlPath.isEmpty()) {
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
        res.getWriter().write("missing paramterers");
        return;
      }

      String[] urlParts = urlPath.split("/");
      // and now validate url path and return the response status code
      // (and maybe also some value if input is valid)

      if (!isUrlValid(urlParts)) {
        System.out.println("1111");
        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
      } else {
        res.setStatus(HttpServletResponse.SC_OK);
        System.out.println("2222");
        // do any sophisticated processing with urlParts which contains all the url params
        // TODO: process url params in `urlParts`
        res.getWriter().write("resortId"+ urlParts[1]);
        res.getWriter().write("It works!!");
      }
  }


  private boolean isUrlValid(String[] urlPath) {
    // TODO: validate the request url path according to the API spec
    // urlPath  = "/1/seasons/2019/day/1/skier/123"
    // urlParts = [, 1, seasons, 2019, day, 1, skier, 123]
    if (urlPath.length>2 && urlPath[2].equals("seasons")) {
      return true;
    }
    return false;
  }
}
