package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FiliereEtudes.
 */
@Entity
@Table(name = "filiere_etudes")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FiliereEtudes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_filiere")
    private String nomFiliere;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomFiliere")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomCariere", "nomFiliere", "nomCours" }, allowSetters = true)
    private Set<Etudiant> etudiants = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_filiere_etudes__cours_requis",
        joinColumns = @JoinColumn(name = "filiere_etudes_id"),
        inverseJoinColumns = @JoinColumn(name = "cours_requis_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etudiants", "filiereEtudes" }, allowSetters = true)
    private Set<Cours> coursRequis = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "carrieres", "filiereEtudes" }, allowSetters = true)
    private ConseillerOrientation nomConseiller;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FiliereEtudes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFiliere() {
        return this.nomFiliere;
    }

    public FiliereEtudes nomFiliere(String nomFiliere) {
        this.setNomFiliere(nomFiliere);
        return this;
    }

    public void setNomFiliere(String nomFiliere) {
        this.nomFiliere = nomFiliere;
    }

    public String getDescription() {
        return this.description;
    }

    public FiliereEtudes description(String description) {
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
            this.etudiants.forEach(i -> i.setNomFiliere(null));
        }
        if (etudiants != null) {
            etudiants.forEach(i -> i.setNomFiliere(this));
        }
        this.etudiants = etudiants;
    }

    public FiliereEtudes etudiants(Set<Etudiant> etudiants) {
        this.setEtudiants(etudiants);
        return this;
    }

    public FiliereEtudes addEtudiant(Etudiant etudiant) {
        this.etudiants.add(etudiant);
        etudiant.setNomFiliere(this);
        return this;
    }

    public FiliereEtudes removeEtudiant(Etudiant etudiant) {
        this.etudiants.remove(etudiant);
        etudiant.setNomFiliere(null);
        return this;
    }

    public Set<Cours> getCoursRequis() {
        return this.coursRequis;
    }

    public void setCoursRequis(Set<Cours> cours) {
        this.coursRequis = cours;
    }

    public FiliereEtudes coursRequis(Set<Cours> cours) {
        this.setCoursRequis(cours);
        return this;
    }

    public FiliereEtudes addCoursRequis(Cours cours) {
        this.coursRequis.add(cours);
        return this;
    }

    public FiliereEtudes removeCoursRequis(Cours cours) {
        this.coursRequis.remove(cours);
        return this;
    }

    public ConseillerOrientation getNomConseiller() {
        return this.nomConseiller;
    }

    public void setNomConseiller(ConseillerOrientation conseillerOrientation) {
        this.nomConseiller = conseillerOrientation;
    }

    public FiliereEtudes nomConseiller(ConseillerOrientation conseillerOrientation) {
        this.setNomConseiller(conseillerOrientation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FiliereEtudes)) {
            return false;
        }
        return getId() != null && getId().equals(((FiliereEtudes) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FiliereEtudes{" +
            "id=" + getId() +
            ", nomFiliere='" + getNomFiliere() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
