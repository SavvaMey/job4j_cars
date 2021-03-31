package service;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Function;

public class AdRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AdRepository.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();


    private static final class Lazy {
        private static final AdRepository INST = new AdRepository();
    }

    public static AdRepository instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public List<Ad> viewAdsToday() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateNew = calendar.getTime();
        return this.tx(session -> session.createQuery(
                "select distinct ad from Ad ad  "
                        + "join fetch ad.car "
                        + "join fetch ad.photo "
                        + "join fetch ad.user "
                        + "where ad.created >= :created ", Ad.class)
                .setParameter("created", dateNew)
                .list()
        );
    }

    public List<Ad> viewAllAds() {
        return this.tx(session -> session.createQuery(
                "select distinct ad from Ad ad "
                        + "join fetch ad.car "
                        + "join fetch ad.photo "
                        + "join fetch ad.user order by ad.created DESC")
                .list()
        );
    }

    public List<Ad> viewAdsWithPhoto() {
        return this.tx(session -> session.createQuery(
                "select distinct ad from Ad ad "
                        + "join fetch ad.car "
                        + "join fetch ad.photo "
                        + "join fetch ad.user "
                        + "where ad.photo.path != 'noPhoto.jpg'", Ad.class)
                .list()
        );
    }

    public List<Ad> viewAdWithDefineMark(String mark) {
        return this.tx(session -> session.createQuery(
                "select distinct ad from Ad ad  "
                        + "join fetch ad.car ca "
                        + "join fetch ad.photo "
                        + "join fetch ad.user "
                        + "where ca.mark.name = :mark ", Ad.class)
                .setParameter("mark", mark)
                .list()
        );
    }

    public List<Ad> viewAdWithDefineBody(String body) {
        return this.tx(session -> session.createQuery(
                "select distinct ad from Ad ad  "
                        + "join fetch ad.car ca "
                        + "join fetch ad.photo "
                        + "join fetch ad.user "
                        + "where ca.body.name = :key ", Ad.class)
                .setParameter("key", body)
                .list()
        );
    }

    public Ad UpdateForSold(Ad ad) {
        return this.tx(session -> {
                    session.update(ad);
                    return ad;
                }
        );
    }

    public Ad CreateAd(Ad ad) {
        return this.tx(session -> {
                    session.save(ad);
                    return ad;
                }
        );
    }

    public List<Mark> findAllMarks() {
        return this.tx(
                session -> session.createQuery("from Mark", Mark.class).list());
    }

    public List<Body> findAllBodies() {
        return this.tx(
                session -> session.createQuery("from Body", Body.class).list());
    }

    public Ad findAd(int id) {
        return this.tx(session -> session.get(Ad.class, id));
    }

    public Body findByNameBody(String body) {
        return this.tx(
                session -> session.createQuery(
                        "from Body where name = :key", Body.class)
                        .setParameter("key", body)
                        .uniqueResult());
    }

    public Mark findByNameMark(String mark) {
        return this.tx(
                session -> session.createQuery(
                        "from Mark where name = :key", Mark.class)
                        .setParameter("key", mark)
                        .uniqueResult());
    }

//    public Photo findByNamePhoto(String photoName) {
//        return this.tx(
//                session -> session.createQuery(
//                        "from Photo where path = :key", Photo.class)
//                        .setParameter("key", photoName)
//                        .uniqueResult());
//    }
}