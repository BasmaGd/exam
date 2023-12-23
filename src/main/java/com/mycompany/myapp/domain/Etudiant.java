package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etudiant.
 */
@Entity
@Table(name = "etudiant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "choix_de_carriere")
    private String choixDeCarriere;

    @Column(name = "progression_academique")
    private Float progressionAcademique;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "etudiants", "nomConseiller" }, allowSetters = true)
    private Carriere nomCariere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "etudiants", "coursRequis", "nomConseiller" }, allowSetters = true)
    private FiliereEtudes nomFiliere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "etudiants", "filiereEtudes" }, allowSetters = true)
    private Cours nomCours;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etudiant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Etudiant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Etudiant prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public Etudiant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChoixDeCarriere() {
        return this.choixDeCarriere;
    }

    public Etudiant choixDeCarriere(String choixDeCarriere) {
        this.setChoixDeCarriere(choixDeCarriere);
        return this;
    }

    public void setChoixDeCarriere(String choixDeCarriere) {
        this.choixDeCarriere = choixDeCarriere;
    }

    public Float getProgressionAcademique() {
        return this.progressionAcademique;
    }

    public Etudiant progressionAcademique(Float progressionAcademique) {
        this.setProgressionAcademique(progressionAcademique);
        return this;
    }

    public void setProgressionAcademique(Float progressionAcademique) {
        this.progressionAcademique = progressionAcademique;
    }

    public Carriere getNomCariere() {
        return this.nomCariere;
    }

    public void setNomCariere(Carriere carriere) {
        this.nomCariere = carriere;
    }

    public Etudiant nomCariere(Carriere carriere) {
        this.setNomCariere(carriere);
        return this;
    }

    public FiliereEtudes getNomFiliere() {
        return this.nomFiliere;
    }

    public void setNomFiliere(FiliereEtudes filiereEtudes) {
        this.nomFiliere = filiereEtudes;
    }

    public Etudiant nomFiliere(FiliereEtudes filiereEtudes) {
        this.setNomFiliere(filiereEtudes);
        return this;
    }

    public Cours getNomCours() {
        return this.nomCours;
    }

    public void setNomCours(Cours cours) {
        this.nomCours = cours;
    }

    public Etudiant nomCours(Cours cours) {
        this.setNomCours(cours);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etudiant)) {
            return false;
        }
        return getId() != null && getId().equals(((Etudiant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etudiant{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", choixDeCarriere='" + getChoixDeCarriere() + "'" +
            ", progressionAcademique=" + getProgressionAcademique() +
            "}";
    }
}
