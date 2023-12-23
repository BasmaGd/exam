import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './etudiant.reducer';

export const EtudiantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const etudiantEntity = useAppSelector(state => state.etudiant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etudiantDetailsHeading">
          <Translate contentKey="appApp.etudiant.detail.title">Etudiant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="appApp.etudiant.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="appApp.etudiant.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.prenom}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="appApp.etudiant.email">Email</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.email}</dd>
          <dt>
            <span id="choixDeCarriere">
              <Translate contentKey="appApp.etudiant.choixDeCarriere">Choix De Carriere</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.choixDeCarriere}</dd>
          <dt>
            <span id="progressionAcademique">
              <Translate contentKey="appApp.etudiant.progressionAcademique">Progression Academique</Translate>
            </span>
          </dt>
          <dd>{etudiantEntity.progressionAcademique}</dd>
          <dt>
            <Translate contentKey="appApp.etudiant.nomCariere">Nom Cariere</Translate>
          </dt>
          <dd>{etudiantEntity.nomCariere ? etudiantEntity.nomCariere.id : ''}</dd>
          <dt>
            <Translate contentKey="appApp.etudiant.nomFiliere">Nom Filiere</Translate>
          </dt>
          <dd>{etudiantEntity.nomFiliere ? etudiantEntity.nomFiliere.id : ''}</dd>
          <dt>
            <Translate contentKey="appApp.etudiant.nomCours">Nom Cours</Translate>
          </dt>
          <dd>{etudiantEntity.nomCours ? etudiantEntity.nomCours.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/etudiant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etudiant/${etudiantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtudiantDetail;
