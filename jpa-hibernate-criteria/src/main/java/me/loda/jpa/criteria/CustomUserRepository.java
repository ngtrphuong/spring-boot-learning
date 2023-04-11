package me.loda.jpa.criteria;

import java.util.Collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import me.loda.jpa.criteria.User.UserType;

@Repository
public class CustomUserRepository {

    @PersistenceContext
    private EntityManager em;

    public User getUserById(Long id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Predicate condition = builder.equal(root.get(User_.ID), id);

        query.select(root).where(condition);
        return em.createQuery(query).getSingleResult();
    }

    public Collection<User> getUserByComplexConditions(String name, UserType type) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Predicate hasNameLike = builder.like(root.get(User_.NAME), name);
        Predicate hasType = builder.equal(root.get(User_.TYPE), type);

        Predicate condition = builder.and(hasNameLike, hasType);

        query.select(root).where(condition);
        return em.createQuery(query).getResultList();
    }
}
