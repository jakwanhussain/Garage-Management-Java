/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence.hibernate.vehicles;

import gmsis.models.vehicles.WarrantyCompany;
import gmsis.persistence.hibernate.HibernateRepository;
import gmsis.persistence.vehicles.WarrantyCompanyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by sabrinasaytac on 02/03/2017.
 */
public class HibernateWarrantyCompanyRepository extends HibernateRepository<WarrantyCompany> implements WarrantyCompanyRepository {

    public HibernateWarrantyCompanyRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<WarrantyCompany> findByName(String search) {

        Session session = sessionFactory.openSession();
        List<WarrantyCompany> result = session.createQuery("from WarrantyCompany where lower(companyName) like lower(:name)", WarrantyCompany.class).setParameter("name", '%' + search + '%').list();
        session.close();
        return result;

    }
}
