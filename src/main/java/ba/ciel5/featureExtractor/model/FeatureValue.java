/**
 * Created on 09.04.2016.
 *
 * @author ymeke
 */

package ba.ciel5.featureExtractor.model;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ba.ciel5.featureExtractor.utils.HibernateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feature_value")
public class FeatureValue implements Serializable {

    @Id
    @Column(name = "feature_id")
    private String featureId;

    @Id
    @Column(name = "version_id")
    private String versionId;

    @Column(name = "value")
    private double value;

    /**
     * Default constructor Hibernate ORM. Do not use this constructor.
     */
    public FeatureValue() {

    }

    /**
     * Overwrite to supress hibernate warning
     * @param other
     * @return
     */
    public boolean equals(Object other) {
        return super.equals(other);
    }

    /**
     * Overwrite to supress hibernate warning
     * @return
     */
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @param featureId The ID of this IFeature type.
     * @param versionId The UUID of the version this value belongs to.
     * @param value     The value this feature has for this version.
     */
    public FeatureValue(String featureId, String versionId, double value) {
        this.featureId = featureId;
        this.versionId = versionId;
        this.value = value;
    }

    public String getFeatureId() {
        return featureId;
    }

    public String getVersionId() {
        return versionId;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Set the feature value for a version. If a value already exists, it will be updated.
     * ATTENTION: This method has a bug
     * @param featureId The ID of this IFeature type.
     * @param versionId The UUID of the version this value belongs to.
     * @param value     The value this feature has for this version.
     * @return The FeatureValue object which was subject to the change.
     */
    @Deprecated
    public static FeatureValue addOrUpdateFeatureValue(String featureId, String versionId, double value) {
        Session session = HibernateUtil.openSession();
        Transaction transaction = null;
        FeatureValue featureValue = null;
        try {
            transaction = session.beginTransaction();
            featureValue = addOrUpdateFeatureValue(featureId, versionId, value);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            session.close();
            throw new HibernateException(e.getMessage());
        }
        session.close();
        return featureValue;
    }

    /**
     * Set the feature value for a version. If a value already exists, it will be updated.
     *
     * @param featureId The ID of this IFeature type.
     * @param versionId The UUID of the version this value belongs to.
     * @param value     The value this feature has for this version.
     * @param session   The DB session to use.
     * @return The FeatureValue object which was subject to the change.
     */
    public static FeatureValue addOrUpdateFeatureValue(String featureId, String versionId, double value, Session session) throws HibernateException {
        FeatureValue featureValue = new FeatureValue(featureId, versionId, value);
        session.saveOrUpdate(featureValue);
        return featureValue;
    }

    /**
     * @param featureId The ID of this IFeature type.
     * @param versionId The UUID of the version this value belongs to.
     * @return A FeatureValue Object if one was found, null if not.
     */
    public static FeatureValue retrieveFeatureValue(String featureId, String versionId) {
        Session session = HibernateUtil.openSession();
        FeatureValue featureValue = retrieveFeatureValue(featureId, versionId, session);
        session.close();
        return featureValue;
    }

    /**
     * @param featureId The ID of this IFeature type.
     * @param versionId The UUID of the version this value belongs to.
     * @param session   The DB session to use.
     * @return A FeatureValue Object if one was found, null if not.
     */
    public static FeatureValue retrieveFeatureValue(String featureId, String versionId, Session session) {
        Query query = session.createQuery("from FeatureValue where feature_id = :featureId and version_id = :versionId");
        query.setParameter("featureId", featureId);
        query.setParameter("versionId", versionId);
        List result = query.list();
        if (result.size() > 0) {
            return (FeatureValue) result.get(0);
        }
        return null;
    }
    /**
     * Delete all entries in the SQL table
     */
    public static void turncate() {
        String hql = String.format("delete from ngram_count");
        Session session = HibernateUtil.openSession();
        try {
            Query query = session.createQuery(hql);
        } catch (HibernateException e) {
            session.close();
            throw new HibernateException(e.getMessage());
        }
        session.close();
    }
}
