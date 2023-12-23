package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Carriere.
 */
@Entity
@Table(name = "carriere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Carriere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_cariere")
    private String nomCariere;

    @Column(name = "prerequis_academiques")
    private String prerequisAcademiques;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomCariere")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCariere", "nomFiliere", "nomCours" }, allowSetters = true)
    private Set<Etudiant> etudiants = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "carrieres", "filiereEtudes" }, allowSetters = true)
    private ConseillerOrientation nomConseiller;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Carriere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomCariere() {
        return this.nomCariere;
    }

    public Carriere nomCariere(String nomCariere) {
        this.setNomCariere(nomCariere);
        return this;
    }

    public void setNomCariere(String nomCariere) {
        this.nomCariere = nomCariere;
    }

    public String getPrerequisAcademiques() {
        return this.prerequisAcademiques;
    }

    public Carriere prerequisAcademiques(String prerequisAcademiques) {
        this.setPrerequisAcademiques(prerequisAcademiques);
        return this;
    }

    public void setPrerequisAcademiques(String prerequisAcademiques) {
        this.prerequisAcademiques = prerequisAcademiques;
    }

    public Set<Etudiant> getEtudiants() {
        return this.etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        if (this.etudiants != null) {
            this.etudiants.forEach(i -> i.setNomCariere(null));
        }
        if (etudiants != null) {
            etudiants.forEach(i -> i.setNomCariere(this));
        }
        this.etudiants = etudiants;
    }

    public Carriere etudiants(Set<Etudiant> etudiants) {
        this.setEtudiants(etudiants);
        return this;
    }

    public Carriere addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.setNomCariere(this);
        return this;
    }

    public Carriere removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.setNomCariere(null);
        return this;
    }

    public ConseillerOrientation getNomConseiller() {
        return this.nomConseiller;
    }

    public void setNomConseiller(ConseillerOrientation conseillerOrientation) {
        this.nomConseiller = conseillerOrientation;
    }

    public Carriere nomConseiller(ConseillerOrientation conseillerOrientation) {
        this.setNomConseiller(conseillerOrientation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carriere)) {
            return false;
        }
        return getId() != null && getId().equals(((Carriere) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carriere{" +
            "id=" + getId() +
            ", nomCariere='" + getNomCariere() + "'" +
            ", prerequisAcademiques='" + getPrerequisAcademiques() + "'" +
            "}";
    }
}
