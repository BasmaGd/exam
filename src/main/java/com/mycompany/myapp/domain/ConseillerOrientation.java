package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConseillerOrientation.
 */
@Entity
@Table(name = "conseiller_orientation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConseillerOrientation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_conseiller")
    private String nomConseiller;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "domaine_expertise")
    private String domaineExpertise;

    @Column(name = "coordonnees")
    private String coordonnees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomConseiller")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etudiants", "nomConseiller" }, allowSetters = true)
    private Set<Carriere> carrieres = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomConseiller")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etudiants", "coursRequis", "nomConseiller" }, allowSetters = true)
    private Set<FiliereEtudes> filiereEtudes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConseillerOrientation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomConseiller() {
        return this.nomConseiller;
    }

    public ConseillerOrientation nomConseiller(String nomConseiller) {
        this.setNomConseiller(nomConseiller);
        return this;
    }

    public void setNomConseiller(String nomConseiller) {
        this.nomConseiller = nomConseiller;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public ConseillerOrientation prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDomaineExpertise() {
        return this.domaineExpertise;
    }

    public ConseillerOrientation domaineExpertise(String domaineExpertise) {
        this.setDomaineExpertise(domaineExpertise);
        return this;
    }

    public void setDomaineExpertise(String domaineExpertise) {
        this.domaineExpertise = domaineExpertise;
    }

    public String getCoordonnees() {
        return this.coordonnees;
    }

    public ConseillerOrientation coordonnees(String coordonnees) {
        this.setCoordonnees(coordonnees);
        return this;
    }

    public void setCoordonnees(String coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Set<Carriere> getCarrieres() {
        return this.carrieres;
    }

    public void setCarrieres(Set<Carriere> carrieres) {
        if (this.carrieres != null) {
            this.carrieres.forEach(i -> i.setNomConseiller(null));
        }
        if (carrieres != null) {
            carrieres.forEach(i -> i.setNomConseiller(this));
        }
        this.carrieres = carrieres;
    }

    public ConseillerOrientation carrieres(Set<Carriere> carrieres) {
        this.setCarrieres(carrieres);
        return this;
    }

    public ConseillerOrientation addCarriere(Carriere carriere) {
        this.carrieres.add(carriere);
        carriere.setNomConseiller(this);
        return this;
    }

    public ConseillerOrientation removeCarriere(Carriere carriere) {
        this.carrieres.remove(carriere);
        carriere.setNomConseiller(null);
        return this;
    }

    public Set<FiliereEtudes> getFiliereEtudes() {
        return this.filiereEtudes;
    }

    public void setFiliereEtudes(Set<FiliereEtudes> filiereEtudes) {
        if (this.filiereEtudes != null) {
            this.filiereEtudes.forEach(i -> i.setNomConseiller(null));
        }
        if (filiereEtudes != null) {
            filiereEtudes.forEach(i -> i.setNomConseiller(this));
        }
        this.filiereEtudes = filiereEtudes;
    }

    public ConseillerOrientation filiereEtudes(Set<FiliereEtudes> filiereEtudes) {
        this.setFiliereEtudes(filiereEtudes);
        return this;
    }

    public ConseillerOrientation addFiliereEtudes(FiliereEtudes filiereEtudes) {
        this.filiereEtudes.add(filiereEtudes);
        filiereEtudes.setNomConseiller(this);
        return this;
    }

    public ConseillerOrientation removeFiliereEtudes(FiliereEtudes filiereEtudes) {
        this.filiereEtudes.remove(filiereEtudes);
        filiereEtudes.setNomConseiller(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConseillerOrientation)) {
            return false;
        }
        return getId() != null && getId().equals(((ConseillerOrientation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConseillerOrientation{" +
            "id=" + getId() +
            ", nomConseiller='" + getNomConseiller() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", domaineExpertise='" + getDomaineExpertise() + "'" +
            ", coordonnees='" + getCoordonnees() + "'" +
            "}";
    }
}
