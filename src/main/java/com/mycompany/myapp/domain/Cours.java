package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_cours")
    private String nomCours;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomCours")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCariere", "nomFiliere", "nomCours" }, allowSetters = true)
    private Set<Etudiant> etudiants = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "coursRequis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etudiants", "coursRequis", "nomConseiller" }, allowSetters = true)
    private Set<FiliereEtudes> filiereEtudes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCours() {
        return this.nomCours;
    }

    public Cours nomCours(String nomCours) {
        this.setNomCours(nomCours);
        return this;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getDescription() {
        return this.description;
    }

    public Cours description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Etudiant> getEtudiants() {
        return this.etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        if (this.etudiants != null) {
            this.etudiants.forEach(i -> i.setNomCours(null));
        }
        if (etudiants != null) {
            etudiants.forEach(i -> i.setNomCours(this));
        }
        this.etudiants = etudiants;
    }

    public Cours etudiants(Set<Etudiant> etudiants) {
        this.setEtudiants(etudiants);
        return this;
    }

    public Cours addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.setNomCours(this);
        return this;
    }

    public Cours removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.setNomCours(null);
        return this;
    }

    public Set<FiliereEtudes> getFiliereEtudes() {
        return this.filiereEtudes;
    }

    public void setFiliereEtudes(Set<FiliereEtudes> filiereEtudes) {
        if (this.filiereEtudes != null) {
            this.filiereEtudes.forEach(i -> i.removeCoursRequis(this));
        }
        if (filiereEtudes != null) {
            filiereEtudes.forEach(i -> i.addCoursRequis(this));
        }
        this.filiereEtudes = filiereEtudes;
    }

    public Cours filiereEtudes(Set<FiliereEtudes> filiereEtudes) {
        this.setFiliereEtudes(filiereEtudes);
        return this;
    }

    public Cours addFiliereEtudes(FiliereEtudes filiereEtudes) {
        this.filiereEtudes.add(filiereEtudes);
        filiereEtudes.getCoursRequis().add(this);
        return this;
    }

    public Cours removeFiliereEtudes(FiliereEtudes filiereEtudes) {
        this.filiereEtudes.remove(filiereEtudes);
        filiereEtudes.getCoursRequis().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cours)) {
            return false;
        }
        return getId() != null && getId().equals(((Cours) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", nomCours='" + getNomCours() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
