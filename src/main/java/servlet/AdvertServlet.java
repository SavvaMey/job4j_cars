package servlet;

import model.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.AdRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvertServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Mark> marks = AdRepository.instOf().findAllMarks();
        List<Body> bodies = AdRepository.instOf().findAllBodies();
        req.setAttribute("marks", marks);
        req.setAttribute("bodies", bodies);
        req.getRequestDispatcher("advert.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        Map<String, String> map = new HashMap<>();
        String photoName = null;
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    photoName =  item.getName();
                    if (!(photoName.equals(""))) {
                        File file = new File(folder + File.separator + item.getName());
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                        }
                    }
                } else {
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        if (photoName == null || photoName.equals("")) {
            photoName = "noPhoto.jpg";
        }
        Photo photo = Photo.of(photoName);
        Mark mark = AdRepository.instOf().findByNameMark(map.get("mark"));
        Body body = AdRepository.instOf().findByNameBody(map.get("body"));
        Car car = Car.of(
                mark,
                map.get("model"),
                body,
                Double.parseDouble(map.get("price")));
        User user = (User) req.getSession().getAttribute("user");
        AdRepository.instOf().createAd(
                Ad.of(
                map.get("description"),
                        car,
                        photo,
                        user,
                        false)
        );
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
