package servlet;

import model.Ad;
import model.Body;
import model.Mark;
import service.AdRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Ad> adList = null;
        String chooseList = req.getParameter("chooseList");
        String marksValue = req.getParameter("marksValue");
        String bodiesValue = req.getParameter("bodiesValue");
          if (marksValue != null) {
             adList = AdRepository.instOf().viewAdWithDefineMark(marksValue);
          } else if (bodiesValue != null) {
            adList = AdRepository.instOf().viewAdWithDefineBody(bodiesValue);
        } else {
              if (chooseList == null) {
                  chooseList = "AllCars";
              }
              if (chooseList.equals("AllCars")) {
                  adList =  AdRepository.instOf().viewAllAds();

              } else if (chooseList.equals("CarsToday")) {
                  adList =  AdRepository.instOf().viewAdsToday();
              } else if (chooseList.equals("CarsByMark")) {
                  adList =  AdRepository.instOf().viewAdsToday();
              } else if (chooseList.equals("CarsPhoto")) {
                  adList = AdRepository.instOf().viewAdsWithPhoto();
              }
          }
        adList.forEach(System.out::println);
        List<Mark> marks = AdRepository.instOf().findAllMarks();
        List<Body> bodies = AdRepository.instOf().findAllBodies();
        req.setAttribute("ads", adList);
        req.setAttribute("marks", marks);
        req.setAttribute("bodies", bodies);
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Ad ad = AdRepository.instOf().findAd(id);
        ad.setSold(true);
        AdRepository.instOf().UpdateForSold(ad);
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}